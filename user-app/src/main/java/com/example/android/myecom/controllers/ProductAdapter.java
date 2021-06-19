package com.example.android.myecom.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.module.Cart;
import com.example.android.module.Product;
import com.example.android.module.ProductType;
import com.example.android.myecom.MainActivity;
import com.example.android.myecom.controllers.databinders.VBProductBinder;
import com.example.android.myecom.controllers.databinders.WBProductBinder;
import com.example.android.myecom.controllers.viewHolder.VBProductViewHolder;
import com.example.android.myecom.controllers.viewHolder.WBProductViewHolder;
import com.example.android.myecom.databinding.ItemVbProductBinding;
import com.example.android.myecom.databinding.ItemWbProductBinding;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  Context context;
    private Cart cart;
    private AdapterCallBackListener listener;
    public WBProductBinder wbProductBinder;
    public VBProductBinder vbProductBinder;
    private  List<Product>products;

   // private final ProductBinder productBinder;

    public ProductAdapter(  Context context,List<Product> products, Cart cart, AdapterCallBackListener listener) {
        this.context = context;
        this.products=products;
        this.cart = cart;
        this.listener = listener;

        wbProductBinder = new WBProductBinder(cart,context,listener);
        vbProductBinder = new VBProductBinder(cart,context,listener);
    }

//    public ProductAdapter(MainActivity mainActivity, List<Product> products, Cart cart, AdapterCallBackListener listener) {
//        this.context = context;
//        this.products=products;
//    }

    @Override
    public int getItemViewType(int position) {
        return products.get(position).type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType== ProductType.TYPE_wb){
            ItemWbProductBinding binding = ItemWbProductBinding.inflate(LayoutInflater.from(context),parent,false);
            return new WBProductViewHolder(binding);
        }
        else {
            ItemVbProductBinding binding = ItemVbProductBinding.inflate(LayoutInflater.from(context),parent,false);
            return new VBProductViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull  RecyclerView.ViewHolder holder, int position) {
    Product product = products.get(position);

    if (holder instanceof WBProductViewHolder){
        wbProductBinder.onBind(product,((WBProductViewHolder)holder).binding,position);
        return;
    }
    vbProductBinder.onBind(product,((VBProductViewHolder)holder).binding,position);
    }

    @Override
    public void onBindViewHolder(@NonNull  RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        Product product = products.get(position);
        if (!payloads.isEmpty()) {
            if (holder instanceof WBProductViewHolder) {
                wbProductBinder.updateviews(product, ((WBProductViewHolder) holder).binding);
            } else {
                vbProductBinder.updateViews(product, ((VBProductViewHolder) holder).binding);
            }
        } else {
            super.onBindViewHolder(holder, position, payloads);
        }
    }
    @Override
    public int getItemCount() {
        return products.size();
    }
}
