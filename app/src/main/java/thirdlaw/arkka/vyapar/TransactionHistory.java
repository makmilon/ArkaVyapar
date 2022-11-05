package thirdlaw.arkka.vyapar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import thirdlaw.arkka.vyapar.R;

public class TransactionHistory extends AppCompatActivity {

    ImageView arrow_back_tranHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        arrow_back_tranHistory=findViewById(R.id.arrow_back_transaction);

        arrow_back_tranHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TransactionHistory.this,MainActivity.class));
            }
        });
    }
}