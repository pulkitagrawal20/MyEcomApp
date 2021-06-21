package com.example.android.myecom.dialog;

import android.content.Context;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.example.android.module.Cart;
import com.example.android.module.Product;
import com.example.android.module.Variants;
import com.example.android.myecom.MainActivity;
import com.example.android.myecom.R;
import com.example.android.myecom.controllers.AdapterCallBackListener;
import com.example.android.myecom.databinding.DialogVarientQtyPickerBinding;
import com.example.android.myecom.databinding.ItemVariantsBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.HashMap;

public class VarinatQtyPickerDialog {
    private Context context;
    private Product product;
    private AdapterCallBackListener listener;
    private Cart cart;
    private int position;
    private DialogVarientQtyPickerBinding binding;
    private AlertDialog alertDialog;
    private HashMap<String, Integer> saveVariantsQty = new HashMap<>();

    public VarinatQtyPickerDialog(Context context, Product product, AdapterCallBackListener listener, Cart cart, int position) {
        this.context = context;
        this.product = product;
        this.listener = listener;
        this.cart = cart;
        this.position = position;
    }
    public void createDialog() {
        binding = DialogVarientQtyPickerBinding.inflate(((MainActivity) context).getLayoutInflater());

        alertDialog = new MaterialAlertDialogBuilder(context, R.style.CustomDialogTheme)
                .setCancelable(false)
                .setView(binding.getRoot())
                .show();

        binding.TextVP.setText(product.name);

        //inflate variants
        inflateVariants();

        //update cart
        saveVariants();

        //remove all variants from cart
        removeAllVariants();

    }
    private void inflateVariants(){
        for (Variants variants : product.variants){
            ItemVariantsBinding itemVariantsBinding = ItemVariantsBinding.inflate(((MainActivity)context).getLayoutInflater());
            itemVariantsBinding.variantName.setText("Rs"+variants.name);
            binding.variants.addView(binding.getRoot());
            //prefill selected variants
            AlreadySelectedVariant(itemVariantsBinding,variants.name);

            //add quantity
            addQuantityPerVariant(itemVariantsBinding, variants.name);

            //dec quantity
            decQuantityPerVariant(itemVariantsBinding, variants.name);
        }
    }

    private void decQuantityPerVariant(ItemVariantsBinding binding, String variantname) {
        binding.decBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (saveVariantsQty.containsKey(variantname)){
                    saveVariantsQty.put(variantname,saveVariantsQty.get(variantname)-1);
            }

                if (saveVariantsQty.get(variantname)==0){
                    binding.nonZeroQtyGrp.setVisibility(View.GONE);
        }
                binding.qty.setText(saveVariantsQty.get(variantname)+"");

            }
        });
    }


    private void removeAllVariants() {
        binding.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!saveVariantsQty.isEmpty()) {
                    cart.removeAllVBP(product);
                    //update views
                    listener.onCartUpdate(position);
                }
                alertDialog.dismiss();
            }
        });
    }

    private void saveVariants() {
        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!saveVariantsQty.isEmpty()) {
                    for (Variants variant : product.variants) {
                        //check variant present in saveVariantsQty
                        if (saveVariantsQty.containsKey(variant.name)) {
                            cart.add(product, variant, saveVariantsQty.get(variant.name));
                        }
                    }
                    //update views
                    listener.onCartUpdate(position);
                }
               alertDialog.dismiss();
            }
        });
    }


    private void addQuantityPerVariant(ItemVariantsBinding binding, String variantName) {
        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save qty of variants
                //check variant present or not
                if (saveVariantsQty.containsKey(variantName)) {
                    saveVariantsQty.put(variantName, saveVariantsQty.get(variantName) + 1);

                } else {
                    saveVariantsQty.put(variantName, 1);
                }
                //update views
                binding.qty.setText(saveVariantsQty.get(variantName) + "");
                binding.nonZeroQtyGrp.setVisibility(View.VISIBLE);
            }
        });
    }

    private void AlreadySelectedVariant(ItemVariantsBinding binding, String name) {
        if (cart.cartItems.containsKey(product.name + " " + name)) {
            //Save qty in saveVariantsQty
            saveVariantsQty.put(name, (int) cart.cartItems.get(product.name + " " + name).quantity);

            binding.nonZeroQtyGrp.setVisibility(View.VISIBLE);
            binding.qty.setText(saveVariantsQty.get(name) + "");
        }
    }
}
