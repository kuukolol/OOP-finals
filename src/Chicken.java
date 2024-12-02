public class Chicken implements MenuCategory {
    private float soloPrice;
    private float mealPrice;

    public Chicken(float soloPrice, float mealPrice) {
        this.soloPrice = soloPrice;
        this.mealPrice = mealPrice;
    }

    public float getSoloPrice() {
        return soloPrice;
    }

    public float getMealPrice() {
        return mealPrice;
    }
}
