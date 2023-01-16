package com.sentaxlab.bbazaarshop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment implements ServerManager.ServerResponseHandler {
    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View currentFocusedView = activity.getCurrentFocus();
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private ImageView banner;
    private ViewFlipper viewFlipper;
    private ServerManager serverManager;
    private List<ImageView> imgList = new ArrayList<>();

    private GridView allProductGrid;
    private  MainGrid mainGridAdapter;
    ArrayList<Model> maingrid = new ArrayList<>();

    RecyclerView featuredProducts;
    ArrayList<Model> recycleTop = new ArrayList<>();
    private TopListAdapter mytopAdapter;

    RecyclerView bestSeller;
    ArrayList<Model> recycleBest = new ArrayList<>();
    private BestListAdapter bestListAdapter;

    private ProgressBar progressBar;
    private RelativeLayout relativeLayout;
    private EditText searchtext;
    private Button searchBtn;

    private LinearLayout topBrand , localBrand , globalBrand , digitalProduct , jobs , freelancing , realestate , usedProducts , propertySale , propertyRent , allCategory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewFlipper = (ViewFlipper) view.findViewById(R.id.flipper);
        serverManager = new ServerManager(this);
        serverManager.mainBanner(100);
        serverManager.getFeatured(101);
        serverManager.getBest(102);
        serverManager.getProduct(103);



        banner = view.findViewById(R.id.banner_img);
        featuredProducts=view.findViewById(R.id.recycle_top_slider);
        progressBar = view.findViewById(R.id.progressbar);

        bestSeller = view.findViewById(R.id.recycle_best_slider);

        allProductGrid = view.findViewById(R.id.allProductGrid);
        mainGridAdapter=new MainGrid(getContext(),maingrid);
        allProductGrid.setAdapter(mainGridAdapter);



        searchtext=view.findViewById(R.id.testSearch);
        searchBtn=view.findViewById(R.id.btn_search);

      searchBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if (searchtext.getText().toString().isEmpty()) {
                  Toast toast = Toast.makeText(getContext(), "Enter Some text", Toast.LENGTH_SHORT);
                  toast.show();
              }
              else {

                  Bundle bundle = new Bundle();
                  bundle.putString("name", searchtext.getText().toString());
                  bundle.putString("flag", "1");
                  FragmentProducts fragment = new FragmentProducts();
                  fragment.setArguments(bundle);
                  FragmentManager fm = getFragmentManager();
                  fm.beginTransaction().replace(R.id.root, fragment).commit();
                  hideKeyboard(getActivity());
                  relativeLayout.setVisibility(View.GONE);

              }


          }
      });


        allProductGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent= new Intent(getContext(),DetailProductActivity.class);
                Model m1 = maingrid.get(i);
                intent.putExtra("id",m1.getProductId());
                intent.putExtra("name",m1.getProductName());
                intent.putExtra("img",m1.getProductImg());
                intent.putExtra("priceAfterDiscount" , m1.getProductPrice());
                intent.putExtra("price" , m1.getPreviousPrice());
                intent.putExtra("detail" , m1.getDetail());
                Log.e("img" , m1.getProductImg());

                startActivity(intent);

            }
        });

        topBrand = view.findViewById(R.id.topBrand);
        localBrand = view.findViewById(R.id.localBrand);
        globalBrand = view.findViewById(R.id.globalBrand);
        digitalProduct = view.findViewById(R.id.digitalBrand);

        jobs = view.findViewById(R.id.jobs);
        freelancing = view.findViewById(R.id.freelancing);
        realestate = view.findViewById(R.id.realEstate);
        usedProducts = view.findViewById(R.id.usedProducts);
        propertyRent = view.findViewById(R.id.propertyrent);
        propertySale = view.findViewById(R.id.propertysale);
        allCategory = view.findViewById(R.id.allCats);



        relativeLayout = view.findViewById(R.id.rel);



        topBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("brands", "37");
                FragmentProducts fragment = new FragmentProducts();
                fragment.setArguments(bundle);
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.root, fragment).commit();
                relativeLayout.setVisibility(View.GONE);

            }
        });

        localBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("brands", "38");
                FragmentProducts fragment = new FragmentProducts();
                fragment.setArguments(bundle);
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.root, fragment).commit();
                relativeLayout.setVisibility(View.GONE);

            }
        });

        globalBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("brands", "39");
                FragmentProducts fragment = new FragmentProducts();
                fragment.setArguments(bundle);
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.root, fragment).commit();
                relativeLayout.setVisibility(View.GONE);

            }
        });


        digitalProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("brands", "40");
                FragmentProducts fragment = new FragmentProducts();
                fragment.setArguments(bundle);
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.root, fragment).commit();
                relativeLayout.setVisibility(View.GONE);

            }
        });

        jobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("brands", "6");
                FragmentMarketing fragment = new FragmentMarketing();
                fragment.setArguments(bundle);
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.root, fragment).commit();
                relativeLayout.setVisibility(View.GONE);

            }
        });

        freelancing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("brands", "15");
                FragmentMarketing fragment = new FragmentMarketing();
                fragment.setArguments(bundle);
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.root, fragment).commit();
                relativeLayout.setVisibility(View.GONE);

            }
        });

        realestate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("brands", "4");
                FragmentMarketing fragment = new FragmentMarketing();
                fragment.setArguments(bundle);
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.root, fragment).commit();
                relativeLayout.setVisibility(View.GONE);

            }
        });

        usedProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("brands", "16");
                FragmentMarketing fragment = new FragmentMarketing();
                fragment.setArguments(bundle);
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.root, fragment).commit();
                relativeLayout.setVisibility(View.GONE);

            }
        });

        propertyRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("brands", "14");
                FragmentMarketing fragment = new FragmentMarketing();
                fragment.setArguments(bundle);
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.root, fragment).commit();
                relativeLayout.setVisibility(View.GONE);


            }
        });

        propertySale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("brands", "4");
                FragmentMarketing fragment = new FragmentMarketing();
                fragment.setArguments(bundle);
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.root, fragment).commit();
                relativeLayout.setVisibility(View.GONE);

            }
        });

        allCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("brands", "16");
                FragmentCategory fragment = new FragmentCategory();
                fragment.setArguments(bundle);
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.root, fragment).commit();
                relativeLayout.setVisibility(View.GONE);
            }
        });




        mytopAdapter = new TopListAdapter(recycleTop,getContext());
        featuredProducts.setAdapter(mytopAdapter);
        featuredProducts.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager2 = new LinearLayoutManager(getContext());
        MyLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        if (recycleTop.size() > 0 & featuredProducts != null) {
        }
        featuredProducts.setLayoutManager(MyLayoutManager2);

        bestListAdapter = new BestListAdapter(recycleBest,getContext());
        bestSeller.setAdapter(bestListAdapter);
        bestSeller.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager3 = new LinearLayoutManager(getContext());
        MyLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        if (recycleBest.size() > 0 & bestSeller != null) {
        }
        bestSeller.setLayoutManager(MyLayoutManager3);


        return view;
    }

    public void flipperImg(String images){
        Log.e("ohh", "Noo");
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        //       imageView.setBackgroundResource(images);
        //Picasso.with(getContext()).load(images).error(R.drawable.logo).into(imageView);
        UrlImageViewHelper.setUrlDrawable(imageView, images,R.drawable.gb);
        viewFlipper.addView(imageView);

    }

    @Override
    public void requestFinished(String response, int requestTag) {

        if (requestTag==100){
            try {
                JSONArray data = new JSONArray(response);
                String imgu = "https://googlebazar.pk/assets/images/sliders/";
                for (int i = 0; i < data.length(); i++) {
                    JSONObject j = data.getJSONObject(i);
                    String imageUrl = imgu+j.getString("photo");
                    ImageView imageView = new ImageView(getContext());
                    Picasso.with(getContext()).load(imageUrl).error(R.drawable.gb).into(imageView);
                    imgList.add(imageView);
                    flipperImg(imageUrl);
                }
                viewFlipper.setAutoStart(true);
                viewFlipper.setFlipInterval(2000);
                viewFlipper.startFlipping();
                //viewFlipper.setFlipInterval(4000);
                //viewFlipper.setAutoStart(true);
                viewFlipper.setInAnimation(getActivity(),android.R.anim.slide_in_left);
                viewFlipper.setOutAnimation(getActivity(),android.R.anim.slide_out_right);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        if(requestTag==101) {

            Log.e("eeeeeeee", response);
            String baseurl = "https://googlebazar.pk/assets/images/thumbnails/";

            try {
                JSONArray jsonarray = new JSONArray(response);
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonObject = jsonarray.getJSONObject(i);
                    Model bestseller = new Model();
                    bestseller.setProductName(jsonObject.getString("name"));
                    bestseller.setProductImg(baseurl+jsonObject.getString("thumbnail"));
                    bestseller.setProductId(jsonObject.getString("id"));
                    bestseller.setDetail(jsonObject.getString("details"));
                    bestseller.setProductPrice(jsonObject.getString("price"));
                    bestseller.setQuantity(1);
                    recycleTop.add(bestseller);
                    progressBar.setVisibility(View.GONE);
                    featuredProducts.setVisibility(View.VISIBLE);
                    banner.setVisibility(View.VISIBLE);

                }
                mytopAdapter.notifyDataSetChanged();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(requestTag==102) {

            Log.e("eeeeeeee", response);
            String baseurl = "https://googlebazar.pk/assets/images/thumbnails/";

            try {
                JSONArray jsonarray = new JSONArray(response);
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonObject = jsonarray.getJSONObject(i);
                    Model bestseller = new Model();
                    bestseller.setProductName(jsonObject.getString("name"));
                    bestseller.setProductImg(baseurl+jsonObject.getString("thumbnail"));
                    bestseller.setProductId(jsonObject.getString("id"));
                    bestseller.setDetail(jsonObject.getString("details"));
                    bestseller.setProductPrice(jsonObject.getString("price"));
                    bestseller.setQuantity(1);
                    recycleBest.add(bestseller);
                  //  progressBar.setVisibility(View.GONE);
                    bestSeller.setVisibility(View.VISIBLE);

                }
                bestListAdapter.notifyDataSetChanged();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }



        if(requestTag==103) {
            String baseurl = "https://googlebazar.pk/assets/images/thumbnails/";

            try {
                JSONArray jsonarray = new JSONArray(response);
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonObject = jsonarray.getJSONObject(i);
                    Model p = new Model();
                    p.setProductImg(baseurl+jsonObject.getString("thumbnail"));
                    p.setProductName(jsonObject.getString("name"));
                    p.setProductPrice(jsonObject.getString("price"));
                    p.setDetail(jsonObject.getString("details"));
                    p.setProductId(jsonObject.getString("id"));
                    maingrid.add(p);


                }
                mainGridAdapter.notifyDataSetChanged();

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

class TopListAdapter extends RecyclerView.Adapter<TopListAdapter.NewsHolder> {

    private ArrayList<Model> list;
    Context context;

    public TopListAdapter(ArrayList<Model> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_list_design, parent, false);
        NewsHolder holder1 = new NewsHolder(view);
        return holder1;



    }


    @Override
    public void onBindViewHolder(@NonNull final NewsHolder holder, final int position) {

        Picasso.with(context).load(list.get(position).getProductImg()).into(holder.item_image);
        holder.item_title.setText(list.get(position).getProductName());
        holder.item_Afterprice.setText("Rs:"+list.get(position).getProductPrice()+"/-");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("resonseeeeeeeeeeeeeee",list.get(position)+"");
                Intent intent= new Intent(context,DetailProductActivity.class);
                Model m1 = list.get(position);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("id",m1.getProductId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("name",m1.getProductName());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("img",m1.getProductImg());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("priceAfterDiscount" , m1.getProductPrice());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("price" , m1.getPreviousPrice());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("detail" , m1.getDetail());
             //   Log.e("img" , m1.getProductImg());
                context.startActivity(intent);

            }
        });


//        holder.btn_addtocart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean flag = true;
//                for(Model m: MainActivity.cartList){
//                    if(m.getProduct_ID().equals(list.get(position).getProduct_ID())){
//                        flag = false;
//                        m.setQuantity(m.getQuantity()+1);
//                    }
//                }
//                if(flag){
//                    Model temp = new Model();
//                    temp.setProduct_ID(list.get(position).getProduct_ID());
//                    temp.setProductName(list.get(position).getProductName());
//                    temp.setProduct_desc(list.get(position).getProduct_desc());
//                    temp.setQuantity(1);
//                    temp.setProduct_price(list.get(position).getPriceAfterDiscount());
//                    temp.setProductImg(list.get(position).getProductImg());
//                    MainActivity.cartList.add(temp);
//                }
//
//                //((MainActivity).update();
//                Toast.makeText(context, "Item Added to cart", Toast.LENGTH_SHORT).show();
//
//
//            }
//        });
//



    }




    @Override
    public int getItemCount() {
        return list.size();
    }


    public class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView item_image;
        //ProgressBar item_progressBar;
        TextView item_title, item_price , sale , item_Afterprice;
        Button btn_addtocart;
  //      MaterialFavoriteButton heartIcon;

        public NewsHolder(@NonNull View view) {
            super(view);
            view.setClickable(true);
            view.setOnClickListener(this);

            item_image = view.findViewById(R.id.saleImg);
            item_title = view.findViewById(R.id.saleitem_title);
            sale = view.findViewById(R.id.txt_saleTxt);
            item_Afterprice = view.findViewById(R.id.saleItem_Afterprice);
        }

        @Override
        public void onClick(View v) {
//            Intent intent=new Intent(context,SaleProduct.class);
//            context.startActivity(intent);

        }
    }
}

class BestListAdapter extends RecyclerView.Adapter<BestListAdapter.NewsHolder> {

    private ArrayList<Model> list;
    Context context;

    public BestListAdapter(ArrayList<Model> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.best_list_design, parent, false);
        NewsHolder holder1 = new NewsHolder(view);
        return holder1;

    }


    @Override
    public void onBindViewHolder(@NonNull final NewsHolder holder, final int position) {

        Picasso.with(context).load(list.get(position).getProductImg()).into(holder.item_image);
        holder.item_title.setText(list.get(position).getProductName());
        holder.item_Afterprice.setText("Rs:"+list.get(position).getProductPrice()+"/-");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("resonseeeeeeeeeeeeeee",list.get(position)+"");
                Intent intent= new Intent(context,DetailProductActivity.class);
                Model m1 = list.get(position);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("id",m1.getProductId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("name",m1.getProductName());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("img",m1.getProductImg());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("priceAfterDiscount" , m1.getProductPrice());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("price" , m1.getPreviousPrice());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("detail" , m1.getDetail());

                Log.e("img" , m1.getProductImg());
                context.startActivity(intent);

            }
        });


//        holder.btn_addtocart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean flag = true;
//                for(Model m: MainActivity.cartList){
//                    if(m.getProduct_ID().equals(list.get(position).getProduct_ID())){
//                        flag = false;
//                        m.setQuantity(m.getQuantity()+1);
//                    }
//                }
//                if(flag){
//                    Model temp = new Model();
//                    temp.setProduct_ID(list.get(position).getProduct_ID());
//                    temp.setProductName(list.get(position).getProductName());
//                    temp.setProduct_desc(list.get(position).getProduct_desc());
//                    temp.setQuantity(1);
//                    temp.setProduct_price(list.get(position).getPriceAfterDiscount());
//                    temp.setProductImg(list.get(position).getProductImg());
//                    MainActivity.cartList.add(temp);
//                }
//
//                //((MainActivity).update();
//                Toast.makeText(context, "Item Added to cart", Toast.LENGTH_SHORT).show();
//
//
//            }
//        });
//



    }




    @Override
    public int getItemCount() {
        return list.size();
    }


    public class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView item_image;
        TextView item_title, item_price , sale , item_Afterprice;
        Button btn_addtocart;
        //      MaterialFavoriteButton heartIcon;

        public NewsHolder(@NonNull View view) {
            super(view);
            view.setClickable(true);
            view.setOnClickListener(this);

            item_image = view.findViewById(R.id.saleImg);
            item_title = view.findViewById(R.id.saleitem_title);
            sale = view.findViewById(R.id.txt_saleTxt);
            item_Afterprice = view.findViewById(R.id.saleItem_Afterprice);
        }

        @Override
        public void onClick(View v) {
//            Intent intent=new Intent(context,SaleProduct.class);
//            context.startActivity(intent);

        }
    }
}

class MainGrid extends BaseAdapter{


    Context context;
    ArrayList<Model> contact;
    private TextView productname,productprice;
    private ImageView productimage;

    private Button Addtocart;

    public MainGrid(Context context, ArrayList<Model> maingrid) {

        this.context = context;
        this.contact = maingrid;

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
            view = inflater.inflate(R.layout.recycle_list_design, viewGroup, false);
        }
        productimage=view.findViewById(R.id.saleImg);
        productname=view.findViewById(R.id.saleitem_title);
        productprice = view.findViewById(R.id.saleItem_Afterprice);

        final Model event_list= (Model)getItem(i);

        productname.setText(event_list.getProductName());
        productprice.setText("Rs:"+event_list.getProductPrice()+"/-");
        Picasso.with(context).load(contact.get(i).getProductImg()).into(productimage);
        return view;

    }
}




