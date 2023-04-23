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
import android.widget.DatePicker;
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
import com.purpulingo.aromabox.Model.OilListModel;
import com.purpulingo.aromabox.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OilQuantityActivity extends AppCompatActivity {

    private ImageView backBtn;
    private TextView from_date, to_date, from_click_date, to_click_date, fromDateResult, toDateResult, updateResultBtn, totalQuantityResult;
    String str_fromDate = "", str_toDate = "", str_viewFromDate, str_viewToDate, orgId, oilQuantity;
    private LinearLayout from_date_linerLayout, to_date_linerLayout;
    private CardView dateTotalDetails;
    private NetworkChange networkChange;
    UserSessionManager userSessionManager;
    double  totalOil = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oil_quantity);

        networkChange = new NetworkChange();
        userSessionManager = new UserSessionManager(getApplicationContext());
        orgId = userSessionManager.getOrgId();

        backBtn = (ImageView) findViewById(R.id.backBtn);
        from_date = (TextView) findViewById(R.id.from_date);
        to_date = (TextView) findViewById(R.id.to_date);
        from_click_date = (TextView) findViewById(R.id.from_click_date);
        to_click_date = (TextView) findViewById(R.id.to_click_date);
        from_date_linerLayout = (LinearLayout) findViewById(R.id.from_date_linerLayout);
        to_date_linerLayout = (LinearLayout) findViewById(R.id.to_date_linerLayout);

        fromDateResult = (TextView) findViewById(R.id.fromDateResult);
        toDateResult = (TextView) findViewById(R.id.toDateResult);
        updateResultBtn = (TextView) findViewById(R.id.updateResultBtn);
        totalQuantityResult = (TextView) findViewById(R.id.totalQuantityResult);
        dateTotalDetails = (CardView) findViewById(R.id.dateTotalDetails);


        //calender
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        from_date_linerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(OilQuantityActivity.this,
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
        to_date_linerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(OilQuantityActivity.this,
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
                    Toast.makeText(OilQuantityActivity.this,"Please Enter From Date", Toast.LENGTH_SHORT).show();
                }if(str_toDate.isEmpty() || str_toDate == null){
                    Toast.makeText(OilQuantityActivity.this,"Please Enter To Date", Toast.LENGTH_SHORT).show();
                }else {
                    dataExtract();
                }
            }
        });
        dateTotalDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OilQuantityListActivity.class);
                intent.putExtra(Constants.KEY_OIL_LIST.KEY_START_DATE, str_fromDate);
                intent.putExtra(Constants.KEY_OIL_LIST.KEY_END_DATE, str_toDate);
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

    public void dataExtract() {
        final ProgressDialog progressDialog = new ProgressDialog(OilQuantityActivity.this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        String JSON_URL_LIST = Url.OIL_LIST;
        //String JSON_URL_LIST = "https://api2.boxfarming. = uin/wsapi/v1/index.php/Aroma/aroma_distillation_list";

        HashMap<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put(Constants.KEY_OIL_LIST.KEY_ORG_ID, orgId);
        jsonParams.put(Constants.KEY_OIL_LIST.KEY_START_DATE, str_fromDate);
        jsonParams.put(Constants.KEY_OIL_LIST.KEY_END_DATE, str_toDate);
        jsonParams.put(Constants.KEY_OIL_LIST.KEY_POS_ID, "1");

        JSONArray mJSONArray = new JSONArray(Arrays.asList(jsonParams));
        Log.d("mJSONArray", "mJSONArray: " + mJSONArray);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, JSON_URL_LIST,
                mJSONArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                dateTotalDetails.setVisibility(View.VISIBLE);
                fromDateResult.setText(str_viewFromDate);
                toDateResult.setText(str_viewToDate);
                for (int i=0;i<response.length();i++){
                    try {
                        progressDialog.dismiss();
                        JSONObject jsonObject = response.getJSONObject(i);
                        oilQuantity = jsonObject.getString(Constants.KEY_OIL_LIST.KEY_QUANTITY);
                        Log.d("response", "response: " + response);

                        totalOil = totalOil+Double.parseDouble(oilQuantity);
                        Log.d("totalOil", "sum totalOil: " + totalOil);

                        totalQuantityResult.setText(""+totalOil+" lts");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                totalOil=0.00;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Please Select Another Date",Toast.LENGTH_LONG).show();
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