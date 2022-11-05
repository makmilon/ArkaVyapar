package thirdlaw.arkka.vyapar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MapViewSe1 extends AppCompatActivity {

   String user_id, ulat, ulong, user_type;
    RecyclerView nearRecview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view_se1);

        SharedPreferences sp=getSharedPreferences("details", MODE_PRIVATE);
        user_id=sp.getString("user_id","");
        ulat=sp.getString("user_lati","");
        ulong=sp.getString("user_longi","");
        user_type=sp.getString("buyer_seller","");

        nearRecview=findViewById(R.id.search_list_recView);
        nearRecview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        String productUrl="http://14.139.158.99/arkvyaper/api/longlat_user.php?uid="+user_id+"&longi="+ulong+"&lati="+ulat;
        StringRequest stringRequest = new StringRequest(productUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson=gsonBuilder.create();
                nearmodel data[]=gson.fromJson(response, nearmodel[].class);
                nearadapter nearAaadapter= new nearadapter(data,getApplicationContext());
                nearRecview.setAdapter(nearAaadapter);
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