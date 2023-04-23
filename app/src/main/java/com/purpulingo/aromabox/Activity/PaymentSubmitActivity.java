package com.purpulingo.aromabox.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.purpulingo.aromabox.Constants.Constants;
import com.purpulingo.aromabox.Global.NetworkChange;
import com.purpulingo.aromabox.Global.Url;
import com.purpulingo.aromabox.Global.UserSessionManager;
import com.purpulingo.aromabox.Model.FarmerPaidListModel;
import com.purpulingo.aromabox.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PaymentSubmitActivity extends AppCompatActivity {
    private NetworkChange networkChange;
    private ImageView backBtn;
    private TextInputEditText paymentDate, paymentAmount, paymentRef;
    private CardView paymentSubmit;
    private ImageView calenderImageBtn;
    private String str_Date = "", str_viewDate, farmerId, farmerName, orgId, str_paymentAmount, str_refNo;
    UserSessionManager userSessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_submit);

        userSessionManager = new UserSessionManager(getApplicationContext());
        networkChange = new NetworkChange();
        backBtn = (ImageView) findViewById(R.id.backBtn);
        paymentDate = (TextInputEditText) findViewById(R.id.paymentDate);
        calenderImageBtn = (ImageView) findViewById(R.id.calenderImageBtn);
        paymentAmount = (TextInputEditText) findViewById(R.id.paymentAmount);
        paymentRef = (TextInputEditText) findViewById(R.id.paymentRef);
        paymentSubmit = (CardView) findViewById(R.id.paymentSubmit);

        orgId = userSessionManager.getOrgId();
        farmerId = getIntent().getStringExtra(Constants.KEY_PAYMENT.KEY_FARMER_ID);


        //calender
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        paymentDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(PaymentSubmitActivity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    int mo = month + 1;
                                    str_Date = year + "-" + mo + "-" + dayOfMonth;
                                    str_viewDate = dayOfMonth + "-" + mo + "-" + year;

                                    if (str_Date.isEmpty() == false) {
                                        paymentDate.setText(str_viewDate);
                                    }
                                }
                            }, year, month, day);
                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    datePickerDialog.show();
                }
            }
        });

        calenderImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(PaymentSubmitActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                int mo = month + 1;
                                str_Date = year + "-" + mo + "-" + dayOfMonth;
                                str_viewDate = dayOfMonth + "-" + mo + "-" + year;

                                if (str_Date.isEmpty() == false) {
                                    paymentDate.setText(str_viewDate);
                                }
                            }
                        }, year, month, day);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        //https://api2.boxfarming.in/wsapi/v1/index.php/Aroma/bx_ph_farmer_payment

        paymentSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paymentAmount.getText().toString().length() == 0){
                    paymentAmount.requestFocus();
                    paymentAmount.setError("Please Enter Amount");
                }else if (paymentDate.getText().toString().length() == 0){
                    paymentDate.requestFocus();
                    paymentDate.setError("Please Enter Date");
                }else if (paymentRef.getText().toString().length() == 0){
                    paymentRef.requestFocus();
                    paymentRef.setError("Please Enter Reference Number");
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PaymentSubmitActivity.this);
                    builder.setMessage(R.string.submitAlert)
                            .setCancelable(false)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    submit();
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

    public void submit(){
        final ProgressDialog progressDialog = new ProgressDialog(PaymentSubmitActivity.this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();

        str_paymentAmount = paymentAmount.getText().toString().trim();
        str_refNo = paymentRef.getText().toString().trim();

        //https://api2.boxfarming.in/wsapi/v1/index.php/Aroma/bx_ph_farmer_payment
        String JSON_URL = Url.PAYMENT_SUBMIT;
        HashMap<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put(Constants.KEY_PAYMENT.KEY_ORG_ID, orgId);
        jsonParams.put(Constants.KEY_PAYMENT.KEY_FARMER_ID, farmerId);
        jsonParams.put(Constants.KEY_PAYMENT.KEY_PAYMENT_DATE, str_Date);
        jsonParams.put(Constants.KEY_PAYMENT.KEY_PAYMENT_AMOUNT, str_paymentAmount);
        jsonParams.put(Constants.KEY_PAYMENT.KEY_REF_NO, str_refNo);

        JSONArray mJSONArray = new JSONArray(Arrays.asList(jsonParams));
        Log.d("mJSONArray", "mJSONArray" + mJSONArray);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, JSON_URL,
                mJSONArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressDialog.dismiss();

                AlertDialog.Builder builder = new AlertDialog.Builder(PaymentSubmitActivity.this);
                builder.setMessage(R.string.afterSubmit)
                        .setCancelable(false)
                        .setPositiveButton(R.string.continueBtn, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("response", "response" + response);
                                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
        requestQueue.add(jsonArrayRequest);
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