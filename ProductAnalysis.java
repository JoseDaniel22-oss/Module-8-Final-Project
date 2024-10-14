import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ProductAnalysis {

    private static final String ORDER_FILE = "orders.txt";
    private static Map<String, Integer> productCount = new HashMap<>();

    // Method to analyze the orders.txt file
    public static void analyzeOrderFile() {
        productCount.clear(); // Reset the count before re-analysis

        try (BufferedReader reader = new BufferedReader(new FileReader(ORDER_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.contains("- $")) {
                    String[] parts = line.split(" - "); // Split the line by " - " to extract the product name
                    String productName = parts[0];

                    // Increment the count for this product
                    productCount.put(productName, productCount.getOrDefault(productName, 0) + 1);
                }
            }

            // Display products sorted by likes
            displayResults();

        } catch (IOException e) {
            System.out.println("Error reading the orders file: " + e.getMessage());
        }
    }

    // Method to display products sorted from most liked to least liked
    private static void displayResults() {
        if (productCount.isEmpty()) {
            System.out.println("No products found.");
            return;
        }

        // Create a list from elements of the map and sort by values (counts)
        List<Map.Entry<String, Integer>> sortedProducts = new ArrayList<>(productCount.entrySet());
        sortedProducts.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())); // Sort in descending order

        System.out.println("Products ranked from most liked to least liked:");

        // Print all products in sorted order
        for (Map.Entry<String, Integer> entry : sortedProducts) {
            System.out.println(entry.getKey() + " (Ordered " + entry.getValue() + " times)");
        }
    }

    // Watcher method to monitor file changes
    public static void watchFileChanges() {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get("."); // Watch current directory
            path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

            System.out.println("Watching for changes to " + ORDER_FILE + "...");

            while (true) {
                WatchKey key = watchService.take(); // Wait for a file change event

                for (WatchEvent<?> event : key.pollEvents()) {
                    if (event.context().toString().equals(ORDER_FILE)) {
                        System.out.println("Detected changes in " + ORDER_FILE + ". Reanalyzing...");
                        analyzeOrderFile(); // Reanalyze the file when it changes
                    }
                }

                key.reset(); // Reset the key to continue watching
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Error watching the file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        analyzeOrderFile(); // Initial analysis
        watchFileChanges(); // Start watching for file changes
    }
}
