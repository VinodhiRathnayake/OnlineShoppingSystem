import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserShoppingGUI extends JFrame {
    private JTable cartTable;
    private JLabel productCategory;
    private JTextArea details;
    private JTextArea detailsHeader;
    private JComboBox<String> categoryList;
    private JButton sort;
    private JButton shoppingCart;
    private JButton addToCart;
    private JTable productTable;

    private ArrayList<Product> productList;
    private ShoppingCart shoppingCartGUI;
    private ArrayList<Product> shoppingCartList;
    private ShoppingCartTableModel productTableModel;
    private Map<String, Integer> productQuantities;
    private boolean userLoggedIn = false;
    private User currentUser;
    private WestminsterShoppingManager shoppingManager;



    // Constructor

    public UserShoppingGUI(ArrayList<Product> productList, WestminsterShoppingManager shoppingManager) {

        this.productList = productList;


        this.shoppingCartList = new ArrayList<>();
        this.productTableModel = new ShoppingCartTableModel(cartTable, shoppingCartList);
        this.productQuantities = productQuantities;
        currentUser = new User("username", "password".toCharArray(), new ArrayList<>(), 0.0);
        this.shoppingManager = shoppingManager;
        this.shoppingCartGUI = new ShoppingCart(currentUser, new ArrayList<>(), new HashMap<>(), this.shoppingManager);



        // Set up the frame layout
        getContentPane().setLayout(new GridLayout(5, 1));
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Panel for Shopping Cart button
        JPanel panel = new JPanel();
        shoppingCart = new JButton("Shopping Cart");
        panel.add(shoppingCart);
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        shoppingCart.addActionListener(new OpenCartWindow());
        getContentPane().add(panel);


        // Panel for selecting product category
        JPanel firstPanel = new JPanel();

        productCategory = new JLabel("Select Product Category");
        firstPanel.add(productCategory);

        categoryList = new JComboBox(new String[]{"All", "Electronics", "Clothing"});
        categoryList.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        categoryList.addActionListener(new CategorySelectionListener());

        sort = new JButton("Sort List");

        sort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Sort the list in alphabetical order
                bubbleSort();

                // Update the displayed products based on the selected category
                String selectedCategory = (String) categoryList.getSelectedItem();
                updateDisplayedProducts(selectedCategory);
            }
        });

        firstPanel.add(categoryList);
        firstPanel.add(sort);




        firstPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        getContentPane().add(firstPanel);


        // Panel for displaying product table
        JPanel secondPanel = new JPanel();
        productTable = new JTable();


        ProductTableModel tableModel = new ProductTableModel(productList);

        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setPreferredSize(new Dimension(600, 200));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        secondPanel.add(scrollPane);


        getContentPane().add(secondPanel);
        updateTableModel();
        productTable.repaint();
        productTable.addMouseListener(new MouseHandler());

        // Separator
        JSeparator customSeparator = new JSeparator(JSeparator.HORIZONTAL);
        customSeparator.setPreferredSize(new Dimension(Integer.MAX_VALUE, 5));
        customSeparator.setBackground(Color.BLACK);
        getContentPane().add(customSeparator);

        // Panel for displaying selected product details

        JPanel headingPanel = new JPanel();
        detailsHeader = new JTextArea("Selected Product Details");
        headingPanel.add(detailsHeader);
        Font boldFont = detailsHeader.getFont().deriveFont(Font.BOLD);

// Set the bold font to the component
        detailsHeader.setFont(boldFont);


        headingPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        getContentPane().add(headingPanel);



        // Panel for displaying product details
        JPanel thirdPanel = new JPanel();

        details = new JTextArea("Click on a product to view more details...");


        thirdPanel.add(details);

        thirdPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        getContentPane().add(thirdPanel);

        // Panel for adding to shopping cart
        JPanel fourthPanel = new JPanel();
        addToCart = new JButton("Add to Shopping Cart");
        fourthPanel.add(addToCart);

        getContentPane().add(fourthPanel);

        // Action listener for the Add to Shopping Cart button
        addToCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if the user is logged in
                if (!userLoggedIn) {
                    // If not logged in, show the login dialog
                    boolean loginSuccessful = showLoginDialog();
                    if (!loginSuccessful) {
                        // If login fails, do not proceed
                        return;
                    }
                    // Set the flag to indicate that the user has logged in
                    userLoggedIn = true;

                }

                // Proceed with adding to the shopping cart
                addToShoppingCart();

            }
        });

        pack();


    }

    // Method to display the login dialog
    private boolean showLoginDialog() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {
                "Username:", usernameField,
                "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            // Validate the username and password (you need to implement this part)
            String username = usernameField.getText();
            char[] password = passwordField.getPassword();


            // Clear the password for security reasons
            passwordField.setText("");

            if (currentUser != null) {
                currentUser.setUsername(username);
                currentUser.setPassword(password);
            }


            return true;
        }
        return false; // User canceled the login
    }


    //Method to update table model
    public void updateTableModel() {
        if (productTable != null) {

            // Create a new product table model with the updated product list
            ProductTableModel tableModel = new ProductTableModel(productList);
            productTable.setModel(tableModel);

            // Customize the table header
            JTableHeader header = productTable.getTableHeader();
            header.setFont(new Font(header.getFont().getName(), Font.BOLD, header.getFont().getSize()));

            productTable.setRowHeight(35);
            tableModel.fireTableDataChanged();
            tableModel.setCenterAlignmentForAllColumns(productTable);
            tableModel.setRowColorBasedOnAvailability(productTable);
        } else {
            System.out.println("Error: productTable is null");
        }
    }

    //Method to add selected product to the shopping cart
    private void addToShoppingCart() {
        try {

            int selectedRow = productTable.getSelectedRow();
            if (selectedRow >= 0 && selectedRow < productList.size()) {
                Product selectedProduct = productList.get(selectedRow);
                int currentQuantity = shoppingCartGUI.addToCart(selectedProduct);
                if (currentQuantity != -1) {
                    // Update the product quantities in the shopping cart
                    shoppingCartGUI.getProductQuantities().put(selectedProduct.getProductID(), currentQuantity);

                    currentUser.setPurchasedProducts(shoppingCartGUI.getShoppingCartList());
                    // Recalculate the total cost and update the table model
                    ShoppingCart.updateTotalCost();
                    ShoppingCart.updateCartTableModel();

                    shoppingCartGUI.getCartTableModel().fireTableDataChanged();


                }
            }
        }catch (Exception e) {
            // Handle the exception, e.g., log it or display an error message
            e.printStackTrace();
        }



    }
    public void bubbleSort() {
        int n = productList.size();
        int bottom = n - 2;
        boolean exchanged = true;

        while (exchanged) {
            exchanged = false;

            for (int i = 0; i <= bottom; i++) {

                String name1 = productList.get(i).getProductID();
                String name2 = productList.get(i + 1).getProductID();

                if (name1.compareTo(name2) > 0) {
                    Product temp = productList.get(i);
                    productList.set(i, productList.get(i + 1));
                    productList.set(i + 1, temp);
                    exchanged = true;
                }
            }

            bottom--;
        }
    }



    private class MouseHandler extends MouseAdapter {

        // Handle mouse clicks on the product table
        public void mouseClicked(MouseEvent evt) {
            int row = productTable.getSelectedRow();
            if (row >= 0) {
                // Display detailed information about the selected product
                Product selectedProduct = productList.get(row);
                String category = selectedProduct.getProduct();
                if (category.equals("Electronics")) {
                    // Display electronics product details

                    String productDetails = "Product ID: " + selectedProduct.getProductID() + "\n\nCategory: " + selectedProduct.getProduct() +
                            "\n\nName: " + selectedProduct.getProductName() + "\n\nBrand: " + selectedProduct.getDetail1() + "\n\nWarranty Period: " + selectedProduct.getDetail2() +
                            "\n\nItems Available: " + selectedProduct.getNoOfAvailableItems();
                    details.setText(productDetails);
                } else if (category.equals("Clothing")) {
                    // Display clothing product details

                    String productDetails = "Product ID: " + selectedProduct.getProductID() + "\n\nCategory: " + selectedProduct.getProduct() +
                            "\n\nName: " + selectedProduct.getProductName() + "\n\nSize: " + selectedProduct.getDetail1() + "\n\nColour: " + selectedProduct.getDetail2() +
                            "\n\nItems Available: " + selectedProduct.getNoOfAvailableItems();
                    details.setText(productDetails);
                }

            }
        }

    }

    private class CategorySelectionListener implements ActionListener {
    // Method to handle category selection from the combo box
        public void actionPerformed(ActionEvent evt) {
            String selectedCategory = (String) categoryList.getSelectedItem();

            // Update displayed products based on the selected category
            updateDisplayedProducts(selectedCategory);



        }
    }

    private class OpenCartWindow implements ActionListener {
        // Method to handle the opening of the shopping cart window
        public void actionPerformed(ActionEvent evt) {

            // Display the shopping cart window
            displayShoppingCartWindow();

        }}

    // Method to display the shopping cart window
    private void displayShoppingCartWindow() {
        try {
            if (shoppingCartGUI != null) {
                // Update the data in the existing shopping cart instance
                shoppingCartGUI.updateCartTableModel();
                shoppingCartGUI.setSize(700, 500);
                shoppingCartGUI.setLocationRelativeTo(null);
                shoppingCartGUI.setVisible(true);
            } else {
                // Create a new shopping cart instance if it doesn't exist

                shoppingCartGUI = new ShoppingCart(currentUser, shoppingCartList, productQuantities, shoppingManager);
                shoppingCartGUI.setTitle("Shopping Cart");
                shoppingCartGUI.setSize(700, 500);
                shoppingCartGUI.setLocationRelativeTo(null);
                shoppingCartGUI.setVisible(true);
                shoppingCartGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                shoppingCartGUI.toFront();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }



    }



    // Method to update displayed products based on the selected category
    private void updateDisplayedProducts(String selectedCategory) {
        if ("All".equals(selectedCategory)) {

            updateTableModel();
        }
        else {
            // If a specific category is selected, filter and display products of that category
            ArrayList<Product> filteredList = new ArrayList<>();
            for (Product product : productList) {
                if (product.getProduct().equals(selectedCategory)) {
                    filteredList.add(product);
                }
            }

            // Update the product table model with the filtered list
            ProductTableModel tableModel = (ProductTableModel) productTable.getModel();
            tableModel.setData(filteredList);
            tableModel.fireTableDataChanged();
        }

    }

    public void setProductList(ArrayList<Product> updatedProductList) {
        this.productList = updatedProductList;
    }






}
