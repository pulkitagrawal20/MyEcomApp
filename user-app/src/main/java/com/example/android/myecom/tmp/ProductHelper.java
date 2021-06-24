package com.example.android.myecom.tmp;

import android.net.Uri;

import com.example.android.module.Product;
import com.example.android.module.Variants;
import com.example.android.myecom.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProductHelper {

      static public List<Product> getProducts() {
        List<Product> products = new ArrayList<>(
                Arrays.asList(
                        new Product("Apple", R.drawable.apple, 80.00f, 0.5f),
                        new Product("Kiwi", R.drawable.kiwi, new ArrayList<>(Arrays.asList(
                                new Variants("500g", 90),
                                new Variants("1kg", 150)))),
                        new Product("Surf Excel", R.drawable.surfexcel, new ArrayList<>(Arrays.asList(
                                new Variants("1kg 50g", 90),
                                new Variants("1kg", 150)))),
                        new Product("ketchup",R.drawable.ketchup,40,1),
                        new Product("Curd",R.drawable.curd,new ArrayList<>(Arrays.asList(
                                new Variants("1kg",20),
                                new Variants("2kg",40)))),
                        new Product("Watermelon",R.drawable.watermelon,10,1)

                )
        );
        return products;
    }
}
