package DBTables;

import java.util.List;

public class Item {

    private int id;
    private Brand brand_id;
    private String name;
    private Size size_id;
    private double price;
    private Color color_id;
    private int stock_balance;

    private List<Category> categories;

    public Item(int id, Brand brand_id, String name, Size size_id, double price, Color color_id, int stock_balance) {
        this.id = id;
        this.brand_id = brand_id;
        this.name = name;
        this.size_id = size_id;
        this.price = price;
        this.color_id = color_id;
        this.stock_balance = stock_balance;
    }

    public Item(int id, Brand brand_id, String name, Size size_id, double price, Color color_id, int stock_balance, List<Category> categories) {
        this.id = id;
        this.brand_id = brand_id;
        this.name = name;
        this.size_id = size_id;
        this.price = price;
        this.color_id = color_id;
        this.stock_balance = stock_balance;
        this.categories = categories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Brand getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(Brand brand_id) {
        this.brand_id = brand_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Size getSize_id() {
        return size_id;
    }

    public void setSize_id(Size size_id) {
        this.size_id = size_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Color getColor_id() {
        return color_id;
    }

    public void setColor_id(Color color_id) {
        this.color_id = color_id;
    }

    public int getStock_balance() {
        return stock_balance;
    }

    public void setStock_balance(int stock_balance) {
        this.stock_balance = stock_balance;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}

