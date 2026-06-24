package com.mycompany.mainpanel;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class DeleteAccountForm {
    private JFrame frame;
    private JTextField accountNumberField;
    private JPasswordField specialPasswordField;

    public DeleteAccountForm() {
        frame = new JFrame("Delete Account");
        frame.setSize(350, 220);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Color kalamata = new Color(76, 45, 61);
        frame.getContentPane().setBackground(kalamata);

        JLabel label = new JLabel("Delete Account");
        label.setBounds(110, 10, 150, 30);
        label.setForeground(Color.WHITE);
        frame.add(label);

        JLabel accLabel = new JLabel("Account Number:");
        accLabel.setBounds(30, 50, 120, 25);
        accLabel.setForeground(Color.WHITE);
        frame.add(accLabel);

        accountNumberField = new JTextField();
        accountNumberField.setBounds(150, 50, 150, 25);
        frame.add(accountNumberField);

        JLabel passLabel = new JLabel("Special Password:");
        passLabel.setBounds(30, 90, 120, 25);
        passLabel.setForeground(Color.WHITE);
        frame.add(passLabel);

        specialPasswordField = new JPasswordField();
        specialPasswordField.setBounds(150, 90, 150, 25);
        frame.add(specialPasswordField);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(80, 140, 80, 30);
        deleteButton.setBackground(Color.WHITE);
        deleteButton.setForeground(Color.BLACK);
        frame.add(deleteButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(180, 140, 80, 30);
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setForeground(Color.BLACK);
        frame.add(cancelButton);

        deleteButton.addActionListener(e -> deleteAccount());
        cancelButton.addActionListener(e -> {
            frame.dispose();
            new Adminpanel(false);
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void deleteAccount() {
        String accNum = accountNumberField.getText().trim();
        String specialPass = new String(specialPasswordField.getPassword());

        if (accNum.isEmpty() || specialPass.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!specialPass.equals("abcd")) {
            JOptionPane.showMessageDialog(frame, "Incorrect special password!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Find user in DataStore
        UserData userToDelete = null;
        for (UserData user : DataStore.userList) {
            if (user.getAccountNumber().equals(accNum)) {
                userToDelete = user;
                break;
            }
        }

        if (userToDelete == null) {
            JOptionPane.showMessageDialog(frame, "Account not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(frame,
                "Are you sure you want to delete account " + accNum + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            DataStore.userList.remove(userToDelete);
            DataStore.saveUsers();
            JOptionPane.showMessageDialog(frame, "Account deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
            new Adminpanel(false);
        }
    }
}
