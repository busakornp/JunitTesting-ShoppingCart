package cart;

import java.util.ArrayList;

public interface PromotionInterface {
    public int getDiscount(Product product);
    public ArrayList<String> getDiscountMessages();
}
