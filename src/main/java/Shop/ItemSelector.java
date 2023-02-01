package Shop;

import DBTables.Item;

import java.util.*;
import java.util.stream.Collectors;

import static Shop.Repository.addToCart;
import static Shop.Repository.getAllItemsInStock;

public class ItemSelector {

    private static ArrayList<Item> items;

    public ItemSelector() {
        items = getAllItemsInStock();
        assert items != null;

        Set<String> brands = new HashSet<>();
        items.forEach(item -> brands.add(item.getBrand_id().getName()));
        System.out.println("Available brands: " + brands);

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter brand name: ");
        String selectedBrand = sc.nextLine();

        List<String> names = items.stream()
                .filter(item -> item.getBrand_id().getName().equals(selectedBrand))
                .map(item -> item.getModel())
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Available names for brand " + selectedBrand + ": " + names);

        System.out.print("Enter name: ");
        String selectedName = sc.nextLine();

        List<String> colors = items.stream()
                .filter(item -> item.getBrand_id().getName().equals(selectedBrand)
                        && item.getModel().equals(selectedName))
                .map(item -> item.getColor_id().getName())
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Available colors for brand " + selectedBrand + " and name " + selectedName + ": " + colors);

        System.out.print("Enter color: ");
        String selectedColor = sc.nextLine();

        List<String> sizes = items.stream()
                .filter(item -> item.getBrand_id().getName().equals(selectedBrand)
                        && item.getModel().equals(selectedName)
                        && item.getColor_id().getName().equals(selectedColor))
                .map(item -> item.getSize_id().getSize())
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Available sizes for brand " + selectedBrand + ", name " + selectedName + ", and color " + selectedColor + ": " + sizes);

        System.out.print("Enter size: ");
        String selectedSize = sc.nextLine();

        Item selectedItem = items.stream()
                .filter(item -> item.getBrand_id().getName().equals(selectedBrand)
                        && item.getModel().equals(selectedName)
                        && item.getColor_id().getName().equals(selectedColor)
                        && item.getSize_id().getSize().equals(selectedSize))
                .findFirst()
                .orElse(null);
        if (selectedItem != null) {
            System.out.println("Selected item: " + selectedItem);
            System.out.print("Do you want to add it to the cart? ");
            System.out.println("1. Yes");
            System.out.println("2. No");
            System.out.println("3. Cancel");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    // TODO addToCart()
                    System.out.println("Item added to cart!");
                    break;
                case 2:
                    System.out.println("Item not added to cart!");
                    break;
                case 3:
                    System.out.println("Cancelled!");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } else {
            System.out.println("Item not found!");
        }
    }
}

