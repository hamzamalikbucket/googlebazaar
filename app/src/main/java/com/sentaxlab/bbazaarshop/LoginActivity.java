package com.sentaxlab.bbazaarshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements ServerManager.ServerResponseHandler {

    EditText userLogin_Email, userLogin_pass;
    Button btnLogin;
    TextView signip;
    ProgressBar pb;
    private ServerManager serverManager;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userLogin_Email = findViewById(R.id.email_login);
        userLogin_pass = findViewById(R.id.password_login);
        btnLogin = findViewById(R.id.btnLogin_id);
        pb = (ProgressBar) findViewById(R.id.pb);


        serverManager = new ServerManager(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userLogin_Email.getText().toString().length() == 0 && userLogin_pass.getText().toString().length() == 0) {
                    Toast toast = Toast.makeText(LoginActivity.this, "Enter Valid Email & Password", Toast.LENGTH_SHORT);
                    toast.show();
                }

                pb.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.GONE);
                serverManager.Login(222, userLogin_Email.getText().toString(), userLogin_pass.getText().toString());
            }
        });

        signip = (TextView) findViewById(R.id.txt_signup);

        signip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterNowActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void requestFinished(String response, int requestTag) {


        try {
          /*  JSONArray jsonarray = new JSONArray(response);*/
            JSONObject jsonObject = new JSONObject(response);
            for (int i = 0; i < jsonObject.length(); i++) {
               // JSONObject jsonObject = jsonarray.getJSONObject(i);

                String status = (jsonObject.getString("status"));
                if (status.equals("success")) {

                    JSONObject jsonObject1 = jsonObject.getJSONObject("user");

                    String user_id = jsonObject1.getString("id");
                    String name = jsonObject1.getString("name");
                    String email = jsonObject1.getString("email");
                    String contact = jsonObject1.getString("phone");
                    String photo = jsonObject1.getString("photo");
                    Log.e("aaaaaaaa", user_id);

                    sharedPreferences = getSharedPreferences("user_log_in", MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putString("status", status);
                    editor.putString("user_id", user_id);
                    editor.putString("name", name);
                    editor.putString("photo", photo);
                    editor.putString("email", email);
                    editor.putString("contact", contact);
                    editor.commit();
                    Log.e("userssssssssss", user_id);


                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);

                } else {

                    dialouge();

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

    public void dialouge() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Error !!!!")
                .setMessage("Email Or Password Not Valid !!!!!")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .show();
    }

}
