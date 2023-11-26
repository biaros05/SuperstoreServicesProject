package jdbcsuperstore;

import java.sql.*;
import java.util.*;

public class Customer implements SQLData {
    private int customerid;
    private String firstName;
    private String lastName;
    private String email;
    private String streetAddress;
    private String city;
    private String province;
    private String country;
    private String sql_type = "CUSTOMER_TYPE";

    public Customer(String firstName, String lastName, String email, String streetAddress, String city, String province,
            String country, Connection conn) throws SQLException, ClassNotFoundException {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.streetAddress = streetAddress;
        this.city = city;
        this.province = province;
        this.country = country;

        Map map = conn.getTypeMap();
        conn.setTypeMap(map);
        map.put(this.sql_type, Class.forName("jdbcsuperstore.Customer"));
    }

    public Customer(int customerID, String firstName, String lastName, String email, String streetAddress, String city,
            String province,
            String country) {
        this.customerid = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.streetAddress = streetAddress;
        this.city = city;
        this.province = province;
        this.country = country;
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeString(this.firstName);
        stream.writeString(this.lastName);
        stream.writeString(this.email);
        stream.writeString(this.streetAddress);
        stream.writeString(this.city);
        stream.writeString(this.province);
        stream.writeString(this.country);
    }

    @Override
    public void readSQL(SQLInput stream, String type) throws SQLException {
        this.firstName = stream.readString();
        this.lastName = stream.readString();
        this.email = stream.readString();
        this.streetAddress = stream.readString();
        this.city = stream.readString();
        this.province = stream.readString();
        this.country = stream.readString();
    }

    @Override
    public String getSQLTypeName() {
        return this.sql_type;
    }

    public String toString() {
        return "customerID: " + this.customerid + ", Name: " + this.firstName + " " + this.lastName + ", Email: "
                + this.email +
                ", Address: " + this.streetAddress + ", " + this.city + ", " + this.province + ", " + this.country;
    }
}
