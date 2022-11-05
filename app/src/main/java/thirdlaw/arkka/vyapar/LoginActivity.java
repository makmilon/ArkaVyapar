package thirdlaw.arkka.vyapar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import java.util.Locale;
import java.util.Map;

import thirdlaw.arkka.vyapar.R;

public class LoginActivity extends AppCompatActivity {

    ImageView changeLang;

    CheckBox checkBox2;
    TextView forgot, registerText;
    Button loginBtn;
    EditText logPhon, logPass;
    String uphone,upass;
    String login_success="Login Successfull";
    String login_error="Phone or Password incorrect";
    public static final String url="http://14.139.158.99/arkvyaper/api/user_login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadLocal();
        setContentView(R.layout.activity_login);

        changeLang=findViewById(R.id.imageView27);

        changeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLanguageDialogue();
            }
        });


        checkBox2=findViewById(R.id.checkBox2);
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    logPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    logPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        forgot=findViewById(R.id.forgetPassword);
        registerText=findViewById(R.id.register_text);
        loginBtn=findViewById(R.id.button_login);

        logPhon=findViewById(R.id.phone_login);
        logPass=findViewById(R.id.password_login);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                uphone= logPhon.getText().toString().trim();
                upass=logPass.getText().toString().trim();

                if(    logPhon.getText().toString().trim().isEmpty()
                        || logPass.getText().toString().trim().isEmpty())
                 {
                    Toast.makeText(LoginActivity.this, "Please fill up the required details", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {

                    login();

                }

            }
        });



        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ForgetPasswordActivity.class));

            }
        });

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignUpPages.class));

            }
        });


    }

    private void login() {


        Dialog dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.dialog_layout1);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogue_background1));
        dialog.setCancelable(false);
        dialog.show();


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject resObject=new JSONObject(response);
                    String responseStr= resObject.getString("ResponseMsg");

                    if(responseStr.equals(login_success))
                    {
                        String u_id=resObject.getString("uid");
                        String u_type=resObject.getString("buyer_seller");
                        String pLati=resObject.getString("lati");
                        String pLongi=resObject.getString("longi");

                        SharedPreferences sp= getSharedPreferences("details",MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sp.edit();
                        myEdit.putString("user_id",u_id);
                        myEdit.putString("buyer_seller",u_type);
                        myEdit.putString("user_lati",pLati);
                        myEdit.putString("user_longi",pLongi);
                        myEdit.commit();

                        dialog.dismiss();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    }
                    else if(responseStr.equals(login_error))
                    {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Wrong Credential", Toast.LENGTH_LONG).show();
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
                params.put("uphone",uphone);
                params.put("upassword",upass);
                return params;
            }
        };


        queue.add(request);
    }

    @Override
    protected void onRestart() {

        super.onRestart();
    }

    private void showChangeLanguageDialogue() {

        final String[] lang_name={"English", "Kannada"};
        AlertDialog.Builder builder= new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Choose Language");
        builder.setSingleChoiceItems(lang_name, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which==0){
                    setLocal("En");
                    recreate();
                }else if (which==1){
                    setLocal("kn");
                    recreate();
                }
                dialog.dismiss();

            }
        });

        AlertDialog mDialog= builder.create();
        mDialog.show();
    }

    public void setLocal(String lang) {
        Locale locale = new Locale(lang);
        locale.setDefault(locale);
        Configuration config= new Configuration();
        config.locale=locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences sp= getSharedPreferences("details",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sp.edit();
        myEdit.putString("my_lang",lang);
        myEdit.commit();

    }

    public void loadLocal(){
        SharedPreferences sp= getSharedPreferences("details", MODE_PRIVATE);
        String user_lang=sp.getString("my_lang","");
        setLocal(user_lang);

    }
}