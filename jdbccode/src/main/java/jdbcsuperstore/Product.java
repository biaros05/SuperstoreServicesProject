package jdbcsuperstore;

public class Product {
    private int productid;
    private String name;
    private String category;

    public Product(int productid, String name, String category) {
        this.productid = productid;
        this.name = name;
        this.category = category;
    }

    @Override
    public String toString() {
        return "ProductID: " + this.productid + ", Name: " + this.name + ", Category: " + this.category;
    }
}
