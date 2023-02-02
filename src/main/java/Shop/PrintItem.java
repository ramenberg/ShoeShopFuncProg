package Shop;

import DBTables.Item;

@FunctionalInterface
public interface PrintItem {
    void printItem(Item item);
}
