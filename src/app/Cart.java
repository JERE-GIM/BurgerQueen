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
        System.out.println("🛒 장바구니");
        printCartItems();
        System.out.printf("합계 : %d원%n", sumOfAllItems());
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
                        "  %s %6d원 (%s(케첩 %d개), %s(빨대 %s))%n",
                        product.getName(),
                        product.getPrice(),
                        burgerSet.getSide().getName(),
                        burgerSet.getSide().getKetchup(),
                        burgerSet.getDrink().getName(),
                        burgerSet.getDrink().isWithStraw() ? "있음" : "없음");
            } else if (product instanceof Hamburger) {
                System.out.printf(
                        "  %-8s %6d원 (단품)%n",
                        product.getName(),
                        product.getPrice());
            } else if (product instanceof Side) {
                System.out.printf(
                        "  %-8s %6d원 (케첩 %d개)%n",
                        product.getName(),
                        product.getPrice(),
                        ((Side) product).getKetchup());
            } else if (product instanceof Drink) {
                System.out.printf(
                        "  %-8s %6d원 (빨대 %s)%n",
                        product.getName(),
                        product.getPrice(),
                        ((Drink) product).isWithStraw() ? "있음" : "없음");
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
        System.out.printf("[📢] %s 을(를) 장바구니에 담았습니다.%n", product.getName());
    }

    private void chooseOption(Product product) {
        String input;

        if (product instanceof Hamburger) {
            System.out.printf("단품이신가요? (1)_예(%d원) (2)_아니오(%d원)%n", product.getPrice(), ((Hamburger) product).getBurgerSetPrice());
            input = scanner.nextLine();
            if (input.equals("2")) ((Hamburger) product).setBurgerSet(true);
        } else if (product instanceof Side) {
            System.out.println("필요한 케첩의 개수를 적어주세요.");
            input = scanner.nextLine();
            ((Side) product).setKetchup(Integer.parseInt(input));
        } else if (product instanceof Drink) {
            System.out.println("빨대가 필요하신가요? (1)_예 (2)_아니오");
            input = scanner.nextLine();
            if (input.equals("1")) ((Drink) product).setWithStraw(true);
        }
    }

    private BurgerSet composeSet(Hamburger hamburger) {
        System.out.println("사이드를 골라주세요.");
        menu.printSideMenu(false);
        String sideId = scanner.nextLine();
        Side side = (Side) productRepository.findById(Integer.parseInt(sideId));
        chooseOption(side);

        System.out.println("음료수를 골라주세요.");
        menu.printDrinkMenu(false);
        String drinkId = scanner.nextLine();
        Drink drink = (Drink) productRepository.findById(Integer.parseInt(drinkId));
        chooseOption(drink);

        String name = hamburger.getName() + "세트";
        int price = hamburger.getBurgerSetPrice();
        int kcal = hamburger.getKcal() + side.getKcal() + drink.getKcal();

        return new BurgerSet(name, price, kcal, hamburger, side, drink);
    }
}
