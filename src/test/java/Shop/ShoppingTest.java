package Shop;

import DBTables.Brand;
import DBTables.Color;
import DBTables.Item;
import DBTables.Size;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.text.Collator;
import java.util.List;

import static org.mockito.Mockito.*;

class ShoppingTest {
    @Mock
    List<Item> cartItems;
    @Mock
    Collator collator;
    @InjectMocks
    Shopping shopping;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testStartShopping() {
        List<Item> result = Shopping.startShopping();
        Assertions.assertEquals(List.of(new Item(0, new Brand(0, "name"), "model", new Size(0, "size"), 0d, new Color(0, "name"), 0)), result);
    }

    @Test
    void testFilterAndGetNames() {
        List<String> result = Shopping.filterAndGetNames(List.of(new Item(0, new Brand(0, "name"), "model", new Size(0, "size"), 0d, new Color(0, "name"), 0)), null, null, null);
        Assertions.assertEquals(List.of("String"), result);
    }

    @Test
    void testGetSubtotal() {
        double result = Shopping.getSubtotal();
        Assertions.assertEquals(0d, result);
    }

    @Test
    void testGetItemById() {
        String result = Shopping.getItemById(0);
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testPrintAllItemsList() {
        Shopping.printAllItemsList(List.of(new Item(0, new Brand(0, "name"), "model", new Size(0, "size"), 0d, new Color(0, "name"), 0)));
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme