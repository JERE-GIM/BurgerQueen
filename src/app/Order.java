package app;

import app.discount.disocuntCondition.CozDiscountCondition;
import app.discount.disocuntCondition.KidDiscountCondition;

public class Order {
    private Cart cart;

    public Order(Cart cart) {
        this.cart = cart;
    }

    public void makeOrder() {
        CozDiscountCondition cozDiscountCondition = new CozDiscountCondition();
        KidDiscountCondition kidDiscountCondition = new KidDiscountCondition();

        cozDiscountCondition.checkDiscountCondition();
        kidDiscountCondition.checkDiscountCondition();

        int totalPrice = cart.sumOfAllItems();
        int finalPrice = totalPrice;

        if (cozDiscountCondition.isSatisfied()) finalPrice = cozDiscountCondition.applyDiscount(finalPrice);
        if (kidDiscountCondition.isSatisfied()) finalPrice = kidDiscountCondition.applyDiscount(finalPrice);
        System.out.println("[ð¢] ì£¼ë¬¸ì´ ìë£ëììµëë¤.");
        System.out.println("[ð¢] ì£¼ë¬¸ ë´ì­ì ë¤ìê³¼ ê°ìµëë¤.");
        cart.printCartItems();
        System.out.printf("í©ê³ ê¸ì¡      : %dì%n", totalPrice);
        System.out.printf("í ì¸ ì ì© ê¸ì¡ : %dì%n", finalPrice);
    }
}
