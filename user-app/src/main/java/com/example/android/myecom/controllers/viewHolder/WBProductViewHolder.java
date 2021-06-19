package com.example.android.myecom.controllers.viewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.myecom.databinding.ItemWbProductBinding;

public class WBProductViewHolder extends RecyclerView.ViewHolder {
    public ItemWbProductBinding binding;

    public WBProductViewHolder(@NonNull ItemWbProductBinding b){
        super(b.getRoot());
        this.binding = b;
    }
}
