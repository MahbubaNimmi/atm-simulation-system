package com.mycompany.mainpanel;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class UpdateCustomerForm {
    private JFrame frame;
    private JTextField accField, nameField, dobField, nidField;
    private List<UserData> userList;

    public UpdateCustomerForm() {
        userList = DataStore.getUsers();

        frame = new JFrame("Update Customer Details");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);

        Color kalamata = new Color(71, 42, 82);
        frame.getContentPane().setBackground(kalamata);

        JLabel accLabel = new JLabel("Enter Account Number:");
        accLabel.setBounds(30, 30, 150, 30);
        accLabel.setForeground(Color.WHITE);
        frame.add(accLabel);

        accField = new JTextField();
        accField.setBounds(180, 30, 150, 30);
        frame.add(accField);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(130, 70, 120, 30);
        searchButton.setBackground(Color.WHITE);
        searchButton.setForeground(Color.BLACK);
        frame.add(searchButton);

        nameField = new JTextField();
        dobField = new JTextField();
        nidField = new JTextField();

        nameField.setBounds(150, 120, 180, 30);
        dobField.setBounds(150, 170, 180, 30);
        nidField.setBounds(150, 220, 180, 30);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 120, 100, 30);
        nameLabel.setForeground(Color.WHITE);
        frame.add(nameLabel);
        frame.add(nameField);

        JLabel dobLabel = new JLabel("DOB:");
        dobLabel.setBounds(50, 170, 120, 30);
        dobLabel.setForeground(Color.WHITE);
        frame.add(dobLabel);
        frame.add(dobField);

        JLabel nidLabel = new JLabel("NID Number:");
        nidLabel.setBounds(50, 220, 100, 30);
        nidLabel.setForeground(Color.WHITE);
        frame.add(nidLabel);
        frame.add(nidField);

        JButton updateBtn = new JButton("Update");
        updateBtn.setBounds(100, 280, 90, 30);
        updateBtn.setBackground(Color.WHITE);
        updateBtn.setForeground(Color.BLACK);
        frame.add(updateBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(200, 280, 90, 30);
        backBtn.setBackground(Color.WHITE);
        backBtn.setForeground(Color.BLACK);
        frame.add(backBtn);

        searchButton.addActionListener(e -> searchAndFillFields());

        updateBtn.addActionListener(e -> updateCustomer());

        backBtn.addActionListener(e -> {
            frame.dispose();
            Adminpanel.openAdminPanel();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void searchAndFillFields() {
        String accNo = accField.getText().trim();
        for (UserData user : userList) {
            if (user.getAccountNumber().equals(accNo)) {
                nameField.setText(user.getName());
                dobField.setText(user.getDob());
                nidField.setText(user.getNid());
                return;
            }
        }
        JOptionPane.showMessageDialog(frame, "Account not found.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void updateCustomer() {
        String accNo = accField.getText().trim();
        String name = nameField.getText().trim();
        String dobText = dobField.getText().trim();
        String nid = nidField.getText().trim();

        if (name.isEmpty() || nid.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            LocalDate dob = LocalDate.parse(dobText);
            int age = Period.between(dob, LocalDate.now()).getYears();
            if (age < 18) {
                JOptionPane.showMessageDialog(frame, "Customer must be 18 or older.", "Invalid DOB", JOptionPane.WARNING_MESSAGE);
                return;
            }

            for (UserData user : userList) {
                if (user.getAccountNumber().equals(accNo)) {
                    user.setName(name);
                    user.setDob(dobText);
                    user.setNid(nid);
                    JOptionPane.showMessageDialog(frame, "Customer details updated successfully.");
                    return;
                }
            }

            JOptionPane.showMessageDialog(frame, "Account not found.", "Error", JOptionPane.ERROR_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Invalid date format. Use yyyy-mm-dd.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
