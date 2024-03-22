import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class WestminsterShoppingManagerTest {

    @Test
    void runMenu() {
        // Redirect System.in to provide custom input for testing
        ByteArrayInputStream inputStream = new ByteArrayInputStream("1\n".getBytes());
        System.setIn(inputStream);

        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();

        // Call runMenu and check if it returns the expected value
        boolean result = shoppingManager.runMenu();

        // Reset System.in
        System.setIn(System.in);

        // Assuming that you expect '1' to add a new product, the method should not exit
        assertFalse(result);
    }

    @Test
    void addProduct() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();

        // Simulate user input for adding electronics product
        String input = "1\n123\nProduct1\n100.0\nBrand1\nWarranty1\n5\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        // Run the addProduct method
        shoppingManager.addProduct();

        // Assert that the product is added to the list
        assertEquals(1, shoppingManager.getProductList().size());

        // Simulate user input for adding clothing product
        input = "2\n456\nProduct2\n50.0\nXL\nBlue\n3\n";
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        // Run the addProduct method
        shoppingManager.addProduct();

        // Assert that the product is added to the list
        assertEquals(2, shoppingManager.getProductList().size());
    }

    @Test
    void delProduct() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();

        // Add a product for testing
        String input = "1\n123\nProduct1\n100.0\nBrand1\nWarranty1\n5\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        shoppingManager.addProduct();

        // Simulate user input for deleting the added product
        input = "123\n";
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        // Run the delProduct method
        shoppingManager.delProduct();

        // Assert that the product is removed from the list
        assertEquals(0, shoppingManager.getProductList().size());

    }

    @Test
    void printProductList() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();

        // Add a product for testing
        String input = "1\n123\nProduct1\n100.0\nBrand1\nWarranty1\n5\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        shoppingManager.addProduct();

        // Redirect System.out to capture the output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Run the printProductList method
        shoppingManager.printProductList();

        // Reset System.out
        System.setOut(System.out);

        // Assert that the printed output matches the expected output
        String expectedOutput = "List of Products:\n" +
                "Product Type: Electronics, Product ID: 123, Product Name: Product1, Product price: 100.0, Product Brand: Brand1, Product Warranty Period: Warranty1, Available items: 5\n" +
                "Available No of Product Types: 1\n";
        assertEquals(expectedOutput, outputStream.toString().trim());

    }

    @Test
    void saveFile() throws IOException {

        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();

        // Add a couple of products for testing
        String input1 = "1\n123\nProduct1\n100.0\nBrand1\nWarranty1\n5\n";
        String input2 = "2\n456\nProduct2\n50.0\nXL\nBlue\n3\n";
        ByteArrayInputStream inputStream1 = new ByteArrayInputStream(input1.getBytes());
        ByteArrayInputStream inputStream2 = new ByteArrayInputStream(input2.getBytes());
        System.setIn(inputStream1);
        shoppingManager.addProduct();
        System.setIn(inputStream2);
        shoppingManager.addProduct();

        // Invoke the saveFile method
        shoppingManager.saveFile();

        // Read the content of the file
        List<String> fileContent = Files.readAllLines(Path.of("ProductsList.txt"));

        // Expected content based on the added products
        List<String> expectedContent = new ArrayList<>();
        expectedContent.add("Product Type :Electronics");
        expectedContent.add("Product ID :123");
        expectedContent.add("Product Name :Product1");
        expectedContent.add("Product price :100.0");
        expectedContent.add("Product Brand :Brand1");
        expectedContent.add("Product Warranty Period :Warranty1");
        expectedContent.add("No of Available Items :5");
        expectedContent.add("");
        expectedContent.add("Product Type :Clothing");
        expectedContent.add("Product ID :456");
        expectedContent.add("Product Name :Product2");
        expectedContent.add("Product price :50.0");
        expectedContent.add("Product size :XL");
        expectedContent.add("Product Colour :Blue");
        expectedContent.add("No of Available Items :3");
        expectedContent.add("");
        expectedContent.add("Available No of Products: 2");

        // Assert that the content of the file matches the expected content
        assertEquals(expectedContent, fileContent);
    }



}