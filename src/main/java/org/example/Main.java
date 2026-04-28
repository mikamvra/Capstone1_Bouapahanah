package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static Scanner input = new Scanner(System.in);
    public static FileManager file = new FileManager();

    public static void main(String[] args) {
        List<Transaction> transactions = FileManager.transactions();

        while (true) {
            System.out.println("~~ HOME SCREEN ~~");
            System.out.println("1. Add Deposit");
            System.out.println("2. Make A Payment");
            System.out.println("3. Ledger");
            System.out.println("4. Exit");

            System.out.println("Select an Option: ");
            String choice = input.nextLine();

            if (choice.equals("1")) {
                addTransaction(input, true);
            } else if (choice.equals("2")) {
                addTransaction(input, false);
            } else if (choice.equals("3")) {
                ledgerMenu(input);
            } else if (choice.equals("4")) {
                System.out.println("Exiting Program!");
                System.exit(0);
            } else {
                System.out.println("Invalid Option. Please Try Again!");
            }
        }


    }

    public static void addTransaction(Scanner input, boolean addDeposit) {
        try {
            FileWriter writer = new FileWriter("transactions.csv", true);


            System.out.println("Add Description: ");
            String description = Main.input.nextLine();
            System.out.println("Name: ");
            String name = Main.input.nextLine();
            System.out.println("Amount: ");
            double amount = Main.input.nextDouble();
            Main.input.nextDouble();

            if (!addDeposit) amount *= -1;
            String date = LocalDate.now().toString();
            String time = LocalTime.now().toString().substring(0, 8);

            String line = String.format("%s|%s|%s|%s|%.2f\n", date, time, description, name, amount);

            writer.write(line);
            writer.close();
            System.out.println("Change has been saved!");


        } catch (Exception ex) {
            System.out.println("Error");
        }
    }

    public static void ledgerMenu(Scanner input) {
        while (true) {
            System.out.println("~~ LEDGER MENU ~~");
            System.out.println("1. All Entries");
            System.out.println("2. Deposit only");
            System.out.println("3. Payments only");
            System.out.println("4. Home Screen");

            System.out.println("Select an Option: ");
            String choice = input.nextLine();
            if (choice.equals("1")){
            displayLedger("All");
            } else if (choice.equals("2")) {
                displayLedger("Deposit");
            }else if (choice.equals("3")){
                displayLedger("Payment");
            }else{
                return;
            }

        }
    }

    public static void displayLedger(String type) {
        List<Transaction> transactions = FileManager.transactions();

        for (Transaction t: transactions){
            boolean shouldPrint = false;

            if (type.equals("All")) shouldPrint = true;
            else if (type.equals("Deposit") && t.getAmount() > 0 ) shouldPrint= true;
            else if (type.equals("Payment") && t.getAmount() < 0 ) shouldPrint = true;

            if (shouldPrint)
            System.out.printf("%t | %t | %s | %s | %s | %.2f",t.getDate(), t.getTime(), t.getDescription(), t.getName(), t.getAmount());
        }


    }
}
