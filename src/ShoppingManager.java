// Interface representing the WestminsterShoppingManager
public interface ShoppingManager {
    public abstract boolean runMenu();

    public abstract void addProduct();

    public abstract void delProduct();

    public abstract void printProductList();

    public abstract void saveFile();
    public abstract void readFile();
    public abstract void addProductList(Product product);
    public abstract void bubbleSort();

}
