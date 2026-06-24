
package com.mycompany.mainpanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Customerpanel {
    public Customerpanel(UserData user) {
        JFrame frame = new JFrame("Customer Panel");
        frame.setSize(400, 330);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        frame.getContentPane().setBackground(new Color(200, 162, 200));

        JLabel label = new JLabel("Welcome, " + user.getName() + "!");
        label.setBounds(120, 20, 200, 30);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        frame.add(label);

        JButton depositBtn = createStyledButton("Deposit", 120, 60, 150, 30);
        JButton withdrawBtn = createStyledButton("Withdraw", 120, 100, 150, 30);
        JButton balanceBtn = createStyledButton("Check Balance", 120, 140, 150, 30);
        JButton transferBtn = createStyledButton("Transfer Money", 120, 180, 150, 30);
        JButton miniStatementBtn = createStyledButton("Mini Statement", 120, 220, 150, 30);
        JButton logoutBtn = createStyledButton("Logout", 120, 260, 150, 30);

        frame.add(depositBtn);
        frame.add(withdrawBtn);
        frame.add(balanceBtn);
        frame.add(transferBtn);
        frame.add(miniStatementBtn);
        frame.add(logoutBtn);

        depositBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Deposit depositDialog = new Deposit(frame, user.getAccountNumber(),
                        DataStore.getUsers());
                depositDialog.setVisible(true);
            }
        });

        withdrawBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Withdrawal withdrawalDialog = new Withdrawal(frame, user.getAccountNumber(),
                        DataStore.getUsers());
                withdrawalDialog.setVisible(true);
            }
        });

        transferBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TMoney transferDialog = new TMoney(frame, user.getAccountNumber(),
                        DataStore.getUsers());
                transferDialog.setVisible(true);
            }
        });

        balanceBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double balance = user.getBalance();
                JOptionPane.showMessageDialog(frame, "Current Balance: " + balance + " Taka");
            }
        });

        miniStatementBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MiniStatement(frame, user.getAccountNumber());
            }
        });

        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Mainpanel.openMainPanel();
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JButton createStyledButton(String text, int x, int y, int width, int height) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, width, height);
        btn.setBackground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        return btn;
    }

    public static void showInvalidCredentialDialog(Component parent) {
        JLabel errorLabel = new JLabel("Invalid credentials!");
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        JOptionPane.showMessageDialog(parent, errorLabel, "Login Failed", JOptionPane.ERROR_MESSAGE);
    }
}
