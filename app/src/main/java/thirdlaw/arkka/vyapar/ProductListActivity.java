package thirdlaw.arkka.vyapar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ProductListActivity extends AppCompatActivity {

    String uid;
    RecyclerView productRecview;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);


        Dialog dialog = new Dialog(ProductListActivity.this);
        dialog.setContentView(R.layout.dialog_layout1);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogue_background1));
        dialog.setCancelable(false);
        dialog.show();



        productRecview=findViewById(R.id.product_list_show);
        productRecview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        uid=getIntent().getStringExtra("uid");



        String productUrl="http://14.139.158.99/arkvyaper/api/product_list.php?uid="+uid;
        StringRequest stringRequest = new StringRequest(productUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                dialog.dismiss();

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson=gsonBuilder.create();
                productListModel data[]=gson.fromJson(response,productListModel[].class);
                productListAdapter productadapter= new productListAdapter(data,getApplicationContext());
                productRecview.setAdapter(productadapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }
}