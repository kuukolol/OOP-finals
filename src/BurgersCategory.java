import java.util.ArrayList;
import java.util.List;

public class BurgersCategory extends MenuCategory {

    @Override
    public List<Item> createItems() {
        List<Item> burgers = new ArrayList<>();
        burgers.add(new Item("Hamburger", 150));
        burgers.add(new Item("Cheeseburger", 180));
        burgers.add(new Item("Double Hamburger", 220));
        return burgers;
    }
}
