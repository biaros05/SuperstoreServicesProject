package jdbcsuperstore;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class Order implements SQLData {
    private int orderID;
    private int customerID;
    private int storeID;
    private java.sql.Date date;
    private String sql_type = "ORDER_TYPE";

    public Order(int customerID, int storeID, Connection conn)
            throws SQLException, ClassNotFoundException {
        this.customerID = customerID;
        this.storeID = storeID;
        Map map = conn.getTypeMap();
        conn.setTypeMap(map);
        map.put(this.sql_type, Class.forName("jdbcsuperstore.Order"));
    }

    public Order(int orderID, int customerID, int storeID, java.sql.Date date) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.storeID = storeID;
        this.date = date;
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeInt(this.customerID);
        stream.writeInt(this.storeID);
        stream.writeDate(this.date);
    }

    @Override
    public void readSQL(SQLInput stream, String type) throws SQLException {
        this.customerID = stream.readInt();
        this.storeID = stream.readInt();
        this.date = stream.readDate();
    }

    @Override
    public String getSQLTypeName() {
        return sql_type; // this corresponds to the type name in SQL!!! (DB side)
    }

    @Override
    public String toString() {
        return "OrderID: " + this.orderID + ", CustomerID: " + this.customerID + ", storeID: " + this.storeID + ", orderDate: " + this.date;
    }

}
