package cart;

public class Product implements Comparable<Product> {

    private String productName;
    private int quantity;
    private int totalPrice;
    double discount;
    String coupon;

    public Product(String productName, int quantity, int totalPrice) {
        this.productName = productName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.discount = 0;
        this.coupon = "";
    }
    public String getProductName() {
        return productName;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name ='" + productName + '\'' +
                ", quantity = '" + quantity + '\'' +
                ", price = " + totalPrice +
                '}';
    }

    @Override
    public int compareTo(Product that) {
        return this.totalPrice - that.totalPrice;
    }

//    public void setTotalPrice(int totalPrice) {
//        this.totalPrice = totalPrice;
//    }
//
//    public void setProductName(String productName) {
//        this.productName = productName;
//    }
//
//    public int getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//    }
}


