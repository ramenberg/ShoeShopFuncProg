//package TestFiltering;
//
//import DBTables.*;
//import Reports.ReportMethods;
//import Reports.ReportsRepository;
//import org.testng.annotations.Test;
//
//import java.text.Collator;
//import java.util.*;
//import java.util.stream.Collectors;
//
//import static Reports.ReportsRepository.*;
//
//public class Main {
//
//    public Main() {
//        ReportsRepository repo = new ReportsRepository();
//    }
//    public static void displayListOfAllValuesFromSelectedSearchFeature() {
//        List<Item> allItems = ReportsRepository.getAllItems();
//
//        List<String> selectedList;
//        selectedList = allItems.stream()
//                .map(Item::getBrand_id)
//                .map(Brand::getName)
//                .distinct()
//                .sorted().collect(Collectors.toList());
//        if (selectedList.isEmpty()) {
//            System.out.println("No items found for that brand.");
//        } else {
//            System.out.println();
//            System.out.println("Select a brand from the following: ");
//            System.out.println();
//            selectedList.forEach(System.out::println);
//        }
//    }
//
//    @Test
//    public void getAllCitiesFromCustomers() {
//        List<Customer> customers = ReportsRepository.getAllCustomers();
//        List<Customer> result = new ArrayList<>();
//        customers.stream()
//                .filter(c -> c.getCity().equals("Sundsvall"))
//                .forEachOrdered(result::add);
//    }
//    @Test
//    public static void listOfAllCustomersAndNumberOfOrders() {
//        List<Customer> allCustomers = ReportsRepository.getAllCustomers();
//        List<Orders> allOrders = ReportsRepository.getAllOrders();
//
//        Map<Customer, Long> customerOrders = allOrders.stream()
//                .collect(Collectors.groupingBy(order -> allCustomers.stream()
//                        .filter(customer -> customer.getId() == order.getCustomer_id())
//                        .findFirst().orElse(null), Collectors.counting()));
//
//        System.out.printf("%-15s %-12s %12s", "First name", "Last name", "Order count");
//        System.out.println();
//        System.out.println("-----------------------------------------");
//        customerOrders.forEach((customer, orderCount) -> {
//            System.out.printf("%-15s %-12s %12s",
//                    customer.getFirst_name(),
//                    customer.getLast_name(),
//                    orderCount);
//            System.out.println();
//        });
//    }
//    @Test
//    public void listOfAllCustomersWhoHavePurchasedSomethingFromACertainBrand() {
//        // test input
//        String selectedBrand = "Nike";
//        Collator collator = Collator.getInstance(new Locale("sv", "SE"));
//        //
//        List<Item> allItems = ReportsRepository.getAllItems();
//        List<Item> filteredByBrand = allItems.stream()
//                .filter(item -> item.getBrand_id().getName().equalsIgnoreCase(selectedBrand))
//                .distinct()
//                .sorted(Comparator.comparing(item -> item.getBrand_id().getName(), collator)).toList();
//
//        List<Orders> allOrders = getAllOrders();
//        List<OrdersItem> allOrdersItems = getAllOrdersItem();
//        List<Customer> allCustomers = getAllCustomers();
//
//        // filtrerar listan efter märke och mappar med alla kunder som har beställt något från valt märke
//        Set<Customer> customers = ReportMethods.listFilteredByAttribute(filteredByBrand, allOrders, allOrdersItems, allCustomers);
//
//        if (customers.isEmpty()) {
//            System.out.println("No customers found who have ordered any of these items.");
//        } else {
//            System.out.println("All customers who have ordered anything from the brand " + selectedBrand + ":");
//            System.out.println();
//            ReportMethods.printCustomersFromSet(customers);
//            System.out.println();
//        }
//    }
//    @Test
//    public void printAllCustomersAndTheirTotalPrice() {
//        List<Customer> allCustomers = ReportsRepository.getAllCustomers();
//        List<Orders> allOrders = ReportsRepository.getAllOrders();
//
//        Map<Customer, Double> customerOrders = allOrders.stream()
//                .collect(Collectors.groupingBy(order -> allCustomers.stream()
//                        .filter(customer -> customer.getId() == order.getCustomer_id())
//                        .findFirst().orElse(null),
//                        Collectors.summingDouble(Orders::getTotal_price)));
//
//        System.out.printf("%-15s %12s", "Customer", "Total value of all orders");
//        System.out.println();
//        System.out.println("-----------------------------------------");
//        customerOrders.forEach((customer, sum) -> {
//            System.out.printf("%-15s %-12s %12s",
//                    customer.getFirst_name(),
//                    customer.getLast_name(),
//                    sum + " SEK.");
//            System.out.println();
//        });
//    }
//}
