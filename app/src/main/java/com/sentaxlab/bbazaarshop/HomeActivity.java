package com.sentaxlab.bbazaarshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    public static List<Model> cartList =new ArrayList<>();
    int count=0;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHome,
                    new FragmentHome()).commit();
        }

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHome,
                                new FragmentHome()).commit();
                        break;
                    case R.id.category:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHome,
                                new FragmentCategory()).commit();
                        break;
                    case R.id.account:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHome,
                                new FragmentAccount()).commit();
                        break;

                    case R.id.cart:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHome,
                                new FragmentCart()).commit();
                        break;

                    case R.id.wanted:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHome,
                                new FragmentWanted()).commit();
                        break;

                }
                return true;
            }


        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return true;
    }

    public void update(){

        count++;

        Log.e("aaaaaaaaasssdd",count+"");

//        textCartItemCount.setText(String.valueOf(count));
//        textCartItemCount.setVisibility(View.VISIBLE);

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
       // Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        dialouge();


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int x = 0;
        for(Model m : cartList){
            x += m.getQuantity();
        }
        count = x;
        Log.e("onresule ", "called");
//        if(textCartItemCount != null) {
//            textCartItemCount.setText(String.valueOf(count));
//            textCartItemCount.setVisibility(View.VISIBLE);
//        }
    }

    public void dialouge(){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
//set icon
                .setIcon(android.R.drawable.ic_dialog_alert)
//set title
                .setTitle("Are you sure you want to exit ?  ")
//set message
//                .setMessage("User Has Been Registered!!!")
//set positive button
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        finish();

                    }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                })

                .show();
    }




}