/**
 * Order.java
 * 
 * This class represents an order in the menu ordering system. It manages a list of 
 * menu items selected by the customer and provides methods to manipulate the order.
 * 
 * Features:
 * - Add and remove items from the order
 * - Calculate the total price of the order
 * - Serialize order details for saving to a file
 * - Append order details to a text file for persistent storage
 * 
 * Author: Jose Daniel
 * Date: 10/14/2024
 */
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

// Class representing an order in the menu ordering system
class Order implements Serializable {
    private static final long serialVersionUID = 1L; // For serialization compatibility
    private ArrayList<MenuItem> items; // List of menu items in the order
    private String customerName; // Name of the customer placing the order

    // Default constructor initializes the order with a default customer name
    public Order() {
        this.customerName = "Unknown Customer"; // Set default customer name
        items = new ArrayList<>(); // Initialize the items list
    }

    // Constructor that initializes the order with a given customer name
    public Order(String customerName) {
        this.customerName = customerName; // Set the customer name
        items = new ArrayList<>(); // Initialize the items list
    }

    // Method to add a specified quantity of a menu item to the order
    public void addItem(MenuItem item, int quantity) {
        for (int i = 0; i < quantity; i++) {
            items.add(item); // Add the item to the list for the specified quantity
        }
    }

    // Method to remove the last item added to the order
    public void removeLastItem() {
        if (!items.isEmpty()) { // Check if there are items in the order
            items.remove(items.size() - 1); // Remove the last item
        }
    }

    // Method to calculate the total price of all items in the order
    public double getTotalPrice() {
        return items.stream().mapToDouble(MenuItem::getPrice).sum(); // Sum the prices of all items
    }

    // Overridden toString method to provide a summary of the order
    @Override
    public String toString() {
        StringBuilder summary = new StringBuilder("Order for " + customerName + ":\n");
        for (MenuItem item : items) {
            summary.append(item.getName()).append(" - $").append(String.format("%.2f", item.getPrice())).append("\n");
        }
        summary.append("Total: $").append(String.format("%.2f", getTotalPrice())); // Add total price to summary
        return summary.toString(); // Return the complete order summary
    }

    // Method to append the order details to a text file
    public void appendOrderToFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("orders.txt", true))) { // 'true' to append to the file
            writer.write("Order for: " + customerName); // Write customer name
            writer.newLine(); // New line for formatting
            for (MenuItem item : items) {
                writer.write(item.getName() + " - $" + item.getPrice()); // Write item details
                writer.newLine(); // New line for each item
            }
            writer.write("Total: $" + getTotalPrice()); // Write total price
            writer.newLine(); // New line for the next order
        }
    }
}
