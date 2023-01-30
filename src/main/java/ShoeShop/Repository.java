package ShoeShop;

import DBTables.Customers;
import DBTables.Items;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Repository {

    static Properties p = new Properties();

    private static JTextField emailField;
    private static JPasswordField passwordField;
    private static int customerId;

    public Repository () {

        try {
            p.load(new FileInputStream("src/main/resources/dbsettings.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static boolean validateLogin(String email, char[] password) {
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
    static Customers getCustomer(String email, char[] password) {
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
                return null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

//    static List<Items> getAvailableItems() {
//        List<Items> items = new ArrayList<>();
//        try (Connection con = DriverManager.getConnection(
//                p.getProperty("connectionString"),
//                p.getProperty("name"),
//                p.getProperty("password"))) {
//            String sql = "SELECT i.id, b.name AS brand, i.name, s.size, MIN(i.price) AS price, c.name AS color, GROUP_CONCAT(DISTINCT ca.name) AS category "
//                    + "FROM items i "
//                    + "JOIN brand b ON i.brand_id = b.id "
//                    + "JOIN colors c ON i.color_id = c.id "
//                    + "JOIN sizes s ON i.size_id = s.id "
//                    + "JOIN Has_category hc ON i.id = hc.item_id "
//                    + "JOIN Category ca ON hc.category_id = ca.id "
//                    + "WHERE i.stock_balance > 0 "
//                    + "GROUP BY i.id, b.name, i.name, s.size, c.name "
//                    + "ORDER BY b.name, i.name, c.name, s.size";
//            Statement stmt = con.createStatement();
//            ResultSet rs = stmt.executeQuery(sql);
//            while (rs.next()) {
//                int id = rs.getInt("id");
//                String brand = rs.getString("brand");
//                String name = rs.getString("name");
//                String size = rs.getString("size");
//                double price = rs.getDouble("price");
//                String color = rs.getString("color");
//                String category = rs.getString("category");
//                items.add(new Items(id, brand, name, size, price, color, category));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return items;
//    }
}
