package thirdlaw.arkka.vyapar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import thirdlaw.arkka.vyapar.R;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

public class SignUpDetails extends AppCompatActivity {

    Button signMeup;
    CheckBox showPassword;
    EditText phonoEt, nameEt, emailEt, passwordEt, confirmPassEt;

    FirebaseAuth mAuth;
    String verificationID;
    TextView clickCamera;
    String encodeImageString;
    String user_success="OTP sent Successfully";
    public static final String otpUrl="http://14.139.158.99/arkvyaper/api/user_otpsend.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_details);



        showPassword=findViewById(R.id.checkBox);
        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                       passwordEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    confirmPassEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    passwordEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirmPassEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        signMeup=findViewById(R.id.signMeupBtn);


        phonoEt=findViewById(R.id.phone_et);
        nameEt=findViewById(R.id.name_et);
        emailEt=findViewById(R.id.email_et);
        passwordEt=findViewById(R.id.psd_et);
        confirmPassEt=findViewById(R.id.psd_et_confirm);

        Dialog dialog = new Dialog(SignUpDetails.this);
        dialog.setContentView(R.layout.dialog_layout1);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogue_background1));
        dialog.setCancelable(false);

/*        SharedPreferences sp= getSharedPreferences("details",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sp.edit();
        myEdit.putString("user_photo",encodeImageString);*/




        signMeup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (
                        phonoEt.getText().toString().trim().isEmpty()
                        || nameEt.getText().toString().trim().isEmpty()
                        || emailEt.getText().toString().trim().isEmpty()
                        || passwordEt.getText().toString().trim().isEmpty()
                        ||confirmPassEt.getText().toString().isEmpty())




                {
                    Toast.makeText(SignUpDetails.this, "Please enter All details", Toast.LENGTH_SHORT).show();
                    return;
                }




                String uphone= phonoEt.getText().toString().trim();
                String uname=nameEt.getText().toString().trim();
                String uemail=emailEt.getText().toString().trim();
                String upassword=passwordEt.getText().toString().trim();
                String confirmPassword=confirmPassEt.getText().toString().trim();

                 if (!upassword.equals(confirmPassword)){
                    Toast.makeText(SignUpDetails.this, "Password do not match", Toast.LENGTH_LONG).show();


                }else {

                     SharedPreferences sp = getSharedPreferences("details", MODE_PRIVATE);
                     SharedPreferences.Editor myEdit = sp.edit();
                     myEdit.putString("user_phone", uphone);
                     myEdit.putString("user_name", uname);
                     myEdit.putString("user_email", uemail);
                     myEdit.putString("user_password", upassword);

                     myEdit.commit();

                     dialog.show();

                     StringRequest request = new StringRequest(Request.Method.POST, otpUrl, new Response.Listener<String>() {
                         @Override
                         public void onResponse(String response) {

                                 dialog.dismiss();

                                 Toast.makeText(getApplicationContext(),user_success, Toast.LENGTH_SHORT).show();
                                 Intent intent = new Intent(getApplicationContext(), OTPActivity.class);
                                 intent.putExtra("mobile",phonoEt.getText().toString().trim());
                                 intent.putExtra("name",nameEt.getText().toString().trim());
                                 startActivity(intent);


                         }
                     }, new Response.ErrorListener() {
                         @Override
                         public void onErrorResponse(VolleyError error) {

                         }
                     }){
                         @Override
                         protected Map<String, String> getParams() {
                             Map<String, String> params = new HashMap<String, String>();
                             params.put("uphone",uphone);
                             params.put("uname",uname);
                             return params;
                         }
                     };

                     RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                     queue.add(request);

                 }
            }
        });
    }



}