import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ArrayList<Product> productList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        // Create an instance of WestminsterShoppingManager for managing shopping operations
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        UserShoppingGUI userShoppingGUI = new UserShoppingGUI(productList, shoppingManager);
        boolean exitProgram = false;

        do {
            try {
                int choice;

                //Main menu options
                System.out.println("Main Menu");
                System.out.println("Press 1 - To open the menu console for the manager.");
                System.out.println("Press 2 - To open the GUI interface for user.");
                System.out.println("Press 3 - To exit system");

                System.out.print("Enter required number: ");
                choice = scanner.nextInt();


                switch (choice) {
                    case 1:
                        //Open menu console of manager
                        ShoppingManager sys = new WestminsterShoppingManager();
                        boolean exit = false;

                        while (!exit) {
                            exit = sys.runMenu();
                        }
                        break;

                    case 2:
                        //Open GUI of the user
                        openGui((WestminsterShoppingManager) shoppingManager);
                        break;
                    case 3:
                        System.exit(0);
                        exitProgram = true;

                        break;
                    default:
                        System.out.println("Invalid input. Please enter a valid number.");

                }

            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }while (!exitProgram);


}

    //Method to open GUI for the user
    private static void openGui(WestminsterShoppingManager shoppingManager){
        try {

            // Create a JFrame for the UserShoppingGUI
            JFrame frame = new UserShoppingGUI(shoppingManager.getProductList(), shoppingManager);
            frame.setTitle("Westminster Shopping Centre");
            frame.setSize(800, 700);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // Update the table model in the GUI
            ((UserShoppingGUI) frame).updateTableModel();
        } catch (Exception ex) {
            ex.printStackTrace();
            // Handle or log the exception as appropriate for your application
        }


    }
}