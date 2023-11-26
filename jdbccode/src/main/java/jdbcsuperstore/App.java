/**@fileoverview - A superstore management system, capable of viewing all information, and manipulating the database.
 * @authors Amy Nguyen, Bianca Rossetti
 */
package jdbcsuperstore;

import java.io.Console;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * Hello world!
 *
 */
public class App {
    public static final Scanner scan = new Scanner(System.in);
    public static Connection conn = null;
    public static SuperstoreServices services = null;

    public static void main(String[] args) throws SQLException {
        Console console = System.console() ;
        System.out.println("-Welcome to SuperStore management application- \n Enter your DB username..");
        String user = scan.nextLine();
        System.out.println("Enter your password..");
		char [] pass = console.readPassword("Enter password: ");
        String password = "";
        for (char letter : pass)
        {
            password+=letter;
        }
        try {
            services = new SuperstoreServices("jdbc:oracle:thin:", "198.168.52.211", "1521", user, password);
            conn = services.getConnection();
            mainMenu();
            conn.close(); // keeps running even with exception?????
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            services.Close();
        }
    }

    /**
     * this function represents the main menu with MAIN FUNCTIONS
     */
    public static void mainMenu() {
        int input = 0;
        System.out.println(
                "\n ------------------------------------------------------------- \n Here are your options.. choose wisely. ");
        System.out.println("1. View information about customers");
        System.out.println("2. View information about reviews and products");
        System.out.println("3. View information about orders");
        System.out.println("4. Add or modify");
        System.out.println("5. EXIT \n");
        System.out.println("Enter the number of the option you would like to select: ");
        do {

            try {
                input = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                input = 0;
            }
            switch (input) {
                case 1:
                    System.out.println("\033c");
                    viewCustomerMenu();
                    break;
                case 2:
                    System.out.println("\033c");
                    viewReviewProdMenu();
                    break;
                case 3:
                    viewOrderMenu();
                    break;
                case 4:
                    manipulationMenu();
                    break;
                case 5:
                    System.out.println("Chosen \"EXIT\". System exiting.. Goodbye!!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }

        } while (true);

    }

    /**
     * The menu of customer related viewing options.
     * Takes in user input to chose option.
     */
    public static void viewCustomerMenu() {
        int input = 0;
        System.out.println("\n------------------------------------------------------------- ");
        System.out.println("1. View all customer information");
        System.out.println("2. View names of flagged customers");
        System.out.println("3. BACK \n");
        System.out.println("Enter the number of the option you would like to select: ");
        do {
            try {
                input = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                input = 0;
            }
            switch (input) {
                case 1:
                    System.out.println("\033c");
                    viewCustomerInfo();
                    break;
                case 2:
                    System.out.println("\033c");
                    viewFlaggedCustomers();
                    break;
                case 3:
                    System.out.println("\033c");
                    mainMenu();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (true);
    }

    /**
     * The menu related to all product viewing related options.
     * Takes in user input to chose option.
     */
    public static void viewReviewProdMenu() {
        int input = 0;
        do {
            System.out.println("\n------------------------------------------------------------- ");
            System.out.println("1. View all reviews");
            System.out.println("2. Find the average review score of a product");
            System.out.println("3. View all information about products");
            System.out.println("4. View all information about products in a specific category");
            System.out.println("5. View all information about stores");
            System.out.println("6. View all information about warehouses");
            System.out.println("7. BACK \n");
            System.out.println("Enter the number of the option you would like to select: ");
            try {
                input = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                input = 0;
            }

            switch (input) {
                case 1:
                    System.out.println("\033c");
                    viewReviewInfo();
                    break;
                case 2:
                    System.out.println("\033c");
                    getAvgReviewScoreforProduct();
                    break;
                case 3:
                    System.out.println("\033c");
                    viewAllProductInfo();
                    break;
                case 4:
                    System.out.println("\033c");
                    viewProductsByCategory();
                    break;
                case 5:
                    System.out.println("\033c");
                    viewAllStoreInfo();
                    break;
                case 6:
                    System.out.println("\033c");
                    viewWarehouseInfo();
                    break;
                case 7:
                    mainMenu();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (true);
    }

    /**
     * The menu to all Order related viewing options
     * Takes in user input to chose option.
     */
    public static void viewOrderMenu() {
        int input = 0;
        System.out.println("\n------------------------------------------------------------- ");
        System.out.println("1. View total inventory of a specific product");
        System.out.println("2. Calculate the number of orders placed on a product");
        System.out.println("3. View all information about orders");
        System.out.println("4. BACK \n");
        System.out.println("Enter the number of the option you would like to select: ");
        do {
            try {
                input = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                input = 0;
            }

            switch (input) {
                case 1:
                    System.out.println("\033c");
                    checkTotalProdInventory();
                    break;
                case 2:
                    System.out.println("\033c");
                    viewNumOrders();
                    break;
                case 3:
                    System.out.println("\033c");
                    viewAllOrderInfo();
                    break;
                case 4:
                    mainMenu();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (true);
    }

    /**
     * Menu option for all database manipulation related things.
     * Takes in user input to chose option.
     */
    public static void manipulationMenu() {
        int input = 0;
        System.out.println("\n------------------------------------------------------------- ");
        System.out.println("1. Place an order an order");
        System.out.println("2. Remove a product");
        System.out.println("3. Log a new delivery into the system");
        System.out.println("4. Flag a review");
        System.out.println("5. Create a review");
        System.out.println("6. Remove warehouse");
        System.out.println("7. Add customer");
        System.out.println("8. BACK \n");
        System.out.println("Enter the number of the option you would like to select: ");
        do {
            try {
                input = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                input = 0;
            }

            switch (input) {
                case 1:
                    System.out.println("\033c");
                    placeOrder();
                    break;
                case 2:
                    System.out.println("\033c");
                    removeProduct();
                    break;
                case 3:
                    System.out.println("\033c");
                    logDelivery();
                    break;
                case 4:
                    System.out.println("\033c");
                    flagReview();
                    break;
                case 5:
                    System.out.println("\033c");
                    createReview();
                    break;
                case 6:
                    System.out.println("\033c");
                    removeWarehouse();
                    break;
                case 7:
                    System.out.println("\033c");
                    addCustomer();
                    break;
                case 8:
                    mainMenu();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (true);
    }

    /**
     * this function creates an order in the Orders table using user input
     */
    public static void placeOrder() {
        boolean isSuccessful = false;
        Order newOrder = null;
        int orderID = 0;
        while (!isSuccessful) {
            try {
                conn.setAutoCommit(false);
                conn.commit();
                System.out.println("Please enter the customerID associated with the order:");
                int customerID = Integer.parseInt(scan.nextLine());
                services.isCustomerIDValid(customerID);
                System.out.println("Please enter the storeID associated with the order:");
                int storeID = Integer.parseInt(scan.nextLine());
                services.isStoreIDValid(storeID);
                newOrder = new Order(customerID, storeID, conn);
                orderID = services.createOrder(newOrder);
                addOrderItems(orderID);
                conn.commit();
                isSuccessful = true;
                manipulationMenu();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("enter an integer please");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * this function takes the orderID of the order recently added and adds the
     * items
     * the user selects into the Orders_Products table
     * 
     * @param orderID - represents the ID of the order created
     * @throws SQLException - may throw an SQL exception
     */
    public static void addOrderItems(int orderID) {
        String addMore = "yes";
        while (addMore.equals("yes") || addMore.equals("y")) {
            try {
                System.out.println("Please enter the productID of the product you would like to add:");
                int productID = Integer.parseInt(scan.nextLine());
                System.out.println("Please enter the quantity of the product:");
                int quantity = Integer.parseInt(scan.nextLine());
                services.addOrderItem(orderID, productID, quantity);
                System.out.println("your order item has been added successfully");
                System.out.println("add another item?");
                addMore = scan.nextLine().toLowerCase();
            } catch (NumberFormatException e) {
                System.out.println("enter an integer please");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * this function takes the orderID of the order recently added and adds the
     * items
     * the user selects into the Orders_Products table
     * 
     * @param orderID - represents the ID of the order created
     * @throws SQLException - may throw an SQL exception
     */
    public static void logDelivery() {
        boolean isSuccessful = false;
        while (!isSuccessful) {
            try {
                conn.setAutoCommit(false);
                conn.commit();
                System.out.println("Please enter the productID of the product in the delivery:");
                int productID = Integer.parseInt(scan.nextLine());
                System.out.println("Please enter the warehouseID of the warehouse where the delivery will be sent:");
                int warehouseID = Integer.parseInt(scan.nextLine());
                System.out.println("Please enter the quantity of the product:");
                int quantity = Integer.parseInt(scan.nextLine());
                services.newDeliveryIncome(productID, warehouseID, quantity);
                System.out.println("your delivery has been added successfully!");
                conn.commit();
                isSuccessful = true;
                manipulationMenu();
            } catch (NumberFormatException e) {
                System.out.println("enter an integer please");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Checks the total available inventory for a product.
     */
    public static void checkTotalProdInventory() {
        boolean isSuccessful = false;
        while (!isSuccessful) {
            try {
                System.out.println("Please enter the productID of the product whose inventory you would like to view:");
                int productID = Integer.parseInt(scan.nextLine());
                services.isProductIDValid(productID);
                int inventory = services.totalInventory(productID);
                System.out.println("total inventory of product with id " + productID + " is " + inventory);
                isSuccessful = true;
                viewOrderMenu();
            } catch (SQLException e) {
                e.getMessage();
            } catch (NumberFormatException e) {
                System.out.println("enter an integer please");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Prints all the customers from a list of customers.
     */
    public static void viewCustomerInfo() {
        List<Customer> customers = null;
        try {
            customers = services.viewCustomers();
            for (Customer c : customers) {
                System.out.println(c);
            }
            viewCustomerMenu();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Prints all warehouse info details from a list of warehouses.
     */
    public static void viewWarehouseInfo() {
        List<Warehouse> warehouses = null;
        try {
            warehouses = services.viewWarehouse();
            for (Warehouse w : warehouses) {
                System.out.println(w);
            }
            viewReviewProdMenu();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Prints all review information from a list of reviews
     */
    public static void viewReviewInfo() {
        List<Review> reviews = null;
        try {
            reviews = services.viewReviews();
            for (Review c : reviews) {
                System.out.println(c);
            }
            viewReviewProdMenu();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    /**
     * Prints all customer info who's review are flagged.
     */
    public static void viewFlaggedCustomers() {
        List<String> flaggedCus = null;
        try {
            flaggedCus = services.flaggedCustomers();
            System.out.println("the flagged customers are: ");
            for (String cus : flaggedCus) {
                System.out.println(cus);
            }
            viewCustomerMenu();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    /**
     * Removes a product from the database using removeProduct() from
     * SuperStoreServices.
     */
    public static void removeProduct() {
        boolean isSuccessful = false;
        while (!isSuccessful) {
            try {
                conn.setAutoCommit(false);
                conn.commit();
                System.out.println("Please enter the productID of the product you would like to remove:");
                int productID = Integer.parseInt(scan.nextLine());
                services.isProductIDValid(productID);
                services.removeProduct(productID);
                System.out.println("product has been successfully removed!");
                conn.commit();
                isSuccessful = true;
                manipulationMenu();
            } catch (SQLException e) {
                e.getMessage();
            } catch (NumberFormatException e) {
                System.out.println("enter an integer please");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Views the total number of orders for a specific product.
     * With the help of numOrders() from SuperStore Services.
     */
    public static void viewNumOrders() {
        boolean isSuccessful = false;
        while (!isSuccessful) {
            try {
                System.out.println("Enter productid to view it's total orders:");
                int productID = Integer.parseInt(scan.nextLine());
                services.isProductIDValid(productID);
                int total = services.numOrders(productID);
                System.out.println("For productID of " + productID + ", there are " + total + " order(s) placed");
                isSuccessful = true;
                viewOrderMenu();
            } catch (SQLException e) {
                e.getMessage();
            } catch (NumberFormatException e) {
                System.out.println("Enter an integer please");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Displays the average review score for a product.
     * With the help of calculateAvgReviewScore() from SuperStoreServices.
     */
    public static void getAvgReviewScoreforProduct() {
        boolean isSuccessful = false;
        while (!isSuccessful) {
            try {
                System.out.println("Enter a productID to view it's average review score:");
                int productID = Integer.parseInt(scan.nextLine());
                services.isProductIDValid(productID);
                double result = services.calculateAvgReviewScore(productID);
                System.out.println("The average score for this product is: " + result + " stars");
                isSuccessful = true;
                viewReviewProdMenu();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Enter an integer please");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Creates a review and sends it to the database.
     * With the help of createReview() from SuperStoreServices.
     */
    public static void createReview() {
        boolean isSuccessful = false;
        while (!isSuccessful) {
            try {
                conn.setAutoCommit(false);
                conn.commit();
                System.out
                        .println("Enter the params in the respective order: productID, customerID, star, description");
                int productID = Integer.parseInt(scan.nextLine());
                int customerID = Integer.parseInt(scan.nextLine());
                int star = Integer.parseInt(scan.nextLine());
                String description = scan.nextLine();

                Review newReview = new Review(productID, customerID, star, description, conn);

                services.createReview(newReview);
                System.out.println("New order review created!");
                conn.commit();
                isSuccessful = true;
                manipulationMenu();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Enter an integer please");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (ClassNotFoundException e) {
                System.out.println("class could not be mapped");
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Flags a review and sends it to the database.
     * With the help of flagReview() from SuperStoreServices.
     */
    public static void flagReview() {
        boolean isSuccessful = false;
        while (!isSuccessful) {
            try {
                conn.setAutoCommit(false);
                conn.commit();
                System.out.println("Please enter the review ID that you would like to flag.");
                int reviewID = Integer.parseInt(scan.nextLine());
                services.isReviewIDValid(reviewID);
                services.flagReview(reviewID);
                System.out.println("Review was successfully flagged. Thank you");
                conn.commit();
                isSuccessful = true;
                manipulationMenu();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Enter an integer please");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Shows all products by a specific category specified through user.
     * With the help of getProductsByCategory() from SuperStoreServices.
     */
    public static void viewProductsByCategory() {
        boolean isSuccessful = false;
        while (!isSuccessful) {
            try {
                System.out.println("Enter category name in which products you'd like to view(CASE SENSITVE)");
                String category = scan.nextLine();
                List<Product> products = services.getProductsByCategory(category);
                for (Product p : products) {
                    System.out.println(p);
                }
                isSuccessful = true;
                viewReviewProdMenu();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Remove warehouse from database.
     */
    public static void removeWarehouse() {
        boolean isSuccessful = false;
        while (!isSuccessful) {
            try {
                conn.setAutoCommit(false);
                conn.commit();
                System.out.println("Enter the warehouseID you would like to delete:");
                int warehouseID = Integer.parseInt(scan.nextLine());
                services.isWarehouseIDValid(warehouseID);
                services.removeWarehouse(warehouseID);
                System.out.println("Warehouse deleted successfully.");
                conn.commit();
                isSuccessful = true;
                manipulationMenu();
            } catch (SQLException e) {
                e.getMessage();
            } catch (NumberFormatException e) {
                System.out.println("Enter an integer please");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    /**
     * Asks for valid information, and adds the customer object to the database.
     */
    public static void addCustomer() {
        boolean isSuccessful = false;
        while (!isSuccessful) {
            try {
                conn.setAutoCommit(false);
                conn.commit();
                System.out.println("Please enter the following values for the new customer: ");
                System.out.println("firstname");
                String firstname = scan.nextLine();
                System.out.println("lastname:");
                String lastname = scan.nextLine();
                System.out.println("email");
                String email = scan.nextLine();
                System.out.println("streetAddress:");
                String address = scan.nextLine();
                System.out.println("city:");
                String city = scan.nextLine();
                System.out.println("province:");
                String province = scan.nextLine();
                System.out.println("country");
                String country = scan.nextLine();

                Customer newCus = new Customer(firstname, lastname, email, address, city, province, country, conn);
                services.addCustomer(newCus);
                System.out.println("Customer added succesffuly!");
                conn.commit();
                isSuccessful = true;
                manipulationMenu();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.out.println("failure encountered during mapping");
                e.printStackTrace();
            }
        }
    }

    /**
     * Prints all orders information, using a list of orders.
     */
    public static void viewAllOrderInfo() {
        try {
            List<Order> orders = services.viewAllOrders();
            for (Order o : orders) {
                System.out.println(o);
            }
            viewOrderMenu();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("failure encountered during mapping");
            e.printStackTrace();
        }
    }

    /**
     * Prints all products information, using a list of products.
     */
    public static void viewAllProductInfo() {
        try {
            List<Product> products = services.getAllProducts();
            for (Product p : products) {
                System.out.println(p);
            }
            viewReviewProdMenu();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("failure encountered during mapping");
            e.printStackTrace();
        }
    }

    /**
     * Prints all store information, using a list of Stores.
     */
    public static void viewAllStoreInfo() {
        try {
            List<Store> stores = services.getAllStores();
            for (Store s : stores) {
                System.out.println(s);
            }
            viewReviewProdMenu();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("failure encountered during mapping");
            e.printStackTrace();
        }
    }
}
