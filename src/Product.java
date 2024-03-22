// Abstract class for product
public abstract class Product {
    // Common attributes for all products
    private String productID;
    private String productName;
    private int noOfAvailableItems=0;
    private double price;


    // Constructor for initializing common attributes of a product
    public Product(String productID, String productName, double price,int noOfAvailableItems) {
        this.noOfAvailableItems = noOfAvailableItems;
        this.productID = productID;
        this.productName = productName;
        this.price = price;
    }

    // Getter and setter methods for product ID
    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }


    // Getter and setter methods for product name
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    // Getter and setter methods for number of available items
    public int getNoOfAvailableItems() {
        return noOfAvailableItems;
    }

    public void setNoOfAvailableItems(int noOfAvailableItems) {
        this.noOfAvailableItems = noOfAvailableItems;
    }

    // Getter and setter methods for price
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    //Method to provide a string representation of the product
    @Override
    public String toString() {
        return "Product{" +
                "productID=" + productID +
                ", productName='" + productName + '\'' +
                ", noOfAvailableItems=" + noOfAvailableItems +
                ", price=" + price +
                '}';
    }


    // Abstract methods for specific details and type of product
    public abstract String getDetail1();
    public abstract String getDetail2();
    public abstract String getProduct();



}
