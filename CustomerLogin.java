package com.mycompany.mainpanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CustomerLogin {
    public CustomerLogin() {
        JFrame frame = new JFrame("Customer Login");
        frame.setSize(300, 250);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);

        // Dark purple background instead of dark gray
        Color darkPurple = new Color(70, 40, 60);
        frame.getContentPane().setBackground(darkPurple);

        JLabel accLabel = new JLabel("Account Number:");
        accLabel.setBounds(30, 30, 120, 30);
        accLabel.setForeground(Color.WHITE);
        accLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        frame.add(accLabel);

        JTextField accField = new JTextField();
        accField.setBounds(150, 30, 100, 30);
        accField.setBackground(Color.WHITE);      // white input background
        accField.setForeground(Color.BLACK);      // black input text
        accField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        frame.add(accField);

        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setBounds(30, 80, 120, 30);
        pinLabel.setForeground(Color.WHITE);
        pinLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        frame.add(pinLabel);

        JPasswordField pinField = new JPasswordField();
        pinField.setBounds(150, 80, 100, 30);
        pinField.setBackground(Color.WHITE);     // white input background
        pinField.setForeground(Color.BLACK);     // black input text
        pinField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        frame.add(pinField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(90, 140, 100, 30);
        loginBtn.setBackground(darkPurple);      // dark purple button background
        loginBtn.setForeground(Color.WHITE);     // white button text
        loginBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        frame.add(loginBtn);

        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String acc = accField.getText().trim();
                String pin = new String(pinField.getPassword()).trim();
                UserData user = DataStore.findUser(acc, pin);
                if (user != null) {
                    frame.dispose();
                    new Customerpanel(user);
                } else {
                    JLabel errorLabel = new JLabel("Invalid credentials!");
                    errorLabel.setForeground(Color.RED);
                    errorLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
                    JOptionPane.showMessageDialog(frame, errorLabel, "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
