package jdbcsuperstore;

public class Warehouse {
    private int warehouseID;
    private String name;
    private String streetAddress;
    private String city;
    private String province;
    private String country;


    public Warehouse(int warehouseID, String name, String streetAddress, String city, String province, String country) {
        this.warehouseID = warehouseID;
        this.name = name;
        this.streetAddress = streetAddress;
        this.city = city;
        this.province = province;
        this.country = country;
    }

    @Override
    public String toString() {
        return
            " Warehouse ID: " + this.warehouseID +
            ", Name: " + this.name + "'" +
            ", Street address" + this.streetAddress + 
            ", City: " + this.city +
            ", Province: " + this.province + 
            ", Country: " + this.country;
    }
    
}
