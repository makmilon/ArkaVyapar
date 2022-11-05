package thirdlaw.arkka.vyapar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import thirdlaw.arkka.vyapar.R;

public class FaviriteListActivity extends AppCompatActivity {

    ImageView arrow_back_favouri_history;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favirite_list);

        arrow_back_favouri_history=findViewById(R.id.arrow_back_favourite);

        arrow_back_favouri_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FaviriteListActivity.this,MainActivity.class));
            }
        });
    }
}