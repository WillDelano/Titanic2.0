package edu.ui.cruiseDetails;

import edu.core.cruise.Country;
import edu.core.cruise.Cruise;
import edu.core.cruise.CruiseSearch;
import edu.databaseAccessors.CruiseDatabase;
import edu.ui.landingPage.LandingPage;
import edu.ui.roomDetails.BrowseRoomPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    JComboBox<String> departureMonth;

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
        //cruiseFrame.add(titleLabel, BorderLayout.NORTH);
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
        JScrollPane listScrollPane = new JScrollPane(cruiseList);
        cruiseFrame.add(listScrollPane, BorderLayout.CENTER);


        selectButton = new JButton("View Details");
        selectButton.addActionListener(this::handleCruiseSelection);
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
                new BrowseRoomPage(selectedCruise.getName()); // Assuming BrowseRoomPage takes a Cruise name
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
            //applyFilters();
            List <Cruise> list = SearchCruises.findCruises(searchTextField.getText());

            //currentCruises = new ArrayList<>(list);
            cruiseFrame.remove(listScrollPane);
            DefaultListModel<String> model = getAllCruiseNames(list);
            cruiseList = new JList<>(model);
            listScrollPane = new JScrollPane(cruiseList);
            listScrollPane.getViewport().revalidate();
            listScrollPane.getViewport().repaint();
            cruiseFrame.add(listScrollPane, BorderLayout.CENTER);


            cruiseFrame.revalidate();
            cruiseFrame.repaint();
        });

        searchMenu.add(searchTextField);
        searchMenu.add(searchButton);
        searchMenu.add(optionsButton);
    }

    private void generateFilterPanel(){
        filterPanel = new JPanel();
        applyButton = new JButton("apply");

        Vector<String> months = getMonths();
        departureMonth = new JComboBox<>(months);

        applyButton.addActionListener( e->{
            //applyFilters();
        });

        filterPanel.add(departureMonth);
        filterPanel.add(applyButton);
    }

    private void filterPanelVisibility(){
        if(!optionVisible) {
            northPanel.add(filterPanel, BorderLayout.CENTER);
            optionVisible = true;
            //roomFrame.add(filterPanel, BorderLayout.NORTH);
            cruiseFrame.revalidate();
        } else {
            optionVisible = false;
            //roomFrame.remove(filterPanel);
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
    private Vector<String> getMonths(){
        Vector<String> allMonths = new Vector<>();
        List<Cruise> cruises = CruiseDatabase.getAllCruises();

        for(Cruise cruise: cruises){
            List<Country> currCountries = cruise.getTravelPath();
            for(Country country: currCountries){
                if(!allMonths.contains(country.getName())){
                    allMonths.add(country.getName());
                }
            }
        }
        Collections.sort(allMonths);

        return allMonths;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SelectCruisePage(null));
    }
}
