package com.sentaxlab.bbazaarshop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

public class CheckoutActivity extends AppCompatActivity implements ServerManager.ServerResponseHandler {

    private EditText username , email , fullname , phone , shipingEmail , address , country , city , postalCode;
    private Button placeOrder;
    private String name , emaillog , id;
    private ServerManager serverManager;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        serverManager = new ServerManager(this);

        username = (EditText)findViewById(R.id.edt_username);
        email = (EditText)findViewById(R.id.edt_email);
        fullname = (EditText)findViewById(R.id.edt_fullname);
        phone = (EditText)findViewById(R.id.edt_phone);
        shipingEmail = (EditText)findViewById(R.id.edt_emailshipping);
        address = (EditText)findViewById(R.id.edt_shippingAddress);
        country = (EditText)findViewById(R.id.edt_country);
        city = (EditText)findViewById(R.id.edt_city);
        postalCode = (EditText)findViewById(R.id.edt_postalCode);

        progressBar = (ProgressBar)findViewById(R.id.prgbr);

        SharedPreferences sp = this.getSharedPreferences("user_log_in", MODE_PRIVATE);
        name = sp.getString("name" , "");
        emaillog = sp.getString("email" , "");
        id = sp.getString("id","");
        Log.e("statussssssss" , name);

        username.setText(name);
        email.setText(emaillog);


        placeOrder = (Button)findViewById(R.id.btnPlaceOrder);

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (username.getText().toString().trim().isEmpty()) {
                    username.setError("Required");
                } else if (email.getText().toString().trim().isEmpty()) {
                    email.setError("Required");
                } else if (fullname.getText().toString().trim().isEmpty()) {
                    fullname.setError("Required");
                } else if (phone.getText().toString().trim().isEmpty()) {
                    phone.setError("Required");
                } else if (shipingEmail.getText().toString().trim().isEmpty()) {
                    shipingEmail.setError("Required");
                } else if (address.getText().toString().trim().isEmpty()) {
                    address.setError("Required");
                } else if (country.getText().toString().trim().isEmpty()) {
                    country.setError("Required");
                } else if (city.getText().toString().trim().isEmpty()) {
                    city.setError("Required");
                } else if (postalCode.getText().toString().trim().isEmpty()) {
                    postalCode.setError("Required");
                } else {

                    serverManager.saveOrderData(102, id, String.valueOf(HomeActivity.cartList), "2", "2500", "Cash On Delivery", "shipto", "", email.getText().toString(), username.getText().toString(), "0", "0", "0", phone.getText().toString(), address.getText().toString(), country.getText().toString(), city.getText().toString(), postalCode.getText().toString(), shipingEmail.getText().toString(), fullname.getText().toString(), phone.getText().toString(), address.getText().toString(), country.getText().toString(), city.getText().toString(), postalCode.getText().toString(), "", "0", "0", "0", "Rs", "1", "0", "0");

                    placeOrder.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    HomeActivity.cartList.clear();
                }


            }
        });

    }

    @Override
    public void requestFinished(String response, int requestTag) {


        try {

            JSONObject json = new JSONObject(response);
            String name = json.getString("msg");
            if (name.equals("data added sucessfully")){

                Intent i = new Intent(CheckoutActivity.this , PlaceOrderActivity.class);
                startActivity(i);
                finish();
            }
            else {
                progressBar.setVisibility(View.GONE);
                open();

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }




    }

    public void open(){

        AlertDialog alertDialog = new AlertDialog.Builder(this)
//set icon
                .setIcon(R.drawable.gb)
//set title
                .setTitle("Error!!!")
//set message
                .setMessage("Something went wrong check your internet connection!!!")
//set positive button
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }



    @Override
    public void requestFailed(String errorMessage, int requestTag) {

    }

    @Override
    public void onClick(View v) {

    }
}