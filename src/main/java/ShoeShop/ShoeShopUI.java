//package ShoeShop;
//
//import javax.swing.*;
//import javax.swing.border.EmptyBorder;
//import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableModel;
//import java.awt.*;
//
//public class ShoeShopUI {
//
//    private JFrame frame;
//    private JPanel contentPane;
//    private JTable table;
//
//    public ShoeShopUI() {
//
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(700, 500);
//        frame.setLocationRelativeTo(null);
//        contentPane = new JPanel();
//        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//        contentPane.setLayout(new BorderLayout(0, 0));
//        frame.setContentPane(contentPane);
//        frame.setVisible(true);
//
//        String[] columns = {"ID", "Brand", "Name", "Color", "Size", "Price", "Category"};
//        DefaultTableModel model = new DefaultTableModel(columns, 0);
//
//        ItemsList itemsList = new ItemsList(model);
//
//        table = new JTable((TableModel) itemsList);
//        contentPane.add(new JScrollPane(table), BorderLayout.CENTER);
//
//
//        frame.repaint();
//        frame.revalidate();
//
//    }
//    private void updateTableModel(String brand, String name, String color, String size) {
//        DefaultTableModel model = (DefaultTableModel) table.getModel();
//        model.setRowCount(0);
//
//    }
//}
