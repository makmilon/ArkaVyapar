package thirdlaw.arkka.vyapar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ProductListForBuyer extends AppCompatActivity {


    RecyclerView productforBuyerRecview;
    Button requirmentBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list_for_buyer);

        Dialog dialog = new Dialog(ProductListForBuyer.this);
        dialog.setContentView(R.layout.dialog_layout1);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogue_background1));
        dialog.setCancelable(false);
        dialog.show();

        requirmentBtn=findViewById(R.id.requirment_list_buyer);
        requirmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductListForBuyer.this,RRRequestListActivity.class));
            }
        });

        productforBuyerRecview=findViewById(R.id.product_list_show_for_buyer);
        productforBuyerRecview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        String productUrl="http://14.139.158.99/arkvyaper/api/all_product_list.php";
        StringRequest stringRequest = new StringRequest(productUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                dialog.dismiss();

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson=gsonBuilder.create();
                forBuyerProductModel data[]=gson.fromJson(response, forBuyerProductModel[].class);
                forBuyerProductAdapter producBuytadapter= new forBuyerProductAdapter(data,getApplicationContext());
                productforBuyerRecview.setAdapter(producBuytadapter);
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