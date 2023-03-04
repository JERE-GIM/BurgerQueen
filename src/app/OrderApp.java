package app;

import app.product.ProductRepository;

import java.util.Scanner;

public class OrderApp {
    ProductRepository productRepository;
    Menu menu;
    Cart cart;
    Order order;

    public OrderApp(ProductRepository productRepository, Menu menu, Cart cart, Order order) {
        this.productRepository = productRepository;
        this.menu = menu;
        this.cart = cart;
        this.order = order;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("🍔 BurgerQueen Order Service");

        while (true) {
            menu.printMenu();
            String input = scanner.nextLine();

            if (input.equals("+")) {
                order.makeOrder();
                break;
            } else {
                int menuNumber = Integer.parseInt(input);

                if (menuNumber == 0) cart.printCart();
                else if (1 <= menuNumber && menuNumber <= productRepository.getAllProducts().length)
                    cart.addToCart(menuNumber);
            }

        }
    }
}
