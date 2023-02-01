package Shop;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Login {
    Properties p = new Properties();
    private int customer_id;

    public Login() {
        try {
            p.load(new FileInputStream("src/main/resources/dbsettings.properties"));
//            Repository.validateLogin();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void checkCredentials(String email, char[] pwd, CheckCredentials checkCredentials) {
        checkCredentials.verify(email, pwd);
    }

    public int getCustomerId() {
        return customer_id;
    }

    @FunctionalInterface
    public interface CheckCredentials {
        void verify(String email, char[] pwd);
    }
}

