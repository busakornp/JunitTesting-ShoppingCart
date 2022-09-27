package cart;

public class NotExistinCart extends RuntimeException {

    public NotExistinCart(String error) {
        super(error);

    }
}
