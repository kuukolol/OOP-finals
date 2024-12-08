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

    private MenuCategory burgersCategory = new BurgersCategory();
    private MenuCategory chickensCategory = new ChickenCategory();
    private MenuCategory drinksCategory = new DrinksCategory();
    private Map<String, CartItem> cart = new HashMap<>();
    private VBox cartItemsContainer = new VBox(10);

    @Override
    public void start(Stage primaryStage) {
        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: #f3f3f3;");

        Label title = new Label("🍔 NIOT Fast Food Ordering 🍟🥤");
        title.setFont(Font.font("Arial", 32));
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        mainLayout.getChildren().add(title);

        HBox categoryButtons = new HBox(20);
        Button burgerButton = createCategoryButton("Burgers 🍔");
        Button chickenButton = createCategoryButton("Chicken 🍗");
        Button drinkButton = createCategoryButton("Drinks 🥤");
        categoryButtons.getChildren().addAll(burgerButton, chickenButton, drinkButton);

        ListView<String> itemList = new ListView<>();
        itemList.setPrefHeight(300);
        itemList.setStyle("-fx-background-color: #ffffff; -fx-border-radius: 5px; -fx-border-color: #ddd;");

        VBox orderSummary = new VBox(10);
        Label orderSummaryLabel = new Label("🛍️ Order Summary:");
        orderSummaryLabel.setFont(Font.font("Arial", 18));
        orderSummary.setPadding(new Insets(10));
        orderSummary.setStyle("-fx-background-color: #ffffff; -fx-border-radius: 5px; -fx-border-color: #ddd;");
        orderSummary.setMinHeight(400);
        orderSummary.setMinWidth(500);
        orderSummary.getChildren().addAll(orderSummaryLabel, cartItemsContainer);

        Button checkoutButton = new Button("Checkout 🛒");
        checkoutButton.setStyle(
                "-fx-font-size: 16px; -fx-padding: 12px; -fx-background-color: #2ecc71; -fx-text-fill: white; -fx-border-radius: 8px;");
        checkoutButton.setMaxWidth(Double.MAX_VALUE);
        checkoutButton.setOnAction(e -> Checkout.showReceipt(cart, burgersCategory, chickensCategory, drinksCategory));

        burgerButton.setOnAction(e -> updateItemList(itemList, "Burgers 🍔"));
        chickenButton.setOnAction(e -> updateItemList(itemList, "Chicken 🍗"));
        drinkButton.setOnAction(e -> updateItemList(itemList, "Drinks 🥤"));

        itemList.setOnMouseClicked(event -> {
            String selectedItem = itemList.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                addItemToOrder(selectedItem);
                updateCartItems();
            }
        });

        HBox content = new HBox(20, itemList, orderSummary);
        content.setPadding(new Insets(20));

        mainLayout.getChildren().addAll(categoryButtons, content, checkoutButton);

        Scene scene = new Scene(mainLayout, 1100, 700);
        primaryStage.setTitle("🍔 NIOT Fast Food Ordering 🍟🥤");
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

    private void updateItemList(ListView<String> itemList, String category) {
        itemList.getItems().clear();
        switch (category) {
            case "Burgers 🍔":
                for (Item item : burgersCategory.getItems()) {
                    itemList.getItems().add(item.toString());
                }
                break;
            case "Chicken 🍗":
                for (Item item : chickensCategory.getItems()) {
                    itemList.getItems().add(item.toString());
                }
                break;
            case "Drinks 🥤":
                for (Item item : drinksCategory.getItems()) {
                    itemList.getItems().add(item.toString());
                }
                break;
        }
    }

    private void addItemToOrder(String itemDescription) {
        String[] parts = itemDescription.split(" - ₱");
        if (parts.length < 2)
            return;

        String name = parts[0];
        double price;
        try {
            price = Double.parseDouble(parts[1]);
        } catch (NumberFormatException e) {
            price = 0.0;
        }

        if (cart.containsKey(name)) {
            cart.get(name).increaseQuantity();
        } else {
            cart.put(name, new CartItem(name, price));
        }
    }

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

    private void updateCartItems() {
        cartItemsContainer.getChildren().clear();
        for (Map.Entry<String, CartItem> entry : cart.entrySet()) {
            CartItem cartItem = entry.getValue();

            HBox cartItemBox = new HBox(10);
            cartItemBox.setStyle(
                    "-fx-padding: 10px; -fx-background-color: #ffffff; -fx-border-radius: 5px; -fx-border-color: #ddd;");
            cartItemBox.setAlignment(Pos.CENTER_LEFT);

            Label itemLabel = new Label(cartItem.getName() + " - ₱" + String.format("%.2f", cartItem.getPrice()));
            if (cartItem.getQuantity() > 1) {
                itemLabel.setText(itemLabel.getText() + " x" + cartItem.getQuantity());
            }

            Button removeButton = new Button("❌");
            removeButton.setStyle(
                    "-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-border-radius: 5px; -fx-font-size: 14px;");
            removeButton.setOnAction(e -> {
                removeItemFromOrder(cartItem.getName());
                updateCartItems();
            });

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            cartItemBox.getChildren().addAll(itemLabel, spacer, removeButton);
            cartItemsContainer.getChildren().add(cartItemBox);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
