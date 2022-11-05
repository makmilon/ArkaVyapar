package thirdlaw.arkka.vyapar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import thirdlaw.arkka.vyapar.R;

public class   CommingRequrmentList extends AppCompatActivity {

    ImageView arrow_back_commingRequList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comming_requrment_list);

        arrow_back_commingRequList=findViewById(R.id.arrow_back_comming_requestList);

        arrow_back_commingRequList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CommingRequrmentList.this,MainActivity.class));
            }
        });
    }

}