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
    //    public static List<Product> getProducts() {
//        /List<Product> products = new ArrayList<>();
      static public List<Product> getProducts() {
        List<Product> products = new ArrayList<>(
                Arrays.asList(
                        new Product("Apple", R.drawable.apple, 0.5f, 80),
                        new Product("Kiwi", R.drawable.kiwi, new ArrayList<>(Arrays.asList(
                                new Variants("500g", 90),
                                new Variants("1kg", 150)))),
                        new Product("Surf Excel", R.drawable.surfexcel, new ArrayList<>(Collections.singletonList(
                                new Variants("1kg 50g", 90))))
                )
        );
        return products;
    }
}
//        products.add(new Product("Apple", R.drawable.apple, 1, 70));
//        products.add(new Product("kiwi", Uri.parse("kiwi").toString(), new ArrayList<>(Arrays.asList(
//                new Variants("500g", 100),
//                new Variants("1kg", 190)
//        ))));
//        products.add(new Product("Orange", Uri.parse("Orange").toString(), 1, 80));
//        products.add(new Product("Surf Excel", Uri.parse("surf_excel").toString(), new ArrayList<>(Arrays.asList(
//                new Variants("1kg", 100),
//                new Variants("2kg", 200),
//                new Variants("5kg", 450)
//        ))));
//
//        products.add(new Product("Curd", Uri.parse("curd").toString(), new ArrayList<>(Collections.singletonList(
//                new Variants("1kg", 50)
//        ))));
//        products.add(new Product("Watermelon", Uri.parse("watermelon").toString(), 1.5f, 10));
//        products.add(new Product("Milk", Uri.parse("milk").toString(), new ArrayList<>(Arrays.asList(
//                new Variants("100g", 65),
//                new Variants("200kg", 120)
//        ))));
//        products.add(new Product("Potato", Uri.parse("potato").toString(), 2, 40));
//        products.add(new Product("Ketchup", Uri.parse("ketchup").toString(), new ArrayList<>(Arrays.asList(
//                new Variants("500g", 110),
//                new Variants("1kg", 100)
//        ))));
