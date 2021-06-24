package com.example.android.myecom.controllers.databinders;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.example.android.module.Cart;
import com.example.android.module.Product;
import com.example.android.module.Variants;
import com.example.android.myecom.MainActivity;
import com.example.android.myecom.controllers.AdapterCallBackListener;
import com.example.android.myecom.databinding.ChipVariantBinding;
import com.example.android.myecom.databinding.ItemVbProductBinding;
import com.example.android.myecom.dialog.VarinatQtyPickerDialog;

import java.util.HashMap;

public class VBProductBinder {

    private Cart cart;
    private Context context;
    private AdapterCallBackListener listener;
private HashMap<String,Boolean>saveVariantGrpVisibility = new HashMap<>();

// Vb product binder
public VBProductBinder(Cart cart , Context context , AdapterCallBackListener listener){
    this.cart = cart;
    this.context = context;
    this.listener = listener;
}

public void onBind(Product product , ItemVbProductBinding binding , int position){
    binding.productVariants.setText(product.variants.size() + " variants");
    binding.imageView.setImageResource(product.imageURL);
    binding.dropBtn.setVisibility(View.VISIBLE);
    binding.dropBtn.setRotation(0);
    binding.Variants.setVisibility(View.GONE);

    if(saveVariantGrpVisibility.containsKey(product.name)){
        if(saveVariantGrpVisibility.get(product.name)){
            binding.dropBtn.setVisibility(View.VISIBLE);
            binding.dropBtn.setRotation(180);
            binding.Variants.setVisibility(View.VISIBLE);
        }
    }
    
    showAndHideVariantGrp(product,binding);
    inflaterVariants(product,binding);
    
    editQty(product,binding,position);
    
    updateViews(product,binding);
}

    public void updateViews(Product product, ItemVbProductBinding binding) {
        int qty = 0;
      //  int image = 0;

        for (Variants variant : product.variants) {
            //check qty present in cart
            if (cart.cartItems.containsKey(product.name + " " + variant.name)) {
                qty += cart.cartItems.get(product.name + " " + variant.name).quantity;
               // image = cart.cartItems.get(product.imageURL+" "+variant.)

            }
        }
        //update views
        if (qty > 0) {
            binding.nonZeroQtyGrp.setVisibility(View.VISIBLE);
            binding.qty.setText(qty + "");
        } else {
            binding.nonZeroQtyGrp.setVisibility(View.GONE);
            binding.qty.setText(0 + "");
        }
    }


    private void editQty(Product product, ItemVbProductBinding binding, int position) {
        binding.incBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for variants more than 1
                //check variant size
                if (product.variants.size() > 1)
                    showDialog(product, position);

                    //for single variant
                else {
                    int qty = Integer.parseInt(binding.qty.getText().toString()) + 1;
                    cart.add(product, product.variants.get(0),qty);
                    listener.onCartUpdate(position);
                }

            }
        });

        binding.decBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for variants more than 1
                //check variant size
                if (product.variants.size() > 1)
                    showDialog(product, position);

                    //for single variant
                else {
                    int qty = Integer.parseInt(binding.qty.getText().toString()) - 1;

                    cart.add(product, product.variants.get(0),qty);
                    listener.onCartUpdate(position);
                }
            }
        });
    }

    private void showDialog(Product product, int position) {
        new VarinatQtyPickerDialog(context, product, listener, cart, position).createDialog();
    }


    private void inflaterVariants(Product product, ItemVbProductBinding binding) {
        binding.Variants.removeAllViews();

        //for variants more than 1
        //check variant size
        if (product.variants.size() > 1) {
            binding.productName.setText(product.name);
            for (Variants variant : product.variants) {
                ChipVariantBinding b = ChipVariantBinding.inflate(((MainActivity) context).getLayoutInflater());
                b.getRoot().setText(variant.name + " - Rs." + variant.price);
                binding.Variants.addView(b.getRoot());
            }
            return;
        }


        binding.dropBtn.setVisibility(View.GONE);
        binding.productVariants.setText("Rs." + product.variants.get(0).price);
        binding.productName.setText(product.name + " " + product.variants.get(0).name);
    }

    private void showAndHideVariantGrp(Product product, ItemVbProductBinding binding) {
        binding.dropBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check visibility
                if (binding.Variants.getVisibility() == View.GONE) {
                    binding.Variants.setVisibility(View.VISIBLE);
                    binding.dropBtn.setRotation(180);

                    saveVariantGrpVisibility.put(product.name,true);
                } else {
                    binding.Variants.setVisibility(View.GONE);
                    binding.dropBtn.setRotation(0);
                    saveVariantGrpVisibility.put(product.name,false);
                }
            }
        });
    }
    }
