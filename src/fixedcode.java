import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class fixedcode extends Application {

    private List<Item> burgers = new ArrayList<>();
    private List<Item> chickens = new ArrayList<>();
    private List<Item> drinks = new ArrayList<>();
    private Map<String, Integer> cart = new HashMap<>();
    private VBox cartItemsContainer = new VBox(10);

    @Override
    public void start(Stage primaryStage) {
        // Initialize Menu Items
        initializeMenuItems();

        // Create UI elements
        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: #f3f3f3;");

        // Title
        Label title = new Label("NIOT Fast Food Ordering");
        title.setFont(Font.font("Arial", 32));
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        mainLayout.getChildren().add(title);

        // Create category buttons
        HBox categoryButtons = new HBox(20);
        Button burgerButton = createCategoryButton("Burgers");
        Button chickenButton = createCategoryButton("Chicken");
        Button drinkButton = createCategoryButton("Drinks");
        categoryButtons.getChildren().addAll(burgerButton, chickenButton, drinkButton);

        // Create an item list for selected category
        ListView<String> itemList = new ListView<>();
        itemList.setPrefHeight(300);
        itemList.setStyle("-fx-background-color: #ffffff; -fx-border-radius: 5px; -fx-border-color: #ddd;");

        // Create order summary area with more height and wider width
        VBox orderSummary = new VBox(10);
        orderSummary.setPadding(new Insets(10));
        orderSummary.setStyle("-fx-background-color: #ffffff; -fx-border-radius: 5px; -fx-border-color: #ddd;");
        orderSummary.setMinHeight(400); // Increased height for the order summary
        orderSummary.setMinWidth(500); // Increased width for the order summary
        orderSummary.getChildren().addAll(new Label("Order Summary:"), cartItemsContainer);

        // Add event handlers for category buttons
        burgerButton.setOnAction(e -> updateItemList(itemList, "Burger"));
        chickenButton.setOnAction(e -> updateItemList(itemList, "Chicken"));
        drinkButton.setOnAction(e -> updateItemList(itemList, "Drink"));

        // Add event handler for item selection
        itemList.setOnMouseClicked(event -> {
            String selectedItem = itemList.getSelectionModel().getSelectedItem();
            addItemToOrder(selectedItem);
            updateCartItems();
        });

        // Arrange layout
        HBox content = new HBox(20, itemList, orderSummary);
        content.setPadding(new Insets(20));

        mainLayout.getChildren().addAll(categoryButtons, content);

        // Set Scene and Stage
        Scene scene = new Scene(mainLayout, 900, 600);
        primaryStage.setTitle("NIOT Fast Food Ordering");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createCategoryButton(String category) {
        Button button = new Button(category);
        button.setStyle(
                "-fx-font-size: 16px; -fx-padding: 12px; -fx-background-color: #3498db; -fx-text-fill: white; -fx-border-radius: 8px;");
        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-font-size: 16px; -fx-padding: 12px; -fx-background-color: #2980b9; -fx-text-fill: white; -fx-border-radius: 8px;"));
        button.setOnMouseExited(e -> button.setStyle(
                "-fx-font-size: 16px; -fx-padding: 12px; -fx-background-color: #3498db; -fx-text-fill: white; -fx-border-radius: 8px;"));
        return button;
    }

    private void initializeMenuItems() {
        // Initialize Burger Items
        burgers.add(new Item("Hamburger", 150));
        burgers.add(new Item("Cheeseburger", 180));
        burgers.add(new Item("Double Hamburger", 220));

        // Initialize Chicken Items
        chickens.add(new Item("Fried Chicken", 250));
        chickens.add(new Item("Spicy Chicken", 280));
        chickens.add(new Item("BBQ Chicken", 300));

        // Initialize Drinks Items
        drinks.add(new Item("Coca Cola", 50));
        drinks.add(new Item("Pepsi", 50));
        drinks.add(new Item("Iced Tea", 60));
    }

    private void updateItemList(ListView<String> itemList, String category) {
        List<Item> items = getCategoryItems(category);
        itemList.getItems().clear();
        for (Item item : items) {
            itemList.getItems().add(item.getName() + " - ₱" + item.getPrice());
        }
    }

    private List<Item> getCategoryItems(String category) {
        switch (category) {
            case "Burger":
                return burgers;
            case "Chicken":
                return chickens;
            case "Drink":
                return drinks;
            default:
                return new ArrayList<>();
        }
    }

    private void addItemToOrder(String itemDescription) {
        String[] parts = itemDescription.split(" - ₱");
        String name = parts[0];
        double price = Double.parseDouble(parts[1]);

        // Update cart with quantity
        cart.put(name, cart.getOrDefault(name, 0) + 1);
    }

    private void removeItemFromOrder(String itemName) {
        if (cart.containsKey(itemName)) {
            int currentQuantity = cart.get(itemName);
            if (currentQuantity > 1) {
                cart.put(itemName, currentQuantity - 1);
            } else {
                cart.remove(itemName);
            }
        }
    }

    private void updateCartItems() {
        cartItemsContainer.getChildren().clear();
        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            String itemName = entry.getKey();
            int quantity = entry.getValue();
            double price = getPriceForItem(itemName);

            HBox cartItem = new HBox(10);
            cartItem.setStyle(
                    "-fx-padding: 10px; -fx-background-color: #ffffff; -fx-border-radius: 5px; -fx-border-color: #ddd;");
            cartItem.setAlignment(Pos.CENTER_LEFT); // Align the item to the left

            Label itemLabel = new Label(itemName + " - ₱" + price);
            if (quantity > 1) {
                itemLabel.setText(itemLabel.getText() + " x" + quantity);
            }

            // Create remove button and position it at the far right of the container
            Button removeButton = new Button("X");
            removeButton.setStyle(
                    "-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-border-radius: 5px; -fx-font-size: 14px;");
            removeButton.setOnAction(e -> {
                removeItemFromOrder(itemName);
                updateCartItems();
            });

            // Create a Region to take up all remaining space and push the button to the
            // right
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS); // Let the spacer take up all available space, pushing the "X"
                                                    // button to the right

            // Add the label, spacer, and remove button to the cart item
            cartItem.getChildren().addAll(itemLabel, spacer, removeButton);
            cartItemsContainer.getChildren().add(cartItem);
        }
    }

    private double getPriceForItem(String itemName) {
        // Search in the menu to get the price for the item
        for (Item item : burgers) {
            if (item.getName().equals(itemName))
                return item.getPrice();
        }
        for (Item item : chickens) {
            if (item.getName().equals(itemName))
                return item.getPrice();
        }
        for (Item item : drinks) {
            if (item.getName().equals(itemName))
                return item.getPrice();
        }
        return 0;
    }

    // Item class to represent an individual menu item
    public static class Item {
        private String name;
        private double price;

        public Item(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}