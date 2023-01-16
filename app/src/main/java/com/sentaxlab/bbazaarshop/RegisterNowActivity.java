package com.sentaxlab.bbazaarshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

public class RegisterNowActivity extends AppCompatActivity implements ServerManager.ServerResponseHandler {

    private EditText fname,lname,country,email,password,phone;
    private Button register;
    private ServerManager serverManager;
    private ProgressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_now);

        fname = (EditText)findViewById(R.id.edt_fName);
        lname = (EditText)findViewById(R.id.edt_lName);
        country = (EditText)findViewById(R.id.edt_gender);
        email = (EditText)findViewById(R.id.edt_email);
        password = (EditText)findViewById(R.id.edt_password);
        phone = (EditText)findViewById(R.id.edt_phone);
        register=(Button)findViewById(R.id.btn_register);
        pb = (ProgressBar)findViewById(R.id.pb);

        serverManager=new ServerManager(this);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register.setVisibility(View.GONE);
                pb.setVisibility(View.VISIBLE);
                serverManager.SignUp(111,fname.getText().toString(),country.getText().toString(),email.getText().toString(),password.getText().toString(),phone.getText().toString());


            }
        });


    }

    @Override
    public void requestFinished(String response, int requestTag) {


        try {
            //JSONArray jsonarray = new JSONArray(response);
            JSONObject jsonObject1 = new JSONObject(response);
            for (int i = 0; i < jsonObject1.length(); i++) {


                String status = (jsonObject1.getString("status"));
                if (status.equals("sucess")){
                    dialouge();
                    fname.setText("");
                    lname.setText("");
                    country.setText("");
                    email.setText("");
                    password.setText("");
                    phone.setText("");
                }

                else {

                    dialouge1();
                    pb.setVisibility(View.GONE);
                    register.setVisibility(View.VISIBLE);
                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void requestFailed(String errorMessage, int requestTag) {

    }

    @Override
    public void onClick(View v) {

    }

    public void dialouge(){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
//set icon
                .setIcon(android.R.drawable.ic_dialog_alert)
//set title
                .setTitle("Success !!!!")
//set message
                .setMessage("User Has Been Registered!!!")
//set positive button
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(getApplicationContext() ,LoginActivity.class );
                        startActivity(intent);

                    }
                })

                .show();
    }


    public void dialouge1(){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
//set icon
                .setIcon(android.R.drawable.ic_dialog_alert)
//set title
                .setTitle("Error !!!!")
//set message
                .setMessage("Email Already Exists!!!!")
//set positive button
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(getApplicationContext() ,LoginActivity.class );
                        startActivity(intent);

                    }
                })

                .show();
    }
}