/**
 * MenuOrderingGUI.java
 * 
 * This class implements a graphical user interface (GUI) for a menu ordering system.
 * Users can select menu items, view their order summary, and save their order to a file.
 * 
 * Features:
 * - Display menu items (Pizzas, Sandwiches, Bowls, Drinks)
 * - Add/remove items from the order
 * - Calculate total price
 * - Save the order to a text file, appending to any existing orders
 * 
 * Author: Jose Daniel
 * Date: 10/14/2024
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MenuOrderingGUI extends JFrame implements ActionListener {
    // Menu Items (Pizzas, Sandwiches, Bowls, Drinks)
    private MenuItem cheesePizza, pepePizza, bbqPizza;
    private MenuItem basilChickenSandwich, bahnmiSandwich, pestoSandwich;
    private MenuItem chickenBowl, tunaBowl;
    private MenuItem water, powerade, tea;  // Drinks

    // GUI Components
    private JButton btnCheesePizza, btnPepePizza, btnBBQPizza;  // Pizza buttons
    private JButton btnBasilChicken, btnBahnmi, btnPesto;       // Sandwich buttons
    private JButton btnChickenBowl, btnTunaBowl;                // Bowl buttons
    private JButton btnWater, btnPowerade, btnTea;              // Drink buttons
    private JButton btnRemoveLast;  // Button to remove last item
    private JButton btnEndOrder;    // Button to end the order

    private JTextArea orderSummary;  // Text area to display the order summary
    private JLabel totalLabel;  // Label to show the total order price

    private Order currentOrder;  // The current order object

    /// Constructor to set up the GUI
public MenuOrderingGUI() {
    // Initialize the menu items with their names, prices, and ingredients
    cheesePizza = new MenuItem("Cheese Pizza", 6.99, new ArrayList<>(Arrays.asList("Cheese", "Tomato Sauce", "Dough")));
    pepePizza = new MenuItem("Pepe Pizza", 9.99, new ArrayList<>(Arrays.asList("Pepperoni", "Cheese", "Tomato Sauce")));
    bbqPizza = new MenuItem("BBQ Pizza", 9.99, new ArrayList<>(Arrays.asList("BBQ Sauce", "Chicken", "Cheese")));

    // Initialize sandwiches
    basilChickenSandwich = new MenuItem("Basil Chicken Sandwich", 7.99, new ArrayList<>(Arrays.asList("Chicken", "Basil", "Lettuce", "Bread")));
    bahnmiSandwich = new MenuItem("Bahnmi Sandwich", 8.99, new ArrayList<>(Arrays.asList("Pork", "Pickled Veggies", "Bread", "Cilantro")));
    pestoSandwich = new MenuItem("Pesto Sandwich", 6.99, new ArrayList<>(Arrays.asList("Pesto", "Tomato", "Cheese", "Bread")));

    // Initialize bowls
    chickenBowl = new MenuItem("Chicken Bowl", 11.99, new ArrayList<>(Arrays.asList("Chicken", "Rice", "Veggies")));
    tunaBowl = new MenuItem("Tuna Bowl", 12.99, new ArrayList<>(Arrays.asList("Tuna", "Rice", "Sauce")));

    // Initialize drinks with no ingredients
    water = new MenuItem("Water", 3.50, new ArrayList<>());  // No ingredients for water
    powerade = new MenuItem("Powerade", 3.50, new ArrayList<>());  // No ingredients for Powerade
    tea = new MenuItem("Tea", 4.00, new ArrayList<>());  // No ingredients for tea

    // Prompt user for customer name and initialize the current order with it
    String customerName = JOptionPane.showInputDialog("Enter Customer Name:");
    currentOrder = new Order(customerName); // Create a new order using the entered customer name

    // Set up the window properties
    setTitle("Menu Ordering System"); // Set the title of the window
    setSize(600, 400); // Set the size of the window
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Define what happens when the window is closed
    setLayout(new BorderLayout()); // Use BorderLayout for arranging components

    // Create buttons for each menu item (to be implemented in another method)
    setupMenuButtons();

    // Create a text area to display the order summary and set it to read-only
    orderSummary = new JTextArea(10, 30); // Create a text area with specified rows and columns
    orderSummary.setEditable(false);  // Make the text area non-editable

    // Create a label to display the total price of the order
    totalLabel = new JLabel("Total: $0.00"); // Initialize with a default total

    // Create a button to remove the last item from the order
    btnRemoveLast = new JButton("Remove Last Item"); // Button label
    btnRemoveLast.addActionListener(e -> { // Add action listener for button
        currentOrder.removeLastItem(); // Remove the last item from the order
        updateOrderSummary(); // Update the order summary display
    });

        // Create a button to end the order
        btnEndOrder = new JButton("End Order");
        btnEndOrder.addActionListener(e -> {
    try {
        currentOrder.appendOrderToFile(); // Save order to file
        JOptionPane.showMessageDialog(this, "Order saved!");
    } catch (IOException ex) {
        JOptionPane.showMessageDialog(this, "Error saving order to file: " + ex.getMessage());
    }
    JOptionPane.showMessageDialog(null, "Thank you for your order!");
    System.exit(0);
});
        // Add components to the frame
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(2, 1));  // 2 rows, 1 column
        controlPanel.add(btnRemoveLast);
        controlPanel.add(btnEndOrder);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(orderSummary), BorderLayout.CENTER);  // Add the order summary in the center
        mainPanel.add(totalLabel, BorderLayout.SOUTH);  // Add the total label at the bottom

        add(mainPanel, BorderLayout.CENTER);  // Main panel in the center
        add(controlPanel, BorderLayout.EAST);  // Control panel on the east side

        setVisible(true);  // Make the GUI visible
    }

    // Method to set up menu buttons
    private void setupMenuButtons() {
        JPanel buttonPanel = new JPanel(new GridLayout(0, 3));  // Dynamic rows and 3 columns

        // Add pizza buttons
        btnCheesePizza = createMenuButton(cheesePizza);
        btnPepePizza = createMenuButton(pepePizza);
        btnBBQPizza = createMenuButton(bbqPizza);
        buttonPanel.add(btnCheesePizza);
        buttonPanel.add(btnPepePizza);
        buttonPanel.add(btnBBQPizza);

        // Add sandwich buttons
        btnBasilChicken = createMenuButton(basilChickenSandwich);
        btnBahnmi = createMenuButton(bahnmiSandwich);
        btnPesto = createMenuButton(pestoSandwich);
        buttonPanel.add(btnBasilChicken);
        buttonPanel.add(btnBahnmi);
        buttonPanel.add(btnPesto);

        // Add bowl buttons
        btnChickenBowl = createMenuButton(chickenBowl);
        btnTunaBowl = createMenuButton(tunaBowl);
        buttonPanel.add(btnChickenBowl);
        buttonPanel.add(btnTunaBowl);

        // Add drink buttons
        btnWater = createMenuButton(water);
        btnPowerade = createMenuButton(powerade);
        btnTea = createMenuButton(tea);
        buttonPanel.add(btnWater);
        buttonPanel.add(btnPowerade);
        buttonPanel.add(btnTea);

        add(buttonPanel, BorderLayout.NORTH);  // Button panel at the top
    }

    // Helper method to create a menu button for each item
    private JButton createMenuButton(MenuItem item) {
        JButton button = new JButton(item.getName() + " - $" + item.getPrice());
        button.addActionListener(this);
        return button;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
    
            // Check which button was clicked and add the corresponding menu item with quantity
            if (button == btnCheesePizza) {
                currentOrder.addItem(cheesePizza, 1);
            } else if (button == btnPepePizza) {
                currentOrder.addItem(pepePizza, 1);
            } else if (button == btnBBQPizza) {
                currentOrder.addItem(bbqPizza, 1);
            } else if (button == btnBasilChicken) {
                currentOrder.addItem(basilChickenSandwich, 1);
            } else if (button == btnBahnmi) {
                currentOrder.addItem(bahnmiSandwich, 1);
            } else if (button == btnPesto) {
                currentOrder.addItem(pestoSandwich, 1);
            } else if (button == btnChickenBowl) {
                currentOrder.addItem(chickenBowl, 1);
            } else if (button == btnTunaBowl) {
                currentOrder.addItem(tunaBowl, 1);
            } else if (button == btnWater) {
                currentOrder.addItem(water, 1);
            } else if (button == btnPowerade) {
                currentOrder.addItem(powerade, 1);
            } else if (button == btnTea) {
                currentOrder.addItem(tea, 1);
            } else if (button == btnEndOrder) {
                try {
                    currentOrder.appendOrderToFile(); // This method must throw IOException
                    JOptionPane.showMessageDialog(this, "Order saved!"); // Notify user
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error saving order to file: " + ex.getMessage());
                }
            }
    
            updateOrderSummary();  // Update the order summary after adding an item
        }
    }
    

private void updateOrderSummary() {
    orderSummary.setText(currentOrder.toString());  // Use toString() for order summary
    totalLabel.setText("Total: $" + String.format("%.2f", currentOrder.getTotalPrice()));  // Use getTotalPrice()
}

// Main method to run the GUI
public static void main(String[] args) {
    SwingUtilities.invokeLater(MenuOrderingGUI::new);  // Create GUI on the Event Dispatch Thread
}
}