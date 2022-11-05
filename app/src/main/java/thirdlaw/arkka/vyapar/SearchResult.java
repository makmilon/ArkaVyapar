package thirdlaw.arkka.vyapar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;

public class SearchResult extends AppCompatActivity {

    RecyclerView searchRecView;
    TextView nodataText;
    String jsonResponse= "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        searchRecView=findViewById(R.id.product_list_search_result);
        searchRecView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        nodataText=findViewById(R.id.textView30);

        Dialog dialog = new Dialog(SearchResult.this);
        dialog.setContentView(R.layout.dialog_layout1);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogue_background1));
        dialog.setCancelable(false);
        dialog.show();


        SharedPreferences sp= getSharedPreferences("details", MODE_PRIVATE);
        String user_searchPin=sp.getString("user_search_Pin","");


        String searchUrl="http://14.139.158.99/arkvyaper/api/pin.php?pin="+user_searchPin;


        StringRequest stringRequest = new StringRequest(searchUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                     dialog.dismiss();

                try {
                    JSONArray jsonArray=new JSONArray(response);

                    if (jsonArray.length()<=0){
                        nodataText.setVisibility(View.VISIBLE);
                        searchRecView.setVisibility(View.GONE);

                    }else if(jsonArray.length()>0){

                        searchRecView.setVisibility(View.VISIBLE);
                        nodataText.setVisibility(View.GONE);
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        forBuyerProductModel data[] = gson.fromJson(response, forBuyerProductModel[].class);
                        forBuyerProductAdapter producBuytadapter = new forBuyerProductAdapter(data, getApplicationContext());
                        searchRecView.setAdapter(producBuytadapter);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }






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