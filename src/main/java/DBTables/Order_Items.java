package DBTables;

public class Order_Items {

    private int order_id;
    private int item_id;
    private int quantity;

    public Order_Items(int order_id, int item_id, int quantity) {
        this.order_id = order_id;
        this.item_id = item_id;
        this.quantity = quantity;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
