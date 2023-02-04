package Reports;
import DBTables.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static Reports.ReportsRepository.getAllItems;

public class ItemFiltering {

    public ItemFiltering() {

    }
    public static void getSalesByBrandColorSize(String input) {
        List<Item> result = null;
        String in = input.toLowerCase();
        String choice;
        if (in.equals("brand")) {
            choice = displayAllBrands();
            if (choice != null) {
                result = getSalesByFilter(new BrandFilter(choice));
            } else {
                return;
            }
        }
        else if (in.equals("color")) {
            choice = displayAllColors();
            if (choice!= null) {
                result = getSalesByFilter(new ColorFilter(choice));
            } else {
                return;
            }
        }
        else {
            choice = displayAllSizes();
            if (choice != null) {
                result = getSalesByFilter(new SizeFilter(choice));
            } else {
                return;
            }
        }
        if (result == null) {
            System.out.println("No items found");
        } else {
            for (Item item : result) {
                System.out.println(item.minimumToString());
            }
        }
    }

    public static List<Item> getSalesByFilter(ItemFilter filter) {

        List<Item> allItems = getAllItems();
        List<Item> result = new ArrayList<>();

        if (allItems == null) {
            return result;
        } else {
            for (Item item : allItems) {
                if (filter.isMatching(item)) {
                    result.add(item);
                }
            }
        }
        return result;
    }
    static class BrandFilter implements ItemFilter {
        private final String brand;

        public BrandFilter(String brand) {
            this.brand = brand;
        }

        @Override
        public boolean isMatching(Item i) {
            return i.getBrand_id().getName().equals(brand);
        }

    }

    static class ColorFilter implements ItemFilter {
        private final String color;

        public ColorFilter(String color) {
            this.color = color;
        }

        @Override
        public boolean isMatching(Item i) {
            return i.getColor_id().getName().equals(color);
        }
    }

    static class SizeFilter implements ItemFilter {
        private final String size;

        public SizeFilter(String size) {
            this.size = size;
        }

        @Override
        public boolean isMatching(Item i) {
            return i.getSize_id().getSize().equals(size);
        }
    }
    static String displayAllBrands() {
        Scanner sc = new Scanner(System.in);
        String result;
        List<Item> allItems = getAllItems();
        if (allItems == null) {
            System.out.println("No items found");
            return null;
        } else {
            System.out.println("Choose one of the following brands: ");
            allItems.stream()
                    .map(item -> item.getBrand_id().getName())
                    .distinct()
                    .forEach(System.out::println);
            System.out.println();
            System.out.print("Enter your choice: ");
            result = sc.nextLine().toLowerCase();
            return result;
        }
    }
    static String displayAllColors() {
        Scanner sc = new Scanner(System.in);
        String result;
        List<Item> allItems = getAllItems();
        if (allItems == null) {
            System.out.println("No items found");
            return null;
        } else {
            System.out.println("Choose one of the following colors: ");
            allItems.stream()
                    .map(item -> item.getColor_id().getName())
                    .distinct()
                    .forEach(System.out::println);
            System.out.println();
            System.out.print("Enter your choice: ");
            result = sc.nextLine().toLowerCase();
            return result;
            }
    }
    static String displayAllSizes() {
        Scanner sc = new Scanner(System.in);
        String result;
        List<Item> allItems = getAllItems();
        if (allItems == null) {
            System.out.println("No items found");
            return null;
        }
        else {
            System.out.println("Choose one of the following sizes: ");
            allItems.stream()
                    .map(item -> item.getSize_id().getSize())
                    .distinct()
                    .forEach(System.out::println);
            System.out.println();
            System.out.print("Enter your choice: ");
            result = sc.nextLine().toLowerCase();
            return result;
            }
    }
}
