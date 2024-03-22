import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Table model for displaying a shopping cart in a JTable
public class ShoppingCartTableModel extends AbstractTableModel {
    String columnNames[] = {"Product", "Quantity", "Price"};

    // List of available products
    private final ArrayList<Product> productList;
    // Map to store quantities of products in the shopping cart
    private final Map<String, Integer> productQuantities;
    // List representing the shopping cart
    private ArrayList<Product> shoppingCartList;
    // Reference to the JTable associated with the model
    private JTable table;


    // Constructor to initialize the shopping cart table model
    public ShoppingCartTableModel(JTable table, ArrayList<Product> productList) {

        this.table = table;
        this.productList = productList;
        this.productQuantities = new HashMap<>();
        this.shoppingCartList = new ArrayList<>();


    }

    // Get the number of rows in the table
    public int getRowCount() {
        return productList.size();
    }

    // Get the number of columns in the table
    public int getColumnCount() {
        return columnNames.length;
    }

    // Get the name of a specific column
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    // Get the value at a specific cell in the table
    public Object getValueAt(int rowIndex, int columnIndex) {

        Object temp = null;
        if (columnIndex == 0) {
            temp = getProductDetailsHTML(rowIndex);
        } else if (columnIndex == 1) {
            String productID = productList.get(rowIndex).getProductID();
            temp = productQuantities.getOrDefault(productID, 1);
        } else if (columnIndex == 2) {
            String productID = productList.get(rowIndex).getProductID();
            int quantity = productQuantities.getOrDefault(productID, 1);
            double price = productList.get(rowIndex).getPrice();
            temp = quantity * price + " £";

        }

        return temp;

    }

    // Method to generate HTML details for a product
    private String getProductDetailsHTML(int rowIndex) {
        Product product = productList.get(rowIndex);
        return "<html>" +
                product.getProductID() + "<br>" +
                product.getProductName() + "<br>" +
                product.getDetail1() + "<br>" +
                product.getDetail2() +
                "</html>";
    }

    // Specify whether a cell is editable
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // Allow editing only for the quantity column
        return columnIndex == 1;
    }


    // Set the value at a specific cell in the table
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if (rowIndex >= 0 && rowIndex < productList.size()) {
            String productID = productList.get(rowIndex).getProductID();
            if (columnIndex == 1) {

                int newQuantity = Integer.parseInt(value.toString());

                // Perform validation to check if the new quantity is within bounds
                int availableItems = productList.get(rowIndex).getNoOfAvailableItems();
                if (newQuantity > 0 && newQuantity <= availableItems) {
                    productQuantities.put(productID, newQuantity);
                    fireTableCellUpdated(rowIndex, 2);


                } else {
                    // Display message indicating invalid quantity
                    JOptionPane.showMessageDialog(null, "Invalid quantity. Please enter a quantity between 1 and " + ".", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }


    // Set center alignment for all columns in the table
    public void setCenterAlignmentForAllColumns(JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Apply center alignment for each column
        for (int i = 0; i < getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setCellRenderer(centerRenderer);
        }
    }







}
