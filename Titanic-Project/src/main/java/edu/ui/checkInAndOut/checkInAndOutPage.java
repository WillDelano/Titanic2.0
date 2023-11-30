package edu.ui.checkInAndOut;

import edu.core.users.User;

import javax.swing.*;
import java.awt.*;

public class checkInAndOutPage {
    private JFrame mainFrame;
    private JPanel headerPanel;
    private JLabel headerLabel;
    private User account;
    public class MyDialog extends JDialog {
        private JTable table;

        public MyDialog(JTable owner) {
            super(SwingUtilities.windowForComponent(owner));
            table = owner;
            createGUI();
        }

        private void createGUI() {
            setPreferredSize(new Dimension(600, 400));
            setTitle(getClass().getSimpleName());
            JFrame listFrame = new JFrame();
            JPanel listPane = new JPanel();
            JButton addButton = new JButton("Add row");
            addButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            listPane.setLayout(new BoxLayout(listPane, BoxLayout.Y_AXIS));
            JLabel label = new JLabel("Cars Selected:");
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            listPane.add(label);
            int[] rows = table.getSelectedRows();
            int rowCount = table.getSelectedRowCount();
            JLabel dataLabel = null;
            if (rowCount <= 0) {
                dataLabel = new JLabel("no row selected");

            } else {
                dataLabel = new JLabel("cannot duplicate multiple rows”");
            }
            dataLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            listPane.add(dataLabel);

            add(listPane);
            pack();
            listFrame.add(listPane);
            listFrame.setVisible(true);
            listFrame.pack();
            setLocationRelativeTo(getParent());
            listPane.setVisible(true);
        }

        @Override
        public void dispose() {
            super.dispose();
        }
    }
}
