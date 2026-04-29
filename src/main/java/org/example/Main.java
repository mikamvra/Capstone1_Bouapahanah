package org.example;

import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static Scanner input = new Scanner(System.in);
    public static boolean shouldGoHome = false;
    public static void main(String[] args) {
        List<Transaction> transactions = FileManager.transactions();

        while (true) {
            System.out.println("\033[38;2;255;105;180m~~ HOME SCREEN ~~\033[0m" );
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
            FileWriter writer = new FileWriter("src/main/resources/transactions.csv", true);


            System.out.println("Add Description: ");
            String description = Main.input.nextLine();
            System.out.println("Name: ");
            String name = Main.input.nextLine();
            System.out.println("Amount: ");
            double amount = Main.input.nextDouble();
            input.nextLine();

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
            System.out.println("\033[38;2;255;105;180m~~ LEDGER MENU ~~\033[0m" );
            System.out.println("1. All Entries");
            System.out.println("2. Deposit only");
            System.out.println("3. Payments only");
            System.out.println("4. Reports");
            System.out.println("5. Home Screen");

            System.out.println("Select an Option: ");
            String choice = input.nextLine();
            if (choice.equals("1")){
            displayLedger("All");
            } else if (choice.equals("2")) {
                displayLedger("Deposit");
            }else if (choice.equals("3")){
                displayLedger("Payment");
            } else if (choice.equals("4")) {
                reportsMenu(input);
            } if (Main.shouldGoHome) {
                Main.shouldGoHome = false;
                return;
            }else{
                return;
            }

        }
    }

    public static void displayLedger(String type) {
        List<Transaction> transactions = FileManager.transactions();

        for (int i = transactions.size() - 1; i >= 0; i--){
            Transaction t = transactions.get(i);
            boolean shouldPrint = false;

            if (type.equals("All")) shouldPrint = true;
            else if (type.equals("Deposit") && t.getAmount() > 0 ) shouldPrint= true;
            else if (type.equals("Payment") && t.getAmount() < 0 ) shouldPrint = true;

            if (shouldPrint)
            System.out.printf("%tD | %tr | %s | %s | %.2f%n",t.getDate(), t.getTime(), t.getDescription(), t.getName(), t.getAmount());
        }


    }
    public static boolean reportsMenu (Scanner input){

        while (true){
            System.out.println("\033[38;2;255;105;180m~~ REPORT MENU ~~\033[0m" );
            System.out.println("1. Month to Date");
            System.out.println("2. Previous Month");
            System.out.println("3. Year To Date");
            System.out.println("4. Previous Year");
            System.out.println("5. Search by Vendor");
            System.out.println("6. Back Ledger Screen");
            System.out.println("7. Return to Home Screen");

            System.out.println("Select an Option");
            String choice = input.nextLine();
            if (choice.equals("1")){
                monthToDate();
            } else if (choice.equals("2")){
                previousMonth();
            } else if (choice.equals("3")) {
                yearToDate();
            } else if (choice.equals("4")) {
                previousYear();
            } else if (choice.equals("5")) {
                vendorName();
            }if (choice.equals("6")) {
                return false;
            } else if (choice.equals("7")) {
                Main.shouldGoHome = true;
                return false;
            }else{
                System.out.println("Invalid Option. Try Again!");
            }
        }
    }public static void monthToDate() {
        List<Transaction> transactions = FileManager.transactions();
        LocalDate today = LocalDate.now();

        System.out.println("\033[38;2;255;105;180m~~ Month to Date Report ~~\033[0m" );

        for (Transaction t : transactions) {
            if (t.getDate().getYear() == today.getYear() &&
                    t.getDate().getMonth() == today.getMonth()) {

                System.out.printf("%tD | %tr | %s | %s | %.2f\n",
                        t.getDate(), t.getTime(), t.getDescription(), t.getName(), t.getAmount());
            }

        }
    }public static void previousMonth() {
        List<Transaction> transactions = FileManager.transactions();

        LocalDate lastMonthDate = LocalDate.now().minusMonths(1);
        int month = lastMonthDate.getMonthValue();
        int year = lastMonthDate.getYear();
        System.out.println("\033[38;2;255;105;180m~~ Previous Month Report ~~\033[0m");
        for (Transaction t : transactions) {
            if (t.getDate().getMonthValue() == month &&
                    t.getDate().getYear() == year) {

                System.out.printf("%tD | %tr | %s | %s | %.2f\n",
                        t.getDate(), t.getTime(), t.getDescription(), t.getName(), t.getAmount());


    }
}
    }public static void yearToDate (){

        List<Transaction> transactions = FileManager.transactions();
        int currentYear = LocalDate.now().getYear();

        System.out.println("\033[38;2;255;105;180m~~ Year to Date Report ~~\033[0m" );

        for (Transaction t : transactions) {
            if (t.getDate().getYear() == currentYear) {

                System.out.printf("%tD | %tr | %s | %s | %.2f\n",
                        t.getDate(), t.getTime(), t.getDescription(), t.getName(), t.getAmount());
            }
        }
    }public static void previousYear(){
        List<Transaction> transactions = FileManager.transactions();

        int lastYear = LocalDate.now().minusYears(1).getYear();

        System.out.println("\033[38;2;255;105;180m~~ Previous Year Report ~~\033[0m");

        for (Transaction t : transactions) {
            if (t.getDate().getYear() == lastYear) {

                System.out.printf("%tD | %tr | %s | %s | %.2f\n",
                        t.getDate(), t.getTime(), t.getDescription(), t.getName(), t.getAmount());

            }
        }
    }public static void vendorName() {
        List<Transaction> transactions = FileManager.transactions();

        System.out.println("Enter Vendor Name: ");
        String name = input.nextLine();
        System.out.println("~~ Results for Name: "+ name);
        boolean found = false;
        for (Transaction t : transactions) {
            if (t.getName().equalsIgnoreCase(name)){

            System.out.printf("%tD | %tr | %s | %s | %.2f\n",
                    t.getDate(), t.getTime(), t.getDescription(), t.getName(), t.getAmount());
            found = true;
        }
        }
        if (!found){
            System.out.println("No Transactions Found With That Name.");
        }
    }
}