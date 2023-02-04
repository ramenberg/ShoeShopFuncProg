package Reports;

import DBTables.*;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

import static Reports.ReportsRepository.*;

public class ReportMethods {

    static Collator collator = Collator.getInstance(new Locale("sv", "SE"));

    public ReportMethods() {

    }

    // Metoder
    public static void getSalesByBrandColorSize() {
        new ReportsRepository();
        List<Item> allItems = ReportsRepository.getAllItems();

        System.out.println("What do you want to search for? ");
        System.out.println("1. Brand");
        System.out.println("2. Color");
        System.out.println("3. Size");
        System.out.println("4. Cancel");
        System.out.println();
        System.out.print("Enter your choice: ");
        Scanner sc = new Scanner(System.in);
        System.out.println();
        try {
            int choice = sc.nextInt();
            switch (choice) {
                case 1: // brand
                    displayListOfAllValuesFromSelectedSearchFeature(1);
                    System.out.println();
                    System.out.print("Enter brand name: ");

                    String selectedBrand = sc.next().trim();

                    assert allItems != null;
                    List<Item> filteredByBrand = allItems.stream()
                            .filter(item -> item.getBrand_id().getName().equalsIgnoreCase(selectedBrand))
                            .distinct()
                            .sorted(Comparator.comparing(item -> item.getBrand_id().getName(), collator)).toList();

                    if (filteredByBrand.isEmpty()) {
                        System.out.println("No items found for that brand.");
                    } else {
                        List<Orders> allOrders = getAllOrders();
                        List<OrdersItem> allOrdersItems = getAllOrdersItem();
                        List<Customer> allCustomers = getAllCustomers();

                        // filtrerar listan efter märke och mappar med alla kunder som har beställt något från valt märke
                        Set<Customer> customers = filteredByBrand.stream()
                                .flatMap(item -> allOrdersItems.stream()
                                        .filter(ordersItem -> item.getId() == ordersItem.getItem_id())
                                        .flatMap(ordersItem -> allOrders.stream()
                                                .filter(order -> order.getId() == ordersItem.getOrder_id())
                                                .flatMap(order -> allCustomers.stream()
                                                        .filter(customer -> customer.getId() == order.getCustomer_id())
                                                )
                                        )
                                )
                                .collect(Collectors.toSet());

                        if (customers.isEmpty()) {
                            System.out.println("No customers found who have ordered any of these items.");
                        } else {
                            System.out.println("All customers who have ordered anything from the brand " + selectedBrand + ":");
                            System.out.println();
                            printCustomersFromSet(customers);
                            System.out.println();
                        }
                        break;
                    }
                case 2: // color
                    displayListOfAllValuesFromSelectedSearchFeature(2);
                    System.out.println();
                    System.out.print("Enter color: ");

                    String selectedColor = sc.next().trim();

                    List<Item> filteredByColor = allItems.stream()
                            .filter(item -> item.getColor_id().getName().equalsIgnoreCase(selectedColor))
                            .distinct()
                            .sorted(Comparator.comparing(item -> item.getBrand_id().getName(), collator)).toList();

                    if (filteredByColor.isEmpty()) {
                        System.out.println("No items found for that color.");
                    } else {
                        List<Orders> allOrders = getAllOrders();
                        List<OrdersItem> allOrdersItems = getAllOrdersItem();
                        List<Customer> allCustomers = getAllCustomers();

                        // filtrerar listan efter färg och mappar med alla kunder som har beställt något från vald färg
                        Set<Customer> customers = filteredByColor.stream()
                                .flatMap(item -> allOrdersItems.stream()
                                        .filter(ordersItem -> item.getId() == ordersItem.getItem_id())
                                        .flatMap(ordersItem -> allOrders.stream()
                                                .filter(order -> order.getId() == ordersItem.getOrder_id())
                                                .flatMap(order -> allCustomers.stream()
                                                        .filter(customer -> customer.getId() == order.getCustomer_id())
                                                )
                                        )
                                )
                                .collect(Collectors.toSet());

                        if (customers.isEmpty()) {
                            System.out.println("No customers found who have ordered any of these items.");
                        } else {
                            System.out.println("All customers who have ordered anything in the color " + selectedColor + ":");
                            System.out.println();
                            printCustomersFromSet(customers);
                            System.out.println();
                        }
                        break;
                    }
                case 3: // size
                    displayListOfAllValuesFromSelectedSearchFeature(3);
                    System.out.println();
                    System.out.print("Enter size: ");

                    String selectedSize = sc.next().trim();

                    List<Item> filteredBySize = allItems.stream()
                            .filter(item -> item.getSize_id().getSize().equalsIgnoreCase(selectedSize))
                            .distinct()
                            .sorted(Comparator.comparing(item -> item.getBrand_id().getName(), collator)).toList();

                    if (filteredBySize.isEmpty()) {
                        System.out.println("No items found for that size.");
                    } else {
                        List<Orders> allOrders = getAllOrders();
                        List<OrdersItem> allOrdersItems = getAllOrdersItem();
                        List<Customer> allCustomers = getAllCustomers();

                        // filtrerar listan efter storlek och mappar med alla kunder som har beställt något med vald storlek
                        Set<Customer> customers = filteredBySize.stream()
                                .flatMap(item -> allOrdersItems.stream()
                                        .filter(ordersItem -> item.getId() == ordersItem.getItem_id())
                                        .flatMap(ordersItem -> allOrders.stream()
                                                .filter(order -> order.getId() == ordersItem.getOrder_id())
                                                .flatMap(order -> allCustomers.stream()
                                                        .filter(customer -> customer.getId() == order.getCustomer_id())
                                                )
                                        )
                                )
                                .collect(Collectors.toSet());

                        if (customers.isEmpty()) {
                            System.out.println("No customers found who have ordered any of these items.");
                        } else {
                            System.out.println("All customers who have ordered anything in the size " + selectedSize + ":");
                            System.out.println();
                            printCustomersFromSet(customers);
                            System.out.println();
                        }
                        break;
                    }
                case 4:
                    break;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void printAllItemsList(List<Item> list) {
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

    public static void printCustomersFromSet(Set<Customer> customers) {
        for (Customer customer : customers) {
            System.out.println(customer.getFirst_name() + " " + customer.getLast_name()
                    + ", " + customer.getAddress() + ", " + customer.getCity());
        }
    }

    public static void displayListOfAllValuesFromSelectedSearchFeature(int input) {
        List<Item> allItems = ReportsRepository.getAllItems();

        List<String> selectedList;
        switch (input) {
            case 1: { // brands
                selectedList = allItems.stream()
                        .map(Item::getBrand_id)
                        .map(Brand::getName)
                        .distinct()
                        .sorted().collect(Collectors.toList());
                if (selectedList.isEmpty()) {
                    System.out.println("No items found for that brand.");
                } else {
                    System.out.println();
                    System.out.println("Select a brand from the following: ");
                    System.out.println();
                    selectedList.forEach(System.out::println);
                }
                break;
            }
            case 2: { // colors
                selectedList = allItems.stream()
                        .map(Item::getColor_id)
                        .map(Color::getName)
                        .distinct()
                        .sorted().collect(Collectors.toList());
                if (selectedList.isEmpty()) {
                    System.out.println("No items found for that color.");
                } else {
                    System.out.println();
                    System.out.println("Select a color from the following: ");
                    System.out.println();
                    selectedList.forEach(System.out::println);
                }
                break;
            }
            case 3: { // sizes
                selectedList = allItems.stream()
                        .map(Item::getSize_id)
                        .map(Size::getSize)
                        .distinct()
                        .sorted().collect(Collectors.toList());
                if (selectedList.isEmpty()) {
                    System.out.println("No items found for that size.");
                } else {
                    System.out.println();
                    System.out.println("Select a size from the following: ");
                    System.out.println();
                    selectedList.forEach(System.out::println);
                }
                break;
            } default: {
                System.out.println("Invalid input.");
            }
        }
    }
    public static void listOfAllCustomersAndNumberOfOrders() {
        List<Customer> allCustomers = ReportsRepository.getAllCustomers();
        List<Orders> allOrders = ReportsRepository.getAllOrders();

        Map<Customer, Long> customerOrders = allOrders.stream()
                .collect(Collectors.groupingBy(order -> allCustomers.stream()
                        .filter(customer -> customer.getId() == order.getCustomer_id())
                        .findFirst().orElse(null), Collectors.counting()));

        System.out.printf("%-15s %-12s %12s", "First name", "Last name", "Order count");
        System.out.println();
        System.out.println("-----------------------------------------");
        customerOrders.forEach((customer, orderCount) -> {
            System.out.printf("%-15s %-12s %12s",
                    customer.getFirst_name(),
                    customer.getLast_name(),
                    orderCount);
            System.out.println();
        });
    }
}
