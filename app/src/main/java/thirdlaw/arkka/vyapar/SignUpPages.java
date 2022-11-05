package thirdlaw.arkka.vyapar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import thirdlaw.arkka.vyapar.R;

public class SignUpPages extends AppCompatActivity {

     Button agreeBtn;
     TextView termsAndConditon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_pages);

        agreeBtn=findViewById(R.id.aggreeBtn);
        termsAndConditon=findViewById(R.id.textView7);

        termsAndConditon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpPages.this, TermsAndConditionActivity.class);
                startActivity(intent);
            }
        });

        agreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignUpPages.this, SignUpDetails.class);
                startActivity(intent);

            }
        });

    }
}