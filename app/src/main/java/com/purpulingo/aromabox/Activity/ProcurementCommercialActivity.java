package com.purpulingo.aromabox.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.purpulingo.aromabox.Model.FarmerList;
import com.purpulingo.aromabox.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ProcurementCommercialActivity extends AppCompatActivity {

    private ImageView backBtn;
    private NetworkChange networkChange;
    private TextView from_date, to_date, from_click_date, to_click_date, fromDateResult, toDateResult, totalPriceResult,
            totalQuantityResult, updateResultBtn;
    String str_fromDate = "", str_toDate = "", str_viewFromDate, str_viewToDate, userId, orgId, quantity = "", amount = "";
    private LinearLayout from_date_linerLayout, to_date_linerLayout;
    private CardView dateTotalDetails;

    UserSessionManager userSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procurement_commercial);

        userSessionManager = new UserSessionManager(getApplicationContext());
        networkChange = new NetworkChange();
        backBtn = (ImageView) findViewById(R.id.backBtn);
        from_date = (TextView) findViewById(R.id.from_date);
        to_date = (TextView) findViewById(R.id.to_date);
        from_click_date = (TextView) findViewById(R.id.from_click_date);
        to_click_date = (TextView) findViewById(R.id.to_click_date);
        from_date_linerLayout = (LinearLayout) findViewById(R.id.from_date_linerLayout);
        to_date_linerLayout = (LinearLayout) findViewById(R.id.to_date_linerLayout);

        fromDateResult = (TextView) findViewById(R.id.fromDateResult);
        toDateResult = (TextView) findViewById(R.id.toDateResult);
        totalPriceResult = (TextView) findViewById(R.id.totalPriceResult);
        totalQuantityResult = (TextView) findViewById(R.id.totalQuantityResult);
        updateResultBtn = (TextView) findViewById(R.id.updateResultBtn);
        dateTotalDetails = (CardView) findViewById(R.id.dateTotalDetails);


        userId = userSessionManager.getUserID();
        orgId = userSessionManager.getOrgId();
        //calender
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);



        from_date_linerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ProcurementCommercialActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                //int mo = month+1;
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
        to_date_linerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ProcurementCommercialActivity.this,
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
                    Toast.makeText(ProcurementCommercialActivity.this,"Please Enter From Date", Toast.LENGTH_SHORT).show();
                }if(str_toDate.isEmpty() || str_toDate == null){
                    Toast.makeText(ProcurementCommercialActivity.this,"Please Enter To Date", Toast.LENGTH_SHORT).show();
                }else {


                    retrieveData();
                    Log.d("str_fromDate", "str_fromDate: " + str_fromDate);
                    Log.d("str_toDate", "str_toDate: " + str_toDate);
                }
            }
        });


        dateTotalDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FarmerListComProcurementActivity.class);
                intent.putExtra(Constants.KEY_PROCUREMENT_SEARCH.KEY_START_DATE, str_fromDate);
                intent.putExtra(Constants.KEY_PROCUREMENT_SEARCH.KEY_END_DATE, str_toDate);
                Log.d("str_fromDate", "str_fromDate: " + str_fromDate);
                Log.d("str_toDate", "str_toDate: " + str_toDate);
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
    public void retrieveData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");

        progressDialog.show();

        String JSON_URL = Url.PROCUREMENT_SEARCH;
        HashMap<String, String> jsonParams = new HashMap<String, String>();

        jsonParams.put(Constants.KEY_PROCUREMENT_SEARCH.KEY_ORG_ID, orgId);
        jsonParams.put(Constants.KEY_PROCUREMENT_SEARCH.KEY_START_DATE, str_fromDate);
        jsonParams.put(Constants.KEY_PROCUREMENT_SEARCH.KEY_END_DATE, str_toDate);

        JSONArray mJSONArray = new JSONArray(Arrays.asList(jsonParams));

        Log.d("mJSONArray", "mJSONArray: " + mJSONArray);

        JsonArrayRequest postRequest = new JsonArrayRequest(Request.Method.POST, JSON_URL,
                mJSONArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("response", "response32: " + response);
                dateTotalDetails.setVisibility(View.VISIBLE);
                fromDateResult.setText(str_viewFromDate);
                toDateResult.setText(str_viewToDate);
                for (int i = 0; i < response.length(); i++) {

                    try {
                        progressDialog.dismiss();

                        JSONObject jsonObject = response.getJSONObject(i);

                        quantity = jsonObject.getString(Constants.KEY_PROCUREMENT_SEARCH.KEY_QUANTITY);
                        amount= jsonObject.getString(Constants.KEY_PROCUREMENT_SEARCH.KEY_AMOUNT);
                        Log.d("quantity", "quantity: " + quantity);
                        Log.d("amount", "amount: " + amount);

                        if (quantity.equals("0") == true || quantity.equals("null") == true) {
                            totalQuantityResult.setText("0");
                        }else {
                            totalQuantityResult.setText(quantity);
                        }

                        if (amount.equals("0") == true || amount.equals("null") == true) {
                            totalPriceResult.setText("0");
                        }else {
                            totalPriceResult.setText(amount);
                        }


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
                Toast.makeText(getApplicationContext(),"Please Select Another Date",Toast.LENGTH_LONG).show();
                Log.d("Error", "ErrorSendOtp" + error.getMessage().toString());
                dateTotalDetails.setVisibility(View.GONE);
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