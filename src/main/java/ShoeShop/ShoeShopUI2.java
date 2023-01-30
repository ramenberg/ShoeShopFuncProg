package ShoeShop;

import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class ShoeShopUI2 extends JFrame {
    private JFrame frame;
    private JPanel topPanel;
    private JPanel centerTopPanel;
    private JPanel centerBottomPanel;
    private JPanel bottomPanel;
    private JLabel welcomeMessage;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton button1;
    private JButton button2;
    private int width = 800;
    private int height = 600;
    private int scrollpaneHeight = 400;

    public ShoeShopUI2() {
        frame = new JFrame("ShoeShop");
        topPanel = new JPanel();
        centerTopPanel = new JPanel();
        centerBottomPanel = new JPanel();
        bottomPanel = new JPanel();

        topPanel.setBackground(Color.RED);
        centerTopPanel.setBackground(Color.GREEN);
        centerBottomPanel.setBackground(Color.YELLOW);
        bottomPanel.setBackground(Color.BLUE);

        welcomeMessage = new JLabel("Welcome to the Swing Application");
        topPanel.add(welcomeMessage);

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/shoe_shop", "katten", "Lollsan£23");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            // Get column names
            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = rsmd.getColumnName(i);
            }

            // Get table data
            rs.last();
            int rowCount = rs.getRow();
            Object[][] data = new Object[rowCount][columnCount];
            rs.beforeFirst();
            int row = 0;
            while (rs.next()) {
                for (int col = 1; col <= columnCount; col++) {
                    data[row][col - 1] = rs.getObject(col);
                }
                row++;
            }

            JTable table = new JTable(data, columnNames);
            table.setFillsViewportHeight(true);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(width, scrollpaneHeight));

            centerTopPanel.add(scrollPane);

            button1 = new JButton("Button 1");
            button2 = new JButton("Button 2");
            bottomPanel.add(button1);
            bottomPanel.add(button2);

            frame.add(topPanel, BorderLayout.NORTH);
            frame.add(centerTopPanel, BorderLayout.CENTER);
//            frame.add(centerBottomPanel, BorderLayout.EAST);
            frame.add(bottomPanel, BorderLayout.SOUTH);

            frame.setSize(width, height);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new ShoeShopUI2();
    }
}

