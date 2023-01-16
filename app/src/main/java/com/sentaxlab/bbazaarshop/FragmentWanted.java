package com.sentaxlab.bbazaarshop;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

public class FragmentWanted extends Fragment implements ServerManager.ServerResponseHandler {

    private EditText name , phone , email , message;
    private Button submit;
    ServerManager serverManager;
    private ProgressBar progressBar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wanted, container, false);

        name = (EditText)view.findViewById(R.id.edt_fName);
        email = (EditText)view.findViewById(R.id.edt_email);
        phone = (EditText)view.findViewById(R.id.edt_phone);
        message = (EditText)view.findViewById(R.id.edt_message);

        progressBar = (ProgressBar)view.findViewById(R.id.pb);
        submit = (Button)view.findViewById(R.id.btn_register);


        serverManager = new ServerManager(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (name.getText().toString().trim().length()==0){
                    name.setError("required");
                }
                else if (email.getText().toString().trim().length()==0){
                    email.setError("required");
                }
                else if (phone.getText().toString().trim().length()==0){
                    phone.setError("required");
                }
                else if (message.getText().toString().trim().length()==0){
                    message.setError("required");
                }
                else {
                    submit.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    serverManager.Wanted(52 , name.getText().toString() , email.getText().toString() , phone.getText().toString() , message.getText().toString());
                }


            }
        });




        return view;
    }

    @Override
    public void requestFinished(String response, int requestTag) {

        try {
            JSONArray jsonarray = new JSONArray(response);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonObject = jsonarray.getJSONObject(i);

                String status = (jsonObject.getString("status"));
                if (status.equals("sucess")){
                    dialouge();
                    name.setText("");
                    email.setText("");
                    phone.setText("");
                    message.setText("");
                    progressBar.setVisibility(View.VISIBLE);
                    submit.setVisibility(View.VISIBLE);
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
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
//set icon
                .setIcon(android.R.drawable.ic_dialog_alert)
//set title
                .setTitle("Success !!!!")
//set message
                .setMessage("Your application submitted our support contact with you soon!!!")
//set positive button
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })

                .show();
    }
}
