package Shop;

import DBTables.*;

import java.text.Collator;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static Shop.Repository.getAllItems;

public class Shopping {

    static final List<Item> cartItems = new ArrayList<>();
    static Collator collator = Collator.getInstance(new Locale("sv", "SE"));

    public static List<Item> startShopping() {

        collator.setStrength(Collator.PRIMARY);
        Scanner sc = new Scanner(System.in);
        List<Item> allItems = getAllItems(); // lista över alla items i databasen
        Item selectedItem = null;

        try {
            if (allItems == null) {
                System.out.println("No items in stock.");
                return null;
            }
            else {
                while (true) {

                    System.out.println();
                    System.out.println("What do you want to do? ");
                    System.out.println();
                    System.out.println("1. Search for item");
                    System.out.println("2. View cart");
                    System.out.println("3. Checkout ");
                    System.out.println("4. Cancel ");
                    System.out.println();
                    System.out.print("Enter your choice: ");
                    try
                        {
                            int choiceShopping = Integer.parseInt(sc.nextLine().trim());
                            switch (choiceShopping) {
                                case 1:
                                    System.out.println();
                                    printAllItemsList(allItems); // skriver ut en lista över alla varor, även de som inte finns i lager

                                    // lista över alla varumärken från listan över alla items i databasen.
                                    // om man bara vill ge användaren möjlighet att välja bland varumärken
                                    // som finns i lager ändras >= 0 till > 0.
                                    List<String> allBrands = allItems.stream()
                                            .filter(item -> item.getStock_balance() >= 0)
                                            .map(Item::getBrand_id)
                                            .map(Brand::getName)
                                            .distinct()
                                            .sorted().toList();

                                    if (allBrands.isEmpty()) {
                                        System.out.println("\nNo items in stock.");
                                        break;
                                    } else {
                                        System.out.println("\nSelect a brand from the following:");
                                        System.out.println();
                                        allBrands.forEach(System.out::println);
                                        System.out.println();
                                        System.out.print("Enter brand name: ");
                                        String selectedBrand = sc.nextLine().trim();

                                        List<Item> filteredByBrand = allItems.stream()
                                                .filter(item -> item.getBrand_id().getName().equalsIgnoreCase(selectedBrand))
                                                .distinct()
                                                .sorted(Comparator.comparing(item -> item.getBrand_id().getName(), collator)).toList();

                                        if (filteredByBrand.isEmpty()) {
                                            System.out.println("No items found for that brand.");
                                        } else {
                                            List<String> modelsByBrand = filterAndGetNames(filteredByBrand,
                                                    item -> item.getModel(), collator,
                                                    item -> item.getBrand_id().getName().equalsIgnoreCase(selectedBrand));

                                            System.out.println();
                                            System.out.println("Select a model from the following: ");
                                            System.out.println();
                                            modelsByBrand.forEach(System.out::println);
                                            System.out.println();
                                            System.out.print("Enter model name: ");
                                            String selectedModel = sc.nextLine().trim();

                                            List<Item> filteredByModel = filteredByBrand.stream()
                                                    .filter(item -> item.getModel().equalsIgnoreCase(selectedModel))
                                                    .distinct()
                                                    .sorted(Comparator.comparing(Item::getModel, collator)).toList();

                                            if (filteredByModel.isEmpty()) {
                                                System.out.println("No items found for that model.");
                                            } else {
                                                List<String> colorsByModel = filterAndGetNames(filteredByModel,
                                                        item -> item.getColor_id().getName(), collator,
                                                        item -> item.getModel().equalsIgnoreCase(selectedModel));

                                                System.out.println();
                                                System.out.println("Select a color from the following: ");
                                                System.out.println();
                                                colorsByModel.forEach(System.out::println);
                                                System.out.println();
                                                System.out.print("Enter color: ");
                                                String selectedColor = sc.nextLine().trim();

                                                List<Item> filteredByColor = filteredByModel.stream()
                                                        .filter(item -> item.getColor_id().getName().equalsIgnoreCase(selectedColor))
                                                        .distinct()
                                                        .sorted(Comparator.comparing(item -> item.getColor_id().getName(), collator)).toList();

                                                if (filteredByColor.isEmpty()) {
                                                    System.out.println("No items found for that color.");
                                                } else {
                                                    List<String> sizesByColor = filterAndGetNames(filteredByColor,
                                                            item -> item.getSize_id().getSize(), collator,
                                                            item -> item.getModel().equalsIgnoreCase(selectedModel) &&
                                                                    item.getColor_id().getName().equalsIgnoreCase(selectedColor));

                                                    System.out.println();
                                                    System.out.println("Select a size from the following: ");
                                                    System.out.println();
                                                    sizesByColor.forEach(System.out::println);
                                                    System.out.println();
                                                    System.out.print("Enter size: ");
                                                    String selectedSize = sc.nextLine().trim();

                                                    selectedItem = filteredByColor.stream()
                                                            .filter(item -> item.getSize_id().getSize().equalsIgnoreCase(selectedSize))
                                                            .distinct().min(Comparator.comparing(item -> item.getSize_id().getSize(), collator))
                                                            .orElse(null);
                                                }
                                            }
                                        }
                                    }
                                    // item är nu valt
                                    if (selectedItem != null) {
                                        System.out.println();
                                        System.out.println("You have selected the following item:");
                                        System.out.println();
                                        System.out.println(selectedItem.minimumToString());
                                        System.out.println();
                                        System.out.println("Do you want to add it to the cart? ");
                                        System.out.println("1. Yes");
                                        System.out.println("2. No");
                                        System.out.println("Enter your choice: ");

                                        int choiceAddToCart = sc.nextInt();

                                        switch (choiceAddToCart) {
                                            case 1 -> {
                                                System.out.println("Enter quantity: ");
                                                int quantity = sc.nextInt();
                                                if (quantity <= 0) {
                                                    System.out.println("Invalid quantity. Please try again.");
                                                } else if (quantity > selectedItem.getStock_balance()) {
                                                    System.out.println("Item is out of stock. Please choose another item.");
                                                }
                                                else {
                                                    for (int i = 0; i < quantity; i++) { // eftersom min stored procedure tar emot quantity
                                                        cartItems.add(selectedItem);
                                                    }
                                                    System.out.println("Item has been added to the cart.");
                                                }
                                            }
                                            case 2 -> System.out.println("Item has not been added to the cart.");
                                            default -> System.out.println("Invalid input. Please try again.");
                                        }

                                        System.out.println("--------------------");
                                        System.out.println("What do you want to do next? ");
                                        System.out.println("1. Add more items to cart");
                                        System.out.println("2. View cart and checkout. ");
                                        System.out.println("3. Cancel ");
                                        System.out.println("Enter your choice: ");

                                        int continueShopping = sc.nextInt();

                                        switch (continueShopping) {
                                            case 1:
                                                startShopping();
                                            case 2:
                                                return cartItems;
                                            case 3:
                                                System.out.println("Thank you for shopping!");
                                                break;
                                            default:
                                                System.out.println("Invalid input. Please try again.");
                                                break;
                                        }
                                    } else {
                                        System.out.println("No items found.");
                                    }

                                case 2:
                                    System.out.println();
                                    System.out.println("Showing cart: ");
                                    System.out.println();
                                    if (cartItems.isEmpty()) {
                                        System.out.println("Your cart is empty.");
                                    } else {
                                        for (Item item : cartItems) {
                                            System.out.println(item.minimumToString());
                                        }
                                        System.out.println("Subtotal: " + getSubtotal() + " SEK");
                                    }
                                    System.out.println();
                                    break;
                                case 3:
                                    if (cartItems.isEmpty()) {
                                        System.out.println("Your cart is empty.");
                                        break;
                                    } else {
                                        return cartItems;
                                    }
                                case 4:
                                    break;
                                default:
                                    System.out.println("Invalid input. Please try again.");
                            }
                        }   catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please try again.");
                        }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    // higher order function
    public static List<String> filterAndGetNames(List<Item> allItems, Function<Item, String> valueToMapBy,
                                                 Collator comparator, Predicate<Item> filter) {
        return allItems.stream()
                .filter(filter)
                .distinct()
                .sorted(Comparator.comparing(valueToMapBy, comparator))
                .map(valueToMapBy)
                .distinct()
                .sorted()
                .toList();
    }

    public static double getSubtotal() {
        double subtotal = 0;
        for (Item item : cartItems) {
            subtotal += item.getPrice();
        }
        return subtotal;
    }
    public static String getItemById(int id) {
        List<Item> allItems = getAllItems();
        Item i;
        if (allItems == null || allItems.isEmpty()) {
            return null;
        }
        else {
            i = allItems.stream()
                    .filter(item -> item.getId() == id)
                    .findFirst()
                    .orElse(null);
        }
            if (i == null) {
                return null;
            }
            else {
                return i.minimumToString();
            }
    }
    public static void printAllItemsList (List<Item> list) {
        assert list != null;
        System.out.println(String.format("%-11s", "Brand") +
                String.format("%-21s", "Model") +
                String.format("%-16s", "Color") +
                String.format("%-11s", "Size") +
                String.format("%-23s", "Price") +
                String.format("%-20s", "Stock"));
        System.out.println("--------------------------------------------" +
                "---------------------------------------------------");
        for (Item item : list) {
            boolean isAvailable = item.getStock_balance() > 0;
            String inStock;
            if (isAvailable) {
                inStock = "Yes";
            } else {
                inStock = "No";
            }
            System.out.printf("%-10s %-20s %-15s %-10s %-22s %-20s%n",
                    item.getBrand_id().getName(),
                    item.getModel(),
                    "Color: " + item.getColor_id().getName(),
                    "Size: " + item.getSize_id().getSize(),
                    "Price: " + String.format("%.2f", item.getPrice()) + " SEK",
                    "In stock: " + inStock);
        }
    }
}

