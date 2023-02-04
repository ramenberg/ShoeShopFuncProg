package Reports;

import DBTables.Item;

public interface ItemFilter {
    boolean isMatching(Item item);
}
