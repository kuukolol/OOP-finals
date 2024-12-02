public class Drink implements MenuCategory {
    private float price;

    public Drink(float price) {
        this.price = price;
    }

    public float getSoloPrice() {
        return price;
    }

    public float getMealPrice() {
        throw new UnsupportedOperationException("Drinks do not have a meal price.");
    }
}
