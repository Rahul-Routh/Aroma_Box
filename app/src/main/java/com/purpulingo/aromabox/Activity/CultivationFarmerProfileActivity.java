package com.purpulingo.aromabox.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.purpulingo.aromabox.Constants.Constants;
import com.purpulingo.aromabox.Global.NetworkChange;
import com.purpulingo.aromabox.Global.Url;
import com.purpulingo.aromabox.Global.UserSessionManager;
import com.purpulingo.aromabox.R;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class CultivationFarmerProfileActivity extends AppCompatActivity {

    private ImageView backBtn;
    private CircleImageView farmerProfilePic;
    private TextView farmerName, address,farmerId, lastFertigationDate, lastIrrigationDate, nextCuttingDate,lastCuttingDate, area;
    private NetworkChange networkChange;

    String str_farmerId, str_farmerName, str_farmerVillage, str_address,str_farmerImg, str_orgId, str_userId, str_orgName , str_area,
            str_lastCutting, str_nextCutting, str_lastIrrigation, str_lastFertigation , str_work,str_viewDate,str_Date;
    UserSessionManager userSessionManager;
    AutoCompleteTextView selectWork;
    private TextInputEditText selectDate;
    private ImageView dateImageBtn;
    private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cultivation_farmer_profile);

        userSessionManager = new UserSessionManager(getApplicationContext());

        networkChange = new NetworkChange();
        backBtn = (ImageView) findViewById(R.id.backBtn);
        farmerProfilePic = (CircleImageView) findViewById(R.id.farmerProfileImg);
        farmerName = (TextView) findViewById(R.id.firstName);
        farmerId = (TextView) findViewById(R.id.farmerId);
        address = (TextView) findViewById(R.id.address);
        selectDate = (TextInputEditText) findViewById(R.id.selectDate);
        dateImageBtn = (ImageView) findViewById(R.id.dateImageBtn);
        lastFertigationDate = (TextView) findViewById(R.id.lastFertigationDate);
        lastIrrigationDate = (TextView) findViewById(R.id.lastIrrigationDate);
        nextCuttingDate = (TextView) findViewById(R.id.nextCuttingDate);
        lastCuttingDate = (TextView) findViewById(R.id.lastCuttingDate);
        area = (TextView) findViewById(R.id.area);
        selectWork = (AutoCompleteTextView) findViewById(R.id.selectWork);
        submitBtn = (Button) findViewById(R.id.submitBtn);

        str_farmerId = getIntent().getStringExtra(Constants.KEY_FARMER_PG_LIST.KEY_FARMER_ID);
        str_farmerName = getIntent().getStringExtra(Constants.KEY_FARMER_PG_LIST.KEY_FARMER_NAME);
        str_farmerVillage = getIntent().getStringExtra(Constants.KEY_FARMER_PG_LIST.KEY_VILLAGE);
        str_farmerImg = getIntent().getStringExtra(Constants.KEY_FARMER_PG_LIST.KEY_IMG);
        str_area = getIntent().getStringExtra(Constants.KEY_FARMER_PG_LIST.KEY_AREA);
        str_address = getIntent().getStringExtra(Constants.KEY_FARMER_PG_LIST.KEY_ADDRESS);

        str_lastCutting = getIntent().getStringExtra(Constants.KEY_FARMER_PG_LIST.KEY_LAST_CUTTING);
        str_nextCutting = getIntent().getStringExtra(Constants.KEY_FARMER_PG_LIST.KEY_NEXT_CUTTING);
        str_lastIrrigation = getIntent().getStringExtra(Constants.KEY_FARMER_PG_LIST.KEY_LAST_IRR);
        str_lastFertigation = getIntent().getStringExtra(Constants.KEY_FARMER_PG_LIST.KEY_LAST_FER);


        str_orgId = userSessionManager.getOrgId();
        str_userId = userSessionManager.getUserID();
        str_orgName = userSessionManager.getOrgName();

        farmerName.setText(str_farmerName);
        farmerId.setText(" ("+str_farmerId+")");
        address.setText(str_farmerVillage);
        lastCuttingDate.setText(str_lastCutting);
        nextCuttingDate.setText(str_nextCutting);
        lastIrrigationDate.setText(str_lastIrrigation);
        lastFertigationDate.setText(str_lastFertigation);
        area.setText(str_area);

        //farmerProfilePic.setImageResource(getIntent().getExtras().getInt("image"));

        calender();

        selectWork.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String work = selectWork.getText().toString();
                if (work.equals("इरीगेशन")) {
                    str_work = "1";
                } else if (work.equals("फर्टिगेशन")) {
                    str_work = "2";
                } else if (work.equals("फील्ड विजिट")) {
                    str_work = "3";
                } else if (work.equals("कोडाइ")) {
                    str_work = "4";
                }else {
                    str_work = "0";
                }

                Log.d("work","work"+str_work);
            }
        });


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (str_work.equals("0")) {
                    Toast.makeText(getApplicationContext(), "सेलेक्ट ऑप्शन", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CultivationFarmerProfileActivity.this);
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

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CultivationFarmerProfileActivity.this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.selectWorkList));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectWork.setAdapter(arrayAdapter);
    }
    public void submit(){
        final ProgressDialog progressDialog = new ProgressDialog(CultivationFarmerProfileActivity.this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();

        String JSON_URL = Url.FARMER_SUBMIT_PROFILE;

        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put(Constants.KEY_FARMER_PROFILE_SUBMIT.KEY_ORG_ID, str_orgId);
        jsonParams.put(Constants.KEY_FARMER_PROFILE_SUBMIT.KEY_USER_ID, str_userId);
        jsonParams.put(Constants.KEY_FARMER_PROFILE_SUBMIT.KEY_FARMER_ID, str_farmerId);
        jsonParams.put(Constants.KEY_FARMER_PROFILE_SUBMIT.KEY_OPS_ID, str_work);
        jsonParams.put(Constants.KEY_FARMER_PROFILE_SUBMIT.KEY_OPS_DATE, str_Date);
        //JSONObject jsonParam = new JSONObject(jsonParams);
        JSONArray mJSONArray = new JSONArray(Arrays.asList(jsonParams));

        Log.d("response123", "response" + mJSONArray);
        //Log.d("json", "json" + jsonParam);
        JsonArrayRequest postRequest = new JsonArrayRequest(Request.Method.POST, JSON_URL,
                mJSONArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressDialog.dismiss();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        AlertDialog.Builder builder = new AlertDialog.Builder(CultivationFarmerProfileActivity.this);
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
                progressDialog.dismiss();
                Toast.makeText(CultivationFarmerProfileActivity.this, "कृपया मान्य विवरण दर्ज करें", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(CultivationFarmerProfileActivity.this);
        requestQueue.add(postRequest);
    }

    public void calender() {
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        //show date
        SimpleDateFormat simpleDateFormatView = new SimpleDateFormat("dd-MM-yyyy");
        str_viewDate = simpleDateFormatView.format(calendar.getTime());
        selectDate.setText(str_viewDate);
        //String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        //send database
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        str_Date = simpleDateFormat1.format(calendar.getTime());
        dateImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CultivationFarmerProfileActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                int mo = month + 1;

                                if (mo < 10) {
                                    str_viewDate = dayOfMonth + "-" + "0" + mo + "-" + year;
                                    str_Date = year + "-" + "0" + mo + "-" + dayOfMonth;
                                } else {
                                    str_viewDate = dayOfMonth + "-" + mo + "-" + year;
                                    str_Date = year + "-" + mo + "-" + dayOfMonth;
                                }

                                if (str_Date.isEmpty() == false) {
                                    selectDate.setText(str_viewDate);
                                }
                            }
                        }, year, month, day);
                calendar.set(calendar.DAY_OF_MONTH, day - 15);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                datePickerDialog.show();
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