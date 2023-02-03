package Reports;

import DBTables.*;

import java.io.FileInputStream;
import java.sql.*;
import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

public class ReportsRepository {
    static final Properties p = new Properties();
    static Collator collator = Collator.getInstance(new Locale("sv", "SE"));

    public ReportsRepository() {
        try {
            p.load(new FileInputStream("src/main/resources/dbsettings.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // osorterad lista av alla items i databasen
    public static ArrayList<Item> getAllItems() {
        new ReportsRepository();
        ArrayList<Item> allItemsList = new ArrayList<>();

        String sql= "SELECT DISTINCT i.*, b.*, c.*, s.* " +
                "FROM item i " +
                "JOIN brand b ON i.brand_id = b.id " +
                "JOIN color c ON i.color_id = c.id " +
                "JOIN size s ON i.size_id = s.id ";

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"))) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            // lista över alla items i databasen
            while (rs.next()) {
                Item item = new Item(
                        rs.getInt("i.id"),
                        new Brand(rs.getInt("b.id"), rs.getString("b.name")),
                        rs.getString("model"),
                        new Size(rs.getInt("s.id"), rs.getString("s.size")),
                        rs.getDouble("price"),
                        new Color(rs.getInt("c.id"), rs.getString("c.name")),
                        rs.getInt("stock_balance"));
                allItemsList.add(item);
            }
            return allItemsList;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("SQL exception");
            return null;
        }
    }
    public static List<Customer> getAllCustomers() {
        new ReportsRepository();
        List<Customer> allCustomersList = new ArrayList<>();

        String sql = "SELECT * FROM customer";

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"))) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            // lista över alla items i databasen
            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("address"),
                        rs.getString("city"),
                        rs.getString("phone_number"),
                        rs.getString("email")
                );
                allCustomersList.add(customer);
            }
            return allCustomersList;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("SQL exception");
            return null;
        }
    }

    public static List<Orders> getAllOrders() {
        new ReportsRepository();
        List<Orders> allOrdersList = new ArrayList<>();

        String sql = "SELECT * from Orders";

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"))) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            // lista över alla items i databasen
            while (rs.next()) {
                Orders order = new Orders(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getString("orders_date"),
                        rs.getDouble("total_price"));
                allOrdersList.add(order);
            }
            return allOrdersList;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("SQL exception");
            return null;
        }
    }

    public static List<OrdersItem> getAllOrdersItem() {
        new ReportsRepository();
        List<OrdersItem> allOrdersItemList = new ArrayList<>();

        String sql = "SELECT * from Orders_Item";

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"))) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            // lista över alla items i databasen
            while (rs.next()) {
                OrdersItem ordersItem = new OrdersItem(
                        rs.getInt("orders_id"),
                        rs.getInt("item_id"),
                        rs.getInt("quantity"));
                allOrdersItemList.add(ordersItem);
            }
            return allOrdersItemList;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("SQL exception");
            return null;
        }
    }

    // Metoder
    public static void getSalesByBrandColorSize() {
        new ReportsRepository();
        List<Item> salesByBrandColorSizeList = new ArrayList<>();
        List<Item> allItems = getAllItems();

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
                case 1:

                    List<String> allBrands = allItems.stream()
                            .map(Item::getBrand_id)
                            .map(Brand::getName)
                            .distinct()
                            .sorted().collect(Collectors.toList());

                    if (allBrands.isEmpty()) {
                        System.out.println("No brands to show.");
                        break;
                    } else {
                        System.out.println();
                        System.out.println("Select a brand from the following: ");
                        System.out.println();
                        allBrands.forEach(System.out::println);
                        System.out.println();
                        System.out.print("Enter brand name: ");

                        String selectedBrand = sc.next().trim();

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
                                for (Customer customer : customers) {
                                    System.out.println(customer.getFirst_name() + " " + customer.getLast_name()
                                    + ", " + customer.getAddress() + ", " + customer.getCity());
                                }
                                System.out.println();
                            }
                        }
                    } case 2:
                    List<String> allColors = allItems.stream()
                            .map(Item::getColor_id)
                            .map(Color::getName)
                            .distinct()
                            .sorted().collect(Collectors.toList());
                    if (allColors.isEmpty()) {
                        System.out.println("No colors to show.");
                        break;
                    } else {
                        System.out.println();
                        System.out.println("Select a color from the following: ");
                        System.out.println();
                        allColors.forEach(System.out::println);
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

                            // make a set list of customers who have ordered any of the items in the filteredByColor list
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
                                for (Customer customer : customers) {
                                    System.out.println(customer.getFirst_name() + " " + customer.getLast_name()
                                            + ", " + customer.getAddress() + ", " + customer.getCity());
                                }
                                System.out.println();
                            }
                        }
                    } case 3:
                    }

        }
        catch (Exception e) {
            throw new RuntimeException(e);
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

//    public static List<Brand> getAllBrands() {
//        List<Brand> allBrandsList = new ArrayList<>();
//
//        String sql = "SELECT * FROM brand";
//
//        try (Connection con = DriverManager.getConnection(
//                p.getProperty("connectionString"),
//                p.getProperty("name"),
//                p.getProperty("password"))) {
//            Statement st = con.createStatement();
//            ResultSet rs = st.executeQuery(sql);
//
//            // lista över alla items i databasen
//            while (rs.next()) {
//                Brand brand = new Brand(
//                        rs.getInt("id"),
//                        rs.getString("name"));
//                allBrandsList.add(brand);
//            }
//            return allBrandsList;
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            System.out.println("SQL exception");
//            return null;
//        }
//    }
//    public static List<Color> getAllColors() {
//        List<Color> allColorsList = new ArrayList<>();
//
//        String sql = "SELECT * FROM color";
//
//        try (Connection con = DriverManager.getConnection(
//                p.getProperty("connectionString"),
//                p.getProperty("name"),
//                p.getProperty("password"))) {
//            Statement st = con.createStatement();
//            ResultSet rs = st.executeQuery(sql);
//
//            // lista över alla items i databasen
//            while (rs.next()) {
//                Color color = new Color(
//                        rs.getInt("id"),
//                        rs.getString("name"));
//                allColorsList.add(color);
//            }
//            return allColorsList;
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            System.out.println("SQL exception");
//            return null;
//        }
//    }
//    public static List<Size> getAllSizes() {
//        List<Size> allSizesList = new ArrayList<>();
//
//        String sql = "SELECT * FROM size";
//
//        try (Connection con = DriverManager.getConnection(
//                p.getProperty("connectionString"),
//                p.getProperty("name"),
//                p.getProperty("password"))) {
//            Statement st = con.createStatement();
//            ResultSet rs = st.executeQuery(sql);
//
//            // lista över alla items i databasen
//            while (rs.next()) {
//                Size size = new Size(
//                        rs.getInt("id"),
//                        rs.getString("name"));
//                allSizesList.add(size);
//            }
//            return allSizesList;
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            System.out.println("SQL exception");
//            return null;
//        }
//    }
}
