import java.io.*;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager{

    // Store the product list and total number of products
    private ArrayList<Product> productList;
    private int totalProducts = 0;

    // Instance of UserShoppingGUI for GUI updates
    private UserShoppingGUI userShoppingGUI;
    Scanner scanner = new Scanner(System.in);

    // Constructor for manager class
    public WestminsterShoppingManager() {
        this.productList = new ArrayList<Product>(50);
        this.totalProducts = 0;
        this.userShoppingGUI = userShoppingGUI;

        // Read existing product data from file
        readFile();
    }


    // Getter for the product list
    public ArrayList<Product> getProductList() {
        return productList;
    }

    // Method to run the main menu for the manager


    public boolean runMenu(){

        boolean exit=false;

        try{
            System.out.println("Manager's Menu");
        System.out.println("Press 1 - Add a new product");
        System.out.println("Press 2 - Delete a product");
        System.out.println("Press 3 - Print the list of the products");
        System.out.println("Press 4 - Save in a file");
        System.out.println("Press 5 - To exit");
        System.out.print("Enter required number: ");


        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                addProduct();
                break;

            case 2:
                delProduct();
                break;

            case 3:
                printProductList();
                break;

            case 4:
                saveFile();
                break;

            case 5:
                System.exit(0);
                exit = true;
                break;
            default:
                System.out.println("Invalid input. Please enter a valid number.");
        }
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.nextLine();

        }

        return exit;
    }

    // Method to add a new product
    public void addProduct(){

        System.out.println("Add a new product");
        System.out.println("Press 1 - Add a product to electronics");
        System.out.println("Press 2 - Add a product to clothing");
        System.out.print("Enter required product: ");
        int selection = scanner.nextInt();


        try {
            switch (selection) {
                case 1:
                    System.out.println("Add new electronics product");

                    System.out.print("Enter Product ID: ");
                    String productIDE = scanner.next();
                    scanner.nextLine();

                    //check whether the product already exists
                    boolean productExists = false;
                    for (Product product : productList) {
                        if (product.getProductID().equals(productIDE)) {
                            System.out.print("Product already exists. Do you want to increase the number of available items? (Y/N): ");
                            String increaseItemsChoice = scanner.next().trim();
                            if (increaseItemsChoice.equalsIgnoreCase("Y")) {
                                System.out.print("Enter the amount to increase: ");
                                int increaseAmount = scanner.nextInt();
                                increaseAmount=product.getNoOfAvailableItems() + increaseAmount;
                                product.setNoOfAvailableItems(increaseAmount);
                                System.out.println("Number of available items increased successfully.");
                                System.out.println("Total no of items available in product ID " + product.getProductID() + " is: " + product.getNoOfAvailableItems());
                            }
                            productExists = true;
                        }
                    }

                    if(!productExists){
                    System.out.print("Enter product Name: ");
                    String productNameE = scanner.nextLine();
                        scanner.nextLine();

                        System.out.print("Enter product price: ");
                    double priceE = scanner.nextDouble();
                        if (priceE <= 0) {
                            System.out.println("Invalid input. Price must be greater than zero.");
                            return; // Return to the menu
                        }
                    scanner.nextLine();


                        System.out.print("Enter product brand: ");
                    String brandE = scanner.nextLine();
                        scanner.nextLine();

                        System.out.print("Enter warranty period: ");
                    String warrantyPeriodE = scanner.nextLine();
                        scanner.nextLine();


                        System.out.print("Enter number of items to be added: ");
                    int noOfAvailableItemsE = scanner.nextInt();
                        if (noOfAvailableItemsE <= 0) {
                            System.out.println("Invalid input. Number of available items must be greater than zero.");
                            return; // Return to the menu
                        }


                    Product productE = new Electronics(productIDE, productNameE, priceE, brandE, warrantyPeriodE,noOfAvailableItemsE);
                    addProductList(productE);
                        System.out.println("Product added to list");
                        int remainingSpace = 50 -totalProducts;
                        System.out.println("No of products that can be added is: " + remainingSpace);
                        }


                    break;

                case 2:
                    System.out.println("Add new clothing product");
                    System.out.print("Enter Product ID: ");
                    String productIDC = scanner.next();
                    scanner.nextLine();

                    //check whether the product already exists
                    boolean productCheck = false;
                    for (Product product : productList) {
                        if (product.getProductID().equals(productIDC)) {
                            System.out.print("Product already exists. Do you want to increase the number of available items? (Y/N): ");
                            String increaseItemsChoice = scanner.next().trim();
                            if (increaseItemsChoice.equalsIgnoreCase("Y")) {
                                System.out.print("Enter the amount to increase: ");
                                int increaseAmount = scanner.nextInt();
                                increaseAmount = product.getNoOfAvailableItems() + increaseAmount;
                                product.setNoOfAvailableItems(increaseAmount);
                                System.out.println("Number of available items increased successfully.");
                                System.out.println("Total no of items available in product ID " + product.getProductID() + " is: " + product.getNoOfAvailableItems());
                            }
                            productCheck = true;
                            break;
                        }
                        } if(!productCheck) {
                            System.out.print("Enter product Name: ");
                            String productNameC = scanner.nextLine();
                    scanner.nextLine();
                    System.out.print("Enter product price: ");
                            double priceC = scanner.nextDouble();
                    scanner.nextLine();
                    if (priceC <= 0) {
                        System.out.println("Invalid input. Price must be greater than zero.");
                        return; // Return to the menu
                    }

                    System.out.print("Enter product size: ");
                            String size = scanner.nextLine();
                    scanner.nextLine();
                    System.out.print("Enter product colour: ");
                            String color = scanner.nextLine();
                    scanner.nextLine();
                    System.out.print("Enter number of items to be added: ");
                            int noOfAvailableItemsC = scanner.nextInt();
                    if (noOfAvailableItemsC <= 0) {
                        System.out.println("Invalid input. Number of available items must be greater than zero.");
                        return; // Return to the menu
                    }

                    Product productC = new Clothing(productIDC, productNameC, priceC, size, color, noOfAvailableItemsC);
                            addProductList(productC);
                    System.out.println("Product added to list");
                    int remainingSpace = 50 -totalProducts;
                    System.out.println("No of products that can be added is: " + remainingSpace);
                        }

                    break;
                default:
                    System.out.println("Invalid input. Please enter a valid number.");
                    break;


            }
        }catch (java.util.InputMismatchException e){
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }



    // Method to delete a product
    public void delProduct(){
        System.out.print("Enter the product ID of the product to be deleted: ");
        String productCode = scanner.next();

        boolean found=false;
        for(int i =0;i < productList.size();i++){
            Product product = productList.get(i);
            if(productCode.equals(product.getProductID())){
                found=true;
                productList.remove(i);
                --totalProducts;
                System.out.println(product.getProduct());
                System.out.println("Product ID: " + productCode);
                System.out.println("Successfully Deleted ");
                System.out.println("No of total products available: " + totalProducts);

                break;
            }
        }
        if(!found){
            System.out.println("Product with ID " + productCode + " not found in the list.");
        }



    }

    // Method to print the product list
    public void printProductList() {

        if (productList.isEmpty()) {
            System.out.println("Product list is empty.");
        } else {
            //Bubble sort to sort alphabetically
            bubbleSort();
            System.out.println("List of Products:");
            for (Product product : productList) {

                String productID = product.getProductID();
                String productName = product.getProductName();
                double price = product.getPrice();

                String productType = product.getProduct();
                if(productType.equals("Electronics")){
                    System.out.println("Product Type: " + product.getProduct() + ", Product ID: " + productID + ", Product Name: " + productName + ", Product price: " + price + ", " + "Product Brand: " + product.getDetail1() + ", Product Warranty Period: " + product.getDetail2() + ", Available items: " + product.getNoOfAvailableItems());
                }
                else if(productType.equals("Clothing")){
                    System.out.println("Product Type: " + product.getProduct() + ", Product ID: " + productID + ", Product Name: " + productName + ", Product price: " + price + ", Product size: " + product.getDetail1() + ", Product Colour: " + product.getDetail2() + ", Available items: " + product.getNoOfAvailableItems());
                }
                else{
                    System.out.println("Error in printing product list.");
                }

            }
            System.out.println("Available No of Product Types: " + totalProducts);


        }
    }


    // Method to save the product list to a file
    public void saveFile() {
        File file = new File("ProductsList.txt");

        try {
            // Check if the file exists
            boolean fileExists = file.exists();

            // If the file exists, delete it
            if (fileExists) {
                boolean deleted = file.delete();
                if (deleted) {
                    System.out.println("Previous file deleted: " + file.getName());
                } else {
                    System.out.println("Failed to delete previous file: " + file.getName());
                }
            }

            // Create a new file
            boolean fileCreated = file.createNewFile();

            // Write data to file
            if (fileCreated) {
                System.out.println("File created: " + file.getName());

                FileWriter writer = new FileWriter(file);
                BufferedWriter bWriter = new BufferedWriter(writer);

                for (Product product : productList) {
                    if (product.getNoOfAvailableItems() > 0) {
                        String productType = product.getProduct();
                        String productInfo;
                        if (productType.equals("Electronics")) {
                            productInfo = "Product Type :" + product.getProduct() + "\nProduct ID :" + product.getProductID() + "\nProduct Name :" + product.getProductName() + "\nProduct price :" + product.getPrice() + "\nProduct Brand :" + product.getDetail1() + "\nProduct Warranty Period :" + product.getDetail2() + "\nNo of Available Items :" + product.getNoOfAvailableItems() + "\n\n";
                        } else {
                            productInfo = "Product Type :" + product.getProduct() + "\nProduct ID :" + product.getProductID() + "\nProduct Name :" + product.getProductName() + "\nProduct price :" + product.getPrice() + "\nProduct size :" + product.getDetail1() + "\nProduct Colour :" + product.getDetail2() + "\nNo of Available Items :" + product.getNoOfAvailableItems() + "\n\n";
                        }
                        writer.write(productInfo);
                    }
                }

                writer.write("Available No of Products: " + totalProducts);

                System.out.println("File saved successfully");

                bWriter.close();
                writer.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while saving the product list to the file.");
            e.printStackTrace();
        }
    }


    // Method to read the product list from a file
    public void readFile() {
        File file = new File("ProductsList.txt");

        try (FileReader reader = new FileReader(file);
             BufferedReader bReader = new BufferedReader(reader)) {

            boolean isNewDataAdded = false;

            String line;

            while ((line = bReader.readLine()) != null) {
                // Ignore blank lines
                if (line.isBlank() || line.startsWith("Available No of Products")) continue;

                // Check for "Product Type" heading
                if (line.startsWith("Product Type")) {
                    String productType = line.split(":")[1].trim();
                    Product productE; // Hold product information

                    // Read specific product data based on type
                    if (productType.equals("Electronics")) {
                        productE = readElectronicsProduct(bReader);
                    } else if (productType.equals("Clothing")) {
                        productE = readClothingProduct(bReader);

                    } else {
                        // handle other product types if needed
                        System.out.println("Unrecognized product type encountered: " + productType);
                        continue;
                    }

                    // Check if the product ID already exists in the productList
                    if (!containsProductWithId(productE.getProductID())) {
                        productList.add(productE);
                        ++totalProducts;
                        if (userShoppingGUI != null) {
                            userShoppingGUI.updateTableModel();
                        }
                        isNewDataAdded = true;
                    }
                } else {
                    // Handle unexpected data format
                    System.out.println("Unexpected data format found in file: " + line);
                }
            }
            if (isNewDataAdded && userShoppingGUI != null) {
                userShoppingGUI.updateTableModel();
            }


        }
        catch (FileNotFoundException e) {
            System.out.println("No existing product list file.");
        }catch (IOException | NumberFormatException e) {
            System.out.println("An error occurred while reading the product list from the file.");
            e.printStackTrace();
        }
    }

    // Method to check if a product with a given ID exists in the productList
    private boolean containsProductWithId(String productId) {
        for (Product product : productList) {
            if (product.getProductID().equals(productId)) {
                return true;
            }
        }
        return false;
    }




    // Method to read details of an electronics product from a file
    private Product readElectronicsProduct(BufferedReader bReader) throws IOException {

        String productID = bReader.readLine().split(":")[1].trim();
        String productName = bReader.readLine().split(":")[1].trim();
        double price = Double.parseDouble(bReader.readLine().split(":")[1].trim());
        String brand = bReader.readLine().split(":")[1].trim();
        String warrantyPeriod = bReader.readLine().split(":")[1].trim();
        int noOfAvailableItems = Integer.parseInt(bReader.readLine().split(":")[1].trim());

        Product product = new Electronics(productID, productName, price, brand, warrantyPeriod, noOfAvailableItems);
        addProductList(product);
        return product;
    }

    // Method to read details of a clothing product from a file
    private Product readClothingProduct(BufferedReader bReader) throws IOException {
        String productID = bReader.readLine().split(":")[1].trim();
        String productName = bReader.readLine().split(":")[1].trim();
        double price = Double.parseDouble(bReader.readLine().split(":")[1].trim());
        String brand = bReader.readLine().split(":")[1].trim();
        String warrantyPeriod = bReader.readLine().split(":")[1].trim();
        int noOfAvailableItems = Integer.parseInt(bReader.readLine().split(":")[1].trim());

        Product product = new Clothing(productID, productName, price, brand, warrantyPeriod, noOfAvailableItems);
        addProductList(product);
        return product;
    }






    // Method to add a product to the productList
    public void addProductList(Product product) {
        if ( totalProducts <= productList.size()) {
            productList.add(product);
//            System.out.println("Product added successfully.");
            ++totalProducts;
            if (userShoppingGUI != null) {
                userShoppingGUI.updateTableModel();
            }
        } else {
            System.out.println("No more space in the list.");
        }
    }

    // Method to perform bubble sort on the productList based on product IDs
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



}
