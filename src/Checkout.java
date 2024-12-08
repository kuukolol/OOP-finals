import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Random;

public class Checkout {

    public static void showReceipt(Map<String, CartItem> cart, MenuCategory burgersCategory,
            MenuCategory chickensCategory, MenuCategory drinksCategory) {
        double totalAmount = 0;
        StringBuilder receipt = new StringBuilder();

        String fastFoodName = "NIOT Fast Food";
        String receiptId = generateReceiptId();
        String currentDateTime = getCurrentDateTime();

        receipt.append(fastFoodName).append("\n")
                .append("Receipt ID: ").append(receiptId).append("\n")
                .append("Date & Time: ").append(currentDateTime).append("\n")
                .append("----------------------------------------------------------------------------------\n");

        String format = "%-35s %5s %15s %15s\n";
        receipt.append(String.format(format, "Item", "Qty", "Price", "Total"));
        receipt.append("----------------------------------------------------------------------------------\n");

        for (Map.Entry<String, CartItem> entry : cart.entrySet()) {
            CartItem cartItem = entry.getValue();
            receipt.append(String.format("%-35s %5s %15s %15s\n",
                    cartItem.getName(),
                    "x" + cartItem.getQuantity(),
                    "₱" + String.format("%.2f", cartItem.getPrice()),
                    "₱" + String.format("%.2f", cartItem.getTotalPrice())));

            totalAmount += cartItem.getTotalPrice();
        }

        receipt.append("----------------------------------------------------------------------------------\n")
                .append(String.format("%-35s %5s %15s %15s\n", "Total Amount:", "", "",
                        "₱" + String.format("%.2f", totalAmount)))
                .append("----------------------------------------------------------------------------------\n");

        receipt.append("Thank you for ordering at ").append(fastFoodName).append("!\n");

        TextArea receiptArea = new TextArea(receipt.toString());
        receiptArea.setEditable(false);
        receiptArea.setStyle(
                "-fx-font-family: 'Courier New'; -fx-font-size: 12px; -fx-background-color: #f9f9f9; -fx-padding: 10px;");
        receiptArea.setWrapText(true);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(receiptArea);

        Scene receiptScene = new Scene(borderPane, 750, 600);
        Stage receiptStage = new Stage();
        receiptStage.setTitle("Receipt");
        receiptStage.setScene(receiptScene);
        receiptStage.show();
    }

    private static String generateReceiptId() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random rand = new Random();
        StringBuilder receiptId = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            int randomIndex = rand.nextInt(chars.length());
            receiptId.append(chars.charAt(randomIndex));
        }
        return receiptId.toString();
    }

    private static String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new java.util.Date());
    }
}
