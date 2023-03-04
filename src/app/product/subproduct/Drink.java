package app.product.subproduct;

import app.product.Product;

public class Drink extends Product {
    private boolean isWithStraw;

    public Drink(int id, String name, int price, int kcal, boolean isWithStraw) {
        super(id, name, price, kcal);
        this.isWithStraw = isWithStraw;
    }

    public Drink(Drink drink) {
        super(drink.getName(), drink.getPrice(), drink.getKcal());
        this.isWithStraw = drink.isWithStraw();
    }

    public boolean isWithStraw() {
        return isWithStraw;
    }

    public void setWithStraw(boolean withStraw) {
        isWithStraw = withStraw;
    }
}
