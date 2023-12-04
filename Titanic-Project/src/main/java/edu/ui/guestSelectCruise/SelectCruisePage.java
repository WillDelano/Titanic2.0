package edu.ui.guestSelectCruise;

import edu.core.cruise.Country;
import edu.core.cruise.Cruise;
import edu.core.cruise.CruiseSearch;
import edu.databaseAccessors.CruiseDatabase;
import edu.ui.landingPage.LandingPage;
import edu.ui.guestBrowseRooms.BrowseRoomPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * Controller for displaying and selecting a cruise on the ui
 *
 * This class allows a user to browse and select cruises
 *
 * @author Vincent Dinh
 * @version 1.0
 * @see Cruise, CruiseDatabase, CruiseDetailsPage
 */
public class SelectCruisePage {
    private JFrame cruiseFrame;
    private JLabel titleLabel;
    private JList<String> cruiseList;
    private JPanel northPanel, filterPanel;
    JScrollPane listScrollPane;
    private JButton selectButton, backButton, searchButton, optionsButton,applyButton;
    private LandingPage landingPage;
    private JTextArea detailsTextArea;
    private JMenuBar searchMenu;
    JComboBox<String> allDestinations, departureCountry;

    private JTextField searchTextField;
    private CruiseSearch SearchCruises;
    private List<Cruise> cruisesFromDatabase;
    private boolean optionVisible = false;

    public SelectCruisePage(LandingPage landingPage) {
        this.landingPage = landingPage;
        prepareGUI();
    }
    private void prepareGUI() {
        cruiseFrame = new JFrame("Select a Cruise");
        cruiseFrame.setSize(1000, 700);
        cruiseFrame.setLayout(new BorderLayout());

        titleLabel = new JLabel("Available Cruises", JLabel.CENTER);
        JPanel titlePanel = new JPanel();
        titlePanel.add(titleLabel);

        northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
        northPanel.add(titlePanel, BorderLayout.NORTH);

        generateSearchMenu();
        generateFilterPanel();

        cruisesFromDatabase = CruiseDatabase.getAllCruises();
        SearchCruises = new CruiseSearch(cruisesFromDatabase);

        DefaultListModel<String> model = getAllCruiseNames(cruisesFromDatabase);

        cruiseList = new JList<>(model);
        listScrollPane = new JScrollPane(cruiseList);
        cruiseFrame.add(listScrollPane, BorderLayout.CENTER);

        selectButton = new JButton("View Details");
        selectButton.addActionListener(e -> {
            handleCruiseSelection(e);
        });
        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            cruiseFrame.dispose();
            if (landingPage != null) landingPage.show();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(selectButton);
        buttonPanel.add(backButton);

        cruiseFrame.add(northPanel, BorderLayout.NORTH);
        cruiseFrame.add(buttonPanel, BorderLayout.SOUTH);
        cruiseFrame.setJMenuBar(searchMenu);
        cruiseFrame.setVisible(true);
    }

    private void handleCruiseSelection(ActionEvent e) {
        String selectedCruiseName = cruiseList.getSelectedValue();
        if (selectedCruiseName != null) {
            Cruise selectedCruise = CruiseDatabase.getCruise(selectedCruiseName);
            if (selectedCruise != null) {
                new BrowseRoomPage(this, selectedCruise.getName());
                cruiseFrame.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(cruiseFrame, "Cruise details not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(cruiseFrame, "Please select a cruise first.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
    private void showMessage(String message) {
        JOptionPane.showMessageDialog(cruiseFrame, message);
    }
    private void generateSearchMenu(){
        searchMenu = new JMenuBar();
        searchMenu.setPreferredSize(new Dimension(1000, 30));
        searchTextField = new JTextField();
        searchButton = new JButton("search");
        optionsButton = new JButton("options");

        optionsButton.addActionListener(e -> {
            filterPanelVisibility();
        });
        searchButton.addActionListener(e -> {
            setFilters();
            List <Cruise> list = SearchCruises.findCruises(searchTextField.getText());
            updateCruiseListView(list);
        });

        searchMenu.add(searchTextField);
        searchMenu.add(searchButton);
        searchMenu.add(optionsButton);
    }

    private void generateFilterPanel(){
        filterPanel = new JPanel();
        applyButton = new JButton("apply");

        JLabel destinationLabel = new JLabel("Destination ");
        Vector<String> countries = getAllDestinations();
        allDestinations = new JComboBox<>(countries);

        JLabel departureLabel = new JLabel("Departure Port ");
        Vector<String> firstCountries = getFirstCountries();
        departureCountry = new JComboBox<>(firstCountries);

        applyButton.addActionListener( e->{
            setFilters();
            List<Cruise> list = new ArrayList<>(cruisesFromDatabase);
            list = SearchCruises.sortAndFilterRooms(list);

            updateCruiseListView(list);
        });

        filterPanel.add(destinationLabel);
        filterPanel.add(allDestinations);
        filterPanel.add(departureLabel);
        filterPanel.add(departureCountry);
        filterPanel.add(applyButton);
    }

    private void filterPanelVisibility(){
        if(!optionVisible) {
            northPanel.add(filterPanel, BorderLayout.CENTER);
            optionVisible = true;
            cruiseFrame.revalidate();
        } else {
            optionVisible = false;
            northPanel.remove(filterPanel);
            cruiseFrame.revalidate();
        }
    }

    private DefaultListModel<String> getAllCruiseNames(List<Cruise> cruises){
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Cruise cruise : cruises) {
            if (!model.contains(cruise.getName())) {
                model.addElement(cruise.getName());
            }
        }
        return model;
    }
    private Vector<String> getAllDestinations(){
        Vector<String> allCountries = new Vector<>();
        List<Cruise> cruises = CruiseDatabase.getAllCruises();

        for(Cruise cruise: cruises){
            List<Country> currCountries = cruise.getTravelPath();
            for(int i = 1; i < currCountries.size(); ++i){
                if(!allCountries.contains(currCountries.get(i).getName())){
                    allCountries.add(currCountries.get(i).getName());
                }
            }
        }
        Collections.sort(allCountries);
        allCountries.add(0,"Any");

        return allCountries;
    }
    private Vector<String> getFirstCountries(){
        Vector<String> firstCountries = new Vector<>();
        List<Cruise> cruises = CruiseDatabase.getAllCruises();
        String currCountry;

        for(Cruise cruise: cruises){
            currCountry = cruise.getTravelPath().get(0).getName();
            if(!firstCountries.contains(currCountry)) {
                firstCountries.add(currCountry);
            }
        }
        Collections.sort(firstCountries);
        firstCountries.add(0,"Any");

        return firstCountries;
    }

    private void setFilters(){
        //destination selection
        if(String.valueOf(allDestinations.getSelectedItem()).equals("Any")) {
            SearchCruises.setPreferredDestination("");

        } else {
            SearchCruises.setPreferredDestination(String.valueOf(allDestinations.getSelectedItem()));
        }

        //departure selection
        if(String.valueOf(departureCountry.getSelectedItem()).equals("Any")) {
            SearchCruises.setPreferredDeparture("");

        } else {
            SearchCruises.setPreferredDeparture(String.valueOf(departureCountry.getSelectedItem()));
        }

    }

    private void updateCruiseListView(List<Cruise> list) {
        cruiseFrame.remove(listScrollPane);
        DefaultListModel<String> model = getAllCruiseNames(list);
        cruiseList = new JList<>(model);
        listScrollPane = new JScrollPane(cruiseList);
        listScrollPane.getViewport().revalidate();
        listScrollPane.getViewport().repaint();
        cruiseFrame.add(listScrollPane, BorderLayout.CENTER);

        cruiseFrame.revalidate();
        cruiseFrame.repaint();
    }

    public void show() {
        cruiseFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SelectCruisePage(null));
    }
}