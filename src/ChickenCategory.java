import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ChickenCategory extends MenuCategory {

    @Override
    public List<Item> createItems() {
        List<Item> chickens = new ArrayList<>();
        chickens.add(new Item("Fried Chicken", 250));
        chickens.add(new Item("Spicy Chicken", 280));
        chickens.add(new Item("BBQ Chicken", 300));
        Collections.sort(chickens, Comparator.comparing(Item::getName));
        return chickens;
    }
}
