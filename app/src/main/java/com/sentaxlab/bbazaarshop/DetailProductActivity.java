package com.sentaxlab.bbazaarshop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailProductActivity extends AppCompatActivity {

    private String name , price , secName , img , sName , saleTag, id , img1,img2,img3,img4 , afterDiscount , shipingCost;
    Button backbtn;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        TextView textView = (TextView) findViewById(R.id.txt_nameProduct);
        TextView textView1 = (TextView)findViewById(R.id.txt_perPrice);
        TextView textView5= (TextView)findViewById(R.id.txt_perAfterPrice);
        TextView textView2 = (TextView)findViewById(R.id.description);
        TextView textView3 = (TextView)findViewById(R.id.txt_nameWithCategory);
        TextView textView4 = (TextView)findViewById(R.id.txt_saleTag);
        TextView shipingCosts = (TextView)findViewById(R.id.txt_shipingCost);
        ImageView imageStatus = (ImageView)findViewById(R.id.img_products);
        Button addToCart = (Button) findViewById(R.id.btn_add);
        backbtn=findViewById(R.id.btn_back);
        backbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        price = getIntent().getStringExtra("price");
        afterDiscount = getIntent().getStringExtra("priceAfterDiscount");
        secName = getIntent().getStringExtra("detail");
        img = getIntent().getStringExtra("img");

        Picasso.with(context).load(img).into(imageStatus);

        textView.setText(name);
        textView1.setText("RS."+price+"/-");
        textView5.setText("RS."+afterDiscount+"/-");
        textView2.setText("Description"+"\n"+ Html.fromHtml(secName));
//        textView4.setText(saleTag+"% Off");
//        shipingCosts.setText("Shiping Cost: "+ shipingCost);


        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Model event_list = new Model();

                boolean flag = true;
                for(Model m: HomeActivity.cartList){
                    if(m.getProductId().equals(id)){
                        flag = false;
                        m.setQuantity(m.getQuantity()+1);
                    }
                }
                if(flag){
                    Model temp = new Model();
                    temp.setProductId(id);
                    temp.setProductName(name);
                    temp.setQuantity(1);
                    temp.setProductPrice(afterDiscount);
                    temp.setProductImg(img);
                    HomeActivity.cartList.add(temp);
                }

                // ((MainActivity)context).update();
//                Toast.makeText(DetailProductActivity.this, "Item Added to cart", Toast.LENGTH_SHORT).show();
                open();
            }
        });


    }
    public void open(){

        AlertDialog alertDialog = new AlertDialog.Builder(this)
//set icon
                .setIcon(R.drawable.gb)
//set title
                .setTitle("Success!!!")
//set message
                .setMessage("Your item add to cart successfully")
//set positive button
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }
}