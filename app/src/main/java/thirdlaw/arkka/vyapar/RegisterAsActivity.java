package thirdlaw.arkka.vyapar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import thirdlaw.arkka.vyapar.R;

public class RegisterAsActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    Button btn_next_seller_or_buyer;
    RadioGroup radioGroup;
    RadioButton sellerRaBtn, buyRabtn;
    String jender;
    String user_success="Registration successfully";
    public static final String url="http://14.139.158.99/arkvyaper/api/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_as);

        btn_next_seller_or_buyer=findViewById(R.id.nextBtn_for_seller_or_buyer);
        radioGroup=findViewById(R.id.radio_group);
        sellerRaBtn=findViewById(R.id.radioSeller);
        buyRabtn=findViewById(R.id.radioBuyer);

        radioGroup.setOnCheckedChangeListener(this);



        btn_next_seller_or_buyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sellerRaBtn.isChecked() && !buyRabtn.isChecked()) {

                    Toast.makeText(RegisterAsActivity.this, "Please Select Any One", Toast.LENGTH_SHORT).show();
                }
                else {
                    SharedPreferences sp= getSharedPreferences("details",MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sp.edit();
                    myEdit.putString("user_type",jender);
                    myEdit.commit();
                    Intent intent = new Intent(RegisterAsActivity.this, GeoLocationActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){

            case R.id.radioSeller:
                jender="Seller";
                break;

            case R.id.radioBuyer:
                jender="Buyer";
                break;
        }

    }

    private void insertData()
    {



        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject resObject=new JSONObject(response);
                    String responseStr= resObject.getString("ResponseMsg");
                    Log.d("REGISTER", responseStr);

                    Toast.makeText(getApplicationContext(),user_success, Toast.LENGTH_SHORT).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() {
                /* String image= encodedBitmapImage(encodedImage);*/

                Map<String, String> params = new HashMap<String, String>();
                params.put("buyer_seller",jender);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

    }

    @Override
    public void onBackPressed() {

    }
}