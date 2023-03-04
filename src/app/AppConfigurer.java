package app;

import app.product.ProductRepository;

public class AppConfigurer {
    Cart cart = new Cart(productRepository(), menu());
    public ProductRepository productRepository() {
        return new ProductRepository();
    }

    public Menu menu() {
        return new Menu(productRepository().getAllProducts());
    }

    public Cart cart() {
        return cart;
    }

    public Order order() {
        return new Order(cart);
    }
}
