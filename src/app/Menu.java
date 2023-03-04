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
        System.out.println("[🔻] 메뉴");
        System.out.println("-".repeat(50));

        printHamburgerMenu(true);
        printSideMenu(true);
        printDrinkMenu(true);

        System.out.println("🛒 (0) 장바구니");
        System.out.println("📦 (+) 주문하기");
        System.out.println("-".repeat(50));
        System.out.print("[📢] 메뉴를 선택해주세요. : ");
    }

    protected void printDrinkMenu(boolean printPrice) {
        System.out.println("🥤 음료수");
        for (Product product : products) {
            if (product instanceof Drink) {
                printEachMenu(product, printPrice);
            }
        }
        System.out.println();
    }

    protected void printSideMenu(boolean printPrice) {
        System.out.println("🍟 사이드");
        for (Product product : products) {
            if (product instanceof Side) {
                printEachMenu(product, printPrice);
            }
        }
        System.out.println();
    }

    private void printHamburgerMenu(boolean printPrice) {
        System.out.println("🍔 햄버거");
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
                printPrice ? product.getPrice()+"원" : "");
    }
}

