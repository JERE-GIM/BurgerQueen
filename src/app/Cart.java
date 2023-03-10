package app;

import app.product.Product;
import app.product.ProductRepository;
import app.product.subproduct.BurgerSet;
import app.product.subproduct.Drink;
import app.product.subproduct.Hamburger;
import app.product.subproduct.Side;

import java.util.ArrayList;
import java.util.Scanner;

public class Cart {
    ProductRepository productRepository;
    Menu menu;
    private ArrayList<Product> items = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    public Cart(ProductRepository productRepository, Menu menu) {
        this.productRepository = productRepository;
        this.menu = menu;
    }

    public void printCart() {
        System.out.println("๐ ์ฅ๋ฐ๊ตฌ๋");
        printCartItems();
        System.out.printf("ํฉ๊ณ : %d์%n", sumOfAllItems());
    }

    protected int sumOfAllItems() {
        int sum = 0;
        for (Product product : items) {
            sum += product.getPrice();
        }
        return sum;
    }
    protected void printCartItems() {
        System.out.println("-".repeat(50));
        for (Product product : items) {
            if (product instanceof BurgerSet) {
                BurgerSet burgerSet = (BurgerSet) product;
                System.out.printf(
                        "  %s %6d์ (%s(์ผ์ฒฉ %d๊ฐ), %s(๋นจ๋ %s))%n",
                        product.getName(),
                        product.getPrice(),
                        burgerSet.getSide().getName(),
                        burgerSet.getSide().getKetchup(),
                        burgerSet.getDrink().getName(),
                        burgerSet.getDrink().isWithStraw() ? "์์" : "์์");
            } else if (product instanceof Hamburger) {
                System.out.printf(
                        "  %-8s %6d์ (๋จํ)%n",
                        product.getName(),
                        product.getPrice());
            } else if (product instanceof Side) {
                System.out.printf(
                        "  %-8s %6d์ (์ผ์ฒฉ %d๊ฐ)%n",
                        product.getName(),
                        product.getPrice(),
                        ((Side) product).getKetchup());
            } else if (product instanceof Drink) {
                System.out.printf(
                        "  %-8s %6d์ (๋นจ๋ %s)%n",
                        product.getName(),
                        product.getPrice(),
                        ((Drink) product).isWithStraw() ? "์์" : "์์");
            }
        }
        System.out.println("-".repeat(50));
    }

    public void addToCart(int productId) {
        Product product = productRepository.findById(productId);
        chooseOption(product);

        if (product instanceof Hamburger) {
            Hamburger hamburger = (Hamburger) product;
            if (hamburger.isBurgerSet()) product = composeSet(hamburger);
        }

        items.add(product);
        System.out.printf("[๐ข] %s ์(๋ฅผ) ์ฅ๋ฐ๊ตฌ๋์ ๋ด์์ต๋๋ค.%n", product.getName());
    }

    private void chooseOption(Product product) {
        String input;

        if (product instanceof Hamburger) {
            System.out.printf("๋จํ์ด์?๊ฐ์? (1)_์(%d์) (2)_์๋์ค(%d์)%n", product.getPrice(), ((Hamburger) product).getBurgerSetPrice());
            input = scanner.nextLine();
            if (input.equals("2")) ((Hamburger) product).setBurgerSet(true);
        } else if (product instanceof Side) {
            System.out.println("ํ์ํ ์ผ์ฒฉ์ ๊ฐ์๋ฅผ ์?์ด์ฃผ์ธ์.");
            input = scanner.nextLine();
            ((Side) product).setKetchup(Integer.parseInt(input));
        } else if (product instanceof Drink) {
            System.out.println("๋นจ๋๊ฐ ํ์ํ์?๊ฐ์? (1)_์ (2)_์๋์ค");
            input = scanner.nextLine();
            if (input.equals("1")) ((Drink) product).setWithStraw(true);
        }
    }

    private BurgerSet composeSet(Hamburger hamburger) {
        System.out.println("์ฌ์ด๋๋ฅผ ๊ณจ๋ผ์ฃผ์ธ์.");
        menu.printSideMenu(false);
        String sideId = scanner.nextLine();
        Side side = (Side) productRepository.findById(Integer.parseInt(sideId));
        chooseOption(side);

        System.out.println("์๋ฃ์๋ฅผ ๊ณจ๋ผ์ฃผ์ธ์.");
        menu.printDrinkMenu(false);
        String drinkId = scanner.nextLine();
        Drink drink = (Drink) productRepository.findById(Integer.parseInt(drinkId));
        chooseOption(drink);

        String name = hamburger.getName() + "์ธํธ";
        int price = hamburger.getBurgerSetPrice();
        int kcal = hamburger.getKcal() + side.getKcal() + drink.getKcal();

        return new BurgerSet(name, price, kcal, hamburger, side, drink);
    }
}
