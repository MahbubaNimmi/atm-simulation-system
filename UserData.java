
package com.mycompany.mainpanel;
import java.io.Serializable;
import java.util.List;
public class UserData implements Serializable {
    private static final long serialVersionUID = 1L;
private String name, nid, dob, accountNumber, pin;
private double balance = 0.0;
public UserData(String name, String nid, String dob, String accountNumber, String pin) {
this.name = name;
this.nid = nid;
this.dob = dob;
this.accountNumber = accountNumber;
this.pin = pin;
}
public String getAccountNumber() {
return accountNumber;
}
public String getPin() {
return pin;
}
public String getName() {
return name;
}
public String getDob() {
return dob;
}
public String getNid() {
return nid;
}
public double getBalance() {
return balance;
}
public void setBalance(double balance) {
this.balance = balance;
}
public static String getData(String accNum, int index, List<UserData> users) {
for (UserData user : users) {
if (user.getAccountNumber().equals(accNum)) {
switch (index) {
case 5:
return Double.toString(user.getBalance());
case 15:
return user.getAccountNumber();
default:
return "";
}
}
}
return null;
}
public static void setData(String accNum, int index, String newData, List<UserData>
users) {
for (UserData user : users) {
if (user.getAccountNumber().equals(accNum)) {
switch (index) {
case 5:
user.setBalance(Double.parseDouble(newData));
break;
}
}
}
}
public static boolean existsAccNo(String accNum, List<UserData> users) {
for (UserData user : users) {
if (user.getAccountNumber().equals(accNum)) {
return true;
}
}
return false;
}
public static double getBalance(String accNum, List<UserData> users) {
for (UserData user : users) {
if (user.getAccountNumber().equals(accNum)) {
return user.getBalance();
}
}
return 0.0;
}
public static void setBalance(String accNum, double newBalance, List<UserData> users) {
for (UserData user : users) {
if (user.getAccountNumber().equals(accNum)) {
user.setBalance(newBalance);
break;
}
}
}
public void setName(String name) {
    this.name = name;
}

public void setDob(String dob) {
    this.dob = dob;
}

public void setNid(String nid) {
    this.nid = nid;
}
}
