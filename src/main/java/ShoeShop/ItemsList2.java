package ShoeShop;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class ItemsList2 extends JFrame {

    private JPanel contentPane;
    private JTable table;

    JComboBox<String> brandComboBox = new JComboBox<>();
    JComboBox<String> nameComboBox = new JComboBox<>();
    JComboBox<String> colorComboBox = new JComboBox<>();
    JComboBox<String> sizeComboBox = new JComboBox<>();

    Properties p = new Properties();

    public ItemsList2() {

        try {
            p.load(new FileInputStream("src/main/resources/dbsettings.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] columns = {"Brand", "Name", "Color", "Size", "Price", "Category"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        contentPane.add(new JScrollPane(table), BorderLayout.CENTER);

        addToJComboBoxes();

        JPanel panel = new JPanel();
        JButton searchButton = new JButton("Search");

//        searchButton.addActionListener(e -> updateTableModel(model, ""));
        searchButton.addActionListener(e -> {
            String brand = brandComboBox.getSelectedItem().toString();
            String name = nameComboBox.getSelectedItem().toString();
            String color = colorComboBox.getSelectedItem().toString();
            String size = sizeComboBox.getSelectedItem().toString();
            String condition = "AND b.name = '" + brand + "' AND i.name = '" + name + "' AND c.name = '" + color + "' AND s.size = '" + size + "'";
            int rows = table.getRowCount();
            if (rows > 0) {
                updateTableModel(model, condition);
            } else  {
                JOptionPane.showMessageDialog(null, "No items found");
            }
        });

        panel.add(new JLabel("Brand:"));
        panel.add(brandComboBox);
        panel.add(new JLabel("Name:"));
        panel.add(nameComboBox);
        panel.add(new JLabel("Color:"));
        panel.add(colorComboBox);
        panel.add(new JLabel("Size:"));
        panel.add(sizeComboBox);
        panel.add(searchButton);
        contentPane.add(panel, BorderLayout.NORTH);

        repaint();
        revalidate();
    }

    private void updateTableModel(DefaultTableModel model, String condition) {
//        model.setRowCount(0);

        try (Connection conn = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"))) {

            Statement stmt = conn.createStatement();
            String sql = "SELECT b.name AS brand, i.name, s.size, i.price, c.name AS color, iic.category AS category "
                    + "FROM items i " + "JOIN brand b ON i.brand_id = b.id "
                    + "JOIN colors c ON i.color_id = c.id "
                    + "JOIN sizes s ON i.size_id = s.id "
                    + "JOIN (SELECT item_id, GROUP_CONCAT(c.name) AS category FROM Has_category hc JOIN Category c ON hc.category_id = c.id GROUP BY item_id) as iic "
                    + "WHERE i.stock_balance > 0 " + condition;
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String brand = rs.getString("brand");
                String name = rs.getString("name");
                String size = rs.getString("size");
                double price = rs.getDouble("price");
                String color = rs.getString("color");
                String category = rs.getString("category");

                model.addRow(new Object[]{brand, name, color, size, price, category});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void updateLists(String brand, String name, String color, String size) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        try (Connection conn = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"))) {

            Statement stmt = conn.createStatement();
            String sql = "SELECT b.name AS brand, i.name, s.size, i.price, c.name AS color, iic.category AS category "
                    + "FROM items i " + "JOIN brand b ON i.brand_id = b.id "
                    + "JOIN colors c ON i.color_id = c.id "
                    + "JOIN sizes s ON i.size_id = s.id "
                    + "JOIN (SELECT item_id, GROUP_CONCAT(c.name) AS category FROM Has_category hc JOIN Category c ON hc.category_id = c.id GROUP BY item_id) as iic "
                    + "WHERE i.stock_balance > 0";
            if (!brand.equals("All")) {
                sql += " AND b.name = '" + brand + "'";
            }
            if (!name.equals("All")) {
                sql += " AND i.name = '" + name + "'";
            }
            if (!color.equals("All")) {
                sql += " AND c.name = '" + color + "'";
            }
            if (!size.equals("All")) {
                sql += " AND s.size = '" + size + "'";
            }
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String brandVal = rs.getString("brand");
                String nameVal = rs.getString("name");
                String sizeVal = rs.getString("size");
                double price = rs.getDouble("price");
                String colorVal = rs.getString("color");
                String category = rs.getString("category");

                model.addRow(new Object[] {brandVal, nameVal, colorVal, sizeVal, price, category});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addToJComboBoxes () {
        try (Connection conn = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"))) {
            Statement stmt = conn.createStatement();
            String sql = "SELECT DISTINCT name FROM brand";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String brand = rs.getString("name");
                brandComboBox.addItem(brand);
            }
            sql = "SELECT DISTINCT name FROM items";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("name");
                nameComboBox.addItem(name);
            }
            sql = "SELECT DISTINCT name FROM colors";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String color = rs.getString("name");
                colorComboBox.addItem(color);
            }
            sql = "SELECT DISTINCT size FROM sizes";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String size = rs.getString("size");
                sizeComboBox.addItem(size);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ItemsList2();
    }
}
