package thirdlaw.arkka.vyapar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import thirdlaw.arkka.vyapar.R;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chaos.view.PinView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OTPActivity extends AppCompatActivity {

/*    EditText inputC1, inputC2,inputC3,inputC4,inputC5,inputC6;*/
    PinView pinview;
    Button verify_otp_btn;
    TextView otpsendTo, resendOtpText;
    ImageView imageView;
    String verficicationId;
    String encodedImage;
    String user_success="Registration successfully";
    String otp_response="otp verified successfully";
    String user_success1="OTP sent Successfully";
    public static final String otpUrl1="http://14.139.158.99/arkvyaper/api/user_otpsend.php";
    public static final String url="http://14.139.158.99/arkvyaper/api/register.php";
    public static final String otpurl="http://14.139.158.99/arkvyaper/api/userotp_check.php";

    String mobileNo, name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);



        resendOtpText=findViewById(R.id.resendOtpText);
        pinview=findViewById(R.id.pinview);

        Dialog dialog = new Dialog(OTPActivity.this);
        dialog.setContentView(R.layout.dialog_layout1);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogue_background1));
        dialog.setCancelable(false);


        verficicationId=getIntent().getStringExtra("verficationid");




      //for image display which come from signup details page from backened
 /*       imageView=findViewById(R.id.imageView17);
        Bundle ex=getIntent().getExtras();
        byte[] bytearray=ex.getByteArray("cameraimage");
        Bitmap bmp= BitmapFactory.decodeByteArray(bytearray,0,bytearray.length);
        imageView.setImageBitmap(bmp);*/

        otpsendTo=findViewById(R.id.textView10);
        otpsendTo.setText(String.format(
                "+91-%s",getIntent().getStringExtra("mobile")));

        verify_otp_btn=findViewById(R.id.very_otp_btn);

        verify_otp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if (pinview.getText().toString().isEmpty()){

                   Toast.makeText(OTPActivity.this, "Please Enter vailid Code", Toast.LENGTH_SHORT).show();
                  return;

               }

                String code= pinview.getText().toString().trim();
                mobileNo=getIntent().getStringExtra("mobile");
                name=getIntent().getStringExtra("name");

                StringRequest request1 = new StringRequest(Request.Method.POST, otpurl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject resObject=new JSONObject(response);
                            String responseStr= resObject.getString("ResponseMsg");
                            Log.d("REGISTER", responseStr);


                            if (responseStr.equals(otp_response)){
                                Toast.makeText(getApplicationContext(),responseStr, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(OTPActivity.this,RegisterAsActivity.class);
                                startActivity(intent);
                                finish();
                                insertData();
                            }else {
                                Toast.makeText(getApplicationContext(),responseStr, Toast.LENGTH_SHORT).show();
                            }






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
                        params.put("uphone",mobileNo);
                        params.put("otp",code);
                        return params;
                    }
                };

                RequestQueue queue1 = Volley.newRequestQueue(getApplicationContext());
                queue1.add(request1);


            }
        });

        resendOtpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String code= pinview.getText().toString().trim();
                mobileNo=getIntent().getStringExtra("mobile");
                name=getIntent().getStringExtra("name");

                dialog.show();
                StringRequest request = new StringRequest(Request.Method.POST, otpUrl1, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                            dialog.dismiss();

                            Toast.makeText(getApplicationContext(),user_success1, Toast.LENGTH_SHORT).show();


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("uphone",mobileNo);
                        params.put("uname",name);
                        return params;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(request);


            }
        });
    }

    private void insertData()
    {
        String mobileNo=getIntent().getStringExtra("mobile");
        String nameUser=getIntent().getStringExtra("name");
        String uemail=getIntent().getStringExtra("email");
        String password=getIntent().getStringExtra("password");



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
                params.put("uname",nameUser);
                params.put("uphone", mobileNo);
                params.put("uemail", uemail);
                params.put("upassword", password);
                 return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

    }

   /* private String encodedBitmapImage(String encodedImage) {

        Bundle ex=getIntent().getExtras();
        byte[] bytearray=ex.getByteArray("cameraimage");
        encodedImage=android.util.Base64.encodeToString(bytearray, Base64.DEFAULT);
        return encodedImage;
    }*/

    @Override
    public void onBackPressed() {

    }
}