import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class FastFoodApp extends Application {

    // Create categories for the menu (Burgers, Chicken, and Drinks)
    private MenuCategory burgersCategory = new BurgersCategory();
    private MenuCategory chickensCategory = new ChickenCategory();
    private MenuCategory drinksCategory = new DrinksCategory();
    // Create a cart to store the items the user orders
    private Map<String, CartItem> cart = new HashMap<>();
    private VBox cartItemsContainer = new VBox(10); // This is where the cart items will be displayed

    @Override
    public void start(Stage primaryStage) {
        // Create the main layout for the app
        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: #7289da;"); // background color use color pallete online for codes

        // Title for the app
        Label title = new Label("🍔 NIOT Fast Food Ordering 🍟🥤");
        title.setFont(Font.font("Arial", 32)); // Make the font bigger
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;"); // Style the title text
        title.setAlignment(Pos.CENTER); // Center the title in the layout
        VBox titleBox = new VBox(title); // Put the title in a VBox to control alignment
        titleBox.setAlignment(Pos.CENTER); // Center the VBox containing the title
        mainLayout.getChildren().add(titleBox);

        // Main content: left side for menu and right side for cart
        HBox mainContent = new HBox(20);

        // Left side (menu): ComboBox for category selection and ListView for items
        VBox leftSide = new VBox(20);
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll("Burgers 🍔", "Chicken 🍗", "Drinks 🥤");
        categoryComboBox.setPromptText("Select a Category"); // Prompt text to guide the user
        categoryComboBox.setStyle(
                "-fx-font-size: 16px; -fx-padding: 10px; -fx-background-color: #FFFFFFFF; -fx-text-fill: white; -fx-border-radius: 8px;");

        // Hover effect for ComboBox to make it interactive
        categoryComboBox.setOnMouseEntered(e -> categoryComboBox.setStyle(
                "-fx-font-size: 16px; -fx-padding: 10px; -fx-background-color: #2980b9; -fx-text-fill: white; -fx-border-radius: 8px;"));
        categoryComboBox.setOnMouseExited(e -> categoryComboBox.setStyle(
                "-fx-font-size: 16px; -fx-padding: 10px; -fx-background-color: #3498db; -fx-text-fill: white; -fx-border-radius: 8px;"));

        // ListView to show items based on category selection
        ListView<String> itemList = new ListView<>();
        itemList.setPrefHeight(300);
        itemList.setStyle("-fx-background-color: #ffffff; -fx-border-radius: 5px; -fx-border-color: #ddd;");

        // Update the ListView when a category is selected
        categoryComboBox.setOnAction(e -> {
            String selectedCategory = categoryComboBox.getValue();
            updateItemList(itemList, selectedCategory);
        });

        // Add ComboBox and ListView to the left side layout
        leftSide.getChildren().addAll(categoryComboBox, itemList);

        // Add item to the cart when an item is clicked in the ListView
        itemList.setOnMouseClicked(event -> {
            String selectedItem = itemList.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                addItemToOrder(selectedItem);
                updateCartItems();
            }
        });

        // Right side (cart): Shows the order summary and checkout button
        VBox rightSide = new VBox(20);

        // Order summary header with a label and button to clear the cart
        HBox orderSummaryHeader = new HBox(20);
        Label orderSummaryLabel = new Label("🛍️ Order Summary");
        orderSummaryLabel.setFont(Font.font("Arial", 18));
        orderSummaryLabel.setStyle("-fx-font-weight: bold;");
        Button clearAllButton = new Button("Clear All"); // Button to clear the cart
        clearAllButton.setStyle(
                "-fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-color: #e74c3c; -fx-text-fill: white; -fx-border-radius: 8px;");
        clearAllButton.setMaxWidth(100);
        clearAllButton.setOnAction(e -> clearAllItems()); // Clears the cart when clicked

        orderSummaryHeader.getChildren().addAll(orderSummaryLabel, clearAllButton);
        orderSummaryHeader.setAlignment(Pos.TOP_RIGHT);

        // Create a container for the cart items and add it to the right side
        VBox orderSummary = new VBox(10);
        orderSummary.setStyle("-fx-background-color: #ffffff; -fx-border-radius: 5px; -fx-border-color: #ddd;");
        orderSummary.setPadding(new Insets(10));
        orderSummary.setMinHeight(400);
        orderSummary.setMinWidth(400);
        orderSummary.getChildren().add(cartItemsContainer); // Add cart items here

        // Checkout button to complete the order
        Button checkoutButton = new Button("Checkout 🛒");
        checkoutButton.setStyle(
                "-fx-font-size: 16px; -fx-padding: 10px; -fx-background-color: #2ecc71; -fx-text-fill: white; -fx-border-radius: 8px;");
        checkoutButton.setMaxWidth(200); // Set the size of the button
        checkoutButton.setOnAction(e -> Checkout.showReceipt(cart, burgersCategory,
                chickensCategory, drinksCategory)); // Show receipt

        // Add the order summary and checkout button to the right side
        rightSide.getChildren().addAll(orderSummaryHeader, orderSummary, checkoutButton);

        // Add left and right side to the main content layout
        mainContent.getChildren().addAll(leftSide, rightSide);
        mainContent.setPadding(new Insets(20));

        // Add everything to the main layout
        mainLayout.getChildren().addAll(mainContent);

        // Set up and show the app window
        Scene scene = new Scene(mainLayout, 900, 675);
        primaryStage.setTitle("🍔 NIOT Fast Food Ordering 🍟🥤");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to update the ListView with items based on the selected category
    private void updateItemList(ListView<String> itemList, String category) {
        itemList.getItems().clear();
        switch (category) {
            case "Burgers 🍔":
                for (Item item : burgersCategory.getItems()) {
                    itemList.getItems().add(item.toString()); // Add items from the burger category
                }
                break;
            case "Chicken 🍗":
                for (Item item : chickensCategory.getItems()) {
                    itemList.getItems().add(item.toString()); // Add items from the chicken category
                }
                break;
            case "Drinks 🥤":
                for (Item item : drinksCategory.getItems()) {
                    itemList.getItems().add(item.toString()); // Add items from the drinks category
                }
                break;
        }
    }

    // Method to add an item to the cart
    private void addItemToOrder(String itemDescription) {
        String[] parts = itemDescription.split(" - ₱"); // Split the item description and price
        if (parts.length < 2)
            return;

        String name = parts[0]; // Get the name of the item
        double price;
        try {
            price = Double.parseDouble(parts[1]); // Convert the price to a number
        } catch (NumberFormatException e) {
            price = 0.0; // If the price is not valid, set it to 0
        }

        // Check if the item is already in the cart
        if (cart.containsKey(name)) {
            cart.get(name).increaseQuantity(); // Increase quantity if the item is already in the cart
        } else {
            cart.put(name, new CartItem(name, price)); // Add the item to the cart
        }
    }

    // Method to remove an item from the cart
    private void removeItemFromOrder(String itemName) {
        if (cart.containsKey(itemName)) {
            CartItem cartItem = cart.get(itemName);
            if (cartItem.getQuantity() == 1) {
                cart.remove(itemName);
            } else {
                cartItem.decreaseQuantity();
            }
        }
    }

    // Method to update the cart display
    private void updateCartItems() {
        cartItemsContainer.getChildren().clear(); // Clear the cart display
        for (Map.Entry<String, CartItem> entry : cart.entrySet()) {
            CartItem cartItem = entry.getValue();

            HBox cartItemBox = new HBox(10);
            cartItemBox.setStyle(
                    "-fx-padding: 10px; -fx-background-color: #ffffff; -fx-border-radius: 5px; -fx-border-color: #ddd;");
            cartItemBox.setAlignment(Pos.CENTER_LEFT);

            // Label showing the name, price, and quantity of the item
            Label itemLabel = new Label(cartItem.getName() + " - ₱" + String.format("%.2f", cartItem.getPrice()));
            if (cartItem.getQuantity() > 1) {
                itemLabel.setText(itemLabel.getText() + " x" + cartItem.getQuantity()); // Show quantity if more than 1
            }

            // Button to remove the item from the cart
            Button removeButton = new Button("❌");
            removeButton.setStyle(
                    "-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-border-radius: 5px; -fx-font-size: 14px;");
            removeButton.setOnAction(e -> {
                removeItemFromOrder(cartItem.getName()); // Remove the item from the cart
                updateCartItems(); // Update the cart display
            });

            Region spacer = new Region(); // Space to push the remove button to the right
            HBox.setHgrow(spacer, Priority.ALWAYS);

            cartItemBox.getChildren().addAll(itemLabel, spacer, removeButton);
            cartItemsContainer.getChildren().add(cartItemBox);
        }
    }

    // Method to clear all items from the cart
    private void clearAllItems() {
        cart.clear(); // Clear the entire cart
        updateCartItems(); // Update the cart display
    }

    public static void main(String[] args) {
        launch(args); // start the gui
    }
}
