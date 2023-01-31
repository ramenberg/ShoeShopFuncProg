package Shop;

import DBTables.Customers;
import DBTables.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Customers c;

    public static void main(String[] args) {
        Repository r = new Repository();
        Scanner sc = new Scanner(System.in);

        while (true) {

            List<Integer> cartItems = new ArrayList<>();

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
                        System.out.println("Login successful!");
                        System.out.println("Welcome " + Repository.getCustomer(email, password).getFirst_name() + "!");
                        c = Repository.getCustomer(email, password);
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
            System.out.println("----------------------------");
            System.out.println("What do you want to do? ");
            System.out.println("1. View items");
            System.out.println("2. Exit");
            System.out.println("----------------------------");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Available brands and models: ");
                    System.out.println("----------------------------");
                    for (String s : Repository.getBrandsAndNames()) {
                        System.out.println(s);
                    }
                    System.out.println("----------------------------");
                    System.out.print("Enter brand: ");
                    String brand = sc.nextLine();
                    System.out.print("Enter model: ");
                    String model = sc.nextLine();
                    int n = 0;
                    List<ItemDetails> items = Repository.getItemsByBrandAndName(brand, model);
                    if (items.size() == 0) {
                        System.out.println("No items found!");
                        break;
                    } else {
                        for (ItemDetails i : items) {
                            System.out.print(n + 1 + " ");
                            System.out.printf("%s %s %s %s $%.2f%n",
                                    i.getBrand(),
                                    i.getName(),
                                    i.getColor(),
                                    i.getSize(),
                                    i.getPrice());
                            n++;
                        }
                        System.out.println("----------------------------");
                        System.out.println("Choose an item number to add to cart: ");
                        int itemNumber = sc.nextInt();
                        sc.nextLine();
                        ItemDetails chosenItem = items.get(itemNumber - 1);

                        System.out.println("You chose: ");
                        System.out.printf("%s %s %s %s $%.2f%n",
                                chosenItem.getBrand(),
                                chosenItem.getName(),
                                chosenItem.getColor(),
                                chosenItem.getSize(),
                                chosenItem.getPrice());
                        System.out.println("----------------------------");
                        System.out.println("Do you want to add this item to cart? ");
                        System.out.println("1. Yes");
                        System.out.println("2. No");
                        System.out.print("Enter your choice: ");
                        choice = sc.nextInt();
                        sc.nextLine();
                        switch (choice) {
                            case 1:
                                cartItems.add(chosenItem.getId());
                                System.out.println("Item added to cart!"); // TODO info ska komma ifrån metoden istället
                                break;
                            case 2:
                                System.out.println("Item not added to cart!");
                                break;
                            default:
                                System.out.println("Invalid choice!");
                                break;
                        }
                        // så att man inte får frågan om att lägga till fler om varukorgen är tom
                        if (cartItems.size() > 0) {
                            System.out.println("Add more items to cart? ");
                            System.out.println("1. Yes");
                            System.out.println("2. No");
                            System.out.print("Enter your choice: ");
                            choice = sc.nextInt();
                            sc.nextLine();
                            switch (choice) {
                                case 1:
                                    break;
                                case 2: {
                                    System.out.println("Your cart: ");
                                    System.out.println("----------------------------");
                                    for (int i : cartItems) {
                                        ItemDetails item = Repository.getItemById(i);
                                        assert item != null;
                                        System.out.printf("%s %s %s %s $%.2f%n",
                                                item.getBrand(),
                                                item.getName(),
                                                item.getColor(),
                                                item.getSize(),
                                                item.getPrice());
                                    }
                                    System.out.println("----------------------------");
                                    System.out.println("Do you want to checkout? ");
                                    System.out.println("1. Yes");
                                    System.out.println("2. No");
                                    System.out.print("Enter your choice: ");
                                    choice = sc.nextInt();
                                    sc.nextLine();
                                    switch (choice) {
                                        case 1:
                                            // TODO koppla till stored procedure
                                            System.out.println("Your order has been placed!");
                                            System.out.println("Thank you for shopping with us!");
                                            System.exit(0);
                                            break;
                                        case 2:
                                            System.out.println("Your cart has been cleared!");
                                            cartItems.clear();
                                            break;
                                        default:
                                            System.out.println("Invalid choice!");
                                            break;
                                    }
                                }
                            }
                        }
                        else {
                            break;
                        }



                    }
            }
        }
    }
}
