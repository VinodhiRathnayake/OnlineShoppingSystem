import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class ShoppingCart extends JFrame {

    // Static variables to maintain shopping cart state
    private static Map<String, Integer> productQuantities;
    private static ArrayList<Product> shoppingCartList;
    private static JTable cartTable;

//    private ArrayList<Product> productList;

    // UI components
    private static JTextArea calculation;
    private static JTextArea welcomeText;
    private static ShoppingCartTableModel cartTableModel;

    // User-related variables
    private boolean userLoggedIn = false;

    private static User currentUser;

    // UI buttons
    private JButton purchase;
    private JButton cancel;
    private static double finalTotal = 0.0;


    // User list for tracking purchases
    private static ArrayList<User> userList = new ArrayList<>();
    private WestminsterShoppingManager shoppingManager;
    private static ArrayList<Product> productList = new ArrayList<>();


    // Constructor
    public ShoppingCart(User currentUser, ArrayList<Product> shoppingCartList, Map<String, Integer> productQuantities,  WestminsterShoppingManager shoppingManager) {

        this.shoppingCartList = shoppingCartList;
        this.productQuantities = productQuantities;
        this.shoppingManager = shoppingManager;


        cartTableModel = new ShoppingCartTableModel(cartTable, shoppingCartList);
        this.currentUser = currentUser;
        this.userList = userList;

        cartTable = new JTable(cartTableModel);


        // Setting up the frame layout

        getContentPane().setLayout(new GridLayout(2, 1));
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Panel for welcome text
        JPanel panel = new JPanel();

        welcomeText = new JTextArea();
        panel.add(welcomeText);
        // Create a bold font based on the current font
        Font boldFont = welcomeText.getFont().deriveFont(Font.BOLD);
// Set the bold font to the component
        welcomeText.setFont(boldFont);


        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        getContentPane().add(panel);

        // Panel for displaying the shopping cart
        JPanel firstPanel = new JPanel();


        JScrollPane scrollPane = new JScrollPane(cartTable);
        scrollPane.setPreferredSize(new Dimension(600, 200));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        firstPanel.add(scrollPane);

        // Mouse listener to remove items from the cart on click
        cartTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = cartTable.getSelectedRow();
                if (selectedRow >= 0) {
                    removeItem(selectedRow);
                }
            }
        });

        cartTable.setModel(cartTableModel);

        getContentPane().add(firstPanel);
        updateCartTableModel();
        cartTable.repaint();


        // Panel for displaying total cost
        JPanel secondPanel = new JPanel();

        calculation = new JTextArea("Calculate your total cost....");
        secondPanel.add(calculation);
        updateTotalCost();
        secondPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        getContentPane().add(secondPanel);

        // Panel for buttons
        JPanel thirdPanel = new JPanel();

        purchase = new JButton("Confirm Purchase");
        thirdPanel.add(purchase);

        // Action listener for the Confirm Purchase button
        purchase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmPurchase();
            }
        });


        cancel = new JButton("Cancel");
        thirdPanel.add(cancel);

        // Action listener for the Cancel button

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear all details in the shopping cart
                clearShoppingCart();
            }
        });

        getContentPane().add(thirdPanel);


    }

    // Method to clear the shopping cart

    private void clearShoppingCart() {

        // Reset the product quantities to 1
        for (Map.Entry<String, Integer> entry : productQuantities.entrySet()) {
            String productID = entry.getKey();
            int rowIndex = findRowIndexByProductID(productID);
            if (rowIndex != -1) {
                cartTableModel.setValueAt(1, rowIndex, 1);
            }
        }


        // Remove all items from the shopping cart
        shoppingCartList.clear();



        // Clear the product quantities
        productQuantities.clear();

        // Clear the table model

        cartTableModel.fireTableDataChanged();

        // Update the total cost
        updateTotalCost();

        updateCartTableModel();

    }

    // Method to confirm the purchase
    private void confirmPurchase() {
        if (finalTotal == 0.0) {
            JOptionPane.showMessageDialog(null, "No items in the shopping cart.", "Empty Shopping Cart", JOptionPane.WARNING_MESSAGE);
            return;
        }else {
            // Create a list to store products to be removed
            ArrayList<Product> productsToRemove = new ArrayList<>();

            for (Product product : shoppingCartList) {
                int currentQuantity = productQuantities.getOrDefault(product.getProductID(), 1);
                int originalQuantity = product.getNoOfAvailableItems();

                // Update the original quantity by reducing the quantity in the shopping cart
                int newQuantity = originalQuantity - currentQuantity;
                product.setNoOfAvailableItems(newQuantity);

                int indexInMainList = findProductIndexInMainList(product.getProductID());
                if (indexInMainList != -1) {
                    productList.get(indexInMainList).setNoOfAvailableItems(newQuantity);
                    // Remove the product from productList if the new quantity is 0
                    if (newQuantity == 0) {
                        productsToRemove.add(productList.get(indexInMainList));
                    }

                }

            }

            // Remove products with quantity 0 from the main list
            productList.removeAll(productsToRemove);

            currentUser.setPurchasedProducts(shoppingCartList);
            currentUser.setTotalCost(finalTotal);

            if (shoppingManager != null) {
                shoppingManager.saveFile();
            }
            else{
                System.out.println("Error");
            }
        }

//        userShoppingGUI.updateTableModel();


            // Save user information to the file
            saveCredentials();


            System.exit(0);

    }

    private int findProductIndexInMainList(String productID) {
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getProductID().equals(productID)) {
                return i;
            }
        }
        return -1; // Product not found in the main list
    }

    // Method to update the total cost display
    public static void updateTotalCost() {
        double totalECost = 0.0;
        double totalCCost = 0.0;
        double totalCost = 0.0;
        double elecDis = 0.0;
        double clothDis = 0.0;
        double totalDis = 0.0;
        double firstPurchDis = 0.0;

        int electronicsQuantity = 0;
        int clothingQuantity = 0;

        // Iterate through the products in the shopping cart
        for (Product product : shoppingCartList) {
            String productID = product.getProductID();
            int quantity = productQuantities.getOrDefault(productID, 1);

            if (product.getProduct().equals("Electronics")) {
                double priceE = product.getPrice();
                totalECost += quantity * priceE;
                electronicsQuantity += quantity;

            } else if (product.getProduct().equals("Clothing")) {
                double priceC = product.getPrice();
                totalCCost += quantity * priceC;
                clothingQuantity += quantity;

            }


        }
        totalCost += (totalECost + totalCCost);


        if (validateLogin(currentUser.getUsername(), currentUser.getPassword())) {
            welcomeText.setText("          Welcome back, " + currentUser.getUsername() + "!\nContinue Shopping in Westminster Shopping Centre");
        } else {
            firstPurchDis = totalCost * 0.1;
            welcomeText.setText("      Welcome, " + currentUser.getUsername() + " , New to Westminster Shopping Centre? \nEnjoy a 10% discount on your first purchase! Start shopping now!");
        }

        if (electronicsQuantity >= 3) {
            elecDis = totalECost * 0.2;
        }
        if (clothingQuantity >= 3) {
            clothDis = totalCCost * 0.2;
        }

        totalDis += (elecDis + clothDis);

        finalTotal = totalCost - (firstPurchDis + totalDis);

        // Update the JTextArea with the calculated total cost
        calculation.setText("Total Cost: " + totalCost + " £" +
                "\n\nFirst Purchase Discount(10%): -" + firstPurchDis + " £" +
                "\n\nThree items in same category Discount(20%): -" + totalDis + " £" +
                "\n\n\nFinal Total: " + finalTotal + " £");


    }

    // Method to save user credentials to a file
    public void saveCredentials() {
        if (currentUser != null) {
            File file = new File("Credentials.txt");
            try (FileWriter writer = new FileWriter(file, true);
                 BufferedWriter bWriter = new BufferedWriter(writer)) {
                String userInfo = "Username :" + currentUser.getUsername() +
                        "\nPassword :" + Arrays.toString(currentUser.getPassword()) +
                        "\nPurchased Products :" + Arrays.toString(currentUser.getPurchasedProducts().toArray()) +
                        "\nTotal Cost :" + currentUser.getTotalCost() + "\n";
                bWriter.write(userInfo);

            } catch (IOException e) {
                System.out.println("An error occurred while saving the credentials to the file.");
                e.printStackTrace();
            }
        } else {
            System.out.println("No user logged in. Cannot save credentials.");
        }
    }

    //Method to read credentials from file
    public static void getCredentials() {
        File file = new File("Credentials.txt");
        // Read user credentials from the file

        try (FileReader reader = new FileReader(file);
             BufferedReader bReader = new BufferedReader(reader)) {

            String line;

            while ((line = bReader.readLine()) != null) {
                // Ignore blank lines
                if (line.isBlank()) continue;

                String[] fields = line.split(":");
                if (fields.length == 2) {
                    String key = fields[0].trim();
                    String value = fields[1].trim();

                    switch (key) {
                        case "Username":
                            String usernameOld = value;
                            char[] passwordOld = bReader.readLine().split(":")[1].replaceAll("[\\[\\] ,]", "").toCharArray();
                            ArrayList<Product> purchasedProducts = readProducts(bReader.readLine().split(":")[1].trim());
                            double totalCost = Double.parseDouble(bReader.readLine().split(":")[1].trim());

                            userList.add(new User(usernameOld, passwordOld, purchasedProducts, totalCost));
                            break;
                        default:
                            throw new IOException("Invalid key in credentials file: " + key);
                    }
                } else {
                    throw new IOException("Invalid format in credentials file: " + line);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("");
        } catch (IOException | NumberFormatException e) {
            System.out.println("An error occurred while reading the credentials from the file.");
            e.printStackTrace();
        }
    }

//Method to read products
    private static ArrayList<Product> readProducts(String productsLine) {
        ArrayList<Product> products = new ArrayList<>();
        try {

            String productsContent = productsLine.substring(1, productsLine.length() - 1);

            // Split the content into individual product strings
            String[] productStrings = productsContent.split(",");

            for (String productStr : productStrings) {

                Product product = parseProduct();
                products.add(product);
            }
        } catch (Exception e) {
            // Handle exceptions that may occur during product reading
            System.out.println("An error occurred while reading purchased products: " + e.getMessage());
            e.printStackTrace();
        }

        return products;
    }


    // Placeholder for parsing individual products
    private static Product parseProduct() {

        return null;
        }

    //Method to validate login
    public static boolean validateLogin(String enteredUsername, char[] enteredPassword) {
        // Validate user login by checking credentials
        getCredentials();
        // Check if the user list is not null
        if (userList != null) {
            for (User user : userList) {
                if (user.getUsername().equals(enteredUsername) && Arrays.equals(user.getPassword(), enteredPassword)) {

                    currentUser=user;

                    return true; // Username and password match
                }
            }
        }

        return false; // No match found
    }





    // Method to update the shopping cart table model
    public static void updateCartTableModel() {
        if (cartTable != null) {
            cartTable.setModel(cartTableModel);
            cartTableModel.setCenterAlignmentForAllColumns(cartTable);
            JTableHeader header = cartTable.getTableHeader();
            header.setFont(new Font(header.getFont().getName(), Font.BOLD, header.getFont().getSize()));

            cartTable.setRowHeight(85);

            cartTableModel.fireTableDataChanged();
            cartTable.repaint(); // Repaint the existing frame to reflect changes
        } else {
            System.out.println("Error: cartTable is null");
        }
    }

//Method to add product to shopping cart
    public int addToCart(Product product) {

        String productID = product.getProductID();
        int rowIndex = findRowIndexByProductID(productID);

        // Check if the product is already in the cart
        if (productQuantities.containsKey(productID)) {
            int currentQuantity = productQuantities.get(productID);


            // Check if the new quantity exceeds the available items
            if (currentQuantity < product.getNoOfAvailableItems()) {
                ++currentQuantity;
                // Increment the quantity
                productQuantities.put(productID, currentQuantity );
                cartTableModel.setValueAt(currentQuantity , rowIndex, 1);

                updateTotalCost();
                updateCartTableModel();

                return currentQuantity;


            } else {
                // Optionally, show a message indicating that the product is out of stock
                JOptionPane.showMessageDialog(null, "This product is out of stock.", "Out of Stock", JOptionPane.WARNING_MESSAGE);
                updateTotalCost();
                updateCartTableModel();
                return -1;

            }
        } else {
            // Add the product to the cart with an initial quantity of 1
            productQuantities.put(productID, 1);
            shoppingCartList.add(product);
            updateTotalCost();
            updateCartTableModel();

            // Call setValueAt to update the table model

            if (rowIndex != -1) {
                cartTableModel.setValueAt(1, rowIndex, 1);
                updateTotalCost();
                updateCartTableModel();
            }
        }
        updateTotalCost();
        cartTableModel.fireTableDataChanged();
        updateCartTableModel();

        return 1;

    }


//Method to find row index by product ID
    private int findRowIndexByProductID(String productID) {
        for (int i = 0; i < shoppingCartList.size(); i++) {
            if (shoppingCartList.get(i).getProductID().equals(productID)) {
                return i;
            }
        }
        return -1; // Product not found
    }
    public ArrayList<Product> getShoppingCartList() {
        return shoppingCartList;

    }

    //Method to get cart table model
    public ShoppingCartTableModel getCartTableModel() {
        if (cartTableModel == null) {
            // Initialize the cartTableModel if it is null
            cartTableModel = new ShoppingCartTableModel(cartTable, shoppingCartList);
        }
        return cartTableModel;
    }

    public Map<String, Integer> getProductQuantities() {
        return productQuantities;
    }


//Method to remove an item from the shopping cart
    public void removeItem(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < shoppingCartList.size()) {
            Product selectedProduct = shoppingCartList.get(rowIndex);
            String message = "Do you want to remove '" + selectedProduct.getProductName() + "' from the shopping cart?";
            int option = JOptionPane.showConfirmDialog(null, message, "Remove from Cart", JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                int currentQuantity = productQuantities.get(selectedProduct.getProductID());

                if (currentQuantity > 1) {

                    // If quantity is more than one, decrement the quantity
                    productQuantities.put(selectedProduct.getProductID(), currentQuantity - 1);
                    cartTableModel.setValueAt(currentQuantity - 1, rowIndex, 1);
                    currentQuantity--;

                    updateCartTableModel();
                } else {
                    // If quantity is one, remove the product from the cart
                    productQuantities.remove(selectedProduct.getProductID());
                    shoppingCartList.remove(rowIndex);
                    cartTableModel.fireTableRowsDeleted(rowIndex, rowIndex);
                    updateCartTableModel();
                }
                updateTotalCost();
                updateCartTableModel();
            }
        }
    }



}
