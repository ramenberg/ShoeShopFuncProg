package Shop;

import DBTables.*;
import com.mysql.cj.protocol.Resultset;

import java.io.FileInputStream;
import java.sql.*;
import java.util.*;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class Repository {

    static final Properties p = new Properties();

    public Repository() {
        try {
            p.load(new FileInputStream("src/main/resources/dbsettings.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    public static boolean validateLogin(String email, char[] password) {
//        try (Connection con = DriverManager.getConnection(
//                p.getProperty("connectionString"),
//                p.getProperty("name"),
//                p.getProperty("password"))) {
//            Statement st = con.createStatement();
//            ResultSet rs = st.executeQuery("SELECT * FROM customer " +
//                    "WHERE email = '" + email + "' " +
//                    "AND password = '" + new String(password) + "'");
//            return rs.next();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            return false;
//        }
//    }

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

    public static ArrayList<Item> getAllItemsInStock() {
        ArrayList<Item> allItemsList = new ArrayList<>();
        Map<Integer, List<Category>> itemCategoriesMap = new HashMap<>();

        String sql= "SELECT i.*, b.*, c.*, s.*, cat.*, hc.* " +
                "FROM item i " +
                "JOIN brand b ON i.brand_id = b.id " +
                "JOIN color c ON i.color_id = c.id " +
                "JOIN size s ON i.size_id = s.id " +
                "JOIN has_category hc ON i.id = hc.item_id " +
                "JOIN category cat ON hc.category_id = cat.id " +
                "WHERE i.stock_balance > 0";

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"))) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            // lista över alla items i databasen med stock_balance > 0
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

                // mappar rätt kategorier till rätt item genom has_category tabellen
                if (itemCategoriesMap.containsKey(rs.getInt("i.id"))) {
                    itemCategoriesMap.get(rs.getInt("i.id")).add(
                            new Category(rs.getInt("cat.id"), rs.getString("cat.name")));
                } else {
                    List<Category> categories = new ArrayList<>();
                    categories.add(
                            new Category(rs.getInt("cat.id"), rs.getString("cat.name")));
                    itemCategoriesMap.put(rs.getInt("i.id"), categories);
                }

                // lägger till rätt kategorier i item
                item.setCategories(itemCategoriesMap.get(rs.getInt("i.id")));
            }
            return allItemsList;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("SQL exception");
            return null;
        }
    }

    public static int addToOrder(int orders_id, int customer_id, int item_id) {
        System.out.println("DEBUG " + orders_id + " " + customer_id + " " + item_id);

        int newOrderId = -2;
        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"))) {
            CallableStatement cs = con.prepareCall("{call AddToCart(?, ?, ?, ?)}");
            cs.setInt(1, orders_id);
            cs.setInt(2, customer_id);
            cs.setInt(3, item_id);
            cs.registerOutParameter(4, java.sql.Types.INTEGER); // int nya orderid
            boolean hadResult = cs.execute();

            while (hadResult) { // TODO här
                ResultSet rs = cs.getResultSet();
                while (rs.next()) {
                    newOrderId = rs.getInt(4);
                }
            }

            if (newOrderId > 0) {
                System.out.println("Item added to the cart successfully.");
            } else if (newOrderId == 0) {
                System.out.println("Item is out of stock.");
            } else {
                System.out.println("An error occurred while adding the item to the cart.");
            }
        } catch (SQLException ex) {
            System.out.println("An error occurred while calling the AddToCart stored procedure: " + ex.getMessage());
        }
        return newOrderId;
    }

//    public static void main(String[] args) {
//        List<Item> items = getAllItemsInStock();
//        assert items != null;
//        System.out.println("Brand | Model | Size | Price | Color | Stock | Categories");
//        System.out.println("------------------------------------------------------------");
//        for (Item item : items) {
//            StringBuilder categoriesString = new StringBuilder();
//            for (Category category : item.getCategories()) {
//                categoriesString.append(category.getName()).append(", ");
//            }
//            if (categoriesString.length() > 0) {
//                categoriesString.delete(categoriesString.length() - 2, categoriesString.length());
//            }
//            System.out.println(
//                    item.getModel() + " " +
//                    item.getBrand_id().getName() + " " +
//                    item.getSize_id().getSize() + " " +
//                    item.getPrice() + " " +
//                    item.getColor_id().getName() + " " +
//                    item.getStock_balance() + " " +
//                    categoriesString);
//        }
//
//    }

}
