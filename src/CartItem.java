public class CartItem {
    private String name;
    private int quantity;
    private double price;

    public CartItem(String name, double price) {
        this.name = name;
        this.quantity = 1;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void increaseQuantity() {
        this.quantity++;
    }

    public void decreaseQuantity() {
        if (this.quantity > 1) {
            this.quantity--;
        }
    }

    public double getTotalPrice() {
        return this.quantity * this.price;
    }
}
