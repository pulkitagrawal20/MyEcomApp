package com.example.android.myecom.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import com.example.android.module.Cart;
import com.example.android.module.Product;
import com.example.android.myecom.MainActivity;
import com.example.android.myecom.R;
import com.example.android.myecom.controllers.AdapterCallBackListener;
import com.example.android.myecom.databinding.DialogWeightPickerBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class WeightPickerDialaog {
    /// agr problem toh yha h0gi....
    private Context Wcontext;
    private  Cart Wcart;

    private  DialogWeightPickerBinding WdialogWeightPickerBinding;

    private  androidx.appcompat.app.AlertDialog WalertDialog;

    private final List<String> KEY_KILOGRAM = new ArrayList<>();

    private final List<String> KEY_GRAM = new ArrayList<>();

    public WeightPickerDialaog(Context context, Cart cart, DialogWeightPickerBinding wdialogWeightPickerBinding, AlertDialog walertDialog) {
        this.Wcontext = context;
        this.Wcart = cart;
        this.WdialogWeightPickerBinding = wdialogWeightPickerBinding.inflate(((MainActivity) context).getLayoutInflater());
        this.WalertDialog = new MaterialAlertDialogBuilder(context, R.style.CustomDialogTheme)
                .setView(WdialogWeightPickerBinding.getRoot())
                .setCancelable(false)
                .create();

        for (int i = 0; i <= 10; i++) {
            KEY_KILOGRAM.add(i + "kg");
        }
        for (int i = 0; i < 20; i++) {
            KEY_GRAM.add(i + "gm");
        }
    }

    public WeightPickerDialaog(Product product, int position, Context context, Cart cart, AdapterCallBackListener listener) {
    }

    public void show(Product product, WeightPickerCompleteListener listener) {
        WalertDialog.show();

        WdialogWeightPickerBinding.TextWP.setText(product.name);
        setValueNumPickers(WdialogWeightPickerBinding, product);

        WdialogWeightPickerBinding.save.setOnClickListener(v -> save(product, listener));

        WdialogWeightPickerBinding.remove.setOnClickListener(v -> {
            if (!Wcart.cartItems.containsKey(product.name)) {
                WalertDialog.dismiss();
                return;
            }
            Wcart.remove(product);

            listener.onCompleted();
            WalertDialog.hide();
        });
    }

    private void setValueNumPickers(DialogWeightPickerBinding wdialogWeightPickerBinding, Product product) {
        String[] qtyString = String.format("%.3f", product.minQuantity).split("\\.");

        int minIndexKg = Integer.parseInt(qtyString[0]);

        List<String> subKgValue = KEY_KILOGRAM.subList(minIndexKg, 11);
        String[] kgArray = new String[subKgValue.size()];
        kgArray = subKgValue.toArray(kgArray);

        WdialogWeightPickerBinding.NumberPickerPrKg.setMaxValue(0);
        WdialogWeightPickerBinding.NumberPickerPrKg.setDisplayedValues(kgArray);
        WdialogWeightPickerBinding.NumberPickerPrKg.setMaxValue(kgArray.length - 1);

        WdialogWeightPickerBinding.NumberPickerPrKg.setOnValueChangedListener((picker, oldVal, newVal) -> {
            String[] grams = WdialogWeightPickerBinding.NumberPickerPrG.getDisplayedValues();
            String g = grams[WdialogWeightPickerBinding.NumberPickerPrG.getValue()];

            String[] kilograms = picker.getDisplayedValues();
            String kg = kilograms[picker.getValue()].replace("kg", "");

            String[] gmArray;

            if (Integer.parseInt(kg) >= ((int) product.minQuantity + 1)) {
                List<String> subGramValues = KEY_GRAM.subList(0, 20);
                gmArray = new String[subGramValues.size()];
                gmArray = subGramValues.toArray(gmArray);
            } else {
                int minIndexGram = Integer.parseInt(qtyString[1]) / 50;


                List<String> subGramValues = KEY_GRAM.subList(minIndexGram, 20);
                gmArray = new String[subGramValues.size()];
                gmArray = subGramValues.toArray(gmArray);
            }
            setGmNumPicker(gmArray);

            for (int i = 0; i < gmArray.length; i++) {
                if (gmArray[i].equals(g)) {
                    WdialogWeightPickerBinding.NumberPickerPrG.setValue(i);
                }
            }
        });
        int minIndexGram = Integer.parseInt(qtyString[1]) / 50;

        // Extracting the array for number picker
        List<String> subGramValues = KEY_GRAM.subList(minIndexGram, 20);
        String[] gmArray = new String[subGramValues.size()];
        gmArray = subGramValues.toArray(gmArray);


        //   setGramsNumberPicker(gramArray);

        if (Wcart.cartItems.containsKey(product.name)) {
            String[] quantityString = String.format("%.3f", Wcart.cartItems.get(product.name).quantity).split("\\.");

            if (Integer.parseInt(quantityString[0]) > (int) product.minQuantity) {
                subGramValues = KEY_GRAM.subList(0, 20);
                gmArray = new String[subGramValues.size()];
                gmArray = subGramValues.toArray(gmArray);
                setGmNumPicker(gmArray);
            }

            for (int i = 0; i < kgArray.length; i++) {
                if ((quantityString[0] + "kg").equals(kgArray[i])) ;
                WdialogWeightPickerBinding.NumberPickerPrKg.setValue(i);
            }
        }
        for (int i = 0; i < gmArray.length; i++) {
            // agr glti toh yhape....
            if ((qtyString[0] + "gm").equals(gmArray[i])){
            WdialogWeightPickerBinding.NumberPickerPrG.setValue(i);
        }
    }

}



    private void setGmNumPicker(String[] gmArray) {
    WdialogWeightPickerBinding.NumberPickerPrG.setMaxValue(0);
    WdialogWeightPickerBinding.NumberPickerPrG.setDisplayedValues(gmArray);
    WdialogWeightPickerBinding.NumberPickerPrG.setMaxValue(gmArray.length-1);
    }

    private void save(Product product, WeightPickerCompleteListener listener) {
        // Getting the array of displayed value in number pickers
        String[] kilogramValues = WdialogWeightPickerBinding.NumberPickerPrKg.getDisplayedValues();
        String[] gramValues = WdialogWeightPickerBinding.NumberPickerPrG.getDisplayedValues();

        // Get selected quantity from number picker
        String kg = kilogramValues[WdialogWeightPickerBinding.NumberPickerPrKg.getValue()].replace("kg", ""),
                gm = gramValues[WdialogWeightPickerBinding.NumberPickerPrG.getValue()].replace("g", "");


        // Adding the item in the cart
        float quantity = Float.parseFloat(kg) + Float.parseFloat(gm) / 1000;


        if (quantity < product.minQuantity) {
            Toast.makeText(Wcontext, "Minimum " + product.minQuantity + " kg needs to be selected", Toast.LENGTH_SHORT).show();
            return;
        }


        Wcart.add(product, quantity);


        listener.onCompleted();
        WalertDialog.hide();
    }

    public void createDialog() {

    }


    public interface WeightPickerCompleteListener{

        void onCompleted();
    }
}


