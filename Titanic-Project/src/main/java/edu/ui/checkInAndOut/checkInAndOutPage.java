package edu.ui.checkInAndOut;

import edu.core.users.User;

import javax.swing.*;
import java.awt.*;

public class checkInAndOutPage {
    private JFrame mainFrame;
    private JPanel headerPanel;
    private JLabel headerLabel;
    private User account;
    private void prepareGUI() {
        mainFrame = new JFrame("");
        mainFrame.setSize(1000, 700);
        mainFrame.setLayout(new BorderLayout());

        headerLabel = new JLabel("", JLabel.CENTER);
        headerPanel = new JPanel(new GridLayout(2, 4));

        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel middlePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    }
}
