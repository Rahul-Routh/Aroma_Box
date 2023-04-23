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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.purpulingo.aromabox.Activity.ExitingBatchActivity;
import com.purpulingo.aromabox.Constants.Constants;
import com.purpulingo.aromabox.Global.NetworkChange;
import com.purpulingo.aromabox.Global.Url;
import com.purpulingo.aromabox.Global.UserSessionManager;
import com.purpulingo.aromabox.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class NewBatchActivity extends AppCompatActivity {

    private TextView dateTextView, timeTextView;
    private CardView newBatchBtn;
    private EditText biomassQuantity;
    private ImageView backBtn;
    private Spinner biomassQuality;
    AutoCompleteTextView farmerListSelect;
    private NetworkChange networkChange;
    private String str_currentDate, str_time, str_quantity, str_userId, str_orgId, str_currentViewDate,
            str_bioMassQuality, indentId, str_farmerId, farmerID, farmerName, jsonFarmer;
    UserSessionManager userSessionManager;

    ArrayAdapter<String> farAdapter;
    ArrayList<String> farList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_batch);

        userSessionManager = new UserSessionManager(getApplicationContext());


        str_userId = userSessionManager.getUserID();
        str_orgId = userSessionManager.getOrgId();
        indentId = "0";

        networkChange = new NetworkChange();
        backBtn = (ImageView) findViewById(R.id.backBtn);
        dateTextView = (TextView) findViewById(R.id.dateTextView);
        timeTextView = (TextView) findViewById(R.id.timeTextView);
        newBatchBtn = (CardView) findViewById(R.id.newBatchBtn);
        biomassQuantity = (EditText) findViewById(R.id.biomassQuantity);
        biomassQuality = (Spinner) findViewById(R.id.biomassQuality);
        farmerListSelect = (AutoCompleteTextView) findViewById(R.id.farmerListSelect);



        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        //show date
        SimpleDateFormat simpleDateFormatView =  new SimpleDateFormat("dd-MM-yyyy");
        str_currentViewDate = simpleDateFormatView.format(calendar.getTime());
        dateTextView.setText(str_currentViewDate);
        //String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        //send database
        SimpleDateFormat simpleDateFormat1 =  new SimpleDateFormat("yyyy-MM-dd");
        str_currentDate = simpleDateFormat1.format(calendar.getTime());


       // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        str_time = simpleDateFormat.format(calendar.getTime());
        timeTextView.setText(str_time);

        //calender
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewBatchActivity.this,
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
                                str_currentDate = year + "-" + str_month + "-" + str_day;
                                str_currentViewDate = str_day + "-" + str_month + "-" + year;
                                dateTextView.setText(str_currentViewDate);
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
        //time
        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(NewBatchActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                //SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                                calendar.set(Calendar.HOUR, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);

                                //calendar.set(0,0,0,hourOfDay,minute);
                                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                                str_time = timeFormat.format(calendar.getTime());
                                timeTextView.setText(str_time);

                            }
                        }, hour, minute,true);
                timePickerDialog.show();
            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        newBatchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(biomassQuantity.getText().toString().trim().isEmpty()){
                    biomassQuantity.requestFocus();
                    biomassQuantity.setError("Enter Quantity");
                }else if(str_bioMassQuality.equals("0")) {
                    Toast.makeText(getApplicationContext(), getString(R.string.pleaseSelectGrade) , Toast.LENGTH_SHORT).show();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NewBatchActivity.this);
                    builder.setMessage(R.string.startNewBatch)
                            .setCancelable(false)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    str_quantity = biomassQuantity.getText().toString().trim();
                                    // Log.d("str_quantity", "str_quantity: " + str_quantity);
                                    retrieveData();
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


        biomassQuality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > 0) {
                    // Notify the selected item text
                    //Toast.makeText(getApplicationContext(), "Selected : " , Toast.LENGTH_SHORT).show();
                }
                if(position==0){
                    str_bioMassQuality = "0";
                }
                if(position==1){
                    str_bioMassQuality = "1";
                }
                if(position==2){
                    str_bioMassQuality = "2";
                }
                if(position==3){
                    str_bioMassQuality = "3";
                }

                //Toast.makeText(getApplicationContext(), "Selected : " +str_bioMassQuality, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        farmerListSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String str_pg = farmerListSelect.getText().toString();

                try {
                JSONArray jsonArr = new JSONArray(jsonFarmer);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "Selected : " +str_pg, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        farmerList();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.selectBiomassQuality, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        biomassQuality.setAdapter(adapter);
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

    public void retrieveData(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");

        progressDialog.show();

        //https://api2.boxfarming.in/wsapi/v1/index.php/Aroma/aroma_distillation_start

       // [{"start_time":"11:33:56","uom":"0", "quantity":"1", "user_id":"128", "org_id":"111", "pos_id":"2", "start_date":"2021-11-20"}]


        String JSON_URL = Url.DISTILLATION_START;

        HashMap<String, String> jsonParams = new HashMap<String, String>();

        jsonParams.put(Constants.START_DISTILLATION.KEY_USER_ID, str_userId);
        jsonParams.put(Constants.START_DISTILLATION.KEY_ORG_ID, str_orgId);
        jsonParams.put(Constants.START_DISTILLATION.KEY_POS_ID, "2");
        jsonParams.put(Constants.START_DISTILLATION.KEY_START_DATE, str_currentDate);
        jsonParams.put(Constants.START_DISTILLATION.KEY_START_TIME, str_time);
        jsonParams.put(Constants.START_DISTILLATION.KEY_QUANTITY, str_quantity);
        jsonParams.put(Constants.START_DISTILLATION.KEY_QUALITY, str_bioMassQuality);
        jsonParams.put(Constants.START_DISTILLATION.KEY_UOM, "0");

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

                        AlertDialog.Builder builder = new AlertDialog.Builder(NewBatchActivity.this);
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

                        //intent.putExtra("currentDate",str_currentDate);
                        //intent.putExtra("time",str_time);
                        //intent.putExtra("quantity",str_quantity);

                        //status = response.getString(Constants.GET_DATA_SEND_OTP.KEY_STATUS);
                        //customer_mobile_no = response.getString(Constants.GET_DATA_SEND_OTP.KEY_MOBILE_NUMBER);
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
                Log.d("Error", "ErrorSendOtp" + error.getMessage().toString());
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

    public void farmerList(){
        //String NOTIFICATION_LIST_DETAILS_URL = "https://api2.boxfarming.in/wsapi/v1/index.php/Farmer/aroma_indent_list_details"
        String JSON_URL_LIST = Url.NOTIFICATION_LIST_DETAILS_URL;
        HashMap<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put(Constants.NOTIFICATION_LIST_DETAILS.KEY_USER_ID, str_userId);
        jsonParams.put(Constants.NOTIFICATION_LIST_DETAILS.KEY_INDENT_ID, indentId);
        jsonParams.put(Constants.NOTIFICATION_LIST_DETAILS.KEY_ORG_ID, str_orgId);
        jsonParams.put(Constants.NOTIFICATION_LIST_DETAILS.KEY_OPS_TYPE, "2");

        JSONArray mJSONArray = new JSONArray(Arrays.asList(jsonParams));
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, JSON_URL_LIST,
                mJSONArray, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonFarmer = String.valueOf(response);
                        JSONObject jsonObject2 = response.getJSONObject(i);
                        farmerName = jsonObject2.getString(Constants.NOTIFICATION_LIST_DETAILS.KEY_FARMER_NAME);
                        farmerID = jsonObject2.getString(Constants.NOTIFICATION_LIST_DETAILS.KEY_FARMER_ID);
                        farList.add(farmerName);

                        farAdapter = new ArrayAdapter<>(NewBatchActivity.this
                                , android.R.layout.simple_spinner_item, farList);
                        farAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        farmerListSelect.setAdapter(farAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),R.string.noOrderListInsert,Toast.LENGTH_SHORT).show();
                Log.d("Error", "ErrorListView: " + error);
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
}