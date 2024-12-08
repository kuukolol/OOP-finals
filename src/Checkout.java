import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Random;

public class Checkout {

    // This method shows the receipt after the user finishes their order
    public static void showReceipt(Map<String, CartItem> cart, MenuCategory burgersCategory,
            MenuCategory chickensCategory, MenuCategory drinksCategory) {
        double totalAmount = 0; // Keep track of the total amount to be paid
        StringBuilder receipt = new StringBuilder(); // Build the receipt as a string

        String fastFoodName = "NIOT Fast Food"; // The name of the fast food restaurant
        String receiptId = generateReceiptId(); // Generate a random receipt ID
        String currentDateTime = getCurrentDateTime(); // Get the current date and time

        // Add the restaurant name, receipt ID, and date/time at the top of the receipt
        receipt.append(centerText(fastFoodName)).append("\n")
                .append(centerText("Receipt ID: " + receiptId)).append("\n")
                .append(centerText("Date & Time: " + currentDateTime)).append("\n")
                .append("----------------------------------------------------------------------------------\n");

        // Format the item list (showing item name, quantity, price, and total)
        String format = "%-35s %5s %15s %15s\n"; // Set column width for the receipt
        receipt.append(String.format(format, "Item", "Qty", "Price", "Total"));
        receipt.append("----------------------------------------------------------------------------------\n");

        // Loop through the cart and add each item to the receipt
        for (Map.Entry<String, CartItem> entry : cart.entrySet()) {
            CartItem cartItem = entry.getValue(); // Get the details of the cart item
            // Add item details (name, quantity, price, and total price) to the receipt
            receipt.append(String.format("%-35s %5s %15s %15s\n",
                    cartItem.getName(),
                    "x" + cartItem.getQuantity(),
                    "₱" + String.format("%.2f", cartItem.getPrice()),
                    "₱" + String.format("%.2f", cartItem.getTotalPrice())));

            totalAmount += cartItem.getTotalPrice(); // Add item total price to the total amount
        }

        // Add the total amount at the bottom of the receipt
        receipt.append("----------------------------------------------------------------------------------\n")
                .append(String.format("%-35s %5s %15s %15s\n", "Total Amount:", "", "",
                        "₱" + String.format("%.2f", totalAmount)))
                .append("----------------------------------------------------------------------------------\n");

        // Thank the customer at the end of the receipt
        receipt.append("Thank you for ordering at ").append(fastFoodName).append("!\n");

        // Create a TextArea to display the receipt text
        TextArea receiptArea = new TextArea(receipt.toString());
        receiptArea.setEditable(false); // Make the receipt non-editable
        receiptArea.setStyle(
                "-fx-font-family: 'Courier New'; -fx-font-size: 12px; -fx-background-color: #f9f9f9; -fx-padding: 10px;");
        receiptArea.setWrapText(true); // Wrap the text if it goes too long

        // Set up the layout for showing the receipt on the screen
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(receiptArea);

        // Create the scene and show the receipt in a window
        Scene receiptScene = new Scene(borderPane, 600, 600);
        Stage receiptStage = new Stage();
        receiptStage.setTitle("Receipt");
        receiptStage.setScene(receiptScene);
        receiptStage.show();
    }

    // Method to generate a random receipt ID (using letters and numbers)
    private static String generateReceiptId() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; // Possible characters for the ID
        Random rand = new Random(); // Random generator to pick characters
        StringBuilder receiptId = new StringBuilder(); // Build the receipt ID
        for (int i = 0; i < 12; i++) {
            int randomIndex = rand.nextInt(chars.length()); // Get a random character
            receiptId.append(chars.charAt(randomIndex)); // Add the character to the ID
        }
        return receiptId.toString(); // Return the generated receipt ID
    }

    // Method to get the current date and time in a readable format
    private static String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd h:mm a"); // Date format
        return sdf.format(new java.util.Date()); // Return the formatted date and time
    }

    // Helper method to center text for the receipt (so it looks neat)
    private static String centerText(String text) {
        int width = 80; // Set the width for centering text
        int padding = (width - text.length()) / 2; // Calculate how much space is needed to center the text
        StringBuilder centeredText = new StringBuilder();
        for (int i = 0; i < padding; i++) {
            centeredText.append(" "); // Add spaces to the left side
        }
        centeredText.append(text); // Add the actual text
        return centeredText.toString(); // Return the centered text
    }
}
