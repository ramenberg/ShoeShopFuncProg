package ShoeShop;

import DBTables.*;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;


import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Properties;

import javax.swing.*;

public class Login extends JFrame {
    private int customerId;
    private JTextField emailField;
    private JPasswordField passwordField;
    private Properties p = new Properties();

    public Login() {
        setTitle("Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10));

        try {
            p.load(new FileInputStream("src/main/resources/dbsettings.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginButtonListener());

        add(emailLabel);
        add(emailField);
        add(passwordLabel);
        add(passwordField);
        add(new JLabel());
        add(loginButton);

        setVisible(true);
    }

    class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = emailField.getText();
            char[] password = passwordField.getPassword();

            try (Connection con = DriverManager.getConnection(
                    p.getProperty("connectionString"),
                    p.getProperty("name"),
                    p.getProperty("password"))) {
                PreparedStatement st = con.prepareStatement("SELECT * FROM customers WHERE email = ? AND password = ?");
                st.setString(1, email);
                st.setString(2, new String(password));
                ResultSet rs = st.executeQuery();

                if (rs.next()) {
                    customerId = rs.getInt("id");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    JOptionPane.showMessageDialog(null, "Login successful. Hello, " + firstName + " " + lastName + "!");
                } else {
                    JOptionPane.showMessageDialog(null, "Login failed. Invalid email/password.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error connecting to the database: " + ex.getMessage());
            }
        }
    }

    public int getCustomerId() {
        return customerId;
    }

    public static void main(String[] args) {
        new Login();
    }
}


//public class Login {
//
//    Properties p = new Properties();
//    private int customer_id;
//
//    public Login() {
//        try {
//            p.load(new FileInputStream("src/main/resources/dbsettings.properties"));
//            authenticate();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void authenticate() {
//        Scanner sc = new Scanner(System.in);
//
//        while (true) {
//            System.out.print("Enter email (or type 'exit' to exit): ");
//            String email = sc.nextLine();
//
//            if (email.equalsIgnoreCase("exit")) {
//                break;
//            }
//
//            System.out.print("Enter password: ");
//            String pwd = sc.nextLine();
//
//            try (Connection con = DriverManager.getConnection(
//                    p.getProperty("connectionString"),
//                    p.getProperty("name"),
//                    p.getProperty("password"))) {
//                PreparedStatement st = con.prepareStatement("SELECT * FROM customers WHERE email = ? AND password = ?");
//                st.setString(1, email);
//                st.setString(2, pwd);
//                ResultSet rs = st.executeQuery();
//
//                if (rs.next()) {
//                    this.customer_id = rs.getInt("id");
//                    String firstName = rs.getString("first_name");
//                    String lastName = rs.getString("last_name");
//                    System.out.println("Login successful. Hello, " + firstName + " " + lastName + "!");
//                } else {
//                    System.out.println("Login failed. Invalid email/password.");
//                }
//            } catch (SQLException e) {
//                System.out.println("Error connecting to the database: " + e.getMessage());
//            }
//        }
//
//    }
//
//    public int getCustomerId() {
//        return customer_id;
//    }
//}