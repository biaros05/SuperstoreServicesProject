package jdbcsuperstore;

public class Store {
    private int storeid;
    private String name;

    public Store(int storeid, String name) {
        this.storeid = storeid;
        this.name = name;
    }

    @Override
    public String toString() {
        return "StoreID: " + this.storeid + ", Store Name: " + this.name;
    }
}
