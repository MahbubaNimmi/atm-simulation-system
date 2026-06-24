
package com.mycompany.mainpanel;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class FileHandler {
public static void recordTransaction(String accNum, double amount, String type) {
try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.txt", true)))
{
String time =
LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
writer.write(type + " | Account: " + accNum + " | Amount: " + amount +" Taka | Time: "
+ time);
writer.newLine();
} catch (IOException e) {
e.printStackTrace();
}
}
}


