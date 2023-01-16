package com.sentaxlab.bbazaarshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TrackOrder extends AppCompatActivity implements ServerManager.ServerResponseHandler {

    private ListView listView;
    ArrayList<Model> category=new ArrayList<>();
    private CategoryGrid categoryGrid;
    private ServerManager serverManager;
    private ProgressBar progressBar;
    private RelativeLayout relativeLayout;
    private EditText trackingOrder;
    private Button trackNow;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);

        trackingOrder = (EditText)findViewById(R.id.email_login);
        trackNow = (Button)findViewById(R.id.btnLogin_id);

        listView = findViewById(R.id.trackinglist);
        categoryGrid=new CategoryGrid(TrackOrder.this,category);
        listView.setAdapter(categoryGrid);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        serverManager = new ServerManager(this);
        trackNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                serverManager.getTracking(125,trackingOrder.getText().toString());
                progressBar.setVisibility(View.VISIBLE);
                trackNow.setVisibility(View.GONE);
            }
        });




    }

    @Override
    public void requestFinished(String response, int requestTag) {

        if (requestTag == 125) {
            try {
                String baseurl = "https://googlebazar.pk/assets/images/categories/";
                JSONArray jsonarray = new JSONArray(response);
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonObject = jsonarray.getJSONObject(i);
                    Model m = new Model();
                    m.setTitle(jsonObject.getString("title"));
                    m.setDescription(jsonObject.getString("text"));
                    category.add(m);
                    listView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    trackNow.setVisibility(View.VISIBLE);
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

        public CategoryGrid(TrackOrder trackOrder, ArrayList<Model> category) {

            this.context = trackOrder;
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
                view = inflater.inflate(R.layout.tracking_list, viewGroup, false);
            }
            orderNo=view.findViewById(R.id.txt_title);
            orderMethod=view.findViewById(R.id.txt_description);

            final Model event_list= (Model)getItem(i);
            orderNo.setText(event_list.getTitle());
            orderMethod.setText(event_list.getDescription());

            return view;

        }
    }

}