package com.purpulingo.aromabox.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.purpulingo.aromabox.Adapter.CalenderFarmerListAdapter;
import com.purpulingo.aromabox.Adapter.CalenderFarmerListModel;
import com.purpulingo.aromabox.Adapter.CultivationFarmerListAdapter2;
import com.purpulingo.aromabox.Adapter.NotificationFarmerListAdapter;
import com.purpulingo.aromabox.Constants.Constants;
import com.purpulingo.aromabox.Global.NetworkChange;
import com.purpulingo.aromabox.Global.Url;
import com.purpulingo.aromabox.Global.UserSessionManager;
import com.purpulingo.aromabox.Model.CultivationFarmerListModel2;
import com.purpulingo.aromabox.Model.FarmerList;
import com.purpulingo.aromabox.Model.FarmerList2;
import com.purpulingo.aromabox.Model.FarmerMain;
import com.purpulingo.aromabox.Model.FarmerMain2;
import com.purpulingo.aromabox.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalenderFarmerListActivity extends AppCompatActivity implements CalenderFarmerListAdapter.CheckBoxCheckedListener {

    private ImageView backBtn;
    private SwipeRefreshLayout refreshLayout;
    private ListView calenderFarmerList;
    private NetworkChange networkChange;

    CalenderFarmerListAdapter calenderFarmerListAdapter;
    public static ArrayList<CalenderFarmerListModel> calenderFarmerListModelArrayList = new ArrayList<>();
    CalenderFarmerListModel calenderFarmerListModel;
    String userId,pgId, pgName, orgId, farmerId, farmerName, farmerVillage, farmerImg, farmerArea, trace, dates,
            lastCutting, nextCutting, lastIrrigation, lastFertigation,str_indentDetailsId, str_indentHeaderId,
            str_farmerName, str_farmerId, str_cutting_area,str_date;
    UserSessionManager userSessionManager;
    double totalCultivationArea = 0.00;
    List<FarmerList2> farmerLists2;
    Gson gson;
    FarmerMain2 farmerMain2;
    String json;
    private Button totalCultivationSelected;
    private TextView totalCultivationAreaTextView, headingFarmer;
    private LinearLayout selectedTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_farmer_list);

        str_date = getIntent().getStringExtra("Date");
        Log.d("str_date","str_date"+str_date);
        userSessionManager = new UserSessionManager(getApplicationContext());
        networkChange = new NetworkChange();
        backBtn = (ImageView) findViewById(R.id.backBtn);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        calenderFarmerList = (ListView) findViewById(R.id.calenderFarmerList);
        totalCultivationSelected = (Button) findViewById(R.id.totalCultivationSelected);
        totalCultivationAreaTextView = (TextView)  findViewById(R.id.totalCultivationAreaTextView);
        headingFarmer = (TextView)  findViewById(R.id.headingFarmer);

        selectedTag = (LinearLayout) findViewById(R.id.selectedTag);

        orgId = userSessionManager.getOrgId();
        userId = userSessionManager.getUserID();
        pgId = getIntent().getStringExtra(Constants.KEY_FARMER_LIST.KEY_PG_ID);
        pgName = getIntent().getStringExtra(Constants.KEY_FARMER_LIST.KEY_PG_NAME);
        headingFarmer.setText(getString(R.string.farmerList)+" ("+ pgName +")");

        calenderFarmerListAdapter = new CalenderFarmerListAdapter(getApplicationContext(), calenderFarmerListModelArrayList);
        calenderFarmerList.setAdapter(calenderFarmerListAdapter);

        calenderFarmerListAdapter.setCheckedListener(this);

        gson = new Gson();
        farmerLists2 = new ArrayList<>();




        totalCultivationSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("totalCultivationArea", "totalCultivationArea = " + totalCultivationArea);
                Log.d("farmerJson", "farmerJson = " + json);
                AlertDialog.Builder builder = new AlertDialog.Builder(CalenderFarmerListActivity.this);
                builder.setMessage(R.string.submitAlert)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedFarmer();
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
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
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
        //https://api2.boxfarming.in/wsapi/v1/index.php/Aroma/bx_farmer_list_by_pg
        String JSON_URL_LIST = Url.FARMER_PG_LIST;
        HashMap<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put(Constants.KEY_FARMER_PG_LIST.KEY_ORG_ID, orgId);
        jsonParams.put(Constants.KEY_FARMER_PG_LIST.KEY_PG_ID, pgId);

        JSONArray mJSONArray = new JSONArray(Arrays.asList(jsonParams));
        Log.d("mJSONArray", "mJSONArray: " + mJSONArray);
        calenderFarmerListModelArrayList.clear();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, JSON_URL_LIST,
                mJSONArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0;i<response.length();i++){
                    try {

//                        "count(*)": "10",
//                                "pg_id": "1",
//                                "pg_name_desc": "Nutangarh"
                        JSONObject jsonObject = response.getJSONObject(i);
                        farmerName = jsonObject.getString(Constants.KEY_FARMER_PG_LIST.KEY_FARMER_NAME);
                        farmerId = jsonObject.getString(Constants.KEY_FARMER_PG_LIST.KEY_FARMER_ID);
                        farmerVillage = jsonObject.getString(Constants.KEY_FARMER_PG_LIST.KEY_VILLAGE);
                        farmerImg = jsonObject.getString(Constants.KEY_FARMER_PG_LIST.KEY_IMG);
                        farmerArea = jsonObject.getString(Constants.KEY_FARMER_PG_LIST.KEY_AREA);
                        trace = jsonObject.getString(Constants.KEY_FARMER_PG_LIST.KEY_TRACE);
                        dates = jsonObject.getString(Constants.KEY_FARMER_PG_LIST.KEY_DATES);

                        if(trace.isEmpty()) {
                            lastIrrigation="NA";
                            lastFertigation="NA";
                            Log.d("trace123", "trace: " + trace);
                        }
                        if(!trace.isEmpty()) {
                            lastIrrigation="NA";
                            lastFertigation="NA";
                            JSONArray mJSONArray2 = new JSONArray(trace);
                            for (int j=0;j<mJSONArray2.length();j++) {
                                JSONObject jsonObject2 = mJSONArray2.getJSONObject(j);

                                String opsId = jsonObject2.getString(Constants.KEY_FARMER_PG_LIST.KEY_OPS_ID);
                                if (opsId.equals("1")) {
                                    lastIrrigation = jsonObject2.getString(Constants.KEY_FARMER_PG_LIST.KEY_TRN_DATE);

                                    DateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd");
                                    DateFormat outputFormat = new SimpleDateFormat("dd-mm-yyyy");
                                    String inputDateStr = lastIrrigation;
                                    Date date = inputFormat.parse(inputDateStr);
                                    lastIrrigation = outputFormat.format(date);
                                }
                                if (opsId.equals("2")) {
                                    lastFertigation = jsonObject2.getString(Constants.KEY_FARMER_PG_LIST.KEY_TRN_DATE);

                                    DateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd");
                                    DateFormat outputFormat = new SimpleDateFormat("dd-mm-yyyy");
                                    String inputDateStr = lastFertigation;
                                    Date date = inputFormat.parse(inputDateStr);
                                    lastFertigation = outputFormat.format(date);
                                }
                            }
                        }
                        if(dates.isEmpty()) {
                            nextCutting="NA";
                            lastCutting="NA";
                            Log.d("trace123", "trace: " + trace);
                        }
                        if(!dates.isEmpty()) {
                            nextCutting="NA";
                            lastCutting="NA";
                            JSONArray mJSONArray2 = new JSONArray(dates);
                            for (int j=0;j<mJSONArray2.length();j++) {
                                JSONObject jsonObject2 = mJSONArray2.getJSONObject(j);

                                nextCutting= jsonObject2.getString(Constants.KEY_FARMER_PG_LIST.KEY_NEXT_CUTTING);
                                lastCutting = jsonObject2.getString(Constants.KEY_FARMER_PG_LIST.KEY_LAST_CUTTING);
                                if(!lastCutting.equals("NA")){
                                    DateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd");
                                    DateFormat outputFormat = new SimpleDateFormat("dd-mm-yyyy");
                                    String inputDateStr = lastCutting;
                                    Date date = inputFormat.parse(inputDateStr);
                                    lastCutting = outputFormat.format(date);
                                }
                                if(!nextCutting.equals("NA")){
                                    DateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd");
                                    DateFormat outputFormat = new SimpleDateFormat("dd-mm-yyyy");
                                    String inputDateStr = nextCutting;
                                    Date date = inputFormat.parse(inputDateStr);
                                    nextCutting = outputFormat.format(date);
                                }
                            }
                        }

                        calenderFarmerListModel = new CalenderFarmerListModel(farmerName, farmerId, farmerVillage, farmerImg, farmerArea,
                                nextCutting,lastCutting,lastFertigation, lastIrrigation);
                        calenderFarmerListModelArrayList.add(calenderFarmerListModel);
                        calenderFarmerListAdapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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

    public void selectedFarmer(){

        //https://api2.boxfarming.in/wsapi/v1/index.php/Aroma/aroma_cutting_scheduling
        //{"farmer_list":[{"cutting_area":"10.00 dcml","farmer_id":"100312"},{"cutting_area":"10.00 dcml","farmer_id":"100313"}],
        // "org_id":"114","schedule_date":"2022-05-12","user_id":"134"}

        final ProgressDialog progressDialog = new ProgressDialog(CalenderFarmerListActivity.this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();

        String JSON_URL = Url.SELECTED_FARMER_LIST_CAL;
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("jsonParams", "jsonParams: " + jsonParams);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, JSON_URL,
                jsonParams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", "response1132: " + response);
                progressDialog.dismiss();

                AlertDialog.Builder builder = new AlertDialog.Builder(CalenderFarmerListActivity.this);
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
                Log.d("error", "error1212: " + error);
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
        requestQueue.add(jsonObjectRequest);
    }
    @Override
    public void getCheckBoxChecked(int position) {
        calenderFarmerListModel = calenderFarmerListModelArrayList.get(position);

        //str_indentDetailsId = notificationFarmerListModel.getFarmerID();
        //str_indentHeaderId = calenderFarmerListModel.getFarmerHeaderId() ;
        str_farmerName =  calenderFarmerListModel.getFarmerName();
        str_farmerId = calenderFarmerListModel.getFarmerId();
        str_cutting_area = calenderFarmerListModel.getFarmerArea();

        totalCultivationArea += Double.parseDouble(calenderFarmerListModel.getFarmerArea());
        //totalCultivationArea += 10.00;

        farmerLists2.add(new FarmerList2(str_farmerId, str_cutting_area));

        farmerMain2 = new FarmerMain2(userId,str_date,orgId,farmerLists2);
        json= gson.toJson(farmerMain2);

        Log.d("farmerAdd", "farmerAdd = " + json);

        totalCultivationAreaTextView.setText(""+totalCultivationArea);

        if(totalCultivationArea > 0) {
            selectedTag.setVisibility(View.VISIBLE);
        }else {
            selectedTag.setVisibility(View.INVISIBLE);
        }
    }
    @Override
    public void getCheckBoxRemoved(int position) {
        calenderFarmerListModel = calenderFarmerListModelArrayList.get(position);

       // str_indentHeaderId = calenderFarmerListModel.getFarmerHeaderId() ;
        str_farmerName =  calenderFarmerListModel.getFarmerName();
        str_farmerId = calenderFarmerListModel.getFarmerId();

        totalCultivationArea -= Double.parseDouble(calenderFarmerListModel.getFarmerArea());
        //totalCultivationArea -= 10.00;

        farmerLists2.remove(position);

        json= gson.toJson(farmerMain2);
        Log.d("farmerRemove", "farmerRemove = " + json);

        totalCultivationAreaTextView.setText(""+totalCultivationArea);
        if(totalCultivationArea > 0){
            selectedTag.setVisibility(View.VISIBLE);
        }else {
            selectedTag.setVisibility(View.INVISIBLE);
        }
    }
    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChange, filter);
        dataExtract();
        super.onStart();
    }
    @Override
    protected void onStop() {
        unregisterReceiver(networkChange);
        super.onStop();
    }
}