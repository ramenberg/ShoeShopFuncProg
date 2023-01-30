package DBTables;

public class Has_Category {

    private int item_id;
    private int category_id;

    public Has_Category(int item_id, int category_id) {
        this.item_id = item_id;
        this.category_id = category_id;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
}
