package Shop;

import DBTables.Customer;
import DBTables.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Item> cartItems = new ArrayList<>();

    public static void main(String[] args) {
        new Repository();
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to the Shoe Shop!");
        System.out.println("----------------------------");
        System.out.println("What do you want to do? ");
        System.out.println();
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.println();
        System.out.print("Enter your choice: ");
        int choiceLogin = sc.nextInt();
        sc.nextLine();

        switch (choiceLogin) {
            case 1 -> {
                System.out.print("Enter your email: ");
                String email = sc.nextLine();
                System.out.print("Enter your password: ");
                char[] password = sc.nextLine().toCharArray();
                final Customer customer; // immutable
                customer = Repository.validateLoginReturnCustomer(email, password); // kolla om inloggningen lyckades

                if (customer != null) { // om inloggningen lyckades finns en kund sparad, annars null
                    // loop för programmet om inloggningen lyckades
                    System.out.println();
                    System.out.println("Login successful.");
                    System.out.println("Welcome " + customer.getFirst_name() + " " + customer.getLast_name() + "!");
                    System.out.println("DEBUG: customer id: " + customer.getId());
                    while (true) {

                        int orderId = 0;
                        // början av val

                        System.out.println("----------------------------");
                        System.out.println("What do you want to do? ");
                        System.out.println("1. Search for an item ");
                        System.out.println("2. Exit ");
                        System.out.println("----------------------------");
                        System.out.print("Enter your choice: ");
                        int choiceSearchItem = sc.nextInt();
                        sc.nextLine();
                        switch (choiceSearchItem) {
                            case 1 -> {
                                cartItems = Shopping.startShopping();
                            }
                            case 2 -> {
                                System.out.println("Goodbye!");
                                System.exit(0);
                            }
                            default -> System.out.println("Invalid choice!");
                        }

                        if (cartItems.size() > 0) {
                            System.out.println("----------------------------");
                            System.out.println("Do you want to checkout? ");
                            System.out.println("1. Yes");
                            System.out.println("2. No, exit.");
                            System.out.println("Enter your choice: ");
                            int choiceCheckout = sc.nextInt();
                            sc.nextLine();
                            switch (choiceCheckout) {
                                case 1 -> {
                                    new Checkout(cartItems, customer.getId(), orderId);
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
