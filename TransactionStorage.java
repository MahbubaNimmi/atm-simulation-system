
package com.mycompany.mainpanel;

import java.util.ArrayList;
import java.util.List;

public class TransactionStorage {
    public static class Transaction {
        String dateTime;
        String accountNumber;
        String type;
        double amount;

        public Transaction(String dateTime, String accountNumber, String type, double amount) {
            this.dateTime = dateTime;
            this.accountNumber = accountNumber;
            this.type = type;
            this.amount = amount;
        }
    }

    private static final List<Transaction> transactions = new ArrayList<>();

    public static void addTransaction(String dateTime, String accountNumber, String type, double amount) {
        transactions.add(new Transaction(dateTime, accountNumber, type, amount));
    }

    public static List<Transaction> getTransactions() {
        return transactions;
    }
}


