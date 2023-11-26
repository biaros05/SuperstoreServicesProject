package jdbcsuperstore;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import java.sql.Array;

public class SuperstoreServices {
    private Connection conn;

    public SuperstoreServices(String driver, String host, String port, String user, String password)
            throws SQLException {
        createConnection(driver, host, port, user, password);
    }

    /* method to close connection */
    public void Close() throws SQLException {
        if (this.conn != null)
            this.conn.close();
    }

    public void createConnection(String driver, String host, String port, String user, String password)
            throws SQLException {
        if (!this.connectionActive()) {
            this.conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@198.168.52.211:1521/pdbora19c.dawsoncollege.qc.ca",
                    user, password);
            String logLogin = "{call logUserLogin(?)}";
            CallableStatement stmt = this.conn.prepareCall(logLogin);
            stmt.setString(1, user);
            stmt.execute();
        }
    }

    public Connection getConnection() {
        return this.conn;
    }

    public boolean connectionActive() throws SQLException {
        return (this.conn != null && !this.conn.isClosed());
    }

    /* CHECK VALIDITY OF IDs */

    /**
     * this function receives a productID as input and checks if that productID is
     * in the db
     * 
     * @param productID - represents the productID
     * @throws SQLException
     */
    public void isProductIDValid(int productID) throws SQLException {
        String sql = "{ ? = call validatingIDs.getProductIDs(?)}";
        CallableStatement stmt = conn.prepareCall(sql);
        stmt.registerOutParameter(1, Types.INTEGER);
        stmt.setInt(2, productID);
        stmt.execute();

        int result = stmt.getInt(1);

        if (result == 0) {
            throw new IllegalArgumentException("invalid productID");
        }
    }

    /**
     * this function receives a customerID as input and checks if that customerID is
     * in the db
     * 
     * @param customerID - represents the customerID
     * @throws SQLException
     */
    public void isCustomerIDValid(int customerID) throws SQLException {
        String sql = "{ ? = call validatingIDs.getCustomerIDs(?)}";
        CallableStatement stmt = conn.prepareCall(sql);
        stmt.registerOutParameter(1, Types.INTEGER);
        stmt.setInt(2, customerID);
        stmt.execute();

        int result = stmt.getInt(1);

        if (result == 0) {
            throw new IllegalArgumentException("invalid customer ID");
        }
    }

    /**
     * this function receives a warehouseID as input and checks if that warehouseID
     * is in the db
     * 
     * @param warehouseID - represents the warehouseID
     * @throws SQLException
     */
    public void isWarehouseIDValid(int warehouseID) throws SQLException {
        String sql = "{ ? = call validatingIDs.getWarehouseIDs(?)}";
        CallableStatement stmt = conn.prepareCall(sql);
        stmt.registerOutParameter(1, Types.INTEGER);
        stmt.setInt(2, warehouseID);
        stmt.execute();

        int result = stmt.getInt(1);

        if (result == 0) {
            throw new IllegalArgumentException("invalid warehouse ID");
        }
    }

    /**
     * this function receives a storeID as input and checks if that storeID is in
     * the db
     * 
     * @param storeID - represents the storeID
     * @throws SQLException
     */
    public void isStoreIDValid(int storeID) throws SQLException {
        String sql = "{ ? = call validatingIDs.getStoreIDs(?)}";
        CallableStatement stmt = conn.prepareCall(sql);
        stmt.registerOutParameter(1, Types.INTEGER);
        stmt.setInt(2, storeID);
        stmt.execute();

        int result = stmt.getInt(1);

        if (result == 0) {
            throw new IllegalArgumentException("invalid store ID");
        }
    }

    /**
     * Validating if ReviewID exists, 1 if yes, 0 means no.
     * 
     * @param reviewID - The review id that is getting used.
     * @throws SQLException - if id entered by user does not exist
     */
    public void isReviewIDValid(int reviewID) throws SQLException {
        String sql = "{ ? = call validatingIDs.getReviewIDs(?)}";
        CallableStatement stmt = conn.prepareCall(sql);
        stmt.registerOutParameter(1, Types.INTEGER);
        stmt.setInt(2, reviewID);
        stmt.execute();

        int result = stmt.getInt(1);

        if (result == 0) {
            throw new IllegalArgumentException("invalid review ID");
        }
    }

    /**
     * this function will call the viewCustomer procedure and loop through the
     * cursor to print out info
     * for all current customers in our table.
     * 
     * @return - represents the list of customers
     * @throws SQLException
     */

    public List<Customer> viewCustomers() throws SQLException {
        CallableStatement cs = this.conn.prepareCall("{call viewCustomerMenu.viewCustomers(?)}");
        cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
        cs.execute();
        ResultSet rs = (ResultSet) cs.getObject(1);
        List<Customer> customers = new ArrayList<Customer>();
        while (rs.next()) {
            customers.add(new Customer(
                    rs.getInt("customerId"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"),
                    rs.getString("streetAddress"),
                    rs.getString("city"),
                    rs.getString("province"),
                    rs.getString("country")));
        }
        return customers;

    }

    /**
     * this function will call the viewCustomer procedure and loop through the
     * cursor to print out info
     * for all current customers in our table.
     * 
     * @return - represents the list of customers
     * @throws SQLException
     */
    public List<Review> viewReviews() throws SQLException {
        CallableStatement cs = this.conn.prepareCall("{call viewReviewProdMenu.viewReviews(?)}");
        cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
        cs.execute();
        ResultSet rs = (ResultSet) cs.getObject(1);
        List<Review> reviews = new ArrayList<Review>();
        while (rs.next()) {
            reviews.add(new Review(
                    rs.getInt("REVIEWID"),
                    rs.getInt("PRODUCTID"),
                    rs.getInt("CUSTOMERID"),
                    rs.getDouble("STAR"),
                    rs.getInt("FLAGNUMS"),
                    rs.getString("DESCRIPTION")));
        }
        return reviews;

    }

    /**
     * this function will call the viewWarehouses procedure and loop through the
     * cursor to print out info
     * for all current warehouses in our table.
     * 
     * @return - represents the list of customers
     * @throws SQLException
     */
    public List<Warehouse> viewWarehouse() throws SQLException {
        CallableStatement cs = this.conn.prepareCall("{call viewReviewProdMenu.viewWarehouses(?)}");
        cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
        cs.execute();
        ResultSet rs = (ResultSet) cs.getObject(1);
        List<Warehouse> warehouses = new ArrayList<Warehouse>();
        while (rs.next()) {
            warehouses.add(new Warehouse(
                    rs.getInt("warehouseID"),
                    rs.getString("name"),
                    rs.getString("streetAddress"),
                    rs.getString("city"),
                    rs.getString("province"),
                    rs.getString("country")));
        }
        return warehouses;

    }

    /**
     * this function takes an Order object as input and creates a new order in the
     * DB
     * 
     * @param orderObj - represents the order object
     * @return - int representing the new OrderID
     * @throws SQLException
     */
    public int createOrder(Order orderObj) throws SQLException {
        String orderProc = "{call dataManipulation.createOrder(?, ?)}";
        CallableStatement stmt = conn.prepareCall(orderProc);
        stmt.setObject(1, orderObj);
        stmt.registerOutParameter(2, Types.INTEGER);
        stmt.execute();
        int newOrderId = stmt.getInt(2);
        return newOrderId;
    }

    /**
     * this function takes the parameters necessary in order to add an order item
     * using the addOrderItem procedure
     * 
     * @param newOrderId - represents the orderID of the order that has just been
     *                   created
     * @param productID  - represents the product to add to that order
     * @param quantity   - represents the quantity of the product they would like to
     *                   add
     * @throws SQLException
     */
    public void addOrderItem(int newOrderId, int productID, int quantity) throws SQLException {
        isProductIDValid(productID);
        if (totalInventory(productID) < quantity) {
            throw new IllegalArgumentException("product does not have enough stock");
        }
        String addItem = "{call dataManipulation.addOrderItem(?, ?, ?)}";
        CallableStatement stmt = conn.prepareCall(addItem);
        stmt.setInt(1, newOrderId);
        stmt.setInt(2, productID);
        stmt.setInt(3, quantity);
        stmt.execute();
    }

    /**
     * this function takes all parameters necessary to call the newDeliveryIncome,
     * which lets employers log in a new
     * delivery and update the quantity in a particular warehouse
     * 
     * @param productID   - represents the id of the product in the delivery
     * @param warehouseID - represents the id of the warehouse the delivery is going
     *                    to
     * @param quantity    - represents the quantity in the delivery
     * @throws SQLException
     */
    public void newDeliveryIncome(int productID, int warehouseID, int quantity) throws SQLException {
        isProductIDValid(productID);
        isWarehouseIDValid(warehouseID);
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantities cannot be negative");
        }
        String addDelivery = "{call dataManipulation.newDeliveryIncome(?, ?, ?)}";
        CallableStatement stmt = conn.prepareCall(addDelivery);
        stmt.setInt(1, productID);
        stmt.setInt(2, warehouseID);
        stmt.setInt(3, quantity);
        stmt.execute();
    }

    /**
     * this function returns the total inventory of a product with a particular ID
     * 
     * @param productID - represents the product whose inventory we are checking
     * @return - returns the total inventory
     * @throws SQLException
     */
    public int totalInventory(int productID) throws SQLException {
        isProductIDValid(productID);
        String inventoryProc = "{? = call viewOrderMenu.totalInventory(?)}";
        CallableStatement stmt = conn.prepareCall(inventoryProc);
        stmt.registerOutParameter(1, Types.INTEGER);
        stmt.setInt(2, productID);
        stmt.execute();
        int total = stmt.getInt(1);
        return (total);
    }

    /**
     * this function will retrieve a list of all the customers whose comments have
     * been flagged at least once
     * 
     * @return - returns a list of customer names
     * @throws SQLException
     */
    public List<String> flaggedCustomers() throws SQLException {
        List<String> customers = new ArrayList<String>();
        String flaggedCusProc = "{ ? = call viewCustomerMenu.flaggedCustomers()}";
        CallableStatement stmt = conn.prepareCall(flaggedCusProc);
        stmt.registerOutParameter(1, Types.ARRAY, "VIEWCUSTOMERMENU.CUS_NAMES");
        stmt.execute();
        Array cusNames = stmt.getArray(1);
        String[] resultArray = (String[]) cusNames.getArray();
        for (String name : resultArray) {
            customers.add(name);
        }
        return customers;
    }

    /**
     * this function takes a productID and and removes that product from the table
     * 
     * @param productID
     * @throws SQLException
     */
    public void removeProduct(int productID) throws SQLException {
        isProductIDValid(productID);
        String removeProd = "{call dataManipulation.removeProduct(?)}";
        CallableStatement stmt = conn.prepareCall(removeProd);
        stmt.setInt(1, productID);
        stmt.execute();
    }

    /**
     * This method creates and inserts the review into the DB
     * 
     * @param productID   - represents the productID that holds the review
     * @param customerID  - represents the customer that is making the review
     * @param star        -represents the star rating.
     * @param description - represents the description of the rating.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void createReview(Review review) throws SQLException {
        String createReview = "{call dataManipulation.createReview(?)}";
        CallableStatement stmt = conn.prepareCall(createReview);
        stmt.setObject(1, review);
        stmt.execute();
    }

    /**
     * This method flags a review in the databse
     * 
     * @param reviewID
     * @throws SQLException
     */
    public void flagReview(int reviewID) throws SQLException {
        isReviewIDValid(reviewID);
        String flagReview = "{call dataManipulation.flagReview(?)}";
        CallableStatement stmt = conn.prepareCall(flagReview);
        stmt.setInt(1, reviewID);
        stmt.execute();
    }

    /**
     * This function gets the average review score for a product
     * 
     * @param productID - the victim.
     * @return - returns back the average review score.
     * @throws SQLException
     */
    public double calculateAvgReviewScore(int productID) throws SQLException {
        isProductIDValid(productID);
        String gettingAvg = "{? = call viewReviewProdMenu.calculateAvgReviewScore(?)}";
        CallableStatement stmt = conn.prepareCall(gettingAvg);
        stmt.registerOutParameter(1, Types.INTEGER);
        stmt.setInt(2, productID);
        stmt.execute();
        double avgScore = stmt.getDouble(1);
        return avgScore;
    }

    /**
     * This function returns the num representing how many orders a product has.
     * 
     * @param productID - The id that is being used.
     * @return - returns an interger corresponding to how many orders has been
     *         placed on this product.
     * @throws SQLException
     */
    public int numOrders(int productID) throws SQLException {
        isProductIDValid(productID);
        String sql = "{? = call viewOrderMenu.numOrders(?)}";
        CallableStatement stmt = conn.prepareCall(sql);
        stmt.registerOutParameter(1, Types.INTEGER);
        stmt.setInt(2, productID);
        stmt.execute();
        int num = stmt.getInt(1);
        return num;
    }

    /**
     * This function removes a warehouse that is specified by it's ID.
     * 
     * @param warehouseID - The ID of the warehouse that is getting deleted
     * @throws SQLException
     */
    public void removeWarehouse(int warehouseID) throws SQLException {
        isWarehouseIDValid(warehouseID);
        String sql = "{call dataManipulation.removeWarehouse(?)}";
        CallableStatement stmt = conn.prepareCall(sql);
        stmt.setInt(1, warehouseID);
        stmt.execute();
    }

    /**
     * Adds a customer using a customer object to the database.
     * 
     * @param cus - The customer object with all valid arguments.
     * @throws SQLException
     */
    public void addCustomer(Customer cus) throws SQLException {
        String createReview = "{call dataManipulation.addCustomer(?)}";
        CallableStatement stmt = conn.prepareCall(createReview);
        stmt.setObject(1, cus);
        stmt.execute();
    }

    /**
     * Shows all orders and it's column details.
     * 
     * @return - A list of objects from the database.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public List<Order> viewAllOrders() throws SQLException, ClassNotFoundException {
        CallableStatement stmt = conn.prepareCall("{call viewOrderMenu.viewOrders(?)}");
        stmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
        stmt.execute();
        ResultSet rs = (ResultSet) stmt.getObject(1);
        List<Order> listOrders = new ArrayList<Order>();
        while (rs.next()) {
            listOrders.add(new Order(
                    rs.getInt("orderID"),
                    rs.getInt("customerID"),
                    rs.getInt("storeID"),
                    rs.getDate("orderDate")));
        }
        return listOrders;
    }

    /**
     * Retrieves all products from the databse.
     * 
     * @return - A list representing all the products and it's columns from the
     *         database.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public List<Product> getAllProducts() throws SQLException, ClassNotFoundException {
        CallableStatement stmt = conn.prepareCall("{call viewReviewProdMenu.viewProducts(?)}");
        stmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
        stmt.execute();
        ResultSet rs = (ResultSet) stmt.getObject(1);
        List<Product> products = new ArrayList<Product>();
        while (rs.next()) {
            products.add(new Product(
                    rs.getInt("productID"),
                    rs.getString("name"),
                    rs.getString("category")));
        }
        return products;
    }

    /**
     * Retrieves all the store from the database.
     * 
     * @return - A list of all stores with it's respective columns.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public List<Store> getAllStores() throws SQLException, ClassNotFoundException {
        CallableStatement stmt = conn.prepareCall("{call viewReviewProdMenu.viewStores(?) }");
        stmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
        stmt.execute();
        ResultSet rs = (ResultSet) stmt.getObject(1);
        List<Store> stores = new ArrayList<Store>();
        while (rs.next()) {
            stores.add(new Store(rs.getInt("storeID"), rs.getString("name")));
        }
        return stores;
    }

    /**
     * Retrieves products from the database depending on which category specified by
     * user.
     * 
     * @param categoryName - The category of the products in which user would like
     *                     to view.
     * @return - A list of products who's category is what is specific through the
     *         user.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public List<Product> getProductsByCategory(String categoryName) throws SQLException, ClassNotFoundException {
        CallableStatement stmt = conn.prepareCall("{call viewReviewProdMenu.viewProductsByCategory(?,?)}");
        stmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
        stmt.setString(2, categoryName);
        stmt.execute();
        ResultSet rs = (ResultSet) stmt.getObject(1);
        List<Product> products = new ArrayList<Product>();
        while (rs.next()) {
            products.add(new Product(rs.getInt("productID"), rs.getString("name"), rs.getString("category")));
        }
        if (products.size() == 0) {
            throw new IllegalArgumentException("category name does not exist");
        }
        return products;
    }

}
