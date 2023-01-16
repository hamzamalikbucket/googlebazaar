package com.sentaxlab.bbazaarshop;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class FragmentCart extends Fragment {

    private ListView listView;
    private TextView subTotal;
    CartListAdapter cartListAdapter;
    ArrayList<Model> cartlist=new ArrayList<>();
    private Button proceed;
    private TextView noItemCart;
    double total = 0;
    private String status;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);


        listView=view.findViewById(R.id.list_cart);
        cartListAdapter=new CartListAdapter(getContext(),cartlist , FragmentCart.this);
        listView.setAdapter(cartListAdapter);

        subTotal = (TextView)view.findViewById(R.id.txt_total_price);
        proceed = (Button)view.findViewById(R.id.btnProccedd);
        noItemCart = (TextView)view.findViewById(R.id.noItemCart);

        SharedPreferences sp = getActivity().getSharedPreferences("user_log_in", MODE_PRIVATE);
         status = sp.getString("status" , "");
        Log.e("statussssssss" , status);


        if (HomeActivity.cartList.size()==0){
            noItemCart.setVisibility(View.VISIBLE);
        }


        cartlist.addAll(HomeActivity.cartList);
        for(Model m : cartlist){
//            Log.e("eror", m.getTopRecycleProductPrice()+" /cake");
//            Log.e("price", m.getTopRecycleProductName());
//            Log.e("qty", m.getQuantity()+"");
            total += Double.valueOf(m.getProductPrice()) * Double.valueOf(m.getQuantity());


        }

        subTotal.setText("Total(tax incl.)"+total);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartlist.size()==0){
                    open();
                }

                else {



                    Intent intent = new Intent(getContext(), CheckoutActivity.class);
                    startActivity(intent);
                    }


            }

        });


        return view;
    }

    public void setTotalAmount(double total){
        Log.e("total", total+"");
        Log.e("sub total", this.total+"");
        this.total = this.total + total;
        subTotal.setText("Total."+this.total);
    }

    public void setTotalLessAmount(double total){
        Log.e("total", total+"");
        Log.e("sub total", this.total+"");
        this.total = this.total - total;
        subTotal.setText("Total."+this.total);
    }

    public void setAllMinus(double total){
        Log.e("total", total+"");
        Log.e("sub total", this.total+"");
        this.total = this.total - total;
        subTotal.setText("Total."+this.total);
    }


    public void open(){

        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
//set icon
                .setIcon(R.drawable.gb)
//set title
                .setTitle("Google Bazar")
//set message
                .setMessage("Your cart is empty!!!")
//set positive button
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }



    }

class CartListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Model> contact;
    FragmentCart fragmentCart;

    public CartListAdapter(Context context, ArrayList<Model> cartlist, FragmentCart fragmentCart) {

        this.context = context;
        this.contact = cartlist;
        this.fragmentCart = fragmentCart;


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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflater.inflate(R.layout.cart_list_item, viewGroup, false);

        }

        TextView PartName = (TextView) view.findViewById(R.id.txt_nameProduct);
        TextView manufacturer = (TextView) view.findViewById(R.id.txt_rate);
        TextView toatlPrice = (TextView) view.findViewById(R.id.txt_CatnameProduct);

        //ImageButton remove=(ImageButton)view.findViewById(R.id.remove);

        ImageView imageView = (ImageView) view.findViewById(R.id.img_product);

        final ImageButton addition = (ImageButton) view.findViewById(R.id.btn_addQty);
        final ImageButton less = (ImageButton) view.findViewById(R.id.btn_minusQty);
        final ImageButton remove = (ImageButton) view.findViewById(R.id.btn_remove);
        final TextView qty = (TextView) view.findViewById(R.id.edt_qty);


        final Model modelCart = (Model) getItem(i);
        toatlPrice.setText(modelCart.getProductName());
        manufacturer.setText(String.valueOf(modelCart.getProductPrice()));
       // PartName.setText(modelCart.getProduct_disc() + " %Off");
        qty.setText(String.valueOf(modelCart.getQuantity()));
        Picasso.with(context).load(modelCart.getProductImg()).into(imageView);

        if(modelCart.getQuantity() >1) {
            less.setVisibility(View.VISIBLE);
        }else {
            less.setVisibility(View.GONE);
        }




        addition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String presentValStr = qty.getText().toString();
                int presentIntVal = Integer.parseInt(presentValStr);
                presentIntVal++;
                qty.setText(String.valueOf(presentIntVal));
                HomeActivity.cartList.get(i).setQuantity(presentIntVal);
                less.setVisibility(View.VISIBLE);

                final Double price1 = Double.parseDouble(String.valueOf(modelCart.getProductPrice()));
                int qut = Integer.parseInt(qty.getText().toString());
                Double total = (price1 * qut);
                Log.e("total", String.valueOf(total));
                modelCart.setTotalAmount(total);
                fragmentCart.setTotalAmount(price1);
                modelCart.setQuantity(qut);

                if (presentIntVal < 10) {
                    addition.setVisibility(View.VISIBLE);
                } else {
                    addition.setVisibility(View.GONE);
                }


            }
        });
        less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String presentValStr = qty.getText().toString();
                int presentIntVal = Integer.parseInt(presentValStr);
                presentIntVal--;
                addition.setVisibility(View.VISIBLE);
                qty.setText(String.valueOf(presentIntVal));

                final Double price1 = Double.parseDouble(String.valueOf(modelCart.getProductPrice()));
                int qut = Integer.parseInt(qty.getText().toString());
                Double total = (price1 * qut);
                Double km = (total - price1);
                Log.e("total", String.valueOf(km));
                modelCart.setTotalAmount(total);
                fragmentCart.setTotalLessAmount(price1);

                if (presentIntVal == 1) {
                    less.setVisibility(View.GONE);
                    addition.setVisibility(View.VISIBLE);
                }


            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pos) {

                final Double price1 = Double.parseDouble(String.valueOf(modelCart.getProductPrice()));
                int qut = Integer.parseInt(qty.getText().toString());
                Double total = (price1 * qut);
                Log.e("total", String.valueOf(total));
                modelCart.setTotalAmount(total);
                fragmentCart.setAllMinus(total);
                HomeActivity.cartList.remove(i);
                contact.remove(i);
                notifyDataSetChanged();


            }
        });


        return view;
    }
}
