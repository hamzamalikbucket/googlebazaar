package com.sentaxlab.bbazaarshop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailMarketing extends AppCompatActivity implements ServerManager.ServerResponseHandler {

    private ServerManager serverManager;
    String id , img , sellername  , sellerphone , selleremail;
    private TextView productName , productPrice , productDescription;
    private ImageView productImg;
    Context context;
    private ProgressBar progressBar;
    private Button call , email , info;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_marketing);

        id = getIntent().getStringExtra("id");
        Log.e("idsssssss" , id);
        img = getIntent().getStringExtra("img");

        productName = (TextView)findViewById(R.id.txt_nameProduct);
        productPrice = (TextView)findViewById(R.id.txt_perAfterPrice);
        productDescription = (TextView)findViewById(R.id.description);

        productImg = (ImageView)findViewById(R.id.img_products);
        Picasso.with(context).load(img).into(productImg);

        serverManager = new ServerManager(this);
        serverManager.getMarketingProDetail(id , 105);

        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        call = (Button)findViewById(R.id.btn_call);
        email = (Button)findViewById(R.id.btn_email);
        info = (Button)findViewById(R.id.btn_info);
        scrollView = (ScrollView)findViewById(R.id.scroll);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                String p = "tel:"+sellerphone;
                i.setData(Uri.parse(p));
                startActivity(i);
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", selleremail, null));
                startActivity(intent);
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open();
            }
        });





    }

    public void open(){

        AlertDialog alertDialog = new AlertDialog.Builder(this)
//set icon
                .setIcon(R.drawable.gb)
//set title
                .setTitle("Seller Information")
//set message
                .setMessage(sellername + "\n" + sellerphone + "\n" + selleremail)
//set positive button
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }

    @Override
    public void requestFinished(String response, int requestTag) {

        try {

            JSONObject json = new JSONObject(response);
            String name = json.getString("title");
            String price = json.getString("price");
            String description = json.getString("description");
             sellername = json.getString("seller_name");
             selleremail = json.getString("seller_email");
             sellerphone = json.getString("phone");

            productName.setText(name);
            productPrice.setText("Rs: "+price+"/-");
            productDescription.setText("Description"+"\n"+ Html.fromHtml(description));
            progressBar.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void requestFailed(String errorMessage, int requestTag) {

    }

    @Override
    public void onClick(View v) {

    }
}