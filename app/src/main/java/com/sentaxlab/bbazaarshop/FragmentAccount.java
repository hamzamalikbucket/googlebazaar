package com.sentaxlab.bbazaarshop;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;

public class FragmentAccount extends Fragment {

    private TextView personName , personEmail , signIn , contactUs , aboutUs , privacyPolicy , trackOrder, myOrder, logOut;

    private ImageView personImg;
    private String name , email , status;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        personName = view.findViewById(R.id.txt_name);
        personEmail = view.findViewById(R.id.txt_email);
        signIn = view.findViewById(R.id.signIn);
        contactUs = view.findViewById(R.id.contactUs);
        aboutUs = view.findViewById(R.id.aboutUs);
        privacyPolicy = view.findViewById(R.id.privacyPolicy);
        logOut = view.findViewById(R.id.logout);
        trackOrder = view.findViewById(R.id.trackOrder);
        myOrder = view.findViewById(R.id.myOrder);
        personImg = view.findViewById(R.id.personImage);

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),AboutUsActivity.class);
                intent.putExtra("ab" , "1");
                startActivity(intent);
            }
        });
        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),AboutUsActivity.class);
                intent.putExtra("ab" , "2");
                startActivity(intent);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext() , LoginActivity.class);
                startActivity(intent);
            }
        });

        trackOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent trackOrder = new Intent(getContext(),TrackOrder.class);
                startActivity(trackOrder);

            }
        });
        myOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myorder = new Intent(getContext(),MyOrder.class);
                startActivity(myorder);
            }
        });

        SharedPreferences sp = getActivity().getSharedPreferences("user_log_in", MODE_PRIVATE);
        name = sp.getString("name", "");
        email = sp.getString("email" , "");
        status = sp.getString("status" , "");
        Log.e("statussssssss" , status);

        personName.setText(name);
        personEmail.setText(email);

        if (status.equals("success")){

            personName.setVisibility(View.VISIBLE);
            personEmail.setVisibility(View.VISIBLE);
            signIn.setVisibility(View.GONE);
            logOut.setVisibility(View.VISIBLE);
            myOrder.setVisibility(View.VISIBLE);
        }

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        return view;

    }


    public void logout() {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
//set icon
                .setIcon(android.R.drawable.ic_dialog_alert)
//set title
                .setTitle("Attention!!!")
//set message
                .setMessage("Are You Want Logout ?!!!")

//set negative button
                .setNegativeButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_log_in", MODE_PRIVATE);
                        sharedPreferences.edit().clear().commit();
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);


                    }
                })
                .show();
    }


}
