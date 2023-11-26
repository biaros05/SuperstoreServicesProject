--Amy Nguyen,Bianca Rossetti
DROP TABLE Orders_Products ;
DROP TABLE Stores_Products;
DROP TABLE Warehouses_Products;
DROP TABLE Warehouses_Stores;
DROP TABLE Reviews;
DROP TABLE Orders;
DROP TABLE Customers;
DROP TABLE Warehouses;
DROP TABLE Stores;
DROP TABLE Loglogins;
DROP TABLE Logwarehousemodification;
DROP TABLE Logordermodification;
DROP TABLE Logreviewmodification;
DROP TABLE Products;
DROP PACKAGE viewCustomerMenu;
DROP PACKAGE viewReviewProdMenu;
DROP PACKAGE viewOrderMenu;
DROP PACKAGE dataManipulation;
DROP PACKAGE validatingIDs;
DROP PROCEDURE logUserLogin;
DROP PROCEDURE checkIfOrderStillValid;
DROP TRIGGER After_OrderModification_Insert;
DROP TRIGGER Before_Review_DeleteInsert;
DROP TRIGGER beforeModifyingWarehouse;

CREATE TABLE Stores(
    storeID NUMBER(2) GENERATED ALWAYS AS IDENTITY CONSTRAINT store_pk PRIMARY KEY,
    name VARCHAR2(20) NOT NULL
);

CREATE TABLE Warehouses(
    warehouseID      NUMBER(2)   GENERATED ALWAYS AS IDENTITY CONSTRAINT warehouse_pk PRIMARY KEY,
    name             VARCHAR2(20) NOT NULL,
    streetAddress    VARCHAR2(40),
    city             VARCHAR2(40),
    province         VARCHAR2(40),
    country          VARCHAR2(40)
);

CREATE TABLE Warehouses_Stores(
    warehouseID NUMBER(2)   REFERENCES Warehouses(warehouseID) ON DELETE CASCADE NOT NULL ,
    storeID     NUMBER(2)   REFERENCES Stores(storeID) ON DELETE CASCADE NOT NULL
);

CREATE TABLE Products(
    productID   NUMBER(2)   GENERATED ALWAYS AS IDENTITY CONSTRAINT products_pk PRIMARY KEY,
    name        VARCHAR2(20) NOT NULL,
    category    VARCHAR2(20) NOT NULL
);

CREATE TABLE Warehouses_Products(
    warehouseID     NUMBER(2)   REFERENCES Warehouses(warehouseID) ON DELETE CASCADE NOT NULL,
    productID       NUMBER(2)   REFERENCES Products(productID) ON DELETE CASCADE NOT NULL,
    quantity        NUMBER(6)   NOT NULL
);

CREATE TABLE Stores_Products(
    storeID         NUMBER(2)   REFERENCES Stores(storeID) ON DELETE CASCADE NOT NULL,
    productID       NUMBER(2)   REFERENCES Products(productID) ON DELETE CASCADE NOT NULL,
    price           NUMBER(8,2) NOT NULL
);

CREATE TABLE Customers(
    customerID       NUMBER(2)   GENERATED ALWAYS AS IDENTITY CONSTRAINT customers_pk PRIMARY KEY,
    firstName        VARCHAR2(40) NOT NULL,
    lastName         VARCHAR2(20) NOT NULL,
    email            VARCHAR2(30) NOT NULL,
    streetAddress    VARCHAR2(40),
    city             VARCHAR2(40),
    province         VARCHAR2(40),
    country          VARCHAR2(40)
);

CREATE TABLE Orders(
    orderID         NUMBER(2)   GENERATED ALWAYS AS IDENTITY CONSTRAINT orders_pk PRIMARY KEY,
    customerID      NUMBER(2)   REFERENCES Customers(customerID) ON DELETE CASCADE NOT NULL,
    storeID         NUMBER(2)   REFERENCES Stores(storeID) ON DELETE CASCADE NOT NULL,
    orderDate       DATE
);

CREATE TABLE Orders_Products(
    orderID         NUMBER(2)   REFERENCES Orders(orderID) ON DELETE CASCADE NOT NULL,
    productID       NUMBER(2)   REFERENCES Products(productID) ON DELETE CASCADE NOT NULL,
    quantity        NUMBER(6)   NOT NULL
);

CREATE TABLE Reviews(
    reviewID        NUMBER(2)   GENERATED ALWAYS AS IDENTITY CONSTRAINT reviews_pk PRIMARY KEY,
    productID       NUMBER(2)   REFERENCES Products(productID) ON DELETE CASCADE NOT NULL,
	customerID  	NUMBER(2) 	REFERENCES Customers(customerID) ON DELETE CASCADE NOT NULL,
    star            NUMBER(2, 1),
    flagNums        NUMBER(1),
    description     VARCHAR2(100)
);

CREATE TABLE LogOrderModification(
    orderID             NUMBER(2)       NOT NULL,
    customerID          NUMBER(2)       NOT NULL,
    storeID             NUMBER(2)       NOT NULL,
    modificationDate    DATE            NOT NULL,
    modificationType    VARCHAR2(20)    NOT NULL
);

CREATE TABLE LogReviewModification(
    reviewID            NUMBER(2)       NOT NULL,
    productID           NUMBER(2)       NOT NULL,
	customerID  	    NUMBER(2)       NOT NULL,
    star                NUMBER(1),
    flagNums            NUMBER(1),
    description         VARCHAR2(100),
    modificationDate    DATE            NOT NULL,
    modificationType    VARCHAR2(20)    NOT NULL
);
CREATE TABLE Loglogins(
    username        VARCHAR2(20) NOT NULL,
    datelogged      DATE         NOT NULL
);

CREATE TABLE LogWarehouseModification(
    warehouseid     NUMBER(2)   NOT NULL,
    productid       NUMBER(2)   NOT NULL,
    quantity        NUMBER(6)   NOT NULL,
    modificationDate    DATE    NOT NULL,
    modificationType    VARCHAR2(20) NOT NULL
);
/


/** inserts **/
INSERT INTO Stores (name) 
VALUES ('marche adonis');
INSERT INTO Stores (name) 
VALUES ('marche atwater');
INSERT INTO Stores (name) 
VALUES ('dawson store');
INSERT INTO Stores (name) 
VALUES ('store magic');
INSERT INTO Stores (name) 
VALUES ('movie store');
INSERT INTO Stores (name) 
VALUES ('super rue champlain');
INSERT INTO Stores (name) 
VALUES ('toy r us');
INSERT INTO Stores (name) 
VALUES ('Dealer one');
INSERT INTO Stores (name) 
VALUES ('dealer montreal');
INSERT INTO Stores (name) 
VALUES ('movie start');
INSERT INTO Stores (name) 
VALUES ('star store');

INSERT INTO Warehouses (name, streetAddress, city, province, country) 
VALUES ('Warehouse A', '100 rue William', 'Saint Laurent', 'Quebec', 'Canada');
INSERT INTO Warehouses (name, streetAddress, city, province, country) 
VALUES ('Warehouse B', '304 Rue Francois-Perrault', 'Villeray Saint-Michel', 'Quebec', 'Canada');
INSERT INTO Warehouses (name, streetAddress, city, province, country) 
VALUES ('Warehouse C', '86700 Weston Rd', 'Toronto', 'Ontario', 'Canada');
INSERT INTO Warehouses (name, streetAddress, city, province, country) 
VALUES ('Warehouse D', '170 Sideroad', 'Quebec City', 'Quebec', 'Canada');
INSERT INTO Warehouses (name, streetAddress, city, province, country) 
VALUES ('Warehouse E', '1231 Trudea road', 'Ottawa', 'Ontario', 'Canada');
INSERT INTO Warehouses (name, streetAddress, city, province, country) 
VALUES ('Warehouse F', '16 Whitlock Rd', NULL, 'Alberta', 'Canada');

/*warehouses_stores???*/

INSERT INTO Products (name, category) VALUES ('laptop ASUS 104S', 'Electronics');
INSERT INTO Products (name, category) VALUES ('apple', 'Grocery');
INSERT INTO Products (name, category) VALUES ('SIMS CD', 'Video Games');
INSERT INTO Products (name, category) VALUES ('orange', 'Grocery');
INSERT INTO Products (name, category) VALUES ('Barbie Movie', 'DVD');
INSERT INTO Products (name, category) VALUES ('L''Oreal Normal Hair', 'Health');
INSERT INTO Products (name, category) VALUES ('BMW iX Lego', 'Toys');
INSERT INTO Products (name, category) VALUES ('BMW i6', 'Cars');
INSERT INTO Products (name, category) VALUES ('Truck 500c', 'Vehicle');
INSERT INTO Products (name, category) VALUES ('paper towel', 'Beauty');
INSERT INTO Products (name, category) VALUES ('plum', 'Grocery');
INSERT INTO Products (name, category) VALUES ('Lamborghini Lego', 'Toys');
INSERT INTO Products (name, category) VALUES ('chicken', 'Grocery');
INSERT INTO Products (name, category) VALUES ('PS5', 'Electronics');
INSERT INTO Products (name, category) VALUES ('pasta', 'Grocery');
INSERT INTO Products (name, category) VALUES ('tomato', 'Grocery');
INSERT INTO Products (name, category) VALUES ('Train X745', 'Vehicle');

INSERT INTO Warehouses_Products (warehouseID, productID, quantity) VALUES (1, 1, 1000);
INSERT INTO Warehouses_Products (warehouseID, productID, quantity) VALUES (2, 2, 24980);
INSERT INTO Warehouses_Products (warehouseID, productID, quantity) VALUES (3, 3, 103);
INSERT INTO Warehouses_Products (warehouseID, productID, quantity) VALUES (4, 4, 35405);
INSERT INTO Warehouses_Products (warehouseID, productID, quantity) VALUES (5, 5, 40);
INSERT INTO Warehouses_Products (warehouseID, productID, quantity) VALUES (6, 6, 450);
INSERT INTO Warehouses_Products (warehouseID, productID, quantity) VALUES (1, 7, 10);
INSERT INTO Warehouses_Products (warehouseID, productID, quantity) VALUES (1, 8, 6);
INSERT INTO Warehouses_Products (warehouseID, productID, quantity) VALUES (5, 9, 1000);
INSERT INTO Warehouses_Products (warehouseID, productID, quantity) VALUES (6, 10, 3532);
INSERT INTO Warehouses_Products (warehouseID, productID, quantity) VALUES (3, 11, 43242);
INSERT INTO Warehouses_Products (warehouseID, productID, quantity) VALUES (2, 10, 39484);
INSERT INTO Warehouses_Products (warehouseID, productID, quantity) VALUES (4, 11, 6579);
INSERT INTO Warehouses_Products (warehouseID, productID, quantity) VALUES (5, 12, 98765);
INSERT INTO Warehouses_Products (warehouseID, productID, quantity) VALUES (6, 13, 43523);
INSERT INTO Warehouses_Products (warehouseID, productID, quantity) VALUES (1, 15, 2132);
INSERT INTO Warehouses_Products (warehouseID, productID, quantity) VALUES (4, 14, 123);
INSERT INTO Warehouses_Products (warehouseID, productID, quantity) VALUES (1, 16, 352222);
INSERT INTO Warehouses_Products (warehouseID, productID, quantity) VALUES (5, 17, 4543);

INSERT INTO Stores_Products (storeID, productID, price) VALUES (1, 1, 970);
INSERT INTO Stores_Products (storeID, productID, price) VALUES (1, 13, 9.5);
INSERT INTO Stores_Products (storeID, productID, price) VALUES (2, 2, 10);
INSERT INTO Stores_Products (storeID, productID, price) VALUES (3, 3, 50);
INSERT INTO Stores_Products (storeID, productID, price) VALUES (4, 4, 2);
INSERT INTO Stores_Products (storeID, productID, price) VALUES (5, 5, 30);
INSERT INTO Stores_Products (storeID, productID, price) VALUES (6, 6, 10);
INSERT INTO Stores_Products (storeID, productID, price) VALUES (7, 7, 40);
INSERT INTO Stores_Products (storeID, productID, price) VALUES (8, 8, 50000);
INSERT INTO Stores_Products (storeID, productID, price) VALUES (9, 9, 856600);
INSERT INTO Stores_Products (storeID, productID, price) VALUES (10, 10, 50);
INSERT INTO Stores_Products (storeID, productID, price) VALUES (2, 11, 10);
INSERT INTO Stores_Products (storeID, productID, price) VALUES (6, 6, 30);
INSERT INTO Stores_Products (storeID, productID, price) VALUES (7, 12, 80);
INSERT INTO Stores_Products (storeID, productID, price) VALUES (5, 3, 16);
INSERT INTO Stores_Products (storeID, productID, price) VALUES (7, 5, 45);
INSERT INTO Stores_Products (storeID, productID, price) VALUES (1, 13, 9.5);
INSERT INTO Stores_Products (storeID, productID, price) VALUES (2, 15, 13.5);
INSERT INTO Stores_Products (storeID, productID, price) VALUES (11, 14, 200);
INSERT INTO Stores_Products (storeID, productID, price) VALUES (7, 7, 38);
INSERT INTO Stores_Products (storeID, productID, price) VALUES (4, 15, 15);

INSERT INTO Customers (firstName, lastName, email, streetAddress, city, province, country) 
VALUES ('mahsa', 'sadeghi', 'msadeghi@dawsoncollege.qc.ca', 'Dawson College', 'Montreal', 'Quebec', 'Canada');
INSERT INTO Customers (firstName, lastName, email, streetAddress, city, province, country) 
VALUES ('alex', 'brown', 'alex@gmail.com', '090 boul saint laurent', 'Montreal', 'Quebec', 'Canada');
INSERT INTO Customers (firstName, lastName, email, streetAddress, city, province, country) 
VALUES ('martin', 'alexandre', 'marting@yahoo.com', NULL, 'Brossard', 'Quebec', 'Canada');
INSERT INTO Customers (firstName, lastName, email, streetAddress, city, province, country) 
VALUES ('daneil', 'hanne', 'daneil@yahoo.com', '100 Atwater Street', 'Toronto', 'Ontario', 'Canada');
INSERT INTO Customers (firstName, lastName, email, streetAddress, city, province, country) 
VALUES ('martin', 'alexandre', 'marting@yahoo.com', NULL, 'Brossard', 'Quebec', 'Canada');
INSERT INTO Customers (firstName, lastName, email, streetAddress, city, province, country) 
VALUES ('John', 'boura', 'bdoura@gmail.com', '100 Young Street', 'Toronto', 'Ontario', 'Canada');
INSERT INTO Customers (firstName, lastName, email, streetAddress, city, province, country) 
VALUES ('Ari', 'brown', 'b.a@gmail.com', NULL, NULL, NULL, NULL);
INSERT INTO Customers (firstName, lastName, email, streetAddress, city, province, country) 
VALUES ('Amanda', 'Harry', 'am.harry@yahoo.com', '100 boul saint laurent', 'Montreal', 'Quebec', 'Canada');
INSERT INTO Customers (firstName, lastName, email, streetAddress, city, province, country) 
VALUES ('Jack', 'Johnson', 'johnson.a@gmail.com', NULL, 'Calgary', 'Alberta', 'Canada');
INSERT INTO Customers (firstName, lastName, email, streetAddress, city, province, country) 
VALUES ('John', 'belle', 'abcd@yahoo.com', '105 Young Street', 'Toronto', 'Ontario', 'Canada');
INSERT INTO Customers (firstName, lastName, email, streetAddress, city, province, country) 
VALUES ('martin', 'Li', 'm.li@gmail.com', '87 boul saint laurent', 'Montreal', 'Quebec', 'Canada');
INSERT INTO Customers (firstName, lastName, email, streetAddress, city, province, country) 
VALUES ('olivia', 'smith', 'smith@hotmail.com', '76 boul decalthon', 'Laval', 'Quebec', 'Canada');
INSERT INTO Customers (firstName, lastName, email, streetAddress, city, province, country) 
VALUES ('Noah', 'Garcia', 'g.noah@yahoo.com', '22222 Happy Street', 'Laval', 'Quebec', 'Canada');
INSERT INTO Customers (firstName, lastName, email, streetAddress, city, province, country) 
VALUES ('mahsa', 'sadeghi', 'ms@gmail.com', '104 Gill Street', 'Toronto', 'Ontario', 'Canada');

INSERT INTO Orders (customerID, storeID, orderDate) 
VALUES (1, 1, TO_DATE('4/21/2023', 'MM/DD/YYYY'));
INSERT INTO Orders (customerID, storeID, orderDate) 
VALUES (2, 2, TO_DATE('10/23/2023', 'MM/DD/YYYY'));
INSERT INTO Orders (customerID, storeID, orderDate) 
VALUES (3, 3, TO_DATE('10/1/2023', 'MM/DD/YYYY'));
INSERT INTO Orders (customerID, storeID, orderDate) 
VALUES (4, 4, TO_DATE('10/23/2023', 'MM/DD/YYYY'));
INSERT INTO Orders (customerID, storeID, orderDate) 
VALUES (2, 5, TO_DATE('10/23/2023', 'MM/DD/YYYY'));
INSERT INTO Orders (customerID, storeID, orderDate) 
VALUES (3, 6, TO_DATE('10/10/2023', 'MM/DD/YYYY'));
INSERT INTO Orders (customerID, storeID, orderDate) 
VALUES (1, 7, TO_DATE('10/11/2023', 'MM/DD/YYYY'));
INSERT INTO Orders (customerID, storeID, orderDate) 
VALUES (6, 8, NULL);
INSERT INTO Orders (customerID, storeID, orderDate) 
VALUES (7, 9, NULL);
INSERT INTO Orders (customerID, storeID, orderDate) 
VALUES (8, 10, NULL);
INSERT INTO Orders (customerID, storeID, orderDate) 
VALUES (9, 2, TO_DATE('5/6/2020', 'MM/DD/YYYY'));
INSERT INTO Orders (customerID, storeID, orderDate) 
VALUES (3, 6, TO_DATE('9/12/2019', 'MM/DD/YYYY'));
INSERT INTO Orders (customerID, storeID, orderDate) 
VALUES (1, 7, TO_DATE('10/11/2010', 'MM/DD/YYYY'));
INSERT INTO Orders (customerID, storeID, orderDate) 
VALUES (1, 2, TO_DATE('5/6/2022', 'MM/DD/YYYY'));
INSERT INTO Orders (customerID, storeID, orderDate) 
VALUES (14, 7, TO_DATE('10/7/2023', 'MM/DD/YYYY'));
INSERT INTO Orders (customerID, storeID, orderDate) 
VALUES (10, 8, TO_DATE('8/10/2023', 'MM/DD/YYYY'));
INSERT INTO Orders (customerID, storeID, orderDate) 
VALUES (2, 5, TO_DATE('10/23/2023', 'MM/DD/YYYY'));
INSERT INTO Orders (customerID, storeID, orderDate) 
VALUES (2, 7, TO_DATE('10/2/2023', 'MM/DD/YYYY'));
INSERT INTO Orders (customerID, storeID, orderDate) 
VALUES (11, 1, TO_DATE('4/3/2019', 'MM/DD/YYYY'));
INSERT INTO Orders (customerID, storeID, orderDate) 
VALUES (12, 2, TO_DATE('12/29/2021', 'MM/DD/YYYY'));
INSERT INTO Orders (customerID, storeID, orderDate) 
VALUES (13, 11, TO_DATE('1/20/2020', 'MM/DD/YYYY'));
INSERT INTO Orders (customerID, storeID, orderDate) 
VALUES (1, 7, TO_DATE('10/11/2022', 'MM/DD/YYYY'));
INSERT INTO Orders (customerID, storeID, orderDate) 
VALUES (12, 4, TO_DATE('12/29/2021', 'MM/DD/YYYY'));

INSERT INTO Orders_Products (orderID, productID, quantity) VALUES (1, 1, 1);
INSERT INTO Orders_Products (orderID, productID, quantity) VALUES (2, 2, 2);
INSERT INTO Orders_Products (orderID, productID, quantity) VALUES (3, 3, 3);
INSERT INTO Orders_Products (orderID, productID, quantity) VALUES (4, 4, 1);
INSERT INTO Orders_Products (orderID, productID, quantity) VALUES (5, 5, 1);
INSERT INTO Orders_Products (orderID, productID, quantity) VALUES (6, 6, 1);
INSERT INTO Orders_Products (orderID, productID, quantity) VALUES (7, 7, 1);
INSERT INTO Orders_Products (orderID, productID, quantity) VALUES (8, 8, 1);
INSERT INTO Orders_Products (orderID, productID, quantity) VALUES (9, 9, 1);
INSERT INTO Orders_Products (orderID, productID, quantity) VALUES (10, 10, 3);
INSERT INTO Orders_Products (orderID, productID, quantity) VALUES (11, 11, 6);
INSERT INTO Orders_Products (orderID, productID, quantity) VALUES (12, 6, 3);
INSERT INTO Orders_Products (orderID, productID, quantity) VALUES (13, 12, 1);
INSERT INTO Orders_Products (orderID, productID, quantity) VALUES (14, 11, 7);
INSERT INTO Orders_Products (orderID, productID, quantity) VALUES (15, 12, 2);
INSERT INTO Orders_Products (orderID, productID, quantity) VALUES (16, 8, 1);
INSERT INTO Orders_Products (orderID, productID, quantity) VALUES (17, 3, 1);
INSERT INTO Orders_Products (orderID, productID, quantity) VALUES (18, 5, 1);
INSERT INTO Orders_Products (orderID, productID, quantity) VALUES (19, 13, 1);
INSERT INTO Orders_Products (orderID, productID, quantity) VALUES (20, 15, 3);
INSERT INTO Orders_Products (orderID, productID, quantity) VALUES (21, 14, 1);
INSERT INTO Orders_Products (orderID, productID, quantity) VALUES (22, 7, 1);
INSERT INTO Orders_Products (orderID, productID, quantity) VALUES (23, 15, 3);

INSERT INTO Reviews (star, flagNums, description, productID, customerID)
VALUES (4, 0, 'It was affordable.', 1, 1);
INSERT INTO Reviews (star, flagNums, description, productID, customerID)
VALUES (3, 0, 'Quality was not good', 2, 2);
INSERT INTO Reviews (star, flagNums, description, productID, customerID)
VALUES (2, 1, NULL, 3, 3);
INSERT INTO Reviews (star, flagNums, description, productID, customerID)
VALUES (5, 0, 'Highly recommend', 4, 4);
INSERT INTO Reviews (star, flagNums, description, productID, customerID)
VALUES (1, 0, NULL, 5, 2);
INSERT INTO Reviews (star, flagNums, description, productID, customerID)
VALUES (1, 0, 'Did not worth the price',6, 3);
INSERT INTO Reviews (star, flagNums, description, productID, customerID)
VALUES (1, 0, 'Missing some parts',7, 1);
INSERT INTO Reviews (star, flagNums, description, productID, customerID)
VALUES (5, 1, 'Trash', 8, 6);
INSERT INTO Reviews (star, flagNums, description, productID, customerID)
VALUES (2, 0, NULL,9, 7);
INSERT INTO Reviews (star, flagNums, description, productID, customerID)
VALUES (5, 0, NULL,10, 8);
INSERT INTO Reviews (star, flagNums, description, productID, customerID)
VALUES (4, 0, NULL,11, 9);
INSERT INTO Reviews (star, flagNums, description, productID, customerID)
VALUES (3, 0, NULL, 6, 3);
INSERT INTO Reviews (star, flagNums, description, productID, customerID)
VALUES (1, 0, 'Missing some parts', 12, 1);
INSERT INTO Reviews (star, flagNums, description, productID, customerID)
VALUES (4, 0, NULL,11, 1);
INSERT INTO Reviews (star, flagNums, description, productID, customerID)
VALUES (1, 0, 'Great product', 12, 14);
INSERT INTO Reviews (star, flagNums, description, productID, customerID)
VALUES (5, 1, 'Bad quality', 8, 10);
INSERT INTO Reviews (star, flagNums, description, productID, customerID)
VALUES (1, 0, NULL, 3, 2);
INSERT INTO Reviews (star, flagNums, description, productID, customerID)
VALUES (4, 0, NULL, 5, 2);
INSERT INTO Reviews (star, flagNums, description, productID, customerID)
VALUES (4, 0, NULL, 13, 11);
INSERT INTO Reviews (star, flagNums, description, productID, customerID)
VALUES (5, 0, NULL, 15, 12);
INSERT INTO Reviews (star, flagNums, description, productID, customerID)
VALUES (1, 2, 'Worse car I have driven!', 7, 13);
INSERT INTO Reviews (star, flagNums, description, productID, customerID)
VALUES (4, 0, NULL, 15, 12);


/
/**BIANCA**/


CREATE OR REPLACE TYPE order_type AS OBJECT(
    customerID      NUMBER(2),
    storeID         NUMBER(2),
    orderDate       DATE
);
/

CREATE OR REPLACE TYPE review_type AS OBJECT(
    productID       NUMBER(2),
    customerID      NUMBER(2),
    star            NUMBER(1),
    flagnums        NUMBER(1),
    description     VARCHAR2(100)
);
/

CREATE OR REPLACE TYPE customer_type AS OBJECT(
    firstname   VARCHAR2(40),
    lastname    VARCHAR2(20),
    email       VARCHAR2(30),
    streetAddress   VARCHAR2(40),
    city            VARCHAR2(40),
    province        VARCHAR2(40),
    country         VARCHAR2(40)
    );
/

CREATE OR REPLACE PACKAGE viewCustomerMenu
AS
    TYPE cursor_table IS REF CURSOR;
    TYPE cus_names IS TABLE OF VARCHAR(100)
    INDEX BY PLS_INTEGER;
    FUNCTION flaggedCustomers RETURN cus_names;
    PROCEDURE viewCustomers(cursor_c IN OUT cursor_table);
END viewCustomerMenu;
/

CREATE OR REPLACE PACKAGE BODY viewCustomerMenu
AS
    /* this function returns all the information for customers 
    in the form of a cursor*/
    PROCEDURE viewCustomers(cursor_c IN OUT cursor_table)
    AS
    BEGIN
        OPEN cursor_c FOR
            SELECT * FROM Customers;
    END;
    
    /*this function returns the names of the flagged customers*/
    FUNCTION flaggedCustomers
    RETURN cus_names
    AS 
        customers cus_names;
    BEGIN
        SELECT
            firstName || ' ' || lastName BULK COLLECT INTO customers
        FROM
            Customers INNER JOIN Reviews
            USING(customerID)
        WHERE
            flagNums IS NOT NULL AND
            flagNums > 0;
        RETURN(customers);
    END;
END viewCustomerMenu;
/


CREATE OR REPLACE PACKAGE viewReviewProdMenu
AS
    TYPE cursor_table IS REF CURSOR;
    PROCEDURE viewReviews(cursor_c IN OUT cursor_table);
    FUNCTION calculateAvgReviewScore(fproductID Reviews.productid%TYPE) 
    RETURN NUMBER;
    PROCEDURE viewProducts (cursor_view IN OUT cursor_table);
    PROCEDURE viewProductsbyCategory (cursor_view IN OUT cursor_table, categoryName VARCHAR2);
    PROCEDURE viewStores (cursor_view IN OUT cursor_table);
    PROCEDURE viewWarehouses(cursor_c IN OUT cursor_table);
END viewReviewProdMenu;
/

CREATE OR REPLACE PACKAGE BODY viewReviewProdMenu
AS
    /* this function returns all the information for reviews 
    in the form of a cursor*/
    PROCEDURE viewReviews(cursor_c IN OUT cursor_table)
    AS
    BEGIN
        OPEN cursor_c FOR
            SELECT * FROM Reviews;
    END;
    
    /**
    *This function calculates the average score of a product.
    *returns the average
    */
    FUNCTION calculateAvgReviewScore(fproductID Reviews.productid%TYPE) RETURN NUMBER
    AS
        averageScore NUMBER(4,2);
    BEGIN
        SELECT AVG(star) INTO averageScore 
            FROM Reviews 
            WHERE productid = fproductID;
        
        RETURN averageScore;
    
    EXCEPTION 
        WHEN OTHERS THEN 
            dbms_output.put_line('something went wrong' || SQLERRM);
            RAISE;
    END;
        
    PROCEDURE viewProducts (cursor_view IN OUT cursor_table)
    AS
    BEGIN
        OPEN cursor_view FOR
            SELECT * FROM Products;
    END;
    
    PROCEDURE viewProductsbyCategory (cursor_view IN OUT cursor_table, categoryName VARCHAR2)
    AS
    BEGIN
        OPEN cursor_view FOR
            SELECT * FROM Products WHERE category = categoryName;
    END;
    
    PROCEDURE viewStores (cursor_view IN OUT cursor_table)
    AS
    BEGIN 
        OPEN cursor_view FOR
            SELECT * FROM Stores;
    END;
    
    PROCEDURE viewWarehouses(cursor_c IN OUT cursor_table)
    AS
    BEGIN
        OPEN cursor_c FOR
            SELECT * FROM Warehouses;
    END;
    
END viewReviewProdMenu;
/

CREATE OR REPLACE PACKAGE viewOrderMenu
AS
    FUNCTION totalInventory(productIDsearch Products.productID%TYPE)
    RETURN NUMBER;
    FUNCTION numOrders(fproductID Products.productid%TYPE) RETURN NUMBER;
    TYPE cursor_table IS REF CURSOR; 
    PROCEDURE viewOrders (cursor_view IN OUT cursor_table);
END viewOrderMenu;
/

CREATE OR REPLACE PACKAGE BODY viewOrderMenu
AS
    /* this function taks a productid as input and will calculate the 
    total inventory for that product across all tables */
    FUNCTION totalInventory(productIDsearch Products.productID%TYPE)
    RETURN NUMBER
    AS
        totalnum NUMBER(10);
    BEGIN
        SELECT
            SUM(quantity) INTO totalNum
        FROM
            Warehouses_Products
        WHERE
            productId = productIDsearch;
        RETURN(totalNum);
    END;
    
    /**
    *counts how many orders has been placed on a specific product 
    */
    FUNCTION numOrders(fproductID Products.productid%TYPE) RETURN NUMBER 
    AS
        results NUMBER(3);
    BEGIN 
        SELECT COUNT(orderid) INTO results 
            FROM Orders_products
            WHERE productid = fproductID;
    
        RETURN results;
    EXCEPTION
        WHEN OTHERS THEN
            dbms_output.put_line('something went wrong');
            RAISE;
    END;
    
    PROCEDURE viewOrders (cursor_view IN OUT cursor_table)
    AS 
    BEGIN
        OPEN cursor_view FOR
            SELECT * FROM Orders;
    END;

END viewOrderMenu;
/

CREATE OR REPLACE PACKAGE dataManipulation
AS
    PROCEDURE createOrder(
    orderObj IN order_type, 
    newOrderID OUT Orders.orderID%TYPE);
    PROCEDURE addOrderItem(
    newOrderID IN Orders_Products.orderID%TYPE,
    newProductID IN Orders_Products.productID%TYPE,
    newQuantity IN OUT Orders_Products.quantity%TYPE);
    PROCEDURE removeProduct(oldProductID IN Products.productID%TYPE);
    PROCEDURE newDeliveryIncome(
    newProductID IN Products.productID%TYPE,
    newWarehouseID IN Warehouses.warehouseID%TYPE,
    newQuantity IN Warehouses_Products.quantity%TYPE);
    PROCEDURE flagReview(pReviewID Reviews.reviewid%TYPE);
    PROCEDURE createReview(reviewObj IN review_type);
    PROCEDURE addCustomer(customer IN customer_type);
    PROCEDURE removeWarehouse(pWarehouseID Warehouses.warehouseid%TYPE);
END dataManipulation;
/

CREATE OR REPLACE PACKAGE BODY dataManipulation
AS
    /* createOrder (orderID OUT, storeID, cusID): function that takes as input storeID and customerID */
    PROCEDURE createOrder(
    orderObj IN order_type, 
    newOrderID OUT Orders.orderID%TYPE)
    AS
    BEGIN
        
        INSERT INTO Orders (customerID, storeID, orderDate)
        VALUES (orderObj.customerID, orderObj.storeID, CURRENT_DATE)
        RETURNING orderID INTO newOrderID;
    END;
    
    /* addOrderItem(orderID -> from CreateOrder, prodID, quantity ): in jdbc, use a loop to call addOrderItem for every product they add to their order 
    this procedure will add a new order item into the database */
    PROCEDURE addOrderItem(
        newOrderID IN Orders_Products.orderID%TYPE,
        newProductID IN Orders_Products.productID%TYPE,
        newQuantity IN OUT Orders_Products.quantity%TYPE)
    AS
        numTotalProducts NUMBER;
    BEGIN
        FOR warehouse IN (SELECT * FROM Warehouses_Products WHERE productID = newProductID) LOOP
            IF warehouse.quantity >= newQuantity THEN
                UPDATE Warehouses_Products SET quantity = warehouse.quantity - newQuantity 
                WHERE warehouseID = warehouse.warehouseID;
                CONTINUE;
            ELSIF warehouse.quantity < newQuantity THEN
                newQuantity := newQuantity - warehouse.quantity;
                UPDATE Warehouses_Products SET quantity = 0 
                WHERE warehouseID = warehouse.warehouseID;
            END IF;
        END LOOP;
        INSERT INTO Orders_Products
        VALUES(newOrderID, newProductID, newQuantity);
    EXCEPTION
        WHEN OTHERS THEN
            dbms_output.put_line('Something went wrong, see block' || SQLERRM);
            RAISE;
    END;
    
    /* this procedure takes an old productID and will remove it from the database */
    PROCEDURE removeProduct(oldProductID IN Products.productID%TYPE)
    AS
    BEGIN
       DELETE FROM Products WHERE productID = oldProductID; 
    END;
    
    /* this procedure will take all necessary info to update the stock in a warehouse,
    simulating logging a new delivery to a warehouse */
    PROCEDURE newDeliveryIncome(
    newProductID IN Products.productID%TYPE,
    newWarehouseID IN Warehouses.warehouseID%TYPE,
    newQuantity IN Warehouses_Products.quantity%TYPE)
    AS
        oldQuantity NUMBER;
    BEGIN
        SELECT
            quantity INTO oldQuantity
        FROM
            Warehouses_Products
        WHERE
            productID = newProductID
            AND warehouseID = newWarehouseID;
        oldQuantity := oldQuantity + newQuantity;
        UPDATE Warehouses_Products SET quantity = oldQuantity 
        WHERE productID = newProductID AND warehouseID = newWarehouseID;
    EXCEPTION
        WHEN OTHERS THEN
            dbms_output.put_line('something went wrong');
    END;

    /**
    *Takes the reviewID, adds  1  to the current flagNums 
    *Valdation: if flagNum >=2, delete row with that reviewID
    *
    **/
    PROCEDURE flagReview(pReviewID Reviews.reviewid%TYPE) 
    AS
        oldFlagNum NUMBER(2) := 0;
    BEGIN
        SELECT flagnums INTO oldFlagNum
        FROM Reviews
        WHERE reviewid = pReviewID;
        
        oldFlagNum := oldFlagNum + 1;
        
        IF (oldFlagNum >= 2) THEN
            DELETE FROM Reviews WHERE reviewid = pReviewID;
        ELSE
            UPDATE Reviews SET flagnums = oldFlagNum
            WHERE reviewid = pReviewID;
        END IF;
        
    EXCEPTION 
        WHEN OTHERS THEN 
            dbms_output.put_line('something went wrong' || SQLERRM);
        RAISE;
        
    END;
    
    /**
    *This procedure adds a review into the Reviews table
    */
    PROCEDURE createReview(reviewObj IN review_type)
        AS
        
        BEGIN
            INSERT INTO Reviews (productid, customerid, star, flagnums, description)
                VALUES (reviewObj.productID, reviewObj.customerID, reviewObj.star, 0, reviewObj.description);
        
        EXCEPTION 
            WHEN OTHERS THEN 
                dbms_output.put_line('something went wrong');
                RAISE;
    END;
    
    PROCEDURE addCustomer(customer IN customer_type)
    AS  
        customerExists NUMBER(2) :=0;
        e_customer_exists EXCEPTION;
        PRAGMA EXCEPTION_INIT (e_customer_exists, -20002);
    BEGIN
        SELECT COUNT(customerid) INTO customerExists
            FROM Customers
            WHERE email = customer.email;
    IF(customerExists >0) THEN
        raise_application_error(-20002, 'customer already exists! Aborting..');
    END IF;
    
    INSERT INTO Customers(firstname, lastname, email, streetAddress, city, province, country)
        VALUES (customer.firstname, customer.lastname, customer.email, customer.streetAddress, customer.city, customer.province, customer.country);
    EXCEPTION
        WHEN OTHERS THEN 
            dbms_output.put_line('something went wrong');
            RAISE;
    END;
    
     
    /**
    *This procedure deletes a warehouse when taken in a warehouseid 
    */
    PROCEDURE removeWarehouse(pWarehouseID Warehouses.warehouseid%TYPE)
    AS
    
    BEGIN
        DELETE FROM Warehouses WHERE warehouseid = pWarehouseID;
        
    EXCEPTION
        WHEN OTHERS THEN
            dbms_output.put_line('something went wrong');
            RAISE;
    END;
    
END dataManipulation;
/


/** getting table IDs **/

CREATE OR REPLACE PACKAGE validatingIDs 
AS
    FUNCTION getProductIDs (fProductID Products.productid%TYPE)RETURN NUMBER;
    FUNCTION getCustomerIDs (fCustomerID Customers.customerid%TYPE) RETURN NUMBER;
    FUNCTION getWarehouseIDs (fWarehouseID Warehouses.warehouseid%TYPE) RETURN NUMBER;
    FUNCTION getStoreIDs (fStoreID Stores.storeid%TYPE) RETURN NUMBER;
    FUNCTION getReviewIDs (fReviewID Reviews.reviewid%TYPE) RETURN NUMBER;

END validatingIDs;
/
CREATE OR REPLACE PACKAGE BODY validatingIDs
AS
    /** this function will return a list containing all valid product IDs **/
    FUNCTION getProductIDs (fProductID Products.productid%TYPE)RETURN NUMBER
        AS
            idCounts    NUMBER(2) := 0;
        BEGIN
            SELECT COUNT(productid) INTO idCounts 
                FROM Products
                WHERE productid = fProductID;
            
            IF (idCounts > 0) THEN 
                RETURN 1;
            ELSE 
                RETURN 0;
            END IF;
            
        EXCEPTION
            WHEN OTHERS THEN 
                dbms_output.put_line('something went wrong');
                RAISE;
    END;

    /** this function will return a list containing all valid customer IDs **/
    FUNCTION getCustomerIDs (fCustomerID Customers.customerid%TYPE) RETURN NUMBER
    AS
        idCounts    NUMBER(2) := 0;
    BEGIN
        SELECT COUNT(customerid) INTO idCounts
            FROM Customers
            WHERE customerid = fCustomerID;
    
            IF (idCounts > 0) THEN 
                RETURN 1;
            ELSE 
                RETURN 0;
            END IF;
            
        EXCEPTION
            WHEN OTHERS THEN 
                dbms_output.put_line('something went wrong');
                RAISE;
    END;

    
    FUNCTION getWarehouseIDs (fWarehouseID Warehouses.warehouseid%TYPE) RETURN NUMBER
    AS
        idCounts    NUMBER(2) := 0;
    BEGIN
        SELECT COUNT(warehouseid) INTO idCounts
            FROM Warehouses
            WHERE warehouseid = fWarehouseID;
    
            IF (idCounts > 0) THEN 
                RETURN 1;
            ELSE 
                RETURN 0;
            END IF;
            
        EXCEPTION
            WHEN OTHERS THEN 
                dbms_output.put_line('something went wrong');
                RAISE;
    END;


    FUNCTION getStoreIDs (fStoreID Stores.storeid%TYPE) RETURN NUMBER
    AS
        idCounts    NUMBER(2) := 0;
    BEGIN
        SELECT COUNT(storeid) INTO idCounts
            FROM Stores
            WHERE storeid = fStoreID;
    
            IF (idCounts > 0) THEN 
                RETURN 1;
            ELSE 
                RETURN 0;
            END IF;
            
        EXCEPTION
            WHEN OTHERS THEN 
                dbms_output.put_line('something went wrong');
                RAISE;
    END;

    FUNCTION getReviewIDs (fReviewID Reviews.reviewid%TYPE) RETURN NUMBER
    AS
        idCounts    NUMBER(2) := 0;
    BEGIN
        SELECT COUNT(reviewid) INTO idCounts
            FROM Reviews
            WHERE reviewid = fReviewID;
    
            IF (idCounts > 0) THEN 
                RETURN 1;
            ELSE 
                RETURN 0;
            END IF;
            
        EXCEPTION
            WHEN OTHERS THEN 
                dbms_output.put_line('something went wrong');
                RAISE;
    END;

END validatingIDs;
/
/**
*This procedure logs the user login with their username and the data that they logged in at
*/
CREATE OR REPLACE PROCEDURE logUserLogin(username Loglogins.username%TYPE)
    AS
    
    BEGIN
        INSERT INTO LogLogins
            VALUES (username, CURRENT_DATE);
    EXCEPTION
        WHEN OTHERS THEN
            dbms_output.put_line('something went wrong');
            RAISE;
    END;
/

CREATE OR REPLACE PROCEDURE checkIfOrderStillValid(newOrderID IN Orders.orderID%TYPE)
AS
    numOrders NUMBER(2);
BEGIN
    SELECT
        COUNT(*) INTO numOrders
    FROM
        Orders_Products
    WHERE
        orderID = newOrderID;
    IF numOrders IS NULL OR numOrders = 0 THEN
        DELETE FROM Orders WHERE orderID = newOrderID;
    END IF;
END;
/

/* TRIGGERS */

/* this trigger logs any orders that have been added into the LogOrderModification table */
CREATE TRIGGER  After_OrderModification_Insert
AFTER INSERT
ON Orders
FOR EACH ROW
BEGIN
    INSERT INTO LogOrderModification 
    VALUES(:NEW.orderID, :NEW.CustomerID, :NEW.StoreID, CURRENT_DATE, 'INSERT');
END;
/

/* this trigger logs any reviews that have been added or deleted into the LogReviewModification table */
CREATE OR REPLACE TRIGGER  Before_Review_DeleteInsert
BEFORE DELETE OR INSERT
ON Reviews
FOR EACH ROW
BEGIN
    IF DELETING THEN
        INSERT INTO LogReviewModification 
        VALUES(:OLD.reviewID, :OLD.ProductID, :OLD.CustomerID, :OLD.star, :OLD.flagNums, :OLD.description, CURRENT_DATE, 'DELETE');
    ELSIF INSERTING THEN
        INSERT INTO LogReviewModification 
        VALUES(:NEW.reviewID, :NEW.ProductID, :NEW.CustomerID, :NEW.star, :NEW.flagNums, :NEW.description, CURRENT_DATE, 'INSERT');
    END IF;
END;
/

--this trigger logs in when a stock is added or deducted.
CREATE OR REPLACE TRIGGER beforeModifyingWarehouse
BEFORE DELETE OR INSERT
ON Warehouses_products
FOR EACH ROW
BEGIN
    IF DELETING THEN
        INSERT INTO Logwarehousemodification
            VALUES (:OLD.warehouseid, :OLD.productid, :OLD.quantity, CURRENT_DATE, 'DELETED');
    ELSIF INSERTING THEN
        INSERT INTO Logwarehousemodification
            VALUES(:NEW.warehouseid, :NEW.productid, :NEW.quantity, CURRENT_DATE, 'INSERTED');
    END IF;
END;
/