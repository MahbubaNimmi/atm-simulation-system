package com.mycompany.mainpanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TMoney extends JDialog {
    private JTextField accNo, moneyT;
    private JButton transButton, cancelButton;
    private List<UserData> userDataList;
    private String senderAccount;
    private Validate v;

    public TMoney(Frame owner, String senderAccount, List<UserData> userDataList) {
        super(owner, "Transfer Money", true);
        this.senderAccount = senderAccount;
        this.userDataList = userDataList;
        v = new Validate();

        Color sunsetPurple = new Color(100, 65, 110);
        getContentPane().setBackground(sunsetPurple);

        setLayout(new GridLayout(4, 2, 10, 10));
        accNo = new JTextField();
        moneyT = new JTextField();
        transButton = new JButton("Transfer Money");
        cancelButton = new JButton("Cancel");

        // Set buttons colors
        transButton.setBackground(Color.WHITE);
        transButton.setForeground(Color.BLACK);
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setForeground(Color.BLACK);

        // Labels with white text
        JLabel accLabel = new JLabel("Enter Receiver's Account Number:");
        accLabel.setForeground(Color.WHITE);
        JLabel amountLabel = new JLabel("Enter Amount to Transfer:");
        amountLabel.setForeground(Color.WHITE);

        add(accLabel);
        add(accNo);
        add(amountLabel);
        add(moneyT);
        add(transButton);
        add(cancelButton);

        transButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String receiverAcc = accNo.getText();
                String amountStr = moneyT.getText();

                if (!v.isAccountNumber(receiverAcc)) {
                    JOptionPane.showMessageDialog(null, "Invalid account number format.");
                    return;
                }

                if (!UserData.existsAccNo(receiverAcc, userDataList)) {
                    JOptionPane.showMessageDialog(null, "Account number not found.");
                    return;
                }

                if (!v.isAmount(amountStr)) {
                    JOptionPane.showMessageDialog(null, "Invalid amount entered.");
                    return;
                }

                double senderBalance = UserData.getBalance(senderAccount, userDataList);
                double amount = Double.parseDouble(amountStr);
                double receiverBalance = UserData.getBalance(receiverAcc, userDataList);

                if (senderBalance - amount < 1000) {
                    JOptionPane.showMessageDialog(null, "You must maintain at least 1000 Taka in your account.");
                    return;
                }

                // Update balances
                UserData.setBalance(senderAccount, senderBalance - amount, userDataList);
                UserData.setBalance(receiverAcc, receiverBalance + amount, userDataList);

                // Add transfer transactions to TransactionStorage
                TransactionStorage.addTransaction(
                    java.time.LocalDateTime.now().toString(),
                    senderAccount,
                    "Transfer Out",
                    amount
                );
                TransactionStorage.addTransaction(
                    java.time.LocalDateTime.now().toString(),
                    receiverAcc,
                    "Transfer In",
                    amount
                );

                // Existing file logging
                FileHandler.recordTransaction(senderAccount, amount, "Transferred To: " + receiverAcc);
                FileHandler.recordTransaction(receiverAcc, amount, "Received From: " + senderAccount);

                JOptionPane.showMessageDialog(null, "Money transferred successfully.");
                dispose();
            }
        });

        cancelButton.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(null);
    }
}
