package com.sentaxlab.bbazaarshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class AboutUsActivity extends AppCompatActivity implements ServerManager.ServerResponseHandler{

    private TextView text;
    ServerManager serverManager;
    private ProgressBar progressBar;
    private String about , privacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        text = (TextView)findViewById(R.id.txt);
        progressBar = (ProgressBar)findViewById(R.id.progressbar);

        serverManager = new ServerManager(this);


        about = getIntent().getStringExtra("ab");
       // privacy = getIntent().getStringExtra("pp");

        if (about.equals("1")){
            serverManager.getAboutUs(121);
        }
        else if (about.equals("2")){
            serverManager.getPrivacy(121);
        }
        else {
            Log.e("aaaaa",about);
        }

    }

    @Override
    public void requestFinished(String response, int requestTag) {

        if (requestTag == 121) {
            try {
                JSONArray jsonarray = new JSONArray(response);
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonObject = jsonarray.getJSONObject(i);
                    Model brand = new Model();
                    String detail = (jsonObject.getString("details"));
                    text.setText(Html.fromHtml(detail));
                    progressBar.setVisibility(View.GONE);

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void requestFailed(String errorMessage, int requestTag) {

    }

    @Override
    public void onClick(View v) {

    }
}