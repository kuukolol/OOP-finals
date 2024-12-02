# Restaurant Ordering System (Fast Food Chain)

## Overview
This is a simple restaurant ordering system in Java. The program allows users to view menu items (burgers, chicken, and drinks), choose their items, select a quantity, and receive a receipt with the total price.

### Features
- Display different categories of items (Burgers, Chicken, Drinks).
- Sort items by price (solo or meal price).
- Place orders and specify quantities.
- Print a receipt showing ordered items and their total cost.

## Structure

The project is split into multiple classes:

1. **App.java**: Main class that starts the program and shows the menu.
2. **Burger.java**: Defines the `Burger` class with solo and meal prices.
3. **Chicken.java**: Defines the `Chicken` class with solo and meal prices.
4. **Drink.java**: Defines the `Drink` class with only solo prices (drinks don't have a meal option).
5. **Menu.java**: Handles displaying the menu and managing the ordering process.
6. **MenuCategory.java**: Interface implemented by `Burger`, `Chicken`, and `Drink` to standardize the price methods.
7. **MenuItem.java**: Holds and initializes the menu data for burgers, chicken, and drinks.
8. **OrderItem.java**: Stores information about each ordered item (name, price, quantity).

---

## How It Works

### Menu Categories
- **Burger**: Includes various burger items, each with a solo price and a meal price.
- **Chicken**: Includes different chicken meals with solo and meal prices.
- **Drink**: Includes drink items, but only with solo prices.

### Menu Display
- The menu items are sorted by price (either solo price or meal price) and displayed in a readable format with item numbers.

### Ordering Process
1. The user can select an item by entering its number and specifying the quantity.
2. For non-drink items, the user can choose between the solo price and the meal price.
3. The program continuously asks for input until the user types "done".
4. Once done, the program prints a receipt showing the ordered items and the total cost.

---

## Flowchart

### 1. Display Menu
- Show Burger, Chicken, and Drink menus, sorted by price.
  
### 2. Place Order
- User selects items by entering the number and quantity.
- For non-drinks, the user selects "solo" or "meal" price.
- Add selected item to the order.

### 3. Print Receipt
- Display all ordered items with their price and quantity.
- Calculate the total cost and display it.

---

## Flowchart (High-Level Overview)

```plaintext
  +---------------------------+
  |       Start Program        |
  +---------------------------+
             |
  +---------------------------+
  |   Display Burger Menu      |
  +---------------------------+
             |
  +---------------------------+
  |   Display Chicken Menu     |
  +---------------------------+
             |
  +---------------------------+
  |   Display Drink Menu       |
  +---------------------------+
             |
  +---------------------------+
  |   Get User Input (Order)   |
  +---------------------------+
             |
  +---------------------------+
  |   Add Items to Order       |
  +---------------------------+
             |
  +---------------------------+
  |   Print Receipt            |
  +---------------------------+
             |
  +---------------------------+
  |       End Program          |
  +---------------------------+
```

---

## Example

**Sample Output:**

```
===== NIoT RESTAURANT =====
=====   BURGER MENU   =====
1. Hamburger                      Solo Price: $3.00    Meal Price: $5.00
2. Cheeseburger                   Solo Price: $3.50    Meal Price: $6.00
...

=====   CHICKEN MENU  =====
1. Fried Chicken                  Solo Price: $8.99    Meal Price: $13.99
...

=====   DRINKS MENU   =====
1. Coca-Cola                      Price: $2.49
...

Enter the number of the item you want to order, followed by quantity (e.g., 1 2 for 2 of item 1). Type 'done' when finished:
1 2
2 1
done

===== RECEIPT =====
Hamburger                     x2 $3.00    Total: $6.00
Cheeseburger                  x1 $3.50    Total: $3.50
----------------------------
Total: $9.50
```

---
