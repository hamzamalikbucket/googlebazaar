package com.sentaxlab.bbazaarshop;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentCategory extends Fragment implements ServerManager.ServerResponseHandler {

    private ListView listView;
    ArrayList<Model> category=new ArrayList<>();
    private  CategoryGrid categoryGrid;
    private ServerManager serverManager;
    private ProgressBar progressBar;
    private RelativeLayout relativeLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        listView = view.findViewById(R.id.listCategory);
        categoryGrid=new CategoryGrid(getContext(),category);
        listView.setAdapter(categoryGrid);

        progressBar = view.findViewById(R.id.progressBar);

        serverManager = new ServerManager(this);
        serverManager.getCategory(104);

        relativeLayout = view.findViewById(R.id.rel);




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("catId", category.get(i).getCatId());
                FragmentSubCategory fragment = new FragmentSubCategory();
                fragment.setArguments(bundle);
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.root, fragment).commit();
                relativeLayout.setVisibility(View.GONE);
            }
        });

    return view;

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
                        m.setCategoryName(jsonObject.getString("name"));
                        m.setCatId(jsonObject.getString("id"));

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
}

class CategoryGrid extends BaseAdapter {

    Context context;
    ArrayList<Model> contact;
    private TextView productname,productprice;
    private ImageView productimage;

    private Button Addtocart;

    public CategoryGrid(Context context, ArrayList<Model> category) {
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
            view = inflater.inflate(R.layout.category_list_design, viewGroup, false);
        }
        productname=view.findViewById(R.id.categoryName);
        final Model event_list= (Model)getItem(i);
        productname.setText(event_list.getCategoryName());
        return view;

    }
}

