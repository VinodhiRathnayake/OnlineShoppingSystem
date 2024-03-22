public class Electronics extends Product{
    // Attributes specific to Electronics
    private String brand;
    private String warrantyPeriod;

    //Constructor of Electronics
    public Electronics(String productID, String productName, double price, String brand, String warrantyPeriod,int noOfAvailableItems) {
        super(productID, productName, price,noOfAvailableItems);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    //Getter method for retrieving the brand of the Electronic
    public String getBrand() {
        return brand;
    }

    // Setter method for setting the brand of the Electronic
    public void setBrand(String brand) {
        this.brand = brand;
    }

    //Getter method for retrieving the warranty period of the Electronic
    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    // Setter method for setting the warranty period of the Electronic
    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    //Method to provide a string representation of the Electronic object
    @Override
    public String toString() {
        return "Electronics{" +
                "brand='" + brand + '\'' +
                ", warrantyPeriod=" + warrantyPeriod +
                '}';
    }

    // Method to get brand of the Electronic
    public String getDetail1(){
        return getBrand();
    }

    // Method to get warranty period of the Electronic
    public String getDetail2(){
        return getWarrantyPeriod();
    }

    // Method to get product type
    public String getProduct(){
        return "Electronics";
    }
}
