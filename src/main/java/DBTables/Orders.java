package DBTables;

public final class Orders {

    private int id;
    private Customer customer_id;
    private String order_date;
    private double total_price;

    public Orders(int id, Customer customer_id, String order_date, double total_price) {
        this.id = id;
        this.customer_id = customer_id;
        this.order_date = order_date;
        this.total_price = total_price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Customer customer_id) {
        this.customer_id = customer_id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }
}