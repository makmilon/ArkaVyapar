package thirdlaw.arkka.vyapar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import thirdlaw.arkka.vyapar.R;

public class TrackerListActivity extends AppCompatActivity {

    ImageView arrow_back_tracker_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker_list);

        arrow_back_tracker_activity=findViewById(R.id.arrow_back_tracker_activity);

        arrow_back_tracker_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrackerListActivity.this,MainActivity.class));
            }
        });
    }
}