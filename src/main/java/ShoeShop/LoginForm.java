package ShoeShop;
import DBTables.Customers;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginForm {
    private JFrame frame;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton cancelButton;

    public LoginForm() {

        frame = new JFrame("ShoeShop Login");
        frame.setSize(300, 170);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        frame.add(panel);
        loginUI(panel);

        frame.setVisible(true);
    }

    private void loginUI(JPanel panel) {
        panel.setLayout(null);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(10, 10, 80, 25);
        panel.add(emailLabel);

        emailField = new JTextField(20);
        emailField.setBounds(100, 10, 160, 25);
        panel.add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 40, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 40, 160, 25);
        panel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);

        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            char[] password = passwordField.getPassword();
            if (Repository.validateLogin(email, password)) {
                Customers c = Repository.getCustomer(email, password);
                JOptionPane.showMessageDialog(frame, "Login accepted.A");
                frame.dispose();
                new ShoeShop(c);
            } else {
                JOptionPane.showMessageDialog(frame, "Login failed.");
            }
        });

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(180, 80, 80, 25);
        panel.add(cancelButton);
        cancelButton.addActionListener(e -> System.exit(0));
    }
}
