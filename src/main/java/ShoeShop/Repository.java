//package ShoeShop;
//
//import DBTables.Customer;
//
//import javax.swing.*;
//import java.io.FileInputStream;
//import java.sql.*;
//import java.util.Properties;
//
//public class Repository {
//
//    static Properties p = new Properties();
//
//    private static JTextField emailField;
//    private static JPasswordField passwordField;
//    private static int customerId;
//
//    public Repository () {
//
//        try {
//            p.load(new FileInputStream("src/main/resources/dbsettings.properties"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    static boolean validateLogin(String email, char[] password) {
//        new Repository();
//        try (Connection con = DriverManager.getConnection(
//                p.getProperty("connectionString"),
//                p.getProperty("name"),
//                p.getProperty("password"))) {
//            Statement st = con.createStatement();
//            ResultSet rs = st.executeQuery("SELECT * FROM customers WHERE email = '" + email + "' AND password = '" + new String(password) + "'");
//            return rs.next();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            return false;
//        }
//    }
//    static Customer getCustomer(String email, char[] password) {
//        new Repository();
//        try (Connection con = DriverManager.getConnection(
//                p.getProperty("connectionString"),
//                p.getProperty("name"),
//                p.getProperty("password"))) {
//            Statement st = con.createStatement();
//            ResultSet rs = st.executeQuery("SELECT * FROM customers WHERE email = '" + email + "' AND password = '" + new String(password) + "'");
//            if (rs.next()) {
//                return new Customer(
//                        rs.getInt("id"),
//                        rs.getString("first_name"),
//                        rs.getString("last_name"),
//                        rs.getString("address"),
//                        rs.getString("city"),
//                        rs.getString("email"),
//                        rs.getString("phone_number"),
//                        rs.getString("password").toCharArray());
//            } else {
//                return null;
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//    }
////    static Items getItemFromTable(int id) {
////        new Repository();
////        try (Connection con = DriverManager.getConnection(
////                p.getProperty("connectionString"),
////                p.getProperty("name"),
////                p.getProperty("password"))) {
////            Statement st = con.createStatement();
////            ResultSet rs = st.executeQuery("SELECT * FROM items WHERE id = " + id);
////            if (rs.next()) {
////                return new Items(
////                        rs.getInt("id"),
////                        rs.getString("name"),
////                        rs.getString("brand"),
////                        rs.getString("color"),
////                        rs.getString("size"),
////                        rs.getInt("price"),
////                        rs.getString("category"));
////            } else {
////                return null;
////            }
////        } catch (SQLException ex) {
////            ex.printStackTrace();
////            return null;
////        }
////    }
//}
