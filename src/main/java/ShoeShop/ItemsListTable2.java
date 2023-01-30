package ShoeShop;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class ItemsListTable2 extends JPanel {

    private JPanel itemsListPanel;
    private JTable jTable;

    Properties p = new Properties();


    public void ItemsListTable2() {

        try {
            p.load(new FileInputStream("src/main/resources/dbsettings.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        itemsListPanel = new JPanel();
        itemsListPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        itemsListPanel.setLayout(new BorderLayout(0, 0));
//        setContentPane(itemsListPanel);
        setVisible(true);

        String[] columns = {"ID", "Brand", "Name", "Color", "Size", "Price", "Category"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        JTable itemsListTable = new JTable(model);
        itemsListPanel.add(new JScrollPane(itemsListTable), BorderLayout.CENTER);

        JLabel label = new JLabel();
        label.setText("<html><table><tr><td>" + itemsListTable.getModel().toString() + "</td></tr></table></html>");
        itemsListPanel.add(label, BorderLayout.NORTH);

        repaint();
        revalidate();

        try (Connection conn = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"))) {

            Statement stmt = conn.createStatement();
            String sql = "SELECT i.id, b.name AS brand, i.name, s.size, MIN(i.price) AS price, c.name AS color, GROUP_CONCAT(DISTINCT ca.name) AS category "
                    + "FROM items i "
                    + "JOIN brand b ON i.brand_id = b.id "
                    + "JOIN colors c ON i.color_id = c.id "
                    + "JOIN sizes s ON i.size_id = s.id "
                    + "JOIN Has_category hc ON i.id = hc.item_id "
                    + "JOIN Category ca ON hc.category_id = ca.id "
                    + "WHERE i.stock_balance > 0 "
                    + "GROUP BY i.id, b.name, i.name, s.size, c.name "
                    + "ORDER BY b.name, i.name, c.name, s.size";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String brand = rs.getString("brand");
                String name = rs.getString("name");
                String size = rs.getString("size");
                double price = rs.getDouble("price");
                String color = rs.getString("color");
                String category = rs.getString("category");

                model.addRow(new Object[]{id, brand, name, color, size, price, category});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
