package DBTables;

public class Has_Category {

    private Item item_id;
    private Category category_id;

    public Has_Category(Item item_id, Category category_id) {
        this.item_id = item_id;
        this.category_id = category_id;
    }

    public Item getItem_id() {
        return item_id;
    }

    public void setItem_id(Item item_id) {
        this.item_id = item_id;
    }

    public Category getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Category category_id) {
        this.category_id = category_id;
    }
}
