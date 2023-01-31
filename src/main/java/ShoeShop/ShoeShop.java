package ShoeShop;

import DBTables.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileInputStream;
import java.sql.*;

import static ShoeShop.Repository.p;

public class ShoeShop extends JFrame {
    Customers customer;
    private JPanel itemsListPanel;
    private JTable itemsListTable;
    public ShoeShop(Customers c) {
        customer = c;
        Repository repo = new Repository();

        JFrame newFrame = new JFrame("Welcome to ShoeShop " + customer.getFirst_name() + " " + customer.getLast_name() + "!");
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setLocationRelativeTo(null);
        newFrame.setSize(300, 200);
        newFrame.setVisible(true);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(null);
        topPanel.setBackground(Color.BLUE);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(null);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(null);

        JLabel welcomeLabel = new JLabel("Welcome to ShoeShop " + customer.getFirst_name() + " " + customer.getLast_name() + "!");
        topPanel.add(welcomeLabel);

        itemsListPanel = new JPanel();
        itemsListPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        itemsListPanel.setLayout(new BorderLayout(0, 0));

        itemsListTable = new JTable();
        itemsListPanel.add(new JScrollPane(itemsListTable), BorderLayout.CENTER);

        newFrame.add(topPanel);
        newFrame.add(centerPanel);
        newFrame.add(bottomPanel);

        newFrame.setVisible(true);
        newFrame.repaint();
        newFrame.revalidate();
    }

}
