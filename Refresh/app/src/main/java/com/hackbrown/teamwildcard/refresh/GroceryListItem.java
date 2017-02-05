package com.hackbrown.teamwildcard.refresh;

/**
 * Created by jenna on 2/4/17.
 */

public class GroceryListItem implements FoodItem{
    String name;
    int quantity;
    boolean selected = false;

    public GroceryListItem(String n, int q) {
        name = n;
        quantity = q;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean s) {
        selected = s;
    }
}