package cart;

import org.junit.Rule;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class CartSpec {
    private ShoppingCart cart;

    private Product Candle;
    private Product Toothpaste;
    private Product InstantCoffee;
    private Product RiceCooker;
    private Product FaceWash;
    @ExtendWith(LoggingTestExecutionExceptionHandler.class)
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeEach
    void initializeShelfWithDatabase() {

        cart = new ShoppingCart();

        Candle = new Product("Candle", 1, 159);
        Toothpaste = new Product("Toothpaste", 1, 35);
        InstantCoffee = new Product("Instant Coffee", 1, 78);
        RiceCooker = new Product("Rice Cooker", 1, 1389);
        FaceWash = new Product("Face Wash", 1, 39);

    }

    @Nested
    @DisplayName("is empty")
    class IsEmpty {
        @Test
        @DisplayName("create an empty cart with product count = 0")
        public void EmptyCart() {
            List<Product> productList = cart.productList();
            assertTrue(productList.isEmpty(), () -> "Cart should be empty.");
            assertEquals(0, cart.getProductCount());
        }
    }


    @Nested
    @DisplayName("add products")
    class AddProducts {
        @Test
        @DisplayName("add single product to cart")
        public void addSingleProductToCart() {
            cart.addProduct(Candle);
            assertEquals(1, cart.getProductCount());
            assertEquals(159.00, cart.getTotalCartValue());
        }

        @Test
        @DisplayName("add multiple products to cart")
        public void addMultipleProductsToCart() {
            cart.addProduct(Candle);
            cart.addProduct(FaceWash);
            assertEquals(2, cart.getProductCount());
            assertEquals(198.00, cart.getTotalCartValue(), 0.0);
        }
    }


    @Nested
    @DisplayName("remove products")
    class RemoveProducts {
        @Test
        @DisplayName("remove single product from cart")
        public void removeSingleProduct() {
            cart.addProduct(Candle);
            cart.addProduct(FaceWash);
            cart.removeProduct(Candle);
            assertEquals(1, cart.getProductCount());
            assertEquals(39, cart.getTotalCartValue(), 0.0);
        }

        @Test
        @DisplayName("remove multiple products from cart")
        public void removeMultipleProductsFromCart() {
            cart.addProduct(Candle);
            cart.addProduct(FaceWash);
            cart.removeProduct(Candle);
            cart.removeProduct(FaceWash);
            assertEquals(0, cart.getProductCount());
            assertEquals(0, cart.getTotalCartValue(), 0.0);
        }

//        @Test
//        void throwsExceptionWhenRemovingUnknownProductFromCart2() {
//            cart.addProduct(Candle);
//            try {
//                cart.removeProduct(Toothpaste);
//                fail(() -> "Should throw Not ExistInCart exception as selected product is not existed in cart");
//            } catch (NotExistinCart e) {
//                assertEquals("Product not found", e.getMessage());
//            }
//        }

        @Test
        @DisplayName("remove unknown product from cart")
        void throwsExceptionWhenRemovingUnknownProductFromCart() {
            cart.addProduct(Candle);
            expectedException.expect(NotExistinCart.class);
            expectedException.expectMessage("Product not found");
            cart.removeProduct(Toothpaste);
            }
        }



        @Nested
        @DisplayName("is arrange")
        class IsArrange {
            @Test
            @DisplayName("arrange cart items in an inserted order")
            void arrangeByInsertedOrder() {
                cart.addProduct(Candle, FaceWash, Toothpaste, InstantCoffee, RiceCooker);
                cart.arrange();
                List<Product> productList = cart.productList();
                assertEquals(Arrays.asList(Candle, FaceWash, Toothpaste, InstantCoffee, RiceCooker), productList, //expected value
                        () -> "Products in cart should be arranged in an inserted order."); //error when expected is not match
            }


            @Test
            @DisplayName("arrange cart items by price (low-high)")
            public void arrangeByPriceLowToHigh() {
                cart.addProduct(Candle, FaceWash, Toothpaste, InstantCoffee, RiceCooker);
                List<Product> productList = cart.arrange(Comparator.<Product>naturalOrder());
                assertEquals(
                        Arrays.asList(Toothpaste, FaceWash, InstantCoffee, Candle, RiceCooker), productList, //expected value
                        () -> "Products in cart should be arranged by price in ascending order"); //error when expected is not match
            }

            @Test
            @DisplayName("arrange cart items by price (high-low)")
            public void arrangeByPriceHighToLow() {
                cart.addProduct(Candle, FaceWash, Toothpaste, InstantCoffee, RiceCooker);
                List<Product> productList = cart.arrange(Comparator.<Product>naturalOrder().reversed());
                assertEquals(
                        Arrays.asList(RiceCooker, Candle, InstantCoffee, FaceWash, Toothpaste), productList, //expected value
                        () -> "Products in cart should be arranged by price in descending order"); //error when expected is not match


            }
        }

        @Nested
        @DisplayName("apply coupon")
        class applyCoupons {
            @Test
            @DisplayName("apply 10฿ discount coupon to cart")
            public void applyCouponToCart() {
                cart.addProduct(Candle);
                cart.addProduct(FaceWash);
                cart.applyCoupon("DIS10");
                assertEquals(2, cart.getProductCount());
                assertEquals(188.00, cart.getTotalCartValue());
            }

            @Test
            @DisplayName("no coupon apply to cart")
            public void noCouponToCart() {
                cart.addProduct(Candle);
                cart.addProduct(FaceWash);
                cart.applyCoupon("");
                assertEquals(2, cart.getProductCount());
                assertEquals(198.00, cart.getTotalCartValue());
            }
        }

    @Nested
    @DisplayName("search")
    class cartSearch {
        @Test
        @DisplayName(" should find product that contains the following letter")
        void findProducts() {
            cart.addProduct(Candle);
            cart.addProduct(FaceWash);
            List<Product> productList = cart.findProduct("Ca");
            assertThat(productList.size()).isEqualTo(1);
        }
    }

        @AfterEach
        void deleteShoppingCartFromDB() {
            System.out.println("<= Cart from DB Deleted =>");
        }

    }

