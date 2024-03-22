public class Clothing extends Product{

    // Attributes specific to Clothing
    private String size;
    private String color;

    //Constructor of Clothing
    public Clothing(String productID, String productName, double price, String size, String color,int noOfAvailableItems) {
        super(productID, productName, price,noOfAvailableItems);
        this.size = size;
        this.color = color;
    }
    //Getter method for retrieving the size of the Clothing
    public String getSize() {
        return size;
    }

    // Setter method for setting the size of the Clothing
    public void setSize(String size) {
        this.size = size;
    }

    // Getter method for retrieving the color of the Clothing
    public String getColor() {
        return color;
    }

    // Setter method for setting the color of the Clothing
    public void setColor(String color) {
        this.color = color;
    }

    //Method to provide a string representation of the Clothing object
    @Override
    public String toString() {
        return "Clothing{" +
                "size=" + size +
                ", color='" + color + '\'' +
                '}';
    }

    // Method to get size of the Clothing
    public String getDetail1(){
        return getSize();
    }

    // Method to get color of the Clothing
    public String getDetail2(){
        return getColor();
    }

    // Method to get product type
    public String getProduct(){
        return "Clothing";
    }
}
