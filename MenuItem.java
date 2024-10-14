/**
 * MenuItem.java
 * 
 * This class represents a menu item in the menu ordering system. It stores the 
 * item's name, price, and ingredients. It provides methods to access these 
 * properties and a string representation for display purposes.
 * 
 * Features:
 * - Store name, price, and ingredients of a menu item
 * - Access item properties via getter methods
 * - Provide a string representation of the menu item
 * 
 * Author: Jose Daniel
 * Date: 10/14/2024
 */

 import javax.swing.*;
 import java.awt.*;
 import java.io.*;
 import java.util.ArrayList;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.util.Arrays;
 import java.io.BufferedWriter;
 import java.io.FileWriter;
 import java.io.IOException;
 
 // Class representing a menu item in the ordering system
 class MenuItem {
     // Attributes to store the name, price, and ingredients of the menu item
     private String name;  // The name of the menu item
     private double price;  // The price of the menu item
     private ArrayList<String> ingredients;  // List of ingredients in the menu item
 
     // Constructor to initialize a MenuItem with its name, price, and ingredients
     public MenuItem(String name, double price, ArrayList<String> ingredients) {
         this.name = name;  // Set the name of the menu item
         this.price = price;  // Set the price of the menu item
         this.ingredients = ingredients;  // Set the ingredients of the menu item
     }
 
     // Getter method to retrieve the name of the menu item
     public String getName() {
         return name;  // Return the name
     }
 
     // Getter method to retrieve the price of the menu item
     public double getPrice() {
         return price;  // Return the price
     }
 
     // Getter method to retrieve the list of ingredients
     public ArrayList<String> getIngredients() {
         return ingredients;  // Return the list of ingredients
     }
 
     // Overridden toString method to provide a string representation of the menu item
     @Override
     public String toString() {
         return name + " - $" + price;  // Return the name and price formatted as a string
     }
 }
 