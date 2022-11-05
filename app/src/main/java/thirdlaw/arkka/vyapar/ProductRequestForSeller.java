package thirdlaw.arkka.vyapar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ProductRequestForSeller extends AppCompatActivity {

    RecyclerView requestListRecview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_request_for_seller);

        requestListRecview=findViewById(R.id.request_list_show_for_seller);

        requestListRecview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        String productUrl="http://14.139.158.99/arkvyaper/api/all_product_request_buyer.php";
        StringRequest stringRequest = new StringRequest(productUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson=gsonBuilder.create();
                forBuyerProductModel data[]=gson.fromJson(response, forBuyerProductModel[].class);
                forBuyerProductAdapter producBuytadapter= new forBuyerProductAdapter(data,getApplicationContext());
                requestListRecview.setAdapter(producBuytadapter);
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