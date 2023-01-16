package com.sentaxlab.bbazaarshop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentMarketing extends Fragment implements ServerManager.ServerResponseHandler {


    private GridView listView;
    ArrayList<Model> category = new ArrayList<>();
    private MarketingGrid categoryGrid;
    private ServerManager serverManager;
    private ProgressBar progressBar;
    private String id;
    private String subCat;
    private TextView noItem;

    private String brandId;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_marketing, container, false);

        id = getArguments().getString("catId");
        subCat = getArguments().getString("sub");

//        Log.e("categoryIdsssss" , id);
//        Log.e("subCatessssssss" , subCat);

        brandId = getArguments().getString("brands");


        listView = view.findViewById(R.id.listCategory);
        categoryGrid = new MarketingGrid(getContext(), category);
        listView.setAdapter(categoryGrid);

        progressBar = view.findViewById(R.id.progressBar);
        noItem = view.findViewById(R.id.noItem);

        serverManager = new ServerManager(this);

        serverManager.getMarketingCats(brandId , 104);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getContext(), DetailMarketing.class);
                Model m1 = category.get(i);
                intent.putExtra("id", m1.getProductId());
                intent.putExtra("img",m1.getProductImg());
                startActivity(intent);

            }
        });

        return view;

    }

    @Override
    public void requestFinished(String response, int requestTag) {

        if (requestTag==104) {

            try {
                JSONArray jsonarray = new JSONArray(response);
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonObject = jsonarray.getJSONObject(i);
                    Model bestseller = new Model();
                    bestseller.setProductName(jsonObject.getString("product_name"));
                    bestseller.setProductImg(jsonObject.getString("picture"));
                    bestseller.setProductId(jsonObject.getString("id"));
                    // bestseller.setDetail(jsonObject.getString("details"));
                    bestseller.setProductPrice(jsonObject.getString("price"));
                    bestseller.setQuantity(1);
                    category.add(bestseller);
                    progressBar.setVisibility(View.GONE);

                }
                categoryGrid.notifyDataSetChanged();


            } catch (Exception e) {
                e.printStackTrace();
            }
            if (category.isEmpty()) {
                progressBar.setVisibility(View.GONE);
                noItem.setVisibility(View.VISIBLE);
            }




        }


    }

    @Override
    public void requestFailed(String errorMessage, int requestTag) {

    }

    @Override
    public void onClick(View v) {

    }


    class MarketingGrid extends BaseAdapter {

        Context context;
        ArrayList<Model> contact;
        private ImageView item_image;
        private TextView item_title, item_price , sale , item_Afterprice;

        private Button Addtocart;

        public MarketingGrid(Context context, ArrayList<Model> category) {

            this.context = context;
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
                view = inflater.inflate(R.layout.best_list_design, viewGroup, false);
            }
            item_image = view.findViewById(R.id.saleImg);
            item_title = view.findViewById(R.id.saleitem_title);
            sale = view.findViewById(R.id.txt_saleTxt);
            item_Afterprice = view.findViewById(R.id.saleItem_Afterprice);
            final Model event_list= (Model)getItem(i);
            item_title.setText(event_list.getProductName());
            item_Afterprice.setText("Rs:"+event_list.getProductPrice()+"/-");
            Picasso.with(context).load(event_list.getProductImg()).into(item_image);

            return view;

        }
    }


}