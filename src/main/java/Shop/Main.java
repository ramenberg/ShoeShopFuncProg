package Shop;

import DBTables.Customer;
import DBTables.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Item> cartItems = new ArrayList<>();
    public Main() {
        new Repository();
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to the Shoe Shop!");

        while (true) {
            System.out.println();
            System.out.println("What do you want to do? ");
            System.out.println();
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.println();
            System.out.print("Enter your choice: ");
            int choiceLogin;
            try {
                choiceLogin = sc.nextInt();
                sc.nextLine();

                if (choiceLogin == 1 || choiceLogin == 2) {
                    switch (choiceLogin) {
                        case 1 -> {
                            System.out.println();
                            System.out.println("Login");
                            System.out.println();
                            System.out.print("Enter your email: ");
                            String email = sc.nextLine().trim();
                            System.out.print("Enter your password: ");
                            char[] password = sc.nextLine().trim().toCharArray();
                            final Customer customer; // immutable
                            customer = Repository.validateLoginReturnCustomer(email, password); // kolla om inloggningen lyckades

                            if (customer != null) {
                                // om inloggningen lyckades finns en kund sparad, annars null
                                // loop för programmet om inloggningen lyckades
                                System.out.println();
                                System.out.println("Login successful.");
                                System.out.println();
                                System.out.println("Welcome " + customer.getFirst_name() + " " + customer.getLast_name() + "!");
                                while (true) {

                                    int orderId = 0;

                                    // början av val för shopping

                                    System.out.println();
                                    System.out.println("What do you want to do? ");
                                    System.out.println("1. Start shopping ");
                                    System.out.println("2. Exit ");
                                    System.out.println();
                                    System.out.print("Enter your choice: ");
                                    int choiceSearchItem = sc.nextInt();
                                    sc.nextLine();
                                    switch (choiceSearchItem) {
                                        case 1 -> cartItems = Shopping.startShopping();
                                        case 2 -> {
                                            System.out.println("Goodbye!");
                                            System.exit(0);
                                        }
                                        default -> System.out.println("Invalid choice!");
                                    }

                                    assert cartItems != null;
                                    if (cartItems.size() > 0) {
                                        System.out.println();
                                        System.out.println("Do you want to checkout? ");
                                        System.out.println("1. Yes");
                                        System.out.println("2. No, cancel.");
                                        System.out.println("Enter your choice: ");
                                        int choiceCheckout = sc.nextInt();
                                        sc.nextLine();
                                        switch (choiceCheckout) {
                                            case 1 -> new Checkout(cartItems, customer.getId(), orderId);
                                            case 2 -> System.out.println("Canceling checkout.");
                                            default -> System.out.println("Invalid choice!");
                                        }
                                    }
                                }
                            } else {
                                System.out.println("\nLogin failed!");
                            }
                        }
                        case 2 -> {
                            System.out.println("Goodbye!");
                            System.exit(0);
                        }
                        default -> System.out.println("Invalid choice!");
                    }
                } else {
                    System.out.println("Invalid choice, try again.");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }
    public static void main(String[] args) {
        new Main();
    }
}
