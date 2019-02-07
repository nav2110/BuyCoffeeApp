package com.viracademy.justjava;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "tag";
    private int quantity = 0;
    private int price=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate");




    }

    public void submitOrder(View view){

        EditText editText=(EditText) findViewById(R.id.email);

        String email=editText.getText().toString();


        if(isValidEmail(email)) {

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Order summary from Virastau Exquisite Limited Edition Coffee");
            intent.putExtra(Intent.EXTRA_TEXT, displayQuantandPrice(quantity));


            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

        }




        Log.v("TAG", "submit order clicked");
    }

    public static boolean isValidEmail(CharSequence email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public void increment(View view) {
        if (quantity == 100) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        displayQuantandPrice(quantity);
    }



    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity <= 0) {
            // Show an error message as a toast
            Toast.makeText(this, R.string.you_cannot_under, Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity - 1;
        displayQuantandPrice(quantity);
    }



    public String displayQuantandPrice(int quantity){

        TextView quantity_digit=(TextView) findViewById(R.id.quantity_digit);
        quantity_digit.setText(""+quantity);

        Log.i("TAG", "setting quantity");

        EditText editText=(EditText) findViewById(R.id.name);

        String name=editText.getText().toString();


        CheckBox chocolateCheckBox = findViewById(R.id.checkChocolate);
        boolean hasChocolate=chocolateCheckBox.isChecked();

        CheckBox whippedCreamCheckBox=findViewById(R.id.checkWhipped);
        boolean hasWhippedCream=whippedCreamCheckBox.isChecked();

        price=5;
        String whippedcream=" Maybe next time.";
        String chocolate=" Maybe next time";

        if (hasChocolate){
            price=price +2;
            chocolate=" YES!";
        }

        if (hasWhippedCream){
            price=price+1;
             whippedcream=" YES!";
        }

        price=price*quantity;

        String message="" +getString(R.string.order_sumary)+" "+
                "\n"+getString(R.string.name, name)+
                "\n"+getString(R.string.quantity2)+" "+quantity+
                "\n"+getString(R.string.add_whipped)+" "+whippedcream+
                "\n"+getString(R.string.add_chocolate)+" "+chocolate+
                "\n"+getString(R.string.total)+" "+NumberFormat.getCurrencyInstance().format(price);

        TextView price_digit=(TextView) findViewById(R.id.price_digit);
        price_digit.setText(message);

        Log.i("TAG", "setting quantity");
        return message;
    }

    public void reset(View view){
        quantity=0;
        price=0;
       displayQuantandPrice(0);
    }

}
