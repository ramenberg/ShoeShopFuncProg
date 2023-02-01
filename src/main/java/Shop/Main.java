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
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1 -> {
                System.out.print("Enter your email: ");
                String email = sc.nextLine();
                System.out.print("Enter your password: ");
                char[] password = sc.nextLine().toCharArray();
                Customer customer = Repository.validateLoginReturnCustomer(email, password); // kolla om inloggningen lyckades
                if (customer != null) { // om inloggningen lyckades finns en kund sparad, annars null
                    // loop för programmet om inloggningen lyckades
                    System.out.println();
                    System.out.println("Login successful.");
                    System.out.println("Welcome " + customer.getFirst_name() + " " + customer.getLast_name() + "!");

                    while (true) {
                        int orderId;
                        // början av val

                        System.out.println("----------------------------");
                        System.out.println("What do you want to do? ");
                        System.out.println("1. Select an item to add to cart");
                        System.out.println("2. Exit");
                        System.out.println("----------------------------");
                        System.out.print("Enter your choice: ");
                        choice = sc.nextInt();
                        sc.nextLine();
                        switch (choice) {
                            case 1 -> {
                                cartItems = Shopping.startShopping();
                            }
                            case 2 -> {
                                System.out.println("Goodbye!");
                                System.exit(0);
                            }
                            default -> System.out.println("Invalid choice!");
                        }
                        if (cartItems.size() != 0) {
                            System.out.println("----------------------------");
                            System.out.println("Do you want to checkout? ");
                            System.out.println("1. Yes");
                            System.out.println("2. No");
                            System.out.println("Enter your choice: ");
                            choice = sc.nextInt();
                            sc.nextLine();
                            switch (choice) {
                                case 1 -> {
//                                    List<Item> cartItems = Shopping.startShopping();
                                    // checkout
                                    if (cartItems!= null) {
                                        System.out.println("----------------------------");
                                        System.out.println("Your shopping cart has the following " + cartItems.size() + " item(s):");
                                        System.out.println("----------------------------");
                                        for (Item i : cartItems) {
                                            System.out.println(
                                                    i.getBrand_id().getName() + " " +
                                                    i.getModel() + ", " +
                                                    i.getColor_id().getName() + ", " +
                                                    i.getSize_id().getSize() + ", Price: " +
                                                    i.getPrice() +" SEK");
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
                                                if (cartItems.size() > 1) {
                                                    orderId = Repository.addToOrder(customer.getId(), (cartItems.get(0)).getId());
                                                } else if (cartItems.size() == 1) {
                                                    orderId = Repository.addToOrder(customer.getId(), cartItems.get(0).getId());
                                                    if (orderId != -1) {
                                                        cartItems.stream().skip(1).forEach(i -> {
                                                            Repository.addToOrder(orderId, i.getId());
                                                        });
                                                    }
                                                }else{
                                                    System.out.println("----------------------------");
                                                    System.out.println("Your cart is empty!");
                                                    System.out.println("----------------------------");
                                                    break;
                                                }
                                                // TODO koppla till SP addToCart
                                                System.out.println("----------------------------");
                                                System.out.println("Your order has been submitted!");
                                                System.out.println("----------------------------");
                                                // TODO lägga in ordernummer istället?
//                                                System.out.println("Your order is:");
//                                                System.out.println("----------------------------");
//                                                for (Item i : cartItems) {
//                                                    System.out.println(i);
//                                                }
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
