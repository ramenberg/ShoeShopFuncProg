package Shop;

import DBTables.*;

import java.io.FileInputStream;
import java.sql.*;
import java.util.*;

public class Repository {

    static final Properties p = new Properties();

    public Repository() {
        try {
            p.load(new FileInputStream("src/main/resources/dbsettings.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Customer validateLoginReturnCustomer(String email, char[] password) {
        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"))) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM customer " +
                    "WHERE email = '" + email + "' " +
                    "AND password = '" + new String(password) + "'");
            if (rs.next()) {
                return new Customer(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("address"),
                        rs.getString("city"),
                        rs.getString("email"),
                        rs.getString("phone_number"));
            } else {
                // ingen kund hittades/ felaktig inloggning
                return null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Item> getAllItems() {
        ArrayList<Item> allItemsList = new ArrayList<>();

        String sql= "SELECT i.*, b.*, c.*, s.*, cat.*, hc.* " +
                "FROM item i " +
                "JOIN brand b ON i.brand_id = b.id " +
                "JOIN color c ON i.color_id = c.id " +
                "JOIN size s ON i.size_id = s.id " +
                "JOIN has_category hc ON i.id = hc.item_id " +
                "JOIN category cat ON hc.category_id = cat.id";

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

    public static int addToOrder(int orders_id, int customer_id, int item_id) {
        int newOrderId = -2;
        int result = -1;
        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"))) {
            CallableStatement cs = con.prepareCall("{call AddToCart(?, ?, ?)}");
            cs.setInt(1, orders_id);
            cs.setInt(2, customer_id);
            cs.setInt(3, item_id);
            boolean hasResult = cs.execute();

            if (hasResult) {
                ResultSet rs = cs.getResultSet();
                if (rs.next()) {
                    newOrderId = rs.getInt(1);
                    result = rs.getInt(2);
                }
            }
            if (result < 0) {
                System.out.println("An error occurred while adding the item to the cart.");
            } else if (result == 0) {
                System.out.println("Out of stock: " + Shopping.getItemById(item_id));
            } else {
                System.out.println("Added to cart: " + Shopping.getItemById(item_id));
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("SQL exception");
        }
        return newOrderId;
    }


    public static void printAllItemsList () {
        List<Item> items = getAllItems();
        assert items != null;
        System.out.println("Brand | Model | Size | Price | Color | Stock");
        System.out.println("---------------------------------------------");
        for (Item item : items) {
            System.out.println(
                    item.getModel() + " " +
                    item.getBrand_id().getName() + " " +
                    item.getSize_id().getSize() + " " +
                    item.getPrice() + " " +
                    item.getColor_id().getName() + " " +
                    item.getStock_balance());
        }
    }
}
