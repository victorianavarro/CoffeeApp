package com.vnf.coffeeapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    int quantity = 1;
    int priceOfOneCup = 5;
    int priceWhippedCream = 1;
    int priceChocolate = 2;

    boolean hasWhippedCrem = false;
    boolean hasChocolate = false;
    //int price = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /*
    This method is called when the order button is clicked
     */
    public void submitOrder(View view){

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        hasWhippedCrem = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        hasChocolate = chocolateCheckBox.isChecked();

        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        int price = calculatePrice(hasWhippedCrem, hasChocolate);
        //String summaryMessage = createOrderSummary(price);
        //displayMessage(createOrderSummary(price, hasWhippedCrem, hasChocolate, name));
        //String summaryMessage = createOrderSummary(price, hasWhippedCrem, hasChocolate, name);
        //Log.v("className", "message");
        Log.v("MainActivity", "The price is: " + price);


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffe Order");
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(price, hasWhippedCrem, hasChocolate, name));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }



    }

    /**
     * This method creates a summary of the order.
     * @param name as the name of the user
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate topping
     * @param price of the order
     * @return text summary
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name){
        String priceMessage = getString(R.string.orderSummary_name, name) + "\n";
        priceMessage += getString(R.string.orderSummary_quantity, "" + quantity + "") + "\n";
        if(addWhippedCream){
            priceMessage += getString(R.string.orderSummary_whippedCreamYes) + "\n";
        }else{
            priceMessage += getString(R.string.orderSummary_whippedCreamNo) + "\n";
        }

        if(addChocolate){
            priceMessage += getString(R.string.orderSummary_chocolateYes) + "\n";
        }else{
            priceMessage += getString(R.string.orderSummary_chocolateNo) + "\n";
        }

        priceMessage += getString(R.string.orderSummary_price, NumberFormat.getCurrencyInstance().format(price)) + "\n";
        priceMessage += getString(R.string.orderSummary_thanks);
Log.v("MainActivity", priceMessage);
        /*
        priceMessage += "\nQuantity " + quantity + "";
        priceMessage += "\nWhipped Cream: " + addWhippedCream;
        priceMessage += "\nChocolate: " + addChocolate;
        priceMessage += "\nTotal " + NumberFormat.getCurrencyInstance().format(price);
        priceMessage += "\nThank you!";

        */
        return priceMessage;
    }

    /*
     * Calculates the price of the order
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate){
        int totalPrice = 0;

        totalPrice = priceOfOneCup;

        if(addChocolate){
            totalPrice = priceOfOneCup + priceChocolate;
        }else if(addWhippedCream){
            totalPrice = priceOfOneCup + priceWhippedCream;
        }


        return quantity * totalPrice;
    }


    /*
    This method displays the given quantity value on the screen
     */

    private void displayQuantity(int quantity){
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);
    }


    /*
    This method increments the quantity of coffees
     */
    public void increment(View view){
        if(quantity < 100){
            quantity = quantity + 1;
        }else{
            Toast.makeText(MainActivity.this,
                    "You cannot order more than one hundred cups. Sorry.", Toast.LENGTH_LONG).show();
        }
        displayQuantity(quantity);
    }

    /*
    THis method decrements the quantity of coffees
     */
    public void decrement(View view){
        if(quantity > 1){
            quantity = quantity - 1;
        }else{
            Toast.makeText(MainActivity.this,
                    "You cannot order less than one cup. Sorry.", Toast.LENGTH_LONG).show();
        }
        displayQuantity(quantity);
    }


    /*
     * This method gets the current value of WhippedCream checkbox
     */
    public void whippedCreamClicked(View view){
        hasWhippedCrem = !hasWhippedCrem;
    }
}
