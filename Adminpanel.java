package com.mycompany.mainpanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class Adminpanel {
    public static void openAdminPanel() {
        new Adminpanel(false);
    }

    public Adminpanel() {
        this(true);
    }

    public Adminpanel(boolean requireLogin) {
        if (requireLogin) {
            int attempts = 0;
            boolean success = false;

            while (attempts < 3) {
                String password = showAdminPasswordDialog();

                if (password == null) return;

                if (password.equals("12345")) {
                    JOptionPane.showMessageDialog(null,
                            createColoredLabel("Correct password!!!", Color.BLUE), "Access Granted",
                            JOptionPane.INFORMATION_MESSAGE);
                    success = true;
                    break;
                } else {
                    attempts++;
                    if (attempts < 3) {
                        JOptionPane.showMessageDialog(null,
                                createColoredLabel("Incorrect! Try again", Color.RED), "Login Failed",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

            if (!success) {
                JOptionPane.showMessageDialog(null,
                        createColoredLabel("Intruder Alert!!!", Color.RED), "Security Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        JFrame frame = new JFrame("Admin Panel");
        frame.setSize(300, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(200, 162, 200));

        JLabel label = new JLabel("Welcome, Admin!");
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        int labelWidth = 200;
        int labelHeight = 30;
        int frameWidth = 300;
        int labelX = (frameWidth - labelWidth) / 2;
        label.setBounds(labelX, 20, labelWidth, labelHeight);
        frame.add(label);

        int buttonX = 60;
        int buttonWidth = 180;
        int buttonHeight = 35;
        int spacing = 15;
        int startY = 70;

        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.setBounds(buttonX, startY, buttonWidth, buttonHeight);
        createAccountButton.setBackground(Color.WHITE);
        frame.add(createAccountButton);
        createAccountButton.addActionListener(e -> {
            frame.dispose();
            new CreateAccountForm();
        });

        JButton updateCustomerButton = new JButton("Update Customer");
        updateCustomerButton.setBounds(buttonX, startY + (buttonHeight + spacing), buttonWidth, buttonHeight);
        updateCustomerButton.setBackground(Color.WHITE);
        frame.add(updateCustomerButton);
        updateCustomerButton.addActionListener(e -> {
            frame.dispose();
            new UpdateCustomerForm();
        });

        JButton searchAccountButton = new JButton("Search Account");
        searchAccountButton.setBounds(buttonX, startY + 2 * (buttonHeight + spacing), buttonWidth, buttonHeight);
        searchAccountButton.setBackground(Color.WHITE);
        frame.add(searchAccountButton);
        searchAccountButton.addActionListener(e -> {
            new SearchAccount(frame).setVisible(true);
        });

        JButton transactionHistoryButton = new JButton("Transaction History");
        transactionHistoryButton.setBounds(buttonX, startY + 3 * (buttonHeight + spacing), buttonWidth, buttonHeight);
        transactionHistoryButton.setBackground(Color.WHITE);
        frame.add(transactionHistoryButton);
        transactionHistoryButton.addActionListener(e -> {
            showTransactionTable(frame);
        });

        JButton deleteAccountButton = new JButton("Delete Account");
        deleteAccountButton.setBounds(buttonX, startY + 4 * (buttonHeight + spacing), buttonWidth, buttonHeight);
        deleteAccountButton.setBackground(Color.WHITE);
        frame.add(deleteAccountButton);
        deleteAccountButton.addActionListener(e -> {
            frame.dispose();
            new DeleteAccountForm();
        });

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(buttonX, startY + 5 * (buttonHeight + spacing), buttonWidth, buttonHeight);
        logoutButton.setBackground(Color.WHITE);
        frame.add(logoutButton);
        logoutButton.addActionListener(e -> {
            frame.dispose();
            Mainpanel.openMainPanel();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JLabel createColoredLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    private String showAdminPasswordDialog() {
        Color mattePurple = new Color(128, 90, 150); // matte purple
        Color mattePurpleHover = new Color(148, 110, 170); // lighter purple on hover
        Color oldButtonBg = UIManager.getColor("Button.background");
        Color oldButtonFg = UIManager.getColor("Button.foreground");
        Font oldButtonFont = UIManager.getFont("Button.font");

        try {
            UIManager.put("Button.background", mattePurple);
            UIManager.put("Button.foreground", Color.WHITE);
            UIManager.put("Button.font", new Font("SansSerif", Font.BOLD, 12));

            UIManager.put("Panel.background", mattePurple);
            UIManager.put("OptionPane.background", mattePurple);
            UIManager.put("OptionPane.messageForeground", Color.WHITE);

            JPanel panel = new JPanel();
            panel.setBackground(mattePurple);
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            JLabel label = new JLabel("Enter Admin Password:");
            label.setFont(new Font("SansSerif", Font.BOLD, 14));
            label.setForeground(Color.WHITE);
            label.setAlignmentX(JComponent.CENTER_ALIGNMENT);

            JPasswordField passwordField = new JPasswordField(15);
            passwordField.setMaximumSize(passwordField.getPreferredSize());
            passwordField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            passwordField.setBackground(Color.WHITE);
            passwordField.setForeground(Color.DARK_GRAY);

            panel.add(label);
            panel.add(Box.createVerticalStrut(10));
            panel.add(passwordField);

            JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE,
                    JOptionPane.OK_CANCEL_OPTION);

            JDialog dialog = optionPane.createDialog(null, "Login");

            dialog.addComponentListener(new java.awt.event.ComponentAdapter() {
                @Override
                public void componentShown(java.awt.event.ComponentEvent e) {
                    for (Component c : optionPane.getComponents()) {
                        if (c instanceof JPanel) {
                            for (Component b : ((JPanel) c).getComponents()) {
                                if (b instanceof JButton) {
                                    JButton btn = (JButton) b;
                                    btn.setBackground(mattePurple);
                                    btn.setForeground(Color.WHITE);
                                    btn.setFocusPainted(false);
                                    btn.addMouseListener(new java.awt.event.MouseAdapter() {
                                        @Override
                                        public void mouseEntered(java.awt.event.MouseEvent evt) {
                                            btn.setBackground(mattePurpleHover);
                                        }
                                        @Override
                                        public void mouseExited(java.awt.event.MouseEvent evt) {
                                            btn.setBackground(mattePurple);
                                        }
                                    });
                                }
                            }
                        }
                    }
                }
            });

            dialog.setVisible(true);

            Object value = optionPane.getValue();
            if (value != null && value.equals(JOptionPane.OK_OPTION)) {
                return new String(passwordField.getPassword());
            }
            return null;

        } finally {
            UIManager.put("Button.background", oldButtonBg);
            UIManager.put("Button.foreground", oldButtonFg);
            UIManager.put("Button.font", oldButtonFont);
            UIManager.put("Panel.background", null);
            UIManager.put("OptionPane.background", null);
            UIManager.put("OptionPane.messageForeground", null);
        }
    }

    private void showTransactionTable(JFrame parentFrame) {
        String[] columnNames = {"Date/Time", "Account Number", "Type", "Amount"};
        List<TransactionStorage.Transaction> txList = TransactionStorage.getTransactions();

        Object[][] data = new Object[txList.size()][4];
        for (int i = 0; i < txList.size(); i++) {
            TransactionStorage.Transaction tx = txList.get(i);
            data[i][0] = tx.dateTime;
            data[i][1] = tx.accountNumber;
            data[i][2] = tx.type;
            data[i][3] = tx.amount;
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        
        // Set table background to mauve
        table.setBackground(new Color(224, 176, 255));
        table.setFillsViewportHeight(true);
        
        // Optional: set header color for better style contrast
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(180, 120, 200));
        header.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);

        JFrame tableFrame = new JFrame("Transaction History");
        tableFrame.setSize(600, 400);
        tableFrame.setLayout(null);

        // Set kalamata background color here
        tableFrame.getContentPane().setBackground(new Color(100, 65, 115));

        scrollPane.setBounds(20, 20, 550, 280);
        tableFrame.add(scrollPane);

        JButton backButton = new JButton("Back");
        backButton.setBounds(250, 320, 100, 30);
        backButton.setBackground(Color.WHITE);
        tableFrame.add(backButton);
        backButton.addActionListener(e -> {
            tableFrame.dispose();
            new Adminpanel(false);
        });

        tableFrame.setLocationRelativeTo(null);
        parentFrame.dispose();
        tableFrame.setVisible(true);
    }
}
