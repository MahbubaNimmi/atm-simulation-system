package com.mycompany.mainpanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deposit extends JDialog {
    private JTextField textField;
    private JButton depositButton, cancelButton;
    private List<UserData> userDataList;
    private String cardNum;
    private Validate v;
    private String accNum;

    public Deposit(Frame owner, String cardNum, List<UserData> userDataList) {
        super(owner, "Deposit", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.cardNum = cardNum;
        this.userDataList = userDataList;
        v = new Validate();

        Color sunsetPurple = new Color(100, 65, 110);
        getContentPane().setBackground(sunsetPurple);

        setLayout(new GridLayout(3, 2, 10, 10));

        textField = new JTextField();
        depositButton = new JButton("Deposit");
        cancelButton = new JButton("Cancel");

        // Set button colors
        depositButton.setBackground(Color.WHITE);
        depositButton.setForeground(Color.BLACK);
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setForeground(Color.BLACK);

        add(new JLabel("Enter Amount to Deposit:") {{
            setForeground(Color.WHITE);
        }});
        add(textField);
        add(depositButton);
        add(cancelButton);

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accNum = UserData.getData(cardNum, 15, userDataList);
                if (v.isAmount(textField.getText())) {
                    double oldBalance = Double.parseDouble(UserData.getData(cardNum, 5, userDataList));
                    double depositAmount = Double.parseDouble(textField.getText());
                    double newBalance = oldBalance + depositAmount;

                    UserData.setData(cardNum, 5, Double.toString(newBalance), userDataList);

                    // Record the transaction using your TransactionStorage class
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String timestamp = dtf.format(LocalDateTime.now());
                    TransactionStorage.addTransaction(timestamp, accNum, "Deposit", depositAmount);

                    JOptionPane.showMessageDialog(null, depositAmount + " Taka deposited successfully into your account.");
                    dispose();
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
