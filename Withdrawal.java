package com.mycompany.mainpanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Withdrawal extends JDialog {
    private JTextField textField;
    private JButton withdrawButton, cancelButton;
    private List<UserData> userDataList;
    private String cardNum;
    private Validate v;
    private String accNum;

    public Withdrawal(Frame owner, String cardNum, List<UserData> userDataList) {
        super(owner, "Withdrawal", true);
        this.cardNum = cardNum;
        this.userDataList = userDataList;
        v = new Validate();
        accNum = UserData.getData(cardNum, 15, userDataList); // account number

        Color sunsetPurple = new Color(100, 65, 110);
        getContentPane().setBackground(sunsetPurple);

        setLayout(new GridLayout(3, 2, 10, 10));

        textField = new JTextField();
        withdrawButton = new JButton("Withdraw");
        cancelButton = new JButton("Cancel");

        withdrawButton.setBackground(Color.WHITE);
        withdrawButton.setForeground(Color.BLACK);
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setForeground(Color.BLACK);

        JLabel label = new JLabel("Enter Amount to Withdraw:");
        label.setForeground(Color.WHITE);

        add(label);
        add(textField);
        add(withdrawButton);
        add(cancelButton);

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (v.isAmount(textField.getText())) {
                    double oldBalance = Double.parseDouble(UserData.getData(cardNum, 5, userDataList));
                    double withdrawalAmount = Double.parseDouble(textField.getText());

                    if (withdrawalAmount <= 10000) {
                        if (oldBalance - withdrawalAmount >= 1000) {
                            double newBalance = oldBalance - withdrawalAmount;
                            UserData.setData(cardNum, 5, Double.toString(newBalance), userDataList);

                            // Record transaction (optional legacy file method)
                            FileHandler.recordTransaction(accNum, withdrawalAmount, "Withdrawal");

                            // Record transaction with timestamp
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            String timestamp = dtf.format(LocalDateTime.now());

                            TransactionStorage.addTransaction(
                                timestamp,
                                accNum,
                                "Withdrawal",
                                withdrawalAmount
                            );

                            JOptionPane.showMessageDialog(null, "Please collect your money.");
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Your balance cannot go below 1000 Taka.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Maximum withdrawal limit is 10,000 Taka.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a valid amount.");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        pack();
        setLocationRelativeTo(null);
    }
}
