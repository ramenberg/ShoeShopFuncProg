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
            case 1 -> {
                System.out.print("Enter your email: ");
                String email = sc.nextLine();
                System.out.print("Enter your password: ");
                char[] password = sc.nextLine().toCharArray();
                c = Repository.validateLoginReturnCustomer(email, password); // kolla om inloggningen lyckades
                if (c != null) { // om inloggningen lyckades finns en kund sparad, annars null
                    // loop för programmet om inloggningen lyckades
                    while (true) {
                        System.out.println("Login successful!");
                        System.out.println("Welcome " + c.getFirst_name() + c.getLast_name() + "!");

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
                            case 1 -> Shopping s = new Shopping();
                            case 2 -> {
                                System.out.println("Goodbye!");
                                System.exit(0);
                            }
                            default -> System.out.println("Invalid choice!");
                        }
                        if (s != null) {
                            System.out.println("----------------------------");
                            System.out.println("Do you want to continue shopping? ");
                            System.out.println("1. Yes");
                            System.out.println("2. No");
                            System.out.println("Enter your choice: ");
                            choice = sc.nextInt();
                            sc.nextLine();
                            switch (choice) {
                                case 1 -> {
                                    List<Item> cartItems = Shopping.startShopping();
                                    // checkout
                                    if (cartItems!= null) {
                                        System.out.println("----------------------------");
                                        System.out.println("Your shopping cart is:");
                                        System.out.println("----------------------------");
                                        for (Item i : cartItems) {
                                            System.out.println(i);
                                        }
                                        System.out.println("Subtotal: " + Shopping.getSubtotal() + " SEK. ");
                                        System.out.println("----------------------------");
                                        System.out.println("Submit order? ");
                                        System.out.println("1. Yes");
                                        System.out.println("2. Cancel");
                                        System.out.println("Enter your choice: ");
                                        choice = sc.nextInt();
                                        switch (choice) {
                                            case 1 -> {
                                                System.out.println("----------------------------");
                                                System.out.println("Your order has been submitted!");
                                                System.out.println("----------------------------");
                                                System.out.println("Your order is:");
                                                System.out.println("----------------------------");
                                                for (Item i : cartItems) {
                                                    System.out.println(i);
                                                }
                                                System.out.println("Subtotal: " + Shopping.getSubtotal() + " SEK. ");
                                                System.out.println("----------------------------");
                                                System.out.println("Thanks for shopping!");
                                                System.exit(0);
                                            }
                                            case 2 -> {
                                                System.out.println("----------------------------");
                                                System.out.println("Your order has been cancelled!");
                                                System.out.println("----------------------------");
                                                System.out.println("Thanks for shopping!");
                                                System.exit(0);
                                            }
                                            default -> System.out.println("Invalid choice!");
                                        }
                                    }
                                }
                                case 2 -> {
                                    System.out.println("Thanks for shopping!");
                                    System.exit(0);
                                }
                                default -> System.out.println("Invalid choice!");
                            }
                        }
                    }
                } else {
                    System.out.println("Login failed!");
                }
            }
            case 2 -> {
                System.out.println("Goodbye!");
                System.exit(0);
            }
            default -> System.out.println("Invalid choice!");
        }
    }
}
