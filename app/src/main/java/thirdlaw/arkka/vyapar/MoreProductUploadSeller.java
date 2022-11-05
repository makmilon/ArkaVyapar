package thirdlaw.arkka.vyapar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MoreProductUploadSeller extends AppCompatActivity {

    RecyclerView productRecview;
    Button againProBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_product_upload_seller);

        Dialog dialog = new Dialog(MoreProductUploadSeller.this);
        dialog.setContentView(R.layout.dialog_layout1);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogue_background1));
        dialog.setCancelable(false);
        dialog.show();

        againProBtn=findViewById(R.id.button4);
        againProBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MoreProductUploadSeller.this,UploadProduct.class));
            }
        });

        productRecview=findViewById(R.id.product_list_seller_show);
        productRecview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        SharedPreferences sp= getSharedPreferences("details",MODE_PRIVATE);
        String user_id=sp.getString("user_id","");

        String productUrl="http://14.139.158.99/arkvyaper/api/product_list.php?uid="+user_id;
        StringRequest stringRequest = new StringRequest(productUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson=gsonBuilder.create();
                productListModel data[]=gson.fromJson(response,productListModel[].class);
                productListAdapter productadapter= new productListAdapter(data,getApplicationContext());
                productRecview.setAdapter(productadapter);
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {

        Dialog dialog1 = new Dialog(MoreProductUploadSeller.this);
        dialog1.setContentView(R.layout.dialog_layout_go_to_home_page);
        dialog1.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogue_background));
        dialog1.setCancelable(false);
        dialog1.show();

        TextView yesText=dialog1.findViewById(R.id.textYes1);
        TextView noText=dialog1.findViewById(R.id.textNo1);

        yesText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }
        });

        noText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog1.dismiss();

            }
        });

    }
}