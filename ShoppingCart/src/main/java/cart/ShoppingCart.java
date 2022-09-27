package cart;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ShoppingCart {
    private List<Product> productList = new ArrayList<>();
    private PromotionInterface promotion;
    private double totalCartValue;
    double discount;
    String coupon;


    public List<Product> productList() {
        return Collections.unmodifiableList(productList);
    }

    public void addProduct(Product... productListToAdd) {
        productList.addAll(Arrays.asList(productListToAdd));
        }

    //apply promotion
    public void setPromotion (PromotionInterface promotion) {
        this.promotion = promotion;
    }

    public List<Product> arrange() {
        return arrange(Comparator.naturalOrder());
    }

    public List<Product> arrange(Comparator<Product> criteria) {
        return productList.stream().sorted(criteria).collect(Collectors.toList());
    }



    //get product name
    public Product getProductName(String name) {
        if (productList.size() > 0) {
            for (Product product : productList) {
                if (product.getProductName().equals(name)) {
                    return product;
                }
            }
        }
        return null;
    }



    //get cart quantity
    public int getProductCount() {
        return productList.size();
    }

    //remove selected product from cart
    public void removeProduct(Product product) {
        productList.remove(product);
    }

    //get total cart value
    public double getTotalCartValue() {
        if (productList.size() > 0) {
            for (Product product : productList) {
                totalCartValue = totalCartValue + product.getTotalPrice();
            }
        }
        return totalCartValue;
    }

    public void applyCoupon(String coupon) {
        this.coupon = "";
        this.discount = 10;
        if (coupon.equals("DIS10")) {
            //this.discount = totalCartValue - 20;
            this.totalCartValue = this.totalCartValue - this.discount;
        } else {
            this.totalCartValue = this.totalCartValue;

        }
    }

    public List<Product> findProduct(String searchKey) {
        return findProduct(searchKey, b -> true);
    }

    public List<Product> findProduct(String productName, ProductFilter filter) {
        return productList.stream()
                .filter(product -> product.getProductName().contains(productName))
                .filter(product -> filter.apply(product))
                .collect(toList());
    }



}
