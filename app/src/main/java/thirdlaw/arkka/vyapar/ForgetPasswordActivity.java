package thirdlaw.arkka.vyapar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

public class ForgetPasswordActivity extends AppCompatActivity {

    Button forget_passwor_submit_btn;
    EditText forPhone,forEmail, forNewPass;
    private  static final String url="http://14.139.158.99/arkvyaper/api/forget_user.php";
    String update_success= "Password Update Successfully";
    String update_error="Phone or Email Incorrect";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        forget_passwor_submit_btn =findViewById(R.id.forget_passowrd_submit_btn);
        forPhone=findViewById(R.id.forget_ph);
        forEmail=findViewById(R.id.forget_email);
        forNewPass=findViewById(R.id.forget_new_pass);



        forget_passwor_submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updataPassword();

            }
        });
    }

    private void updataPassword() {

        String userPhone= forPhone.getText().toString().trim();
        String userEmail=forEmail.getText().toString().trim();
        String userPass=forNewPass.getText().toString().trim();

        StringRequest request= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject resObject=new JSONObject(response);
                    String responseStr= resObject.getString("ResponseMsg");
                    Log.d("LOGIN", responseStr);
                    if(responseStr.equals(update_success))
                    {
                        forPhone.setText("");
                        forEmail.setText("");
                        forNewPass.setText("");
                        alertDialog();
                    }
                    else if(responseStr.equals(update_error))
                    {
                        Toast.makeText(getApplicationContext(),"Please Check Your Phone or Email", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_LONG).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("uphone",userPhone);
                params.put("uemail",userEmail);
                params.put("upassword",userPass);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }


    public void alertDialog(){
        ViewGroup viewGroup=findViewById(android.R.id.content);

        Button okBtn;

        AlertDialog.Builder builder = new AlertDialog.Builder(ForgetPasswordActivity.this);
        View view1= LayoutInflater.from(ForgetPasswordActivity.this).inflate(R.layout.alert_dialog,viewGroup,false);
        builder.setCancelable(false);
        builder.setView(view1);

        okBtn=view1.findViewById(R.id.okBtn_alert_forgot_password);

        AlertDialog alertDialog = builder.create();

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }


}