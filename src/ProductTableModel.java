import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;

// Table model for displaying a list of products in a JTable
public class ProductTableModel extends AbstractTableModel {

    // Column names for the table
    String columnNames[] = {"Product ID", "Name", "Category", "Price(£)", "Info"};

    // List of products displayed in the table
    private ArrayList<Product> productList;

    // Constructor to initialize the table model
    public ProductTableModel(ArrayList<Product> productList) {
        this.productList = productList;
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
            temp = productList.get(rowIndex).getProductID();
        } else if (columnIndex == 1) {
            temp = productList.get(rowIndex).getProductName();
        } else if (columnIndex == 2) {
            temp = productList.get(rowIndex).getProduct();
        } else if (columnIndex == 3) {
            temp = productList.get(rowIndex).getPrice();
        } else if (columnIndex == 4) {
            temp = productList.get(rowIndex).getDetail1() + ", " + productList.get(rowIndex).getDetail2();
        }

        return temp;
    }
    // Set new data for the table
    public void setData(ArrayList<Product> newData) {
        productList = newData;
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
        // Set preferred width for the "Info" column
        table.getColumnModel().getColumn(4).setPreferredWidth(250);
    }

    // Get the count of available items for a given row
    public int getAvailableItemsCount(int rowIndex) {
        return productList.get(rowIndex).getNoOfAvailableItems();
    }

    // Set row color based on the availability of items
    public void setRowColorBasedOnAvailability(JTable table) {
        Color lightRed = new Color(252, 81, 84);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Check if the number of available items is less than or equal to 3
                try {
                if (getAvailableItemsCount(row) <= 3) {
                    component.setBackground(lightRed);
                } else {
                    // Reset background color to default
                    component.setBackground(table.getBackground());
                }
                }catch (Exception e) {
                    // Handle or log the error
                    e.printStackTrace();
                }

                return component;
            }
        };
        // Apply custom renderer for each column
        for (int i = 0; i < getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setCellRenderer(renderer);
        }
    }



}
