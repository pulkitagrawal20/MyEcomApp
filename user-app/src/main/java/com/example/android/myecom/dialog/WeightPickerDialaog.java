package com.example.android.myecom.dialog;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.android.module.Cart;
import com.example.android.module.Product;
import com.example.android.myecom.MainActivity;
import com.example.android.myecom.R;
import com.example.android.myecom.controllers.AdapterCallBackListener;
import com.example.android.myecom.controllers.viewHolder.WBProductViewHolder;
import com.example.android.myecom.databinding.DialogWeightPickerBinding;
import com.google.android.material.datepicker.MaterialStyledDatePickerDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class WeightPickerDialaog {
    /// agr problem toh yha h0gi....
    private Product Wproduct;
    private  int Wposition;
    private Context Wcontext;
    private  Cart Wcart;

    private  DialogWeightPickerBinding WdialogWeightPickerBinding;
private AdapterCallBackListener listener;
    private AlertDialog WalertDialog;
    
    private int minValueKg;
    
    private int minValueGm;

    private  int selectedPosition = 0;
    
//    private final List<String> KEY_KILOGRAM = new ArrayList<>();
//
//    private final List<String> KEY_GRAM = new ArrayList<>();

    public WeightPickerDialaog(Product product, int position,Context context, Cart cart, AdapterCallBackListener listener ) {
        this.Wcontext = context;
        this.Wposition = position;
        this.Wproduct = product;
        this.Wcart = cart;
        this.listener = listener;
    }
    public void createDialog(){
    WdialogWeightPickerBinding = DialogWeightPickerBinding.inflate(((MainActivity)Wcontext).getLayoutInflater());
    WalertDialog  = new MaterialAlertDialogBuilder(Wcontext,R.style.CustomDialogTheme)
            .setCancelable(false)
            .setView(WdialogWeightPickerBinding.getRoot())
            .show();
    
    WdialogWeightPickerBinding.TextWP.setText(Wproduct.name);
    
    minQty();
    
    saveQty();
    
    removeQty();
    
    preSelectedQty();
            
}

    private void removeQty() {
        WdialogWeightPickerBinding.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Wcart.cartItems.containsKey(Wproduct.name)) {
                    Wcart.remove(Wproduct);
                    listener.onCartUpdate(Wposition);
                }
                WalertDialog.dismiss();
            }
        });
    }


    private void saveQty() {
        WdialogWeightPickerBinding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Quantity of product
                float qty = (minValueKg + WdialogWeightPickerBinding.NumberPickerPrKg.getValue())
                        + ((((minValueGm / 50f) + WdialogWeightPickerBinding.NumberPickerPrG.getValue()) * 50) / 1000f);

                //check same quantity present in cart
                if(Wcart.cartItems.containsKey(Wproduct.name)&&(Wcart.cartItems.get(Wproduct.name).quantity==qty)){
                    WalertDialog.dismiss();
                    return;
                }
                Wcart.add(Wproduct, qty);

                //update view
                listener.onCartUpdate(Wposition);
                WalertDialog.dismiss();
            }
        });

    }

    private void preSelectedQty() {
        //to show pre-selected quantity
        //check item present in cart or not
        if (Wcart.cartItems.containsKey(Wproduct.name)) {
            String[] minValues = String.valueOf(Wcart.cartItems.get(Wproduct.name).quantity).split("\\.");

            String minQtyGm = "0." + minValues[1];
            int gm = (int) (Float.parseFloat(minQtyGm) * 1000);

            WdialogWeightPickerBinding.NumberPickerPrKg.setValue(Integer.parseInt(minValues[0]) - minValueKg);


            if(Integer.parseInt(minValues[0])!=minValueKg){
                if(minValueGm!=0) {
                    minValueGm = 0;
                    initializeNumberPickerForGm();
                }
            }
            WdialogWeightPickerBinding.NumberPickerPrG.setValue((gm - minValueGm) / 50);

        }
    }

    private void minQty() {
        //get minimum values of kg and g
        String[] minValues = String.valueOf(Wproduct.minQuantity).split("\\.");


        //minimum value for kg
        minValueKg = Integer.parseInt(minValues[0]);

        //Initialize number picker for kg
        initializeNumberPickerForKg();

        //minimum value for kg
        String minQtyGm = "0." + minValues[1];
        minValueGm = (int) (Float.parseFloat(minQtyGm) * 1000);


        //Initialize number picker for g
        initializeNumberPickerForGm();

    }

    private void initializeNumberPickerForGm() {
        Log.d("Abhi", "initializeNumberPickerForGm: " +minValueGm);
//        if(cart.cartItems.containsKey(product.name)){
//            minValuesGm=0;
//        }
        //num of values in the picker
        int NUMBER_OF_VALUES = 20 - (minValueGm / 50);

        int PICKER_RANGE = minValueGm;
        String[] displayedValues = new String[NUMBER_OF_VALUES];

        displayedValues[0] = minValueGm + "g";
        for (int i = 1; i < NUMBER_OF_VALUES; i++) {
            displayedValues[i] = (PICKER_RANGE + 50) + "g";
            PICKER_RANGE += 50;
        }
        Log.d("Abhi", "initializeNumberPickerForGm: " +displayedValues.length);
        //update picker

        WdialogWeightPickerBinding.NumberPickerPrG.setDisplayedValues(null);
        WdialogWeightPickerBinding.NumberPickerPrG.setMinValue(0);
        WdialogWeightPickerBinding.NumberPickerPrG.setMaxValue(NUMBER_OF_VALUES - 1);
        WdialogWeightPickerBinding.NumberPickerPrG.setDisplayedValues(displayedValues);
        WdialogWeightPickerBinding.NumberPickerPrG.setValue(selectedPosition);


    }

    private void initializeNumberPickerForKg() {
        int NUMBER_OF_VALUES = 11 - minValueKg;

        int PICKER_RANGE = minValueKg;
        String[] displayedValues = new String[NUMBER_OF_VALUES];

        displayedValues[0] = minValueKg + "kg";
        for (int i = 1; i < NUMBER_OF_VALUES; i++) {
            displayedValues[i] = (++PICKER_RANGE) + "kg";
        }

        //update picker
        WdialogWeightPickerBinding.NumberPickerPrKg.setMinValue(0);
        WdialogWeightPickerBinding.NumberPickerPrKg.setMaxValue(displayedValues.length - 1);
        WdialogWeightPickerBinding.NumberPickerPrKg.setDisplayedValues(displayedValues);

        WdialogWeightPickerBinding.NumberPickerPrKg.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if(picker.getValue()+minValueKg!=minValueKg){
                    if(minValueGm==0){
                        return;
                    }
                    selectedPosition=((minValueGm/50+WdialogWeightPickerBinding.NumberPickerPrG.getValue())*50)/50;
                    minValueGm=0;
                    initializeNumberPickerForGm();
                }else if(picker.getValue()+minValueKg==minValueKg){
                    minValueGm=(int) ((Wproduct.minQuantity-minValueKg)*1000);

                    selectedPosition=((WdialogWeightPickerBinding.NumberPickerPrG.getValue()*50)-minValueGm)/50;
                    if(selectedPosition<0){
                        selectedPosition=0;
                    }
                    initializeNumberPickerForGm();
                }
            }
        });
    }
}