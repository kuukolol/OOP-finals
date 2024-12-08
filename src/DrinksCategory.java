import java.util.ArrayList;
import java.util.List;

public class DrinksCategory extends MenuCategory {

    @Override
    public List<Item> createItems() {
        List<Item> drinks = new ArrayList<>();
        drinks.add(new Item("Coca Cola", 50));
        drinks.add(new Item("Pepsi", 50));
        drinks.add(new Item("Iced Tea", 60));
        return drinks;
    }
}
