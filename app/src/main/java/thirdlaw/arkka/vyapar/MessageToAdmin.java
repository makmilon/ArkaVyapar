package thirdlaw.arkka.vyapar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class MessageToAdmin extends AppCompatActivity {


    EditText msgBox;
    Button sendMsgBtn;
    ImageView backarrow;

    String user_success="Message Send successfully";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_to_admin);

        msgBox=findViewById(R.id.sendMessage);
        sendMsgBtn=findViewById(R.id.sendMsgBtn);
        backarrow=findViewById(R.id.arrow_back_message);

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessageToAdmin.this,MainActivity.class));
                finish();
            }
        });

        SharedPreferences sp= getSharedPreferences("details",MODE_PRIVATE);
        String user_ide=sp.getString("user_id","");

        String messageUrl="http://14.139.158.99/arkvyaper/api/user_message.php";

        sendMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message=msgBox.getText().toString().trim();

                StringRequest request = new StringRequest(Request.Method.POST, messageUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject resObject=new JSONObject(response);
                            String responseStr= resObject.getString("ResponseMsg");
                            Log.d("REGISTER", responseStr);

                            msgBox.setText("");

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
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("uid",user_ide);
                        params.put("message",message);
                        return params;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(request);
            }
        });


    }


}