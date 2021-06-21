package com.example.android.module;



import java.util.HashMap;

public class Cart {
   public  HashMap<String, CartItem> cartItems=new HashMap<>();
    public float total, noOfItems=0;

    //To Add wb products:
   public void add(Product product, float quantity) {
       if(quantity==0){
           remove(product);
           return;
       }
        // if item already exists in cart:
        if (cartItems.containsKey(product.name)) {
            total -= cartItems.get(product.name).Cost();
            cartItems.get(product.name).quantity = quantity;
        }
        //if item doesn't exists in cart:
        else {
            CartItem newItem = new CartItem(product.name, product.pricePerKg, quantity);
            cartItems.put(product.name, newItem);

            noOfItems++;
        }
        //Updated cart summary:
       // total += product.pricePerKg * quantity;
       total += cartItems.get(product.name).Cost();
    }


    //To Add vb products:
    public void add(Product product, Variants variants,int qty) {
        String key= product.name+ "" + variants.name;
        //if already exists:
        if(cartItems.containsKey(key)){
            total-=cartItems.get(key).Cost();
            noOfItems -= cartItems.get(key).quantity;
            cartItems.get(key).quantity++;
        }
        //Added for the first time:
        else{
            CartItem newItem=new CartItem(product.name, variants.price,1);
            cartItems.put(key,newItem);
        }
        //Updated cart summary:
        noOfItems++;
        total+= variants.price;

        if (cartItems.get(key).quantity==0){
            cartItems.remove(key);
        }
    }

    //to remove wb products:
    public void remove(Product product){
       if(product.type==ProductType.TYPE_wb){

           removeWBP(product);
       }
       else
           removeAllVBP(product);
    }

    private void removeWBP(Product product) {
        //Update cart:
        total-=cartItems.get(product.name).Cost();
        noOfItems--;
        cartItems.remove(product.name);
    }

    //to remove vb products:
    public void removeAllVBP(Product product){
        for(Variants variants : product.variants){
            String key= product.name+ "" + variants.name;

            if(cartItems.containsKey(key)){
                //Update cart:
                total-=cartItems.get(key).Cost();
                noOfItems-=cartItems.get(key).quantity;
                cartItems.remove(key);
            }

        }
    }

    //decrement quantity:
    public void decrement(Product product, Variants variants){
        String key= product.name+ "" + variants.name;
            //update quantity:
            cartItems.get(key).quantity--;

            //Update cart:
            total-= variants.price;
            noOfItems--;

            if(cartItems.get(key).quantity==0){
                cartItems.remove(key);
            }
    }

    @Override
    public String toString() {
        return cartItems.values()
                + String.format("\n total %f items (Rs. %f)",noOfItems,total);
    }
}
