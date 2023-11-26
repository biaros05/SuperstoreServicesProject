package jdbcsuperstore;

import java.sql.*;
import java.util.*;

public class Review implements SQLData {
    private int reviewID;
    private int productID;
    private int customerID;
    private double star;
    private int flagnums;
    private String description;
    private String sql_type = "REVIEW_TYPE";

    public Review(int productID, int customerID, int star, String description, Connection conn)
            throws SQLException, ClassNotFoundException {

        if (star < 0.5 || star > 5.0) {
            throw new IllegalArgumentException("Star needs to be above 0.5 and below 5");
        }
        this.productID = productID;
        this.customerID = customerID;
        this.star = star;
        this.flagnums = 0;
        this.description = description;

        Map map = conn.getTypeMap();
        conn.setTypeMap(map);
        map.put(this.sql_type, Class.forName("jdbcsuperstore.Review"));
    }

    public Review(int reviewID, int productID, int customerID, double star, int flagnums, String description) {
        this.reviewID = reviewID;
        this.productID = productID;
        this.customerID = customerID;
        this.star = star;
        this.flagnums = flagnums;
        this.description = description;
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeInt(this.productID);
        stream.writeInt(this.customerID);
        stream.writeDouble(this.star);
        stream.writeInt(this.flagnums);
        stream.writeString(this.description);
    }

    @Override
    public void readSQL(SQLInput stream, String type) throws SQLException {
        this.productID = stream.readInt();
        this.customerID = stream.readInt();
        this.star = stream.readDouble();
        this.flagnums = stream.readInt();
        this.description = stream.readString();
    }

    @Override
    public String getSQLTypeName() {
        return this.sql_type;
    }

    public String toString()
    {
        return("Review ID: " + this.reviewID +
        ", ProductID: " + this.productID +
        ", CustomerID: " + this.customerID +
        ", Star Rating: " + this.star +
        ", Flags: " + this.flagnums + 
        (this.description != null ? ", Description: " + this.description : ""));
    }
}