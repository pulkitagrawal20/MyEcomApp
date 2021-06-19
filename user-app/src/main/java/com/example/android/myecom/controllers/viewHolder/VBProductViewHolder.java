package com.example.android.myecom.controllers.viewHolder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.myecom.databinding.ItemVbProductBinding;

public class VBProductViewHolder extends RecyclerView.ViewHolder {
   public ItemVbProductBinding binding;
    public VBProductViewHolder(@NonNull  ItemVbProductBinding b) {
        super(b.getRoot());
        this.binding = b;
    }
}
