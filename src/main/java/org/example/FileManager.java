package org.example;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    public static List<Transaction> transactions(){
        List<Transaction> transactions = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"));

            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                LocalDate date = LocalDate.parse(parts[0]);
                LocalTime time = LocalTime.parse(parts[1]);
                String description = parts[2];
                String name = parts[3];
                double amount = Double.parseDouble(parts[4]);

                Transaction transaction = new Transaction(date, time, description, name, amount);
                transactions.add(transaction);
            }

            reader.close();

        } catch (IOException e) {
            System.out.println("There was a problem reading the transactions file.");
        }
        catch(Exception ex){
            System.out.println("Something went from with the file.");
        }

        return transactions;
    }
    public static void writetransaction(Transaction transaction){
        try{
            File file = new File("transactions.csv");
            FileWriter fileWriter = new FileWriter(file, true);
            if (file.length() > 0) {
                fileWriter.write(System.lineSeparator());
            }

            fileWriter.write(String.format("%s|%s|%s|%s|%.2f", transaction.getDate(), transaction.getTime(),
                    transaction.getDescription(), transaction.getName(), transaction.getAmount()));

            fileWriter.close();
        }
        catch(IOException ex){
            System.out.println("Error writing to file.");
        }
    }
}

