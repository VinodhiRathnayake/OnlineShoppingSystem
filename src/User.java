import java.util.List;

public class User {

    // Attributes specific to user
    private String username;
    private char[] password;
    private List<Product> purchasedProducts;
    private double totalCost;

    //Constructor of User
    public User(String username, char[] password, List<Product> purchasedProducts, double totalCost) {
        this.username = username;
        this.password = password;
        this.purchasedProducts = purchasedProducts;
        this.totalCost = totalCost;
    }

    // Getter and setter methods for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter and setter methods for password
    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    // Getter and setter methods for purchased products
    public List<Product> getPurchasedProducts() {
        return purchasedProducts;
    }

    public void setPurchasedProducts(List<Product> purchasedProducts) {
        this.purchasedProducts = purchasedProducts;
    }

    // Getter and setter methods for total cost
    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    //Method to provide a string representation of the user
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", purchasedProducts=" + purchasedProducts +
                ", totalCost=" + totalCost +
                '}';
    }
}

