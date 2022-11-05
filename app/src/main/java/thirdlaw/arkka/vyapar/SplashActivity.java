package thirdlaw.arkka.vyapar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import thirdlaw.arkka.vyapar.R;

public class SplashActivity extends AppCompatActivity {

    Context context;
    ImageView logo, nointernet;
    TextView notext;

    LottieAnimationView lottieAnimationView;

    String uid_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo=findViewById(R.id.imageView6);
        lottieAnimationView=findViewById(R.id.nointerNetLottie);
//        nointernet=findViewById(R.id.imageView17);
        notext=findViewById(R.id.textView39);

        if (!isConnected()){

/*            nointernet.setVisibility(View.VISIBLE);*/
            lottieAnimationView.setVisibility(View.VISIBLE);
            notext.setVisibility(View.VISIBLE);



        }else {
            logo.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    SharedPreferences sp= getSharedPreferences("details",MODE_PRIVATE);
                    uid_save=sp.getString("user_id","");


                    if(uid_save!="")

                    {
                        startActivity(new Intent(SplashActivity.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();
                    }
                    else{
                        startActivity(new Intent(SplashActivity.this,LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();

                    }

                }
            },3000);
        }



    }

    private boolean isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo()!=null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}