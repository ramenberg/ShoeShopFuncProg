package Shop;

import DBTables.Customers;
import DBTables.Items;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Repository {

    static Properties p = new Properties();

    public Repository() {
        try {
            p.load(new FileInputStream("src/main/resources/dbsettings.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean validateLogin(String email, char[] password) {
        new Repository();
        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"))) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM customers WHERE email = '" + email + "' AND password = '" + new String(password) + "'");
            return rs.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    public static List<String> getBrandsAndNames() {
        new Repository();
        List<String> brandsAndNames = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"))) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT DISTINCT Items.name AS item_name, Brand.name AS brand_name " +
                    "FROM Items " +
                    "JOIN Brand ON Items.brand_id = Brand.id");
            while (rs.next()) {
                brandsAndNames.add(rs.getString("brand_name") + " " + rs.getString("item_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } return brandsAndNames;
    }
    public static List<ItemDetails> getItemsByBrandAndName(String brand, String name) {
        new Repository();
        List<ItemDetails> items = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"))) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT i.id, b.name as brand_name, i.name, s.size, c.name as color_name, i.price, i.stock_balance FROM Items i " +
                    "JOIN Brand b ON i.brand_id = b.id " +
                    "JOIN Sizes s ON i.size_id = s.id " +
                    "JOIN Colors c ON i.color_id = c.id " +
                    "WHERE b.name = '" + brand + "' AND i.name = '" + name + "' AND i.stock_balance > 0 ");
            while (rs.next()) {
                items.add(new ItemDetails(
                        rs.getInt("id"),
                        rs.getString("brand_name"),
                        rs.getString("name"),
                        rs.getString("color_name"),
                        rs.getString("size"),
                        rs.getDouble("price")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } return items;
    }

        public static Customers getCustomer(String email, char[] password) {
        new Repository();
        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"))) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM customers WHERE email = '" + email + "' AND password = '" + new String(password) + "'");
            if (rs.next()) {
                return new Customers(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("address"),
                        rs.getString("city"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getString("password"));
            } else {
                System.out.println("No customer found.");
                return null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("SQL exception");
            return null;
        }
    }

    // find an item in items by the brand, name, color and size. return the item id.
    public static int findItem(String brand, String name, String color, String size) {
        new Repository();
        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"))) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT i.id FROM Items i " +
                    "JOIN Brand b ON i.brand_id = b.id " +
                    "JOIN Sizes s ON i.size_id = s.id " +
                    "JOIN Colors c ON i.color_id = c.id " +
                    "WHERE b.name = '" + brand + "' AND i.name = '" + name + "' AND c.name = '" + color + "' AND s.size = '" + size + "'");
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                System.out.println("Item not found.");
                return 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("SQL exception");
            return 0;
        }
    }

    // get item by id
    public static ItemDetails getItemById(int item_id) {
        new Repository();
        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"))) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT i.id, b.name as brand_name, i.name, s.size, c.name as color_name, i.price, i.stock_balance FROM Items i " +
                    "JOIN Brand b ON i.brand_id = b.id " +
                    "JOIN Sizes s ON i.size_id = s.id " +
                    "JOIN Colors c ON i.color_id = c.id " +
                    "WHERE i.id = '" + item_id + "'");
            if (rs.next()) {
                return new ItemDetails(
                        rs.getInt("id"),
                        rs.getString("brand_name"),
                        rs.getString("name"),
                        rs.getString("color_name"),
                        rs.getString("size"),
                        rs.getDouble("price"));
            } else {
                System.out.println("Item not found.");
                return null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("SQL exception");
            return null;
        }
    }

    public static int addToCart(int item_id) {
        new Repository();
        int items;
        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"))) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM items WHERE id = '" + item_id + "'");
            if (rs.next()) {
                items = rs.getInt("id");
                System.out.println("Item added to cart.");
                return items;
            } else {
                System.out.println("Item not found.");
                return 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("SQL exception");
        } return 0;
    }
}
