package Shop;

import DBTables.Customer;
import DBTables.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Customer c;
    private static List<Integer> cartItems = new ArrayList<>();
    private static List<Item> allItemsInStockList = new ArrayList<>();

    public static void main(String[] args) {
        Repository r = new Repository();
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to the shop!");
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Enter your email: ");
                String email = sc.nextLine();
                System.out.print("Enter your password: ");
                char[] password = sc.nextLine().toCharArray();
                if (Repository.validateLogin(email, password)) {
                    // loop för programmet om inloggningen lyckades
                    while (true) {
                        System.out.println("Login successful!");
                        System.out.println("Welcome " + Repository.getCustomer(email, password).getFirst_name() + "!");
                        c = Repository.getCustomer(email, password); // sparar ner kunden i en variabel

                        // början av val

                        System.out.println("----------------------------");
                        System.out.println("What do you want to do? ");
                        System.out.println("1. Select item to add to cart");
                        System.out.println("2. Exit");
                        System.out.println("----------------------------");
                        System.out.print("Enter your choice: ");
                        choice = sc.nextInt();
                        sc.nextLine();
                        switch (choice) {
                            case 1:
                                System.out.println("Available brands and models: ");
                                System.out.println("----------------------------");
                        }
                    }
                } else {
                    System.out.println("Login failed!");
                }
                break;
            case 2:
                System.out.println("Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice!");
                break;
        }
    }
}
