//package ShoeShop;
//
//import DBTables.*;
//
//import java.io.FileInputStream;
//import java.sql.*;
//import java.util.Properties;
//import java.awt.event.*;
//
//import javax.swing.*;
//
//public class Login extends JFrame {
//    private int customerId;
//    private JTextField emailField;
//    private JPasswordField passwordField;
//    private Properties p = new Properties();
//
//    public Login() {
//
//        try {
//            p.load(new FileInputStream("src/main/resources/dbsettings.properties"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        JLabel emailLabel = new JLabel("Email:");
//        emailField = new JTextField(20);
//        JLabel passwordLabel = new JLabel("Password:");
//        passwordField = new JPasswordField(20);
//        JButton loginButton = new JButton("Login");
//        loginButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String email = emailField.getText();
//                char[] password = passwordField.getPassword();
//                if (Repository.validateLogin(email, password)) {
//                    Customers customer = getCustomer(email, password);
//                    customerId = customer.getId();
//                    System.out.println("Login successful. Hello, " + customer.getFirst_name() + " " + customer.getLast_name() + "!");
//                } else {
//                    System.out.println("Login failed. Please try again.");
//                }
//            }
//        });
//
//        add(emailLabel);
//        add(emailField);
//        add(passwordLabel);
//        add(passwordField);
//        add(new JLabel());
//        add(loginButton);
//
//        setVisible(true);
//    }
//
//
//}
//
//
////public class Login {
////
////    Properties p = new Properties();
////    private int customer_id;
////
////    public Login() {
////        try {
////            p.load(new FileInputStream("src/main/resources/dbsettings.properties"));
////            authenticate();
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
////
////    public void authenticate() {
////        Scanner sc = new Scanner(System.in);
////
////        while (true) {
////            System.out.print("Enter email (or type 'exit' to exit): ");
////            String email = sc.nextLine();
////
////            if (email.equalsIgnoreCase("exit")) {
////                break;
////            }
////
////            System.out.print("Enter password: ");
////            String pwd = sc.nextLine();
////
////            try (Connection con = DriverManager.getConnection(
////                    p.getProperty("connectionString"),
////                    p.getProperty("name"),
////                    p.getProperty("password"))) {
////                PreparedStatement st = con.prepareStatement("SELECT * FROM customers WHERE email = ? AND password = ?");
////                st.setString(1, email);
////                st.setString(2, pwd);
////                ResultSet rs = st.executeQuery();
////
////                if (rs.next()) {
////                    this.customer_id = rs.getInt("id");
////                    String firstName = rs.getString("first_name");
////                    String lastName = rs.getString("last_name");
////                    System.out.println("Login successful. Hello, " + firstName + " " + lastName + "!");
////                } else {
////                    System.out.println("Login failed. Invalid email/password.");
////                }
////            } catch (SQLException e) {
////                System.out.println("Error connecting to the database: " + e.getMessage());
////            }
////        }
////
////    }
////
////    public int getCustomerId() {
////        return customer_id;
////    }
////}