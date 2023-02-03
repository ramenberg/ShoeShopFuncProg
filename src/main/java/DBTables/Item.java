package DBTables;

public final class Item {

    private int id;
    private Brand brand_id;
    private String model;
    private Size size_id;
    private double price;
    private Color color_id;
    private int stock_balance;

    public Item(int id, Brand brand_id, String model, Size size_id, double price, Color color_id, int stock_balance) {
        this.id = id;
        this.brand_id = brand_id;
        this.model = model;
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

    public Brand getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(Brand brand_id) {
        this.brand_id = brand_id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String name) {
        this.model = name;
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

    public String minimumToString() {
        return brand_id.getName() + " " +
                model + ", " +
                color_id.getName() + ", " +
                size_id.getSize() + ", Price: " +
                price + " SEK";
    }
    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", brand_id=" + brand_id +
                ", model='" + model + '\'' +
                ", size_id=" + size_id +
                ", price=" + price +
                ", color_id=" + color_id +
                ", stock_balance=" + stock_balance +
                '}';
    }
}

