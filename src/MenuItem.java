import java.util.HashMap;
import java.util.Map;

class MenuItem {

    // Maps to hold burger, chicken, and drink items
    Map<String, MenuCategory> burger = new HashMap<>();
    Map<String, MenuCategory> chicken = new HashMap<>();
    Map<String, Float> drinks = new HashMap<>();

    // initialize all items
    public MenuItem() {
        burgerList();
        chickenList();
        drinkList();
    }

    // Setting up the burger items
    private void burgerList() {
        String[][] burgerlist = {
                { "Hamburger", "3", "5" },
                { "Cheeseburger", "3.5", "6" },
                { "Double Hamburger", "4.50", "7.50" },
                { "Double Cheeseburger", "5", "8" },
                { "Chicken Burger", "5", "6" },
                { "Chicken Burger with Cheese", "4.50", "6" }
        };
        for (String[] item : burgerlist) {
            String burgerName = item[0];
            float soloPrice = Float.parseFloat(item[1]);
            float mealPrice = Float.parseFloat(item[2]);
            burger.put(burgerName, new Burger(soloPrice, mealPrice));
        }
    }

    // Setting up the chicken items
    private void chickenList() {
        String[][] chickenlist = {
                { "Fried Chicken", "8.99", "13.99" },
                { "Spicy Fried Chicken", "9.99", "14.99" },
                { "Korean Fried Chicken", "10.99", "15.99" },
                { "Garlic Parmesan Fried Chicken", "10.99", "15.99" },
                { "BBQ Style Chicken", "12.99", "15.99" },
                { "Roasted Chicken", "12.99", "15.99" }
        };
        for (String[] item : chickenlist) {
            String chickenName = item[0];
            float soloPrice = Float.parseFloat(item[1]);
            float mealPrice = Float.parseFloat(item[2]);
            chicken.put(chickenName, new Chicken(soloPrice, mealPrice));
        }
    }

    // Setting up drinks
    private void drinkList() {
        Object[][] drinkList = {
                { "Coca-Cola", 2.49 },
                { "Pepsi", 2.49 },
                { "Lemonade", 2.99 },
                { "Iced Tea", 2.49 },
                { "Root Beer", 2.49 },
                { "Sprite", 2.49 }
        };
        for (Object[] item : drinkList) {
            String drinkName = item[0].toString();
            float price = Float.parseFloat(item[1].toString());
            drinks.put(drinkName, price);
        }
    }
}
