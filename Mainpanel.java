package com.mycompany.mainpanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Mainpanel {
    public static void main(String[] args) {
        Color chineseViolet = new Color(133, 96, 136); 
        Color oldButtonBg = UIManager.getColor("Button.background");
        Color oldButtonFg = UIManager.getColor("Button.foreground");

        try {
           
            UIManager.put("Button.background", Color.WHITE);
            UIManager.put("Button.foreground", Color.BLACK);

            
            JPanel panel = new JPanel();
            panel.setBackground(chineseViolet);
            JLabel messageLabel = new JLabel("Hi!! Welcome to our ATM simulation system. Please click OK to continue.");
            messageLabel.setForeground(Color.WHITE); // White text
            panel.add(messageLabel);

            JOptionPane.showMessageDialog(
                    null,
                    panel,
                    "Welcome",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } finally {
            // Restore default button colors
            UIManager.put("Button.background", oldButtonBg);
            UIManager.put("Button.foreground", oldButtonFg);
        }

        openMainPanel();
    }

    public static void openMainPanel() {
        JFrame frame = new JFrame("Main Panel");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        frame.getContentPane().setBackground(new Color(216, 191, 216)); // Thistle background

        JButton adminButton = new JButton("Admin Panel");
        adminButton.setBounds(80, 40, 140, 30);
        adminButton.setBackground(Color.WHITE);
        frame.add(adminButton);

        JButton customerButton = new JButton("Customer Panel");
        customerButton.setBounds(80, 90, 140, 30);
        customerButton.setBackground(Color.WHITE);
        frame.add(customerButton);

        adminButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new Adminpanel();
            }
        });

        customerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new CustomerLogin();
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
