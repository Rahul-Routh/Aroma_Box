package com.purpulingo.aromabox.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.purpulingo.aromabox.Adapter.CultivationFarmerListAdapter;
import com.purpulingo.aromabox.Adapter.CultivationFarmerListAdapter2;
import com.purpulingo.aromabox.Constants.Constants;
import com.purpulingo.aromabox.Global.NetworkChange;
import com.purpulingo.aromabox.Global.Url;
import com.purpulingo.aromabox.Global.UserSessionManager;
import com.purpulingo.aromabox.Model.CultivationFarmerListModel;
import com.purpulingo.aromabox.Model.CultivationFarmerListModel2;
import com.purpulingo.aromabox.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CultivationFarmerList2Activity extends AppCompatActivity {
    private ImageView backBtn;
    private SwipeRefreshLayout refreshLayout;
    private ListView cultivationFarmerList2;
    private NetworkChange networkChange;

    CultivationFarmerListAdapter2 cultivationFarmerListAdapter2;
    public static ArrayList<CultivationFarmerListModel2> cultivationFarmerListModel2ArrayList;
    CultivationFarmerListModel2 cultivationFarmerListModel2;
    String pgId,pgName, orgId, farmerId, farmerName, farmerVillage, farmerImg, farmerArea,
            lastCutting, nextCutting, lastIrrigation, lastFertigation, trace, dates;
    UserSessionManager userSessionManager;
    private TextView headingFarmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cultivation_farmer_list2);

        userSessionManager = new UserSessionManager(getApplicationContext());
        networkChange = new NetworkChange();
        backBtn = (ImageView) findViewById(R.id.backBtn);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        cultivationFarmerList2 = (ListView) findViewById(R.id.cultivationFarmerList2);
        headingFarmer = (TextView)  findViewById(R.id.headingFarmer);

        orgId = userSessionManager.getOrgId();
        pgId = getIntent().getStringExtra(Constants.KEY_FARMER_LIST.KEY_PG_ID);
        pgName = getIntent().getStringExtra(Constants.KEY_FARMER_LIST.KEY_PG_NAME);

        headingFarmer.setText(getString(R.string.farmerList)+" ("+ pgName +")");

        cultivationFarmerListModel2ArrayList = new ArrayList<>();
        cultivationFarmerListAdapter2 = new CultivationFarmerListAdapter2(getApplicationContext(), cultivationFarmerListModel2ArrayList);
        cultivationFarmerList2.setAdapter(cultivationFarmerListAdapter2);
//
//        dataExtract();

        cultivationFarmerList2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cultivationFarmerListModel2 = cultivationFarmerListModel2ArrayList.get(position);
                Intent intent = new Intent(getApplicationContext(),CultivationFarmerProfileActivity.class);
                intent.putExtra(Constants.KEY_FARMER_PG_LIST.KEY_FARMER_NAME, cultivationFarmerListModel2.getFarmerName() );
                intent.putExtra(Constants.KEY_FARMER_PG_LIST.KEY_IMG, cultivationFarmerListModel2.getFarmerImg());
                intent.putExtra(Constants.KEY_FARMER_PG_LIST.KEY_VILLAGE, cultivationFarmerListModel2.getFarmerVillage());
                intent.putExtra(Constants.KEY_FARMER_PG_LIST.KEY_FARMER_ID, cultivationFarmerListModel2.getFarmerId());
                intent.putExtra(Constants.KEY_FARMER_PG_LIST.KEY_AREA, cultivationFarmerListModel2.getFarmerArea());

                intent.putExtra(Constants.KEY_FARMER_PG_LIST.KEY_LAST_CUTTING, cultivationFarmerListModel2.getLastCutting());
                intent.putExtra(Constants.KEY_FARMER_PG_LIST.KEY_NEXT_CUTTING, cultivationFarmerListModel2.getNextCutting());
                intent.putExtra(Constants.KEY_FARMER_PG_LIST.KEY_LAST_IRR, cultivationFarmerListModel2.getLastIrrigation());
                intent.putExtra(Constants.KEY_FARMER_PG_LIST.KEY_LAST_FER, cultivationFarmerListModel2.getLastFertigation());
                startActivity(intent);

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
        cultivationFarmerListModel2ArrayList.clear();
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

                        //lastCutting = "12-05-2022";
                        //nextCutting = "13-05-2022";
                        //lastIrrigation = "15-02-2022";
                        //lastFertigation = "16-02-2022";


                        if(trace.isEmpty()) {
                            lastIrrigation="NA";
                            lastFertigation="NA";
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


                        cultivationFarmerListModel2 = new CultivationFarmerListModel2(farmerName, farmerId, farmerVillage, farmerImg, farmerArea,
                                nextCutting,lastCutting,lastFertigation, lastIrrigation);
                        cultivationFarmerListModel2ArrayList.add(cultivationFarmerListModel2);
                        cultivationFarmerListAdapter2.notifyDataSetChanged();

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
//        for (int i = 0; i < 20; i++) {
//            farmerNameCultivation = "Subhendu Das";
//            farmerIdCultivation = "12345";
//            farmerAddress = "Raghunathpur, jhargram";
//            farmerCultivationArea = "10";
//
//            cultivationFarmerListModel2 = new CultivationFarmerListModel2(farmerNameCultivation, farmerIdCultivation, farmerAddress, farmerCultivationArea);
//            cultivationFarmerListModel2ArrayList.add(cultivationFarmerListModel2);
//            cultivationFarmerListAdapter2.notifyDataSetChanged();
//        }
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