package Reports;

import DBTables.*;

import java.io.FileInputStream;
import java.sql.*;
import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

public class ReportsRepository {
    static final Properties p = new Properties();

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
