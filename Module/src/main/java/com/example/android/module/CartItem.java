package com.example.android.module;

public class CartItem {
    public float quantity;
   public String name;
    public float unitPrice;

    public CartItem(String name, float unitPrice, float quantity) {
        this.name=name;
        this.unitPrice=unitPrice;
        this.quantity=quantity;
    }

    public float Cost(){
        return unitPrice*quantity;
    }

    @Override
    public String toString() {
        return "\n\t" + name + " ( " +
                String.format("%f X %f = %f", unitPrice, quantity, Cost()) +
                " )";

    }
}
