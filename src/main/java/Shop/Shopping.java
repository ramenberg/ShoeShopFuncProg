package Shop;

import DBTables.Brand;
import DBTables.Item;

import java.util.*;
import java.util.stream.Collectors;

import static Shop.Repository.getAllItemsInStock;

public class Shopping {

    static List<Item> cartItems = new ArrayList<>();

    public static List<Item> startShopping() {
        Scanner sc = new Scanner(System.in);
        List<Item> allItems = getAllItemsInStock();
        Item selectedItem;

        Set<String> allBrands = allItems.stream()
                .map(Item::getBrand_id)
                .map(Brand::getName)
                .collect(Collectors.toSet());

        if (allBrands.isEmpty()) {
            System.out.println("No items in stock.");
            return cartItems;

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

        if (selectedItem != null) {
            System.out.println();
            System.out.println("You have selected the following item:");
            System.out.println("----------------------------");
            System.out.println(
                    selectedItem.getBrand_id().getName() + " " +
                    selectedItem.getModel() + ", " +
                    selectedItem.getColor_id().getName() + ", " +
                    selectedItem.getSize_id().getSize() + ", Price: " +
                    selectedItem.getPrice() +" SEK");
            System.out.println("----------------------------");
            System.out.println("Do you want to add it to the cart? ");
            System.out.println("1. Yes");
            System.out.println("2. No");
            System.out.println("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    cartItems.add(selectedItem);
                    System.out.println("Item has been added to the cart.");
                case 2:
                    break;
                default:
                    System.out.println("Invalid input. Please try again.");
                    break;
            }
            System.out.println("--------------------");
            System.out.println("What do you want to do next? ");
            System.out.println("1. Add more items to cart");
            System.out.println("2. Checkout");
            System.out.println("Enter your choice: ");
            int continueShopping = sc.nextInt();

            switch (continueShopping) {
                case 1:
                    startShopping();
                case 2:
                    // TODO tillbaka tll main, visa kundvagnen
                    return cartItems;
                default:
                    System.out.println("Invalid input. Please try again.");
                    break;
            }
        } return null;
    }
    public static double getSubtotal() {
        double subtotal = 0;
        for (Item item : cartItems) {
            subtotal += item.getPrice();
        }
        return subtotal;
    }
}

