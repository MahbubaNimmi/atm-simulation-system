package com.mycompany.mainpanel;

import javax.swing.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.Random;
import java.awt.Color;

public class CreateAccountForm {
    public CreateAccountForm() {
        Color kalamata = new Color(70, 40, 60); // Kalamata color

        JFrame frame = new JFrame("Create New Account");
        frame.setSize(500, 550);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);
        frame.getContentPane().setBackground(kalamata);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 30, 150, 30);
        nameLabel.setForeground(Color.WHITE);
        frame.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(220, 30, 200, 30);
        frame.add(nameField);

        JLabel dobLabel = new JLabel("Date of Birth (yyyy-mm-dd):");
        dobLabel.setBounds(50, 80, 200, 30);
        dobLabel.setForeground(Color.WHITE);
        frame.add(dobLabel);

        JTextField dobField = new JTextField();
        dobField.setBounds(270, 80, 150, 30);
        frame.add(dobField);

        JLabel nidLabel = new JLabel("NID Number:");
        nidLabel.setBounds(50, 130, 150, 30);
        nidLabel.setForeground(Color.WHITE);
        frame.add(nidLabel);

        JTextField nidField = new JTextField();
        nidField.setBounds(220, 130, 200, 30);
        frame.add(nidField);

        JLabel accLabel = new JLabel("Account Number:");
        accLabel.setBounds(50, 180, 150, 30);
        accLabel.setForeground(Color.WHITE);
        frame.add(accLabel);

        JTextField accField = new JTextField();
        accField.setBounds(220, 180, 200, 30);
        accField.setEditable(false);
        frame.add(accField);

        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setBounds(50, 230, 150, 30);
        pinLabel.setForeground(Color.WHITE);
        frame.add(pinLabel);

        JTextField pinField = new JTextField();
        pinField.setBounds(220, 230, 200, 30);
        pinField.setEditable(false);
        frame.add(pinField);

        JButton generateBtn = new JButton("Generate Account");
        generateBtn.setBounds(150, 300, 200, 40);
        generateBtn.setBackground(Color.WHITE);
        generateBtn.setForeground(Color.BLACK);
        frame.add(generateBtn);

        JButton backButton = new JButton("Back");
        backButton.setBounds(150, 360, 200, 30);
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);
        frame.add(backButton);

        backButton.addActionListener(e -> {
            frame.dispose();
            Adminpanel.openAdminPanel();
        });

        generateBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String dobText = dobField.getText().trim();
                String nid = nidField.getText().trim();

                if (name.isEmpty() || dobText.isEmpty() || nid.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!nid.matches("\\d{7}")) {
                    JOptionPane.showMessageDialog(frame, "NID must be exactly 7 digits.", "Invalid NID",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (DataStore.nidExists(nid)) {
                    JOptionPane.showMessageDialog(frame, "Account with this NID already exists!", "Duplicate Account",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    LocalDate dob = LocalDate.parse(dobText);
                    LocalDate today = LocalDate.now();
                    int age = Period.between(dob, today).getYears();

                    if (age < 18) {
                        JOptionPane.showMessageDialog(frame, "You are underage, can't open an account.", "Underage",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    String accountNumber = generateAccountNumber();
                    String pin = generatePin();
                    accField.setText(accountNumber);
                    pinField.setText(pin);

                    UserData user = new UserData(name, nid, dobText, accountNumber, pin);
                    DataStore.addUser(user);

                    JOptionPane.showMessageDialog(frame,
                            "Account created successfully!\nAccount Number: " + accountNumber +
                                    "\nPIN: " + pin,
                            "Success", JOptionPane.INFORMATION_MESSAGE);

                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid date format. Use yyyy-mm-dd.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private String generateAccountNumber() {
        Random random = new Random();
        int accNum = 10000 + random.nextInt(90000);
        return String.valueOf(accNum);
    }

    private String generatePin() {
        Random random = new Random();
        int pin = 1000 + random.nextInt(9000);
        return String.valueOf(pin);
    }
}
