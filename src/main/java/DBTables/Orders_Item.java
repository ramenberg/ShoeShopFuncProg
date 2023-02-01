package DBTables;

public class Orders_Item {

    private Orders order_id;
    private Item item_id;
    private int quantity;

    public Orders_Item(Orders order_id, Item item_id, int quantity) {
        this.order_id = order_id;
        this.item_id = item_id;
        this.quantity = quantity;
    }

    public Orders getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Orders order_id) {
        this.order_id = order_id;
    }

    public Item getItem_id() {
        return item_id;
    }

    public void setItem_id(Item item_id) {
        this.item_id = item_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}