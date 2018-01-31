package com.pharogrammer.fedora.millionarabprog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private int numberOfCofees = 0 ;
    private String summaryText ;
    private boolean hasWhippedCream = false;
    private boolean hasChocolate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //check if there's saved state or not if there's one it will get saved data
        if (savedInstanceState != null) {
            numberOfCofees = savedInstanceState.getInt("Coffees");
            summaryText = savedInstanceState.getString("SummaryText");
            hasWhippedCream = savedInstanceState.getBoolean("WhippedCream");
            hasChocolate = savedInstanceState.getBoolean("Chocolate");
        }

        setContentView(R.layout.activity_main);

        //initialize views
        Button orderButton = findViewById(R.id.btn_order);
        Button increment = findViewById(R.id.btn_increment);
        Button decrement = findViewById(R.id.btn_decrement);
        final TextView quantityText = findViewById(R.id.tv_quantity);
        TextView priceText = findViewById(R.id.tv_order_summary);
        final CheckBox whippedCreamBox = findViewById(R.id.cb_whipped_cream);
        final CheckBox chocolateBox = findViewById(R.id.cb_chocolate);

        //set saved values if present
        quantityText.setText(String.valueOf(numberOfCofees));
        priceText.setText(summaryText);
        whippedCreamBox.setChecked(hasWhippedCream);
        chocolateBox.setChecked(hasChocolate);


        //event of plus button
        increment.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                numberOfCofees +=1;
                quantityText.setText(String.valueOf(numberOfCofees));
            }
        });

        //event of minus button
        decrement.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (numberOfCofees > 0)
                    numberOfCofees -= 1;
                else
                    Toast.makeText(MainActivity.this, "Quantity can not be below Zero", Toast.LENGTH_SHORT).show();
                quantityText.setText(String.valueOf(numberOfCofees));
            }
        });


        //carried out after clicking the order button
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText clientName = findViewById(R.id.et_name);
                String message = getOrderSummary(clientName.getText().toString().trim(), numberOfCofees , 5.5f);
                display(message);
            }
        });

        // Set click listener for both of the checkboxes to get the boolean values from them
        whippedCreamBox.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                hasWhippedCream = whippedCreamBox.isChecked();
            }
        });

        chocolateBox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                hasChocolate = chocolateBox.isChecked();
            }
        });

    }

    //savedInstanceState saves some values important when rotating the screen or destroying it
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Coffees" , numberOfCofees);
        outState.putString("SummaryText" , summaryText);
        outState.putBoolean("WhippedCream", hasWhippedCream);
        outState.putBoolean("Chocolate" , hasChocolate);
    }

    /**
     * this method used to change the Value of the text view with the value of the parameter
     * @param message takes the message coming from getOrderSummary method to display it in the textView
     */
    public void display(String message){
        TextView summaryTextView = findViewById(R.id.tv_order_summary);
        summaryTextView.setText(message);
    }

    /**
     *
     * @param name of the client
     * @param num -ber of coffees
     * @param price of one coffee
     * @return String summaryText
     */
    public String getOrderSummary(String name , int num , float price){
        summaryText = "Name : " + name ;
        summaryText += "\nAdd whipped cream or not ?" + hasWhippedCream;
        summaryText += "\nAdd chocolate or not ?" + hasChocolate;
        summaryText += "\nQuantity : " + num ;
        summaryText += "\nTotal : $" + num * price ;
        summaryText += "\nthank you :)";
        return summaryText;
    }
}
