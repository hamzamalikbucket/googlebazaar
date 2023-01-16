package com.sentaxlab.bbazaarshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyOrder extends AppCompatActivity  implements ServerManager.ServerResponseHandler{

    private ListView listView;
    ArrayList<Model> category=new ArrayList<>();
    private  CategoryGrid categoryGrid;
    private ServerManager serverManager;
    private ProgressBar progressBar;
    private RelativeLayout relativeLayout;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        listView = findViewById(R.id.listCategory);
        categoryGrid=new CategoryGrid(MyOrder.this,category);
        listView.setAdapter(categoryGrid);

        progressBar = findViewById(R.id.progressBar);



        SharedPreferences sp = getApplication().getSharedPreferences("user_log_in", MODE_PRIVATE);
        user_id = sp.getString("user_id", "");


        serverManager = new ServerManager(this);
        serverManager.getMyOrder(104 ,user_id );
    }

    @Override
    public void requestFinished(String response, int requestTag) {

        if (requestTag == 104) {
            try {
                String baseurl = "https://googlebazar.pk/assets/images/categories/";
                JSONArray jsonarray = new JSONArray(response);
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonObject = jsonarray.getJSONObject(i);
                    Model m = new Model();
                    m.setOrderNo(jsonObject.getString("order_number"));
                    m.setOrderLocation(jsonObject.getString("customer_country"));
                    m.setOrderAmount(jsonObject.getString("pay_amount"));
                    m.setOrderMethod(jsonObject.getString("method"));
                    m.setStatus(jsonObject.getString("status"));
                    category.add(m);
                    progressBar.setVisibility(View.GONE);
                }
                categoryGrid.notifyDataSetChanged();
            } catch (JSONException e) {
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


    class CategoryGrid extends BaseAdapter {

        Context context;
        ArrayList<Model> contact;
        private TextView orderNo,orderMethod , orderLocation , orderPrice , orderStatus;
        private ImageView productimage;

        private Button Addtocart;

        public CategoryGrid(MyOrder myOrder, ArrayList<Model> category) {

            this.context = myOrder;
            this.contact = category;
        }


        @Override
        public int getCount() {
            return contact.size();
        }

        @Override
        public Object getItem(int i) {
            return contact.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (view == null) {
                view = inflater.inflate(R.layout.order_list, viewGroup, false);
            }
            orderNo=view.findViewById(R.id.txt_order);
            orderLocation=view.findViewById(R.id.txt_location);
            orderMethod=view.findViewById(R.id.txt_method);
            orderPrice=view.findViewById(R.id.txt_amount);
            orderStatus=view.findViewById(R.id.txt_status);
            final Model event_list= (Model)getItem(i);
            orderNo.setText("Order No:"+event_list.getOrderNo());
            orderMethod.setText("Order Method: "+event_list.getOrderMethod());
            orderLocation.setText("Delivery: "+event_list.getOrderLocation());
            orderPrice.setText("Total Amount: "+event_list.getOrderAmount());
            orderStatus.setText(event_list.getStatus());
            return view;

        }
    }


}