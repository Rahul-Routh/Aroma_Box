package com.purpulingo.aromabox.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.purpulingo.aromabox.Constants.Constants;
import com.purpulingo.aromabox.Global.NetworkChange;
import com.purpulingo.aromabox.Global.Url;
import com.purpulingo.aromabox.Global.UserSessionManager;
import com.purpulingo.aromabox.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ExitingBatchActivity extends AppCompatActivity {

    private TextView batchIdTextView, dateExitingTextView, timeExitingTextView, biomassExitingQuantity, endDateTextView, endTimeTextView;
    private EditText oilQuantity;
    private ImageView backBtn;
    private CardView exitingBatchBtn;
    private String str_batchId, str_start_date, str_start_time, str_bioMass, str_oilQuantity, str_endDate,str_endViewDate, str_endTime, str_userId,
                    str_machineId, str_orgId;
    private NetworkChange networkChange;
    UserSessionManager userSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exiting_batch);

        userSessionManager = new UserSessionManager(getApplicationContext());
        networkChange = new NetworkChange();
        backBtn = (ImageView) findViewById(R.id.backBtn);

        batchIdTextView = (TextView) findViewById(R.id.batchId);
        dateExitingTextView = (TextView) findViewById(R.id.dateExitingTextView);
        timeExitingTextView = (TextView) findViewById(R.id.timeExitingTextView);
        biomassExitingQuantity = (TextView) findViewById(R.id.biomassExitingQuantity);
        exitingBatchBtn = (CardView) findViewById(R.id.exitingBatchBtn);
        endDateTextView = (TextView) findViewById(R.id.endDateTextView);
        endTimeTextView = (TextView) findViewById(R.id.endTimeTextView);
        oilQuantity = (EditText) findViewById(R.id.oilQuantity);

        str_userId = userSessionManager.getUserID();
        str_orgId = userSessionManager.getOrgId();


        str_machineId = getIntent().getStringExtra(Constants.EXITING_DISTILLATION_BATCH.KEY_POS_ID);
        str_batchId = getIntent().getStringExtra(Constants.EXITING_DISTILLATION_BATCH.KEY_BATCH_ID);
        str_start_date = getIntent().getStringExtra(Constants.EXITING_DISTILLATION_BATCH.KEY_START_DATE);
        str_start_time = getIntent().getStringExtra(Constants.EXITING_DISTILLATION_BATCH.KEY_START_TIME);
        str_bioMass = getIntent().getStringExtra(Constants.EXITING_DISTILLATION_BATCH.KEY_QUANTITY);

        batchIdTextView.setText(str_batchId);
        dateExitingTextView.setText(str_start_date);
        timeExitingTextView.setText(str_start_time);
        biomassExitingQuantity.setText(str_bioMass);

        //calender
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        //show date
        SimpleDateFormat simpleDateFormatView =  new SimpleDateFormat("dd-MM-yyyy");
        str_endViewDate = simpleDateFormatView.format(calendar.getTime());
        endDateTextView.setText(str_endViewDate);
        //send database
        SimpleDateFormat simpleDateFormat1 =  new SimpleDateFormat("yyyy-MM-dd");
        str_endDate = simpleDateFormat1.format(calendar.getTime());

        //time
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        str_endTime = simpleDateFormat.format(calendar.getTime());
        endTimeTextView.setText(str_endTime);


        endDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ExitingBatchActivity.this,
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
                                str_endDate = year + "-" + str_month + "-" + str_day;
                                str_endViewDate = str_day + "-" + str_month + "-" + year;
                                endDateTextView.setText(str_endViewDate);
                            }
                        }, year,month,day);
                //disable future date
                calendar.set(calendar.DAY_OF_MONTH, day-7);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                //show date picker dialog
                datePickerDialog.show();
            }
        });

        endTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(ExitingBatchActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                //SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                                calendar.set(Calendar.HOUR, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);

                                //calendar.set(0,0,0,hourOfDay,minute);
                                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                                str_endTime = timeFormat.format(calendar.getTime());
                                endTimeTextView.setText(str_endTime);

                            }
                        }, hour, minute,true);
                timePickerDialog.show();
            }
        });

        exitingBatchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oilQuantity.getText().toString().trim().isEmpty()) {
                    oilQuantity.requestFocus();
                    oilQuantity.setError("Enter Quantity");
                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(ExitingBatchActivity.this);
                    builder.setMessage(R.string.startExitingBatch)
                            .setCancelable(false)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    str_oilQuantity = oilQuantity.getText().toString().trim();
                                    retrieveData();
                                   // Toast.makeText(getApplicationContext(), "Submit", Toast.LENGTH_SHORT).show();
                                }
                            }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

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


    public void retrieveData(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");

        progressDialog.show();

        String JSON_URL = Url.DISTILLATION_END;

        HashMap<String, String> jsonParams = new HashMap<String, String>();

        jsonParams.put(Constants.END_DISTILLATION.KEY_USER_ID, str_userId);
        jsonParams.put(Constants.END_DISTILLATION.KEY_ORG_ID, str_orgId);
        jsonParams.put(Constants.END_DISTILLATION.KEY_POS_ID, str_machineId);
        jsonParams.put(Constants.END_DISTILLATION.KEY_BATCH_ID, str_batchId);
        jsonParams.put(Constants.END_DISTILLATION.KEY_END_DATE, str_endDate);
        jsonParams.put(Constants.END_DISTILLATION.KEY_END_TIME, str_endTime);
        jsonParams.put(Constants.END_DISTILLATION.KEY_QUANTITY, str_oilQuantity);
        jsonParams.put(Constants.END_DISTILLATION.KEY_UOM, "0");

        JSONArray mJSONArray = new JSONArray(Arrays.asList(jsonParams));

        Log.d("mJSONArray", "mJSONArray: " + mJSONArray);

        JsonArrayRequest postRequest = new JsonArrayRequest(Request.Method.POST, JSON_URL,
                mJSONArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        progressDialog.dismiss();

                        JSONObject jsonObject2 = response.getJSONObject(i);

                        String s = jsonObject2.getString(Constants.START_DISTILLATION.KEY_RETCODE);
                        Log.d("response","response: "+s);
                        AlertDialog.Builder builder = new AlertDialog.Builder(ExitingBatchActivity.this);
                        builder.setMessage(R.string.afterSubmit)
                                .setCancelable(false)
                                .setPositiveButton(R.string.continueBtn, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //   Handle Error
                progressDialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(postRequest);

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