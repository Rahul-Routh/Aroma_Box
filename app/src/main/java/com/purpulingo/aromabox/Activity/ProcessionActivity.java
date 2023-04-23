package com.purpulingo.aromabox.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.purpulingo.aromabox.Activity.DistillationActivity;
import com.purpulingo.aromabox.Activity.ProcurementActivity;
import com.purpulingo.aromabox.Global.NetworkChange;
import com.purpulingo.aromabox.Global.UserSessionManager;
import com.purpulingo.aromabox.R;

public class ProcessionActivity extends AppCompatActivity {

    private ImageView backBtn;
    private CardView distillationBtn, totalOil;
    private NetworkChange networkChange;
    UserSessionManager userSessionManager;
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procession);

        userSessionManager = new UserSessionManager(getApplicationContext());
        role = userSessionManager.getRole();

        networkChange = new NetworkChange();
        backBtn = (ImageView) findViewById(R.id.backBtn);
        //procurementBtn = (CardView) findViewById(R.id.procurementBtn);
        distillationBtn = (CardView) findViewById(R.id.distillationBtn);
        totalOil = (CardView) findViewById(R.id.totalOil);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(role.equals("OP")){
           // procurementBtn.setVisibility(View.GONE);
            totalOil.setVisibility(View.VISIBLE);
        }


        distillationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DistillationActivity.class);
                startActivity(intent);
            }
        });
        totalOil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OilQuantityActivity.class);
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