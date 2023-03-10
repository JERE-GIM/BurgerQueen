package app;

import app.product.Product;
import app.product.subproduct.Drink;
import app.product.subproduct.Hamburger;
import app.product.subproduct.Side;

public class Menu {
    Product[] products;

    public Menu(Product[] products) {
        this.products = products;
    }

    public void printMenu() {
        System.out.println("[π»] λ©λ΄");
        System.out.println("-".repeat(50));

        printHamburgerMenu(true);
        printSideMenu(true);
        printDrinkMenu(true);

        System.out.println("π (0) μ₯λ°κ΅¬λ");
        System.out.println("π¦ (+) μ£Όλ¬ΈνκΈ°");
        System.out.println("-".repeat(50));
        System.out.print("[π’] λ©λ΄λ₯Ό μ νν΄μ£ΌμΈμ. : ");
    }

    protected void printDrinkMenu(boolean printPrice) {
        System.out.println("π₯€ μλ£μ");
        for (Product product : products) {
            if (product instanceof Drink) {
                printEachMenu(product, printPrice);
            }
        }
        System.out.println();
    }

    protected void printSideMenu(boolean printPrice) {
        System.out.println("π μ¬μ΄λ");
        for (Product product : products) {
            if (product instanceof Side) {
                printEachMenu(product, printPrice);
            }
        }
        System.out.println();
    }

    private void printHamburgerMenu(boolean printPrice) {
        System.out.println("π νλ²κ±°");
        for (Product product : products) {
            if (product instanceof Hamburger) {
                printEachMenu(product, printPrice);
            }
        }
        System.out.println();
    }

    private void printEachMenu(Product product, boolean printPrice) {

        System.out.printf(
                "   (%d) %-5s %3dKcal %s%n",
                product.getId(),
                product.getName(),
                product.getKcal(),
                printPrice ? product.getPrice()+"μ" : "");
    }
}

