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
                        new Product("Apple",R.drawable.apple,1,80),
                        new Product("Kiwi", R.drawable.kiwi, new ArrayList<>(Arrays.asList(
                                new Variants("500g", 90),
                                new Variants("1kg", 150)))),
                        new Product("Banana",R.drawable.banana,1,30),
                        new Product("Surf Excel", R.drawable.surf_excel, new ArrayList<>(Arrays.asList(
                                new Variants(" 1kg", 95),
                                new Variants("2kg", 180),
                                new Variants("5kg",400)))),

                        new Product("Milk",R.drawable.milk,new ArrayList<>(Collections.singletonList(
                                new Variants("1kg", 50))))

                )
        );
        return products;
    }
}
