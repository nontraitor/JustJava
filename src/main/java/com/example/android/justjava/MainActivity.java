/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;
import android.content.Intent;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends Activity {
    int quantity=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField=(EditText)findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        //Add Whipped Cream Checkbox
        CheckBox whippedCreamCheckBox= (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        //Add Chocolate Checkbox
        CheckBox chocolateCheckBox= (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price=calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage=createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for "+name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice=5;

        if (addWhippedCream){
            basePrice=basePrice+1;
        }

        if (addChocolate){
            basePrice=basePrice+2;
        }

        return quantity * basePrice;
    }

    /*
    *This method create order summary.
    * @param name of the customer
    * @param price of the order
    * @param addWhippedCream for whether the user wants whipped cream added
    * @param addChocolate for whether the user wants chocolate added
    * @return text summary
     */
    public String createOrderSummary (String name, int price, boolean addWhippedCream, boolean addChocolate){
        String priceMessage="Name: "+name;
        priceMessage+="\nAdd whipped cream? "+addWhippedCream;
        priceMessage+="\nAdd Chocolate? "+addChocolate;
        priceMessage+="\nQuantity: "+quantity;
        priceMessage=priceMessage+"\nPrice: $"+price;
        priceMessage=priceMessage+"\nThank You!";

        return priceMessage;
    }

    public void increment(View view) {
        if (quantity==100){
            //Show an error message as toast
            Toast.makeText(this, "You cannot order more than 100 cup of coffee.", Toast.LENGTH_SHORT).show();
            //Exit this method early since there's nothing left to do
            return;
        }
        quantity=quantity+1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity==1){
            //Show an error message as toast
            Toast.makeText(this, "You cannot order less than one cup of coffee.", Toast.LENGTH_SHORT).show();
            //Exit this method early since there's nothing left to do
            return;
        }
        quantity=quantity-1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}