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

public class CommercialActivity extends AppCompatActivity {

    private ImageView backBtn;
    private NetworkChange networkChange;
    private CardView procurementBtn,procurementCommercial, paymentCommercial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commercial);

        networkChange = new NetworkChange();
        backBtn = (ImageView) findViewById(R.id.backBtn);
        procurementBtn = (CardView) findViewById(R.id.procurementBtn);
        procurementCommercial = (CardView) findViewById(R.id.procurementCommercial);
        paymentCommercial = (CardView) findViewById(R.id.paymentCommercial);

        procurementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProcurementActivity.class);
                startActivity(intent);
            }
        });

        procurementCommercial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProcurementCommercialActivity.class);
                startActivity(intent);
            }
        });
        paymentCommercial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                startActivity(intent);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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