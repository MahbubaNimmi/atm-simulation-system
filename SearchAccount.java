package com.mycompany.mainpanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SearchAccount extends JDialog {
    private JTextField searchField;
    private JButton searchButton;
    private JTable resultTable;
    private DefaultTableModel tableModel;

    public SearchAccount(Frame owner) {
        super(owner, "Search Account", true);
        setLayout(new BorderLayout());
        setSize(500, 300);
        setLocationRelativeTo(owner);

        Color kalamata = new Color(71, 42, 82);
        getContentPane().setBackground(kalamata);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(kalamata);

        JLabel promptLabel = new JLabel("Enter Account Number:");
        promptLabel.setForeground(Color.WHITE);
        topPanel.add(promptLabel);

        searchField = new JTextField(20);
        topPanel.add(searchField);

        searchButton = new JButton("Search");
        searchButton.setBackground(Color.WHITE);
        searchButton.setForeground(Color.BLACK);
        topPanel.add(searchButton);

        add(topPanel, BorderLayout.NORTH);

        // Table with columns for showing user details
        String[] columns = {"Name", "NID", "DOB", "Account Number", "Balance"};
        tableModel = new DefaultTableModel(columns, 0);
        resultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.getViewport().setBackground(kalamata);
        add(scrollPane, BorderLayout.CENTER);

        searchButton.addActionListener(e -> performSearch());

        // Close on window close
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void performSearch() {
        String accNum = searchField.getText().trim();
        tableModel.setRowCount(0); // Clear previous results

        if (accNum.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an account number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<UserData> users = DataStore.getUsers();
        boolean found = false;

        for (UserData user : users) {
            if (user.getAccountNumber().equals(accNum)) {
                Object[] row = {
                    user.getName(),
                    user.getNid(),
                    user.getDob(),
                    user.getAccountNumber(),
                    user.getBalance()
                };
                tableModel.addRow(row);
                found = true;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(this, "No account found with that number.", "Not Found", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
