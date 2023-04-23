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

import com.purpulingo.aromabox.Constants.Constants;
import com.purpulingo.aromabox.Global.NetworkChange;
import com.purpulingo.aromabox.R;

import java.util.Calendar;

public class FarmerPaidSearchActivity extends AppCompatActivity {
    private ImageView backBtn;
    private NetworkChange networkChange;
    String str_fromDate = "", str_toDate = "", str_viewFromDate, str_viewToDate;
    LinearLayout fromDateSelected,toDateSelected;
    TextView from_click_date,to_click_date, from_date, to_date, updateResultBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_paid_search);

        networkChange = new NetworkChange();
        backBtn = (ImageView) findViewById(R.id.backBtn);

        fromDateSelected = (LinearLayout) findViewById(R.id.fromDateSelected);
        toDateSelected = (LinearLayout) findViewById(R.id.toDateSelected);

        from_click_date = (TextView) findViewById(R.id.from_click_date);
        to_click_date = (TextView) findViewById(R.id.to_click_date);
        from_date = (TextView) findViewById(R.id.from_date);
        to_date = (TextView) findViewById(R.id.to_date);

        updateResultBtn = (TextView) findViewById(R.id.updateResultBtn);

        //calender
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        fromDateSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(FarmerPaidSearchActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String str_month = null;
                                String str_day = null;
                                month = month+1;
                                if((month+"").length()<2){
                                    str_month = "0"+month;
                                }else {
                                    str_month = ""+month;
                                }
                                if ((dayOfMonth+"").length()<2){
                                    str_day = "0"+dayOfMonth;
                                }else {
                                    str_day = ""+dayOfMonth;
                                }
                                str_fromDate = year + "-" + str_month + "-" + str_day;
                                str_viewFromDate = str_day + "-" + str_month + "-" +year ;


                                if(str_fromDate.isEmpty() == false){
                                    from_click_date.setVisibility(View.GONE);
                                    from_date.setVisibility(View.VISIBLE);
                                    from_date.setText(str_viewFromDate);
                                }
                            }
                        },year,month,day);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
        toDateSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(FarmerPaidSearchActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String str_month = null;
                                String str_day = null;
                                month = month+1;
                                if((month+"").length()<2){
                                    str_month = "0"+month;
                                }else {
                                    str_month = ""+month;
                                }
                                if ((dayOfMonth+"").length()<2){
                                    str_day = "0"+dayOfMonth;
                                }else {
                                    str_day = ""+dayOfMonth;
                                }
                                str_toDate = year + "-" + str_month + "-" + str_day;
                                str_viewToDate = str_day + "-" + str_month + "-" +year ;
                                if(str_fromDate.isEmpty() == false){
                                    to_click_date.setVisibility(View.GONE);
                                    to_date.setVisibility(View.VISIBLE);
                                    to_date.setText(str_viewToDate);
                                }

                            }
                        },year,month,day);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
        updateResultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(str_fromDate.isEmpty() || str_fromDate == null){
                    Toast.makeText(FarmerPaidSearchActivity.this,"Please Enter From Date", Toast.LENGTH_SHORT).show();
                }if(str_toDate.isEmpty() || str_toDate == null){
                    Toast.makeText(FarmerPaidSearchActivity.this,"Please Enter To Date", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(getApplicationContext(), FarmerListPaymentActivity.class);
                    intent.putExtra(Constants.KEY_PAID_LIST.KEY_START_DATE, str_fromDate);
                    intent.putExtra(Constants.KEY_PAID_LIST.KEY_END_DATE, str_toDate);
                    startActivity(intent);
                }
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