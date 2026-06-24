package com.mycompany.mainpanel;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class MiniStatement extends JDialog {
    private String accountNumber;

    public MiniStatement(Frame owner, String accountNumber) {
        super(owner, "Mini Statement", true);
        this.accountNumber = accountNumber;

        setSize(500, 300);
        setLayout(new BorderLayout());

        Color sunsetPurple = new Color(100, 50, 90);
        getContentPane().setBackground(sunsetPurple);

        JLabel headerLabel = new JLabel("Mini Statement for A/C: ****" +
                accountNumber.substring(accountNumber.length() - 4));
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBackground(sunsetPurple);
        headerLabel.setOpaque(true);
        add(headerLabel, BorderLayout.NORTH);

        JTextArea statementArea = new JTextArea();
        statementArea.setEditable(false);
        statementArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        statementArea.setBackground(sunsetPurple);
        statementArea.setForeground(Color.WHITE);
        statementArea.setCaretColor(Color.WHITE);

        List<TransactionStorage.Transaction> transactions = getLastTransactions(accountNumber, 5);

        if (transactions.isEmpty()) {
            statementArea.setText("No transactions found.");
        } else {
            statementArea.append(String.format("%-20s %-15s %-10s %-10s\n",
                    "Date/Time", "Type", "Amount", "Account"));
            statementArea.append("-------------------------------------------------------------\n");
            for (TransactionStorage.Transaction tx : transactions) {
                statementArea.append(formatTransaction(tx) + "\n");
            }
        }

        JScrollPane scrollPane = new JScrollPane(statementArea);
        scrollPane.getViewport().setBackground(sunsetPurple);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        setLocationRelativeTo(owner);
        setVisible(true);
    }

    private List<TransactionStorage.Transaction> getLastTransactions(String accNum, int limit) {
        return TransactionStorage.getTransactions()
                .stream()
                .filter(tx -> tx.accountNumber.equals(accNum))
                .sorted((a, b) -> b.dateTime.compareTo(a.dateTime))
                .limit(limit)
                .collect(Collectors.toList());
    }

    private String formatTransaction(TransactionStorage.Transaction tx) {
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            LocalDateTime parsedDateTime = LocalDateTime.parse(tx.dateTime, inputFormatter);
            String formattedDateTime = parsedDateTime.format(outputFormatter);

            return String.format("%-20s %-15s %-10.2f %-10s",
                    formattedDateTime, tx.type, tx.amount,
                    "****" + tx.accountNumber.substring(tx.accountNumber.length() - 4));
        } catch (Exception e) {
            return String.format("%-20s %-15s %-10.2f %-10s",
                    tx.dateTime, tx.type, tx.amount,
                    "****" + tx.accountNumber.substring(tx.accountNumber.length() - 4));
        }
    }
}
