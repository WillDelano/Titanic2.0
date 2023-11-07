package edu.ui.manageUsers;

import javax.swing.*;
import java.awt.*;

public class ManageUsersPage {
    private JFrame mainFrame;
    public ManageUsersPage(){createGUI();}

    public void createGUI(){
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        mainFrame = new JFrame("Manage Users");
        mainFrame.setSize(1000, 700);
        mainFrame.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JButton createAgentButton = new JButton("Create Travel Agent");
        createAgentButton.setPreferredSize(new Dimension(400,100));
        JButton editUserProfileButton = new JButton("Edit Users");
        editUserProfileButton.setPreferredSize(new Dimension(400, 100));

        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(createAgentButton);
        buttonPanel.add(Box.createVerticalStrut(20)); // vertical spacing between buttons
        buttonPanel.add(editUserProfileButton);
        buttonPanel.add(Box.createVerticalGlue());

        mainFrame.add(new JLabel(), BorderLayout.PAGE_START);
        mainFrame.add(buttonPanel, BorderLayout.CENTER);
        mainFrame.add(new JLabel(), BorderLayout.PAGE_END);

        mainFrame.setVisible(true);
    }

    public static void main(String[] args){
        ManageUsersPage manageUsersPage = new ManageUsersPage();
    }
}
