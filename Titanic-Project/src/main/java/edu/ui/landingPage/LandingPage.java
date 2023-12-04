package edu.ui.landingPage;

import javax.swing.*;

/**
 * Creates the landing page
 *
 * This class creates the landing page for the cruise reservation application
 *
 * @author William Delano
 * @version 1.0
 * @see LandingPageController
 */
public abstract class LandingPage {
    private JFrame mainFrame;
    private JPanel headerPanel;
    private JLabel headerLabel;
    public void show() {
        mainFrame.setVisible(true);
    }

}