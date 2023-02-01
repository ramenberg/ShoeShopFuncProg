package Shop;

import DBTables.*;

import java.io.FileInputStream;
import java.sql.*;
import java.util.*;

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
            ResultSet rs = st.executeQuery("SELECT * FROM customers " +
                    "WHERE email = '" + email + "' " +
                    "AND password = '" + new String(password) + "'");
            return rs.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static Customer getCustomer(String email, char[] password) {
        new Repository();
        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"))) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM customers " +
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
                        rs.getString("phone_number"),
                        rs.getString("password").toCharArray());
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

    public static ArrayList<Item> getAllItemsInStock() {
        new Repository();
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
                        rs.getString("name"),
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

    // get all categories from database and pair with their respective items id by using the has_category table
    public static ArrayList<Category> getAllCategories() {
        new Repository();
        ArrayList<Category> allCategoriesList = new ArrayList<>();
        String sql= "SELECT cat.*, hc.* " +
                "FROM category cat " +
                "JOIN has_category hc ON cat.id = hc.category_id";

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"))) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            // list of all categories
            while (rs.next()) {
                allCategoriesList.add(new Category(
                        rs.getInt("cat.id"),
                        rs.getString("cat.name")));
            }
            return allCategoriesList;
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

    public static void main(String[] args) {
        List<Item> items = getAllItemsInStock();
        assert items != null;
        System.out.println("Brand | Name | Size | Price | Color | Stock | Categories");
        System.out.println("------------------------------------------------------------");
        for (Item item : items) {
            StringBuilder categoriesString = new StringBuilder();
            for (Category category : item.getCategories()) {
                categoriesString.append(category.getName()).append(", ");
            }
            if (categoriesString.length() > 0) {
                categoriesString.delete(categoriesString.length() - 2, categoriesString.length());
            }
            System.out.println(
                    item.getName() + " " +
                            item.getBrand_id().getName() + " " +
                            item.getSize_id().getSize() + " " +
                            item.getPrice() + " " +
                            item.getColor_id().getName() + " " +
                            item.getStock_balance() + " " +
                            categoriesString);
        }

    }
}
