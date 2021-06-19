package com.example.android.myecom.controllers.databinders;

import android.content.Context;
import android.view.View;

import com.example.android.module.Cart;
import com.example.android.module.Product;
import com.example.android.myecom.controllers.AdapterCallBackListener;
import com.example.android.myecom.databinding.ItemWbProductBinding;
import com.example.android.myecom.dialog.WeightPickerDialaog;

public class WBProductBinder {

    private Cart cart;
    private Context context;
    private AdapterCallBackListener listener;

    public WBProductBinder(Cart cart, Context context,AdapterCallBackListener listener){
        this.cart = cart;
        this.context = context;
        this.listener = listener;
    }
    public void onBind(Product product , ItemWbProductBinding binding , int position){
        binding.productName.setText(product.name);
        binding.productWeight.setText("Rs."+product.pricePerKg+"/kg");
        binding.imageView.setImageResource(product.imageURL);

        Quantityedit(product,binding,position);
        updateviews(product,binding);
    }

    public void updateviews(Product product, ItemWbProductBinding binding) {
        if (cart.cartItems.containsKey(product.name)){
            binding.nonZeroQuantityGroup.setVisibility(View.VISIBLE);
            binding.addBtn.setVisibility(View.GONE);
            binding.qty.setText(cart.cartItems.get(product.name).quantity+"kg");
        }
        else {
            binding.nonZeroQuantityGroup.setVisibility(View.GONE);
            binding.addBtn.setVisibility(View.VISIBLE);
        }
    }

    private void Quantityedit(Product product, ItemWbProductBinding binding, int position) {
        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(product,position);
            }
        });
    }

    private void showDialog(Product product,int position){
        new WeightPickerDialaog(product,position,context,cart,listener).createDialog();
    }
}
