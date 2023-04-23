package com.purpulingo.aromabox.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.purpulingo.aromabox.Global.NetworkChange;
import com.purpulingo.aromabox.R;

public class DistillationActivity extends AppCompatActivity {

    private CardView newBatch,exitingBatch;
    private ImageView backBtn;
    private NetworkChange networkChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distillation);

        networkChange = new NetworkChange();
        backBtn = (ImageView) findViewById(R.id.backBtn);

        newBatch = (CardView) findViewById(R.id.newBatch);
        exitingBatch = (CardView) findViewById(R.id.exitingBatch);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        newBatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewBatchActivity.class);
                startActivity(intent);
            }
        });
        exitingBatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ExitingBatchListActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChange, filter);
        super.onStart();
    }
    @Override
    protected void onStop() {
        unregisterReceiver(networkChange);
        super.onStop();
    }
}