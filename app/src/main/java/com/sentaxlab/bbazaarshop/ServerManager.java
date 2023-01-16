package com.sentaxlab.bbazaarshop;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServerManager {

    private String preFix = "https://googlebazar.pk/api/";
    private String slider = preFix+"getsliders";
    private String getFeatured = preFix+"getfeatured";
    private String getBest = preFix+"getbest";
    private String getAllProduct = preFix+"getAllProducts";
    private String getAllCategory = preFix+"categoriesnon";
    private String getAllsubCategory = preFix+"subcategories";
    private String subCatBrandProduct= preFix+"getProductById";
    private String productByCat= preFix+"getProductByCat";
    private String Login= preFix+"login";
    private String signUp= preFix+"signup";
    private String wanted= preFix+"wanted";
    private String aboutUs= preFix+"getAbout";
    private String privacy= preFix+"getPrivacy";
    private String saveOrder= preFix+"saveOrderData";
    private String myOrder= preFix+"orders";
    private String TrackingOrder= preFix+"trackingOrder";
    private String search= preFix+"search";

    private String marketingCats = "http://marketing.googlebazar.pk/api/v1/?action=home_latest_ads";
    private String marketingProductsDetail = "https://marketing.googlebazar.pk/api/v1/?action=ad_detail";


    private ServerResponseHandler responseHandler;
    public ServerManager(ServerResponseHandler handler) {
        this.responseHandler = handler;
    }


    public void getMarketingCats(String category_id, int requestTag){
        HashMap<String, String> params = new HashMap<>();
        marketingCats= marketingCats +"&category_id="+category_id;
        Log.e("url", marketingCats);
        params.put("category_id",category_id);
        AsyncHttpPost requestSender = new AsyncHttpPost(marketingCats, params, requestTag);
        requestSender.execute();
    }

    public void getMarketingProDetail(String item_id, int requestTag){
        HashMap<String, String> params = new HashMap<>();
        marketingProductsDetail= marketingProductsDetail +"&item_id="+item_id;
        Log.e("url", marketingCats);
        params.put("item_id",item_id);
        AsyncHttpPost requestSender = new AsyncHttpPost(marketingProductsDetail, params, requestTag);
        requestSender.execute();
    }

    public void getsearchresult(String name, int requestTag){
        HashMap<String, String> params = new HashMap<>();
        params.put("name",name);
        AsyncHttpPost requestSender = new AsyncHttpPost(search, params, requestTag);
        requestSender.execute();
    }

    public void saveOrderData(int requestTag,String user_id , String cart , String totalQty , String pay_amount , String method , String shipping , String pickup_location , String customer_email , String customer_name , String shipping_cost , String packing_cost , String tax , String customer_phone , String customer_address , String customer_country , String customer_city , String customer_zip , String shipping_email ,  String shipping_name , String shipping_phone , String shipping_address , String shipping_country , String shipping_city , String shipping_zip , String order_note , String coupon_code , String coupon_discount , String dp , String currency_sign , String currency_value , String vendor_shipping_id , String vendor_packing_id ){
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id",user_id);
        params.put("cart",cart);
        params.put("totalQty",totalQty);
        params.put("pay_amount",pay_amount);
        params.put("method",method);
        params.put("shipping",shipping);
        params.put("pickup_location",pickup_location);
        params.put("customer_email",customer_email);
        params.put("customer_name",customer_name);
        params.put("shipping_cost",shipping_cost);
        params.put("packing_cost",packing_cost);
        params.put("tax",tax);
        params.put("customer_phone",customer_phone);
        params.put("customer_address",customer_address);
        params.put("customer_country",customer_country);
        params.put("customer_city",customer_city);
        params.put("customer_zip",customer_zip);
        params.put("shipping_email",shipping_email);
        params.put("shipping_name",shipping_name);
        params.put("shipping_phone",shipping_phone);
        params.put("shipping_address",shipping_address);
        params.put("shipping_country",shipping_country);
        params.put("shipping_city",shipping_city);
        params.put("shipping_zip",shipping_zip);
        params.put("order_note",order_note);
        params.put("coupon_code",coupon_code);
        params.put("coupon_discount",coupon_discount);
        params.put("dp",dp);
        params.put("currency_sign",currency_sign);
        params.put("currency_value",currency_value);
        params.put("vendor_shipping_id",vendor_shipping_id);
        params.put("vendor_packing_id",vendor_packing_id);
        AsyncHttpPost requestSender = new AsyncHttpPost(saveOrder, params, requestTag);
        requestSender.execute();
    }

    public void getProductByCategory(int requestTag,String category_id){
        HashMap<String, String> params = new HashMap<>();
        params.put("category_id",category_id);
        AsyncHttpPost requestSender = new AsyncHttpPost(productByCat, params, requestTag);
        requestSender.execute();
    }

    public void getMyOrder(int requestTag,String user_id){
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id",user_id);
        AsyncHttpPost requestSender = new AsyncHttpPost(myOrder, params, requestTag);
        requestSender.execute();
    }


    public void getTracking(int requestTag,String order_id){
        HashMap<String, String> params = new HashMap<>();
        params.put("order_id",order_id);
        AsyncHttpPost requestSender = new AsyncHttpPost(TrackingOrder, params, requestTag);
        requestSender.execute();
    }

    public void getAboutUs(int requestTag){
        HashMap<String, String> params = new HashMap<>();
        AsyncHttpPost requestSender = new AsyncHttpPost(aboutUs, params, requestTag);
        requestSender.execute();
    }
    public void getPrivacy(int requestTag){
        HashMap<String, String> params = new HashMap<>();
        AsyncHttpPost requestSender = new AsyncHttpPost(privacy, params, requestTag);
        requestSender.execute();
    }

    public void SignUp(int requestTag,String name,String address,String email,String password,String phone){
        HashMap<String, String> params = new HashMap<>();
        params.put("name",name);
        params.put("address",address);
        params.put("email",email);
        params.put("password",password);
        params.put("phone",phone);
        AsyncHttpPost requestSender = new AsyncHttpPost(signUp, params, requestTag);
        requestSender.execute();
    }


    public void Wanted(int requestTag,String name,String email,String phone,String message){
        HashMap<String, String> params = new HashMap<>();
        params.put("name",name);
        params.put("email",email);
        params.put("phone",phone);
        params.put("message",message);
        AsyncHttpPost requestSender = new AsyncHttpPost(wanted, params, requestTag);
        requestSender.execute();
    }

    public void Login(int requestTag,String email,String password){
        HashMap<String, String> params = new HashMap<>();
        params.put("email",email);
        params.put("password",password);
        AsyncHttpPost requestSender = new AsyncHttpPost(Login, params, requestTag);
        requestSender.execute();
    }

    public void mainBanner(int requestTag){
        HashMap<String, String> params = new HashMap<>();
        AsyncHttpPost requestSender = new AsyncHttpPost(slider, params, requestTag);
        requestSender.execute();
    }

    public void getFeatured(int requestTag){
        HashMap<String, String> params = new HashMap<>();
        AsyncHttpPost requestSender = new AsyncHttpPost(getFeatured, params, requestTag);
        requestSender.execute();
    }

    public void getBest(int requestTag){
        HashMap<String, String> params = new HashMap<>();
        AsyncHttpPost requestSender = new AsyncHttpPost(getBest, params, requestTag);
        requestSender.execute();
    }

    public void getProduct(int requestTag){
        HashMap<String, String> params = new HashMap<>();
        AsyncHttpPost requestSender = new AsyncHttpPost(getAllProduct, params, requestTag);
        requestSender.execute();
    }

    public void getCategory(int requestTag){
        HashMap<String, String> params = new HashMap<>();
        AsyncHttpPost requestSender = new AsyncHttpPost(getAllCategory, params, requestTag);
        requestSender.execute();
    }

    public void getSubCategory(int requestTag , String category_id){
        HashMap<String, String> params = new HashMap<>();
        params.put("category_id",category_id);
        AsyncHttpPost requestSender = new AsyncHttpPost(getAllsubCategory, params, requestTag);
        requestSender.execute();
    }

    public void getsubCatProduct(int requestTag,String category_id,String subcategory_id){
        HashMap<String, String> params = new HashMap<>();
        params.put("category_id",category_id);
        params.put("subcategory_id",subcategory_id);
        AsyncHttpPost requestSender = new AsyncHttpPost(subCatBrandProduct, params, requestTag);
        requestSender.execute();
    }


    private class AsyncHttpPost extends AsyncTask<Void, Void, String> {
        private String url = "";
        private HashMap<String, String> postParams = null;
        private int requestTag;
        private String errorMessage = "";
        private String resultString = "";
        public final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        public AsyncHttpPost(String url, HashMap<String, String> parms, int tag){
            this.url = url;
            this.postParams = parms;
            this.requestTag = tag;
        }


        @Override
        protected String doInBackground(Void... voids) {
            FormBody.Builder builder = new FormBody.Builder();
            for ( Map.Entry<String, String> entry : postParams.entrySet() ) {
                builder.add( entry.getKey(), entry.getValue() );
            }
            RequestBody body = builder.build();

            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();

            final Request request = new Request.Builder().url(url).post(body).build();
            Response response = null;
            try {
                response = httpClient.newCall(request).execute();
                if (!response.isSuccessful()) {
                    errorMessage = "An error occurred";
                    throw new IOException("Unexpected code " + response);
                }

                /*Headers responseHeaders = response.headers();
                for (int i = 0; i < responseHeaders.size(); i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }*/

                else {
                    errorMessage = "ok";
                    resultString = response.body().string();
                    Log.e("http response", resultString);
                }
            } catch (IOException e) {

                e.printStackTrace();
//                errorMessage = "An error occurred";
                errorMessage = e.getMessage();
            }
            return resultString;
        }

        @Override
        protected void onPostExecute(String s) {
            if(errorMessage == null || s == null || s.isEmpty()) {
                Log.e("error", s);
                responseHandler.requestFailed("server timeout", requestTag);
            }
            if(errorMessage.equals("ok")){
                responseHandler.requestFinished(s, requestTag);
            }
            else{
                Log.e("Error", errorMessage);
                responseHandler.requestFailed(errorMessage, requestTag);
            }
        }
    }

    public interface ServerResponseHandler {

        void requestFinished(String response, int requestTag);
        void requestFailed(String errorMessage, int requestTag);

        void onClick(View v);
    }

}
