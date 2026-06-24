package com.mycompany.mainpanel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataStore {
    private static final String FILE_NAME = "users.dat";

    // Load users from file or create empty list if file doesn't exist
    public static List<UserData> userList = loadUsers();

    public static void addUser(UserData user) {
        userList.add(user);
        saveUsers(); // Save to file after every addition
    }

    public static List<UserData> getUsers() {
        return userList;
    }

    public static UserData findUser(String accountNumber, String pin) {
        for (UserData user : userList) {
            if (user.getAccountNumber().equals(accountNumber) && user.getPin().equals(pin)) {
                return user;
            }
        }
        return null;
    }

    public static boolean nidExists(String nid) {
        for (UserData user : userList) {
            if (user.getNid().equals(nid)) {
                return true;
            }
        }
        return false;
    }

    // Save user list to file
    public static void saveUsers() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(userList);
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    // Load user list from file
    public static List<UserData> loadUsers() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (List<UserData>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading users: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
