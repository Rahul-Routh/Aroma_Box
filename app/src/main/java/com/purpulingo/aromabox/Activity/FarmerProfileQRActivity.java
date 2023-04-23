package com.purpulingo.aromabox.Activity;

import androidx.appcompat.app.AppCompatActivity;

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
import com.purpulingo.aromabox.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class FarmerProfileQRActivity extends AppCompatActivity {

    private TextView farmerId, firstName, lastName, mobileNo, village, block, dist, state, pinCode;
    private ImageView backBtn;
    private NetworkChange networkChange;
    private LinearLayout qrFarmerProfile;
    private CircleImageView farmerProfilePic;

    private EditText profileBioMassQuantity, selectDate;
    private ImageView calenderImageBtn;
    private Button submit_btn;

    private String farmer_photo, qrval, str_firstName, str_lastName, str_mobile_no,
            str_village, str_block, str_dist, str_state, str_pinCode
            , retcode, str_orgId, str_productId, str_farmerId, str_qty, str_uom, str_update;
    String str_Date = "", str_viewDate;

    UserSessionManager userSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_profile_qr);

        networkChange = new NetworkChange();
        userSessionManager = new UserSessionManager(getApplicationContext());
        str_orgId  = userSessionManager.getOrgId();

        backBtn = (ImageView) findViewById(R.id.backBtn);
        qrFarmerProfile = (LinearLayout) findViewById(R.id.qrFarmerProfile);

        farmerId = (TextView) findViewById(R.id.farmerIdProfile);
        farmerProfilePic = (CircleImageView) findViewById(R.id.farmerProfilePic);
        firstName = (TextView) findViewById(R.id.farmerFirstNameProfile);
        lastName = (TextView) findViewById(R.id.farmerLastNameProfile);
        mobileNo = (TextView) findViewById(R.id.farmerMobileNoProfile);

        village = (TextView) findViewById(R.id.profileAddressVillage);
        block = (TextView) findViewById(R.id.profileAddressBlock);
        dist = (TextView) findViewById(R.id.profileAddressDistrict);
        state = (TextView) findViewById(R.id.profileAddressState);
        pinCode = (TextView) findViewById(R.id.profileAddressPinCode);

        profileBioMassQuantity = (EditText) findViewById(R.id.profileBioMassQuantity);
        selectDate = (EditText) findViewById(R.id.selectDate);
        calenderImageBtn = (ImageView) findViewById(R.id.calenderImageBtn);
        submit_btn = (Button) findViewById(R.id.submit_btn);

        //String text = getIntent().getStringExtra("Result");
        //textView.setText(text);

        //calender
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        //String getCode = getIntent().getStringExtra(Constants.QR_SCANNER_PROFILE.KEY_QRVAL);
        qrval = getIntent().getStringExtra(Constants.QR_SCANNER_PROFILE.KEY_QRVAL);
        //qrval = str_orgId +"|"+getCode;
        Log.d("KEY_QRVAL", "KEY_QRVAL = " + qrval);



//        selectDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    DatePickerDialog datePickerDialog = new DatePickerDialog(FarmerProfileQRActivity.this,
//                            new DatePickerDialog.OnDateSetListener() {
//                                @Override
//                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                                    String str_month = null;
//                                    String str_day = null;
//                                    month = month+1;
//                                    if((month+"").length()<2){
//                                        str_month = "0"+month;
//                                    }else {
//                                        str_month = ""+month;
//                                    }
//                                    if ((dayOfMonth+"").length()<2){
//                                        str_day = "0"+dayOfMonth;
//                                    }else {
//                                        str_day = ""+dayOfMonth;
//                                    }
//                                    str_Date = year + "-" + str_month + "-" + str_day;
//                                    str_viewDate = str_day + "-" + str_month + "-" + year;
//
//                                    if (str_Date.isEmpty() == false) {
//                                        selectDate.setText(str_viewDate);
//                                    }
//                                }
//                            }, year, month, day);
//                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
//                    datePickerDialog.show();
//                }
//            }
//        });
        calenderImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(FarmerProfileQRActivity.this,
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
                                str_Date = year + "-" + str_month + "-" + str_day;
                                str_viewDate = str_day + "-" + str_month + "-" + year;

                                if (str_Date.isEmpty() == false) {
                                    selectDate.setText(str_viewDate);
                                }
                            }
                        }, year, month, day);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(profileBioMassQuantity.getText().toString().length() == 0){
                    profileBioMassQuantity.requestFocus();
                    profileBioMassQuantity.setError("Please Enter BioMass Quantity");
                }else if(selectDate.getText().toString().length() == 0) {
                    selectDate.requestFocus();
                    selectDate.setError("Please Enter Date");
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(FarmerProfileQRActivity.this);
                    builder.setMessage(R.string.submitAlert)
                            .setCancelable(false)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    submitData();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChange, filter);
        retrieveData();
        super.onStart();
    }
    @Override
    protected void onStop() {
        unregisterReceiver(networkChange);
        super.onStop();
    }

    public void submitData(){
        str_qty = profileBioMassQuantity.getText().toString();

        final ProgressDialog progressDialog = new ProgressDialog(FarmerProfileQRActivity.this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();

        // String JSON_URL_LIST = "https://api2.boxfarming.in/wsapi/v1/index.php/Aroma/bx_ph_stock_in"
        //[{"org_id":"101", "product_id":"1", "farmer_id":"123", "qty":"10", "uom":"0", "update":"2021-02-11"}]
        String JSON_URL = Url.QR_PROFILE;
        //str_productId, str_farmerId, str_qty, str_uom, str_update
        HashMap<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put(Constants.KEY_QR_FARMER_PROFILE.KEY_ORG_ID, str_orgId);
        jsonParams.put(Constants.KEY_QR_FARMER_PROFILE.KEY_PRODUCT_ID, "1");
        jsonParams.put(Constants.KEY_QR_FARMER_PROFILE.KEY_FARMER_ID, str_farmerId);
        jsonParams.put(Constants.KEY_QR_FARMER_PROFILE.KEY_QUANTITY, str_qty);
        jsonParams.put(Constants.KEY_QR_FARMER_PROFILE.KEY_UOM, "0");
        jsonParams.put(Constants.KEY_QR_FARMER_PROFILE.KEY_DATE, str_Date);

        JSONArray mJSONArray = new JSONArray(Arrays.asList(jsonParams));
        Log.d("mJSONArray", "mJSONArray: " + mJSONArray);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, JSON_URL,
                mJSONArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("mJSONArray", "mJSONArray: " + response);
                progressDialog.dismiss();
                        //JSONObject jsonObject = response.getJSONObject(i);

//                        retcode = jsonObject.getString(Constants.START_DISTILLATION.KEY_RETCODE);
//                        Log.d("response","response: "+retcode);

                AlertDialog.Builder builder = new AlertDialog.Builder(FarmerProfileQRActivity.this);
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



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d("Error", "ErrorListView: " + error);
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonArrayRequest);

    }

    public void retrieveData() {
        final ProgressDialog progressDialog = new ProgressDialog(FarmerProfileQRActivity.this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();

       // String JSON_URL_LIST = "https://api2.boxfarming.in/wsapi/v1/index.php/Aroma/farmer_search_qr";
        //[{"qrval":"101|123"}]
        String JSON_URL = Url.FARMER_SEARCH_QR;

        HashMap<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put(Constants.QR_SCANNER_PROFILE.KEY_QRVAL, qrval);

        JSONArray mJSONArray = new JSONArray(Arrays.asList(jsonParams));
        Log.d("mJSONArray", "mJSONArray: " + mJSONArray);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, JSON_URL,
                mJSONArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("response", "response: " + response);
               // for (int i =0; i<response.length();i++) {
                    try {
                        qrFarmerProfile.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                        Log.d("JsonArray",response.toString());
                        for(int i=0;i<response.length();i++){
                            JSONObject jsonResponse = response.getJSONObject(i);

                            str_farmerId = jsonResponse.getString(Constants.QR_SCANNER_PROFILE.KEY_FARMER_ID);
                            str_firstName = jsonResponse.getString(Constants.QR_SCANNER_PROFILE.KEY_FIRST_NAME);
                            str_lastName = jsonResponse.getString(Constants.QR_SCANNER_PROFILE.KEY_LAST_NAME);
                            str_mobile_no = jsonResponse.getString(Constants.QR_SCANNER_PROFILE.KEY_MOBILE);

                            str_village = jsonResponse.getString(Constants.QR_SCANNER_PROFILE.KEY_VILLAGE);
                            str_block = jsonResponse.getString(Constants.QR_SCANNER_PROFILE.KEY_BLOCK);
                            str_dist = jsonResponse.getString(Constants.QR_SCANNER_PROFILE.KEY_DISTRICT);
                            str_state = jsonResponse.getString(Constants.QR_SCANNER_PROFILE.KEY_STATE);
                            str_pinCode = jsonResponse.getString(Constants.QR_SCANNER_PROFILE.KEY_PIN_CODE);

                            farmer_photo = jsonResponse.getString(Constants.QR_SCANNER_PROFILE.KEY_PHOTO);
                            Log.d("farmer_photo", "farmer_photo: " + farmer_photo);

                            farmerId.setText(str_farmerId);
                            firstName.setText(str_firstName);
                            lastName.setText(str_lastName);
                            mobileNo.setText(str_mobile_no);

                            village.setText(str_village);
                            block.setText(str_block);
                            dist.setText(str_dist);
                            state.setText(str_state);
                            pinCode.setText(str_pinCode);

                            Picasso.get().load(farmer_photo).into(farmerProfilePic);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                qrFarmerProfile.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"Wrong Farmer Id",Toast.LENGTH_SHORT).show();
                Log.d("Error", "ErrorListView: " + error);

                AlertDialog.Builder builder = new AlertDialog.Builder(FarmerProfileQRActivity.this);
                builder.setMessage(R.string.wrongFarmer)
                        .setCancelable(false)
                        .setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getApplicationContext(), ProcurementActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonArrayRequest);
    }
}