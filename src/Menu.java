import java.util.*;

public class Menu extends MenuItem {
    MenuItem menu = new MenuItem();
    Scanner scanner = new Scanner(System.in);

    // Store the order details
    List<OrderItem> order = new ArrayList<>();

    // Display items and allow user to place an order
    void displayItemsAndOrder() {
        System.out.println("===== NIoT RESTAURANT =====");
        // Display Burger Menu with correct numbering, sorted by solo price
        System.out.println("=====   BURGER MENU   =====");
        displayMenu(menu.burger, 1, true); // Sort by solo price
        /*
         * Display Chicken Menu with numbering starting after burger items
         * sorted by solo price
         */

        System.out.println("\n=====   CHICKEN MENU  =====");
        displayMenu(menu.chicken, menu.burger.size() + 1, true); // Sort by solo price
        /*
         * Display Drinks Menu with numbering starting after all other items
         * sorted byprice
         */

        System.out.println("\n=====   DRINKS MENU   =====");
        displayDrinkMenu(menu.drinks, menu.burger.size() + menu.chicken.size() + 1); // Sort by price

        // Place the order
        System.out.println(
                "\nEnter the number of the item you want to order, followed by quantity (e.g., 1 2 for 2 of item 1). Type 'done' when finished:");
        placeOrder();

        // Print the receipt
        printReceipt();
    }

    // Method to display menu items sorted by price
    private void displayMenu(Map<String, MenuCategory> menuItems, int startNumber, boolean sortBySoloPrice) {
        List<Map.Entry<String, MenuCategory>> sortedItems = new ArrayList<>(menuItems.entrySet());

        // Sort by solo price
        sortedItems.sort((entry1, entry2) -> {
            float price1 = sortBySoloPrice ? entry1.getValue().getSoloPrice() : entry1.getValue().getMealPrice();
            float price2 = sortBySoloPrice ? entry2.getValue().getSoloPrice() : entry2.getValue().getMealPrice();
            return Float.compare(price1, price2); // Sorting in ascending order
        });

        // Print sorted menu items with neat formatting
        int counter = startNumber;
        for (Map.Entry<String, MenuCategory> entry : sortedItems) {
            String name = entry.getKey();
            MenuCategory category = entry.getValue();
            // Ensure all columns are aligned with appropriate width
            System.out.printf("%-3d. %-30s Solo Price: $%-10.2f Meal Price: $%-10.2f%n", counter++, name,
                    category.getSoloPrice(), category.getMealPrice());
        }
    }

    // Method to display drinks menu, sorted by price
    private void displayDrinkMenu(Map<String, Float> menuItems, int startNumber) {
        List<Map.Entry<String, Float>> sortedItems = new ArrayList<>(menuItems.entrySet());

        // Sort drinks by price
        sortedItems.sort(Map.Entry.comparingByValue());

        // Print sorted drinks menu with neat formatting
        int counter = startNumber; // Start numbering from the given start number
        for (Map.Entry<String, Float> entry : sortedItems) {
            String name = entry.getKey();
            float price = entry.getValue();
            // Ensuring proper alignment for drinks
            System.out.printf("%-3d. %-30s Price: $%-10.2f%n", counter++, name, price);
        }
    }

    // Method to place an order
    private void placeOrder() {
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("done")) {
                break;
            }

            try {
                String[] orderParts = input.split(" ");
                int itemNumber = Integer.parseInt(orderParts[0]);
                int quantity = Integer.parseInt(orderParts[1]);

                // Adjust item number for each category
                if (itemNumber <= 0 || itemNumber > menu.burger.size() + menu.chicken.size() + menu.drinks.size()) {
                    System.out.println("Invalid item number. Please try again.");
                    continue;
                }

                // Ask the user whether they want a solo or meal price for non-drink items
                boolean isMeal = false;
                if (itemNumber <= menu.burger.size() + menu.chicken.size()) {
                    String priceChoice;
                    while (true) {
                        System.out.println(
                                "Would you like a solo or meal price? Type 'solo' for solo price or 'meal' for meal price:");
                        priceChoice = scanner.nextLine().trim().toLowerCase();

                        if (priceChoice.equals("solo")) {
                            isMeal = false; // Solo price selected
                            break;
                        } else if (priceChoice.equals("meal")) {
                            isMeal = true; // Meal price selected
                            break;
                        } else {
                            System.out.println("Invalid input. Please enter 'solo' or 'meal'.");
                        }
                    }
                }

                // Add the item to the order
                addItemToOrder(itemNumber, quantity, isMeal);
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter the item number followed by quantity (e.g., '1 2').");
            }
        }
    }

    // Add an item to the order based on its number
    private void addItemToOrder(int itemNumber, int quantity, boolean isMeal) {
        int totalItems = menu.burger.size() + menu.chicken.size();

        if (itemNumber <= menu.burger.size()) {
            String name = new ArrayList<>(menu.burger.keySet()).get(itemNumber - 1);
            MenuCategory category = menu.burger.get(name);
            float price = isMeal ? category.getMealPrice() : category.getSoloPrice();
            order.add(new OrderItem(name, price, quantity));
        } else if (itemNumber <= totalItems) {
            String name = new ArrayList<>(menu.chicken.keySet()).get(itemNumber - menu.burger.size() - 1);
            MenuCategory category = menu.chicken.get(name);
            float price = isMeal ? category.getMealPrice() : category.getSoloPrice();
            order.add(new OrderItem(name, price, quantity));
        } else {
            String name = new ArrayList<>(menu.drinks.keySet()).get(itemNumber - totalItems - 1);
            float price = menu.drinks.get(name);
            order.add(new OrderItem(name, price, quantity));
        }

        System.out.println("Item added to order!");
    }

    private void printReceipt() {
        System.out.println("\n===== RECEIPT =====");
        float total = 0;

        // Format the receipt neatly
        for (OrderItem item : order) {
            float itemTotal = item.getTotalPrice();
            total += itemTotal;
            System.out.printf("%-30s x%-2d $%-10.2f Total: $%-10.2f%n", item.getName(), item.getQuantity(),
                    item.getPrice(), itemTotal);
        }

        System.out.println("\n----------------------------");
        System.out.printf("Total: $%-10.2f%n", total);
    }

}
