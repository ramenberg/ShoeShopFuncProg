package ShoeShop;

import DBTables.Customers;

public class Main {

    public Main () {
        Customers c;
        Customers mock = new Customers(999, "John", "Doe", "Address", "City", "Email", "1234567890", "Password");

//        new LoginForm();
//        new ItemsList();
        new ShoeShopUI2(mock);
    }

    public static void main(String[] args) {
        new Main();
    }
}
