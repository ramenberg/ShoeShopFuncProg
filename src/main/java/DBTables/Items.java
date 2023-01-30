package DBTables;

public class Items {

    private int id;
    private int brand_id;
    private String name;
    private int size_id;
    private double price;
    private int color_id;
    private int stock_balance;

    public Items(int id, int brand_id, String name, int size_id, double price, int color_id, int stock_balance) {
        this.id = id;
        this.brand_id = brand_id;
        this.name = name;
        this.size_id = size_id;
        this.price = price;
        this.color_id = color_id;
        this.stock_balance = stock_balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize_id() {
        return size_id;
    }

    public void setSize_id(int size_id) {
        this.size_id = size_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getColor_id() {
        return color_id;
    }

    public void setColor_id(int color_id) {
        this.color_id = color_id;
    }

    public int getStock_balance() {
        return stock_balance;
    }

    public void setStock_balance(int stock_balance) {
        this.stock_balance = stock_balance;
    }
}
