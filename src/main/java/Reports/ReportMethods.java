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
                    // visar lista över alla varumärken som finns i databasen
                    displayListOfAllValuesFromSelectedSearchFeature(1);
                    System.out.println();
                    System.out.print("Enter brand name: ");

                    String selectedBrand = sc.next().trim();

                    if (allItems == null) {
                        System.out.println("There is not enough data to perform this search.");
                    }
                    else {
                        // filtrerar listan av items efter valt märke och sorterar den
                        List<Item> filteredByBrand = allItems.stream()
                                .filter(item -> item.getBrand_id().getName().equalsIgnoreCase(selectedBrand))
                                .distinct()
                                .sorted(Comparator.comparing(item -> item.getBrand_id().getName(), collator)).toList();

                        if (filteredByBrand.isEmpty()) {
                            System.out.println("No items found for that brand.");
                        } else {
                            // eftersom ordrar och kund är mappade via order_item måste alla tre tabellerna/listorna hämtas
                            List<Orders> allOrders = getAllOrders();
                            List<OrdersItem> allOrdersItems = getAllOrdersItem();
                            List<Customer> allCustomers = getAllCustomers();

                            // om någon av listorna är tomma kan vi inte fortsätta
                            if (allOrders == null || allOrdersItems == null || allCustomers == null) {
                                System.out.println("There is not enough data to perform this search.");
                            }
                            else {
                                // anropar metod som filtrerar listan efter varumärke och mappar med alla
//                              // kunder som har beställt något från valt varumärke
                                Set<Customer> customers = listFilteredByAttribute(
                                        filteredByBrand, allOrders, allOrdersItems, allCustomers);

                                if (customers.isEmpty()) {
                                    System.out.println("No customers found who have ordered any of these items.");
                                } else {
                                    System.out.println("All customers who have ordered anything from the brand " + selectedBrand + ":");
                                    System.out.println();
                                    printCustomersFromSet(customers); // metod för bättre läslighet av utskriften
                                    System.out.println();
                                }
                            }
                        }
                        break;
                    }
                case 2: // color
                    // visar lista över alla färger som finns i databasen
                    displayListOfAllValuesFromSelectedSearchFeature(2);
                    System.out.println();
                    System.out.print("Enter color: ");

                    String selectedColor = sc.next().trim();

                    if (allItems == null) {
                        System.out.println("There is not enough data to perform this search.");
                    }
                    else {
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

                            if (allOrders == null || allOrdersItems == null || allCustomers == null) {
                                System.out.println("There is not enough data to perform this search.");
                            }
                            else {
                                // anropar metod som filtrerar listan efter färg och mappar med alla
                                // kunder som har beställt något från vald färg
                                Set<Customer> customers = listFilteredByAttribute(
                                        filteredByColor, allOrders, allOrdersItems, allCustomers);

                                if (customers.isEmpty()) {
                                    System.out.println("No customers found who have ordered any of these items.");
                                } else {
                                    System.out.println("All customers who have ordered anything in the color " + selectedColor + ":");
                                    System.out.println();
                                    printCustomersFromSet(customers);
                                    System.out.println();
                                }
                            }

                        }
                        break;
                    }
                case 3: // size
                    displayListOfAllValuesFromSelectedSearchFeature(3);
                    System.out.println();
                    System.out.print("Enter size: ");

                    String selectedSize = sc.next().trim();

                    if ( allItems == null) {
                        System.out.println("There is not enough data to perform this search.");
                    }
                    else {
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

                            if (allOrders == null || allOrdersItems == null || allCustomers == null) {
                                System.out.println("There is not enough data to perform this search.");
                            }
                            else {
                                // anropar metod som filtrerar listan efter storlek och mappar med alla
//                              // kunder som har beställt något från vald storlek
                                Set<Customer> customers = listFilteredByAttribute(
                                        filteredBySize, allOrders, allOrdersItems, allCustomers);

                                if (customers.isEmpty()) {
                                    System.out.println("No customers found who have ordered any of these items.");
                                } else {
                                    System.out.println("All customers who have ordered anything in the size " + selectedSize + ":");
                                    System.out.println();
                                    printCustomersFromSet(customers);
                                    System.out.println();
                                }
                            }
                            break;
                        }
                    }
                case 4:
                    break;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // denna metod tar in en filtrerad lista och mappar den med alla kunder som har beställt något från den filtrerade listan
    // invariabeln List<Item> filteredBy kan ta in både filteredByBrand, filteredByColor och filteredBySize
    public static Set<Customer> listFilteredByAttribute(List<Item> filteredBy, List<Orders> allOrders, List<OrdersItem> allOrdersItems, List<Customer> allCustomers) {
        return filteredBy.stream()
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
    }

    // metod för att skriva ut valda parametrar för alla kunder i ett set
    public static void printCustomersFromSet(Set<Customer> customers) {
        for (Customer customer : customers) {
            System.out.println(customer.getFirst_name() + " " + customer.getLast_name()
                    + ", " + customer.getAddress() + ", " + customer.getCity());
        }
    }

    // metoden används för att söka fram en lista över varumärken, färger eller storlekar
    // från databasen och som användaren kan välja mellan
    public static void displayListOfAllValuesFromSelectedSearchFeature(int input) {
        List<Item> allItems = ReportsRepository.getAllItems();

        List<String> selectedList;
        switch (input) {
            case 1 -> { // brands
                if (allItems == null) {
                    System.out.println("There is not enough data to perform this search.");
                } else {
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
                }
            }
            case 2 -> { // colors
                if (allItems == null) {
                    System.out.println("There is not enough data to perform this search.");
                } else {
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
                }
            }
            case 3 -> { // sizes
                if (allItems == null) {
                    System.out.println("There is not enough data to perform this search.");
                } else {
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
                }
            }
            default -> System.out.println("Invalid input.");
        }
    }
    // metoden används för sökfunktionen där kunder listas med antal ordrar eller totala köpsumman
    public static void printAllCustomersAndSelectedInfo(String infoType) {
        List<Customer> allCustomers = ReportsRepository.getAllCustomers();
        List<Orders> allOrders = ReportsRepository.getAllOrders();

        if (allOrders == null || allCustomers == null) {
            System.out.println("There are no orders and/or customers in the system.");
        } else {
            if (infoType.equalsIgnoreCase("orders")) {
                Map<Customer, Long> customerOrders = allOrders.stream()
                        .collect(Collectors.groupingBy(order -> allCustomers.stream()
                                        .filter(customer -> customer.getId() == order.getCustomer_id())
                                        .findFirst().orElse(null),
                                Collectors.counting()));

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
            } else if (infoType.equalsIgnoreCase("total")) {
                Map<Customer, Double> customerOrders = allOrders.stream()
                        .collect(Collectors.groupingBy(order -> allCustomers.stream()
                                        .filter(customer -> customer.getId() == order.getCustomer_id())
                                        .findFirst().orElse(null),
                                Collectors.summingDouble(Orders::getTotal_price)));

                System.out.printf("%-15s %12s", "Customer", "Total value of all orders");
                System.out.println();
                System.out.println("-----------------------------------------");
                customerOrders.forEach((customer, sum) -> {
                    System.out.printf("%-15s %-12s %12s",
                            customer.getFirst_name(),
                            customer.getLast_name(),
                            sum + " SEK");
                    System.out.println();
                });
            }
        }
    }
}
