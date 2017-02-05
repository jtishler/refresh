package com.hackbrown.teamwildcard.refresh;

/**
 * Created by jenna on 2/5/17.
 */

public class KitchenListItem implements FoodItem{
    String name;
    String date;
    int quantity;

    public KitchenListItem(String n) {
        name = n;
        date = "expiration date";
    }

    public void setName(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }

    @Override
    public void setQuantity(int n) {
        quantity = n;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }
}
