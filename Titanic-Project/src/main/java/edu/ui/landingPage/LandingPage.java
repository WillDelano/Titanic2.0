package edu.ui.landingPage;

import edu.core.users.CurrentGuest;
import edu.core.users.Guest;
import edu.core.users.User;
import edu.uniqueID.UniqueID;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

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