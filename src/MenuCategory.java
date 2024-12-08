import java.util.List;

public abstract class MenuCategory {
    protected List<Item> items;

    public MenuCategory() {
        items = createItems();
    }

    public abstract List<Item> createItems();

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.add(item);
    }
}
