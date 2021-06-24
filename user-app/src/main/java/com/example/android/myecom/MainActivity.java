package com.example.android.myecom;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.android.module.Cart;
import com.example.android.module.Product;
import com.example.android.myecom.controllers.AdapterCallBackListener;
import com.example.android.myecom.controllers.ProductAdapter;
import com.example.android.myecom.databinding.ActivityMainBinding;
import com.example.android.myecom.tmp.ProductHelper;
import com.google.gson.Gson;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;
     List<Product>products = ProductHelper.getProducts();
     Cart cart;
  ProductAdapter adapter;
  //  private SharedPreferences sharedPreferences;
 // private static final String CART_SUMMARY_KEY = "cartsummary";
  
  private  boolean isUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
//        sharedPreferences = getPreferences(MODE_PRIVATE);
//        if (sharedPreferences.contains(CART_SUMMARY_KEY)){
//            getDataofSharedPredrence();
//        }
//        products = ProductHelper.getProducts();
        loadcartFromSharedPrefernce();

        setAdapter();

    }

    private void loadcartFromSharedPrefernce() {


        String cart = getPreferences(MODE_PRIVATE).getString("CART", null);

        if(cart==null){
            this.cart=new Cart();
            return;
        }

        this.cart=new Gson().fromJson(cart,Cart.class);

        updateCartSummary();
    }

    private void setAdapter() {

        AdapterCallBackListener listener = new AdapterCallBackListener() {
            @Override
            public void onCartUpdate(int position) {
                updateCartSummary();
                isUpdate = true;
                adapter.notifyItemChanged(position,"payload");

            }
        };
        adapter = new ProductAdapter(this,products,cart,listener);

//        AdapterCallBackListener listener = (position) -> updateCartSummary();
//
//        activityMainBinding.list.setLayoutManager(new LinearLayoutManager(this));
//        activityMainBinding.list.setAdapter(adapter);
        activityMainBinding.list.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        activityMainBinding.list.setAdapter(adapter);
        activityMainBinding.list.setLayoutManager(new LinearLayoutManager(this));
    }

    private void updateCartSummary() {
        if (!cart.cartItems.isEmpty()) {
            activityMainBinding.totalItems.setText(cart.noOfItems + "items");

            activityMainBinding.totalAmount.setText("Rs." + String.format("%.2f", cart.total));

            activityMainBinding.FrameLayout.setVisibility(View.VISIBLE);
        } else {
            activityMainBinding.FrameLayout.setVisibility(View.GONE);
        }
//       activityMainBinding.totalItems.setText(String.format("%d items",cart.noOfItems));

//        activityMainBinding.totalAmount.setText(String.format("₹%.2f",cart.total));
//    }
    }
//    private void getDataofSharedPredrence() {
//        String json = sharedPreferences.getString(CART_SUMMARY_KEY,"");
//        cart = (new Gson()).fromJson(json,Cart.class);
//        updateCartSummary();
//    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isUpdate) {
            Gson gson = new Gson();
            String json = gson.toJson(cart);
            getPreferences(MODE_PRIVATE).edit().putString("CART", json).apply();
            isUpdate=false;
        }
    }
}
