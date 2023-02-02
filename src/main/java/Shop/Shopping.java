package Shop;

import DBTables.Brand;
import DBTables.Item;

import java.util.*;
import java.util.stream.Collectors;

import static Shop.Repository.getAllItemsInStock;

public class Shopping {

    static final List<Item> cartItems = new ArrayList<>();

    public static List<Item> startShopping() {
        Scanner sc = new Scanner(System.in);
        List<Item> allItems = getAllItemsInStock();
        Item selectedItem;

        if (allItems.size() == 0) {
            System.out.println("No items in stock.");
        } else {
            Set<String> allBrands = allItems.stream()
                    .map(Item::getBrand_id)
                    .map(Brand::getName)
                    .collect(Collectors.toSet());

            if (allBrands.isEmpty()) {
                System.out.println("No items in stock.");
                return null;

            } else {
                System.out.println();
                System.out.println("Select a brand from the following: ");
                System.out.println();
                allBrands.forEach(System.out::println);
                System.out.println();
                System.out.print("Enter brand name: ");
                String selectedBrand = sc.nextLine().trim();

                List<Item> filteredByBrand = allItems.stream()
                        .filter(item -> item.getBrand_id().getName().equalsIgnoreCase(selectedBrand)).toList();

                if (filteredByBrand.isEmpty()) {
                    System.out.println("No items found for that brand.");
                    return cartItems;
                } else {
                    Set<String> namesByBrand = filteredByBrand.stream()
                            .map(Item::getModel)
                            .collect(Collectors.toSet());

                    System.out.println();
                    System.out.println("Select a model from the following: ");
                    System.out.println();
                    namesByBrand.forEach(System.out::println);
                    System.out.println();
                    System.out.print("Enter model name: ");
                    String selectedName = sc.nextLine().trim();

                    List<Item> filteredByName = filteredByBrand.stream()
                            .filter(item -> item.getModel().equalsIgnoreCase(selectedName)).toList();

                    if (filteredByName.isEmpty()) {
                        System.out.println("No items found for that model.");
                        return cartItems;
                    } else {
                        Set<String> colorsByName = filteredByName.stream()
                                .map(item -> item.getColor_id().getName())
                                .collect(Collectors.toSet());

                        System.out.println();
                        System.out.println("Select a color from the following: ");
                        System.out.println();
                        colorsByName.forEach(System.out::println);
                        System.out.println();
                        System.out.print("Enter color: ");
                        String selectedColor = sc.nextLine().trim();

                        List<Item> filteredByColor = filteredByName.stream()
                                .filter(item -> item.getColor_id().getName().equalsIgnoreCase(selectedColor)).toList();

                        if (filteredByColor.isEmpty()) {
                            System.out.println("No items found for that color.");
                            return cartItems;
                        } else {
                            Set<String> sizesByColor = filteredByColor.stream()
                                    .map(item -> item.getSize_id().getSize())
                                    .collect(Collectors.toSet());

                            System.out.println();
                            System.out.println("Select a size from the following: ");
                            System.out.println();
                            sizesByColor.forEach(System.out::println);
                            System.out.println();
                            System.out.print("Enter size: ");
                            String selectedSize = sc.nextLine().trim();

                            selectedItem = filteredByColor.stream()
                                    .filter(item -> item.getSize_id().getSize().equals(selectedSize))
                                    .findFirst()
                                    .orElse(null);
                        }
                    }
                }
            }

            // item är nu valt och
            if (selectedItem != null) {
                System.out.println();
                System.out.println("You have selected the following item:");
                System.out.println("----------------------------");
                System.out.println(selectedItem.minimumToString());
                System.out.println("----------------------------");
                System.out.println("Do you want to add it to the cart? ");
                System.out.println("1. Yes");
                System.out.println("2. No");
                System.out.println("Enter your choice: ");

                int choiceAddToCart = sc.nextInt();

                switch (choiceAddToCart) {
                    case 1:
                        System.out.println("Enter quantity: ");
                        int quantity = sc.nextInt();
                        if (quantity <= 0) {
                            System.out.println("Invalid quantity. Please try again.");
                        } else {
                            for (int i = 0; i < quantity; i++) { // eftersom min stored procedure tar emot quantity
                                cartItems.add(selectedItem);
                            }
                            System.out.println("Item has been added to the cart.");
                        } break;
                    case 2:
                        System.out.println("Item has not been added to the cart.");
                        break;
                    default:
                        System.out.println("Invalid input. Please try again.");
                        break;
                }

                System.out.println("--------------------");
                System.out.println("What do you want to do next? ");
                System.out.println("1. Add more items to cart");
                System.out.println("2. Checkout");
                System.out.println("3. Exit");
                System.out.println("Enter your choice: ");

                int continueShopping = sc.nextInt();

                switch (continueShopping) {
                    case 1:
                        startShopping();
                    case 2:
                        // TODO tillbaka tll main, visa kundvagnen
                        return cartItems;
                    case 3:
                        System.out.println("Thank you for shopping with us. See you next time!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid input. Please try again.");
                        break;
                }
            } else {
                System.out.println("No items found.");
            }
            return null;
        } return null;
    }
    public static double getSubtotal() {
        double subtotal = 0;
        for (Item item : cartItems) {
            subtotal += item.getPrice();
        }
        return subtotal;
    }
    public static void printItems(Item i) {
        System.out.println(
                i.getBrand_id().getName() + " " +
                        i.getModel() + ", " +
                        i.getColor_id().getName() + ", " +
                        i.getSize_id().getSize() + ", Price: " +
                        i.getPrice() +" SEK");
    }
}

