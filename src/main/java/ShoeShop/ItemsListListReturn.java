//package ShoeShop;
//
//import DBTables.Item;
//
//import java.io.FileInputStream;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Properties;
//
//public class ItemsListListReturn {
//
//    Properties p = new Properties();
//
//    public List<Item> ItemsListListReturn() {
//
//        List<Item> itemList = new ArrayList<>();
//        try {
//            p.load(new FileInputStream("src/main/resources/dbsettings.properties"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try (Connection conn = DriverManager.getConnection(
//                p.getProperty("connectionString"),
//                p.getProperty("name"),
//                p.getProperty("password"))) {
//
//            Statement stmt = conn.createStatement();
//            String sql = "SELECT * FROM items";
//            ResultSet rs = stmt.executeQuery(sql);
//
//            while (rs.next()) {
//                int id = rs.getInt("id");
//                int brand = rs.getInt("brand_id");
//                String name = rs.getString("name");
//                int size = rs.getInt("size_id");
//                double price = rs.getDouble("price");
//                int color = rs.getInt("color_id");
//                int stock = rs.getInt("stock_balance");
//
//                itemList.add(new Item(id, brand, name, size, price, color, stock));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return itemList;
//    }
//}
