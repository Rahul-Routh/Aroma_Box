package com.purpulingo.aromabox.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.purpulingo.aromabox.Global.NetworkChange;
import com.purpulingo.aromabox.Model.Farmer;
import com.purpulingo.aromabox.R;

import java.util.Calendar;

public class
PaymentActivity extends AppCompatActivity {
    private ImageView backBtn;
    private NetworkChange networkChange;
    CardView farmerPaidList,farmerDueList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        networkChange = new NetworkChange();
        backBtn = (ImageView) findViewById(R.id.backBtn);



        farmerDueList = (CardView) findViewById(R.id.farmerDueList);
        farmerPaidList = (CardView) findViewById(R.id.farmerPaidList);

        farmerDueList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FarmerPaymentDueListActivity.class);
                startActivity(intent);
            }
        });
        farmerPaidList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FarmerPaidSearchActivity.class);
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