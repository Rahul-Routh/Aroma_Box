package com.purpulingo.aromabox.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.purpulingo.aromabox.Adapter.FarmerListAdapter;
import com.purpulingo.aromabox.Constants.Constants;
import com.purpulingo.aromabox.Global.NetworkChange;
import com.purpulingo.aromabox.Global.Url;
import com.purpulingo.aromabox.Global.UserSessionManager;
import com.purpulingo.aromabox.Model.ExitingBatchListModel;
import com.purpulingo.aromabox.Model.FarmerListModel;
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
import java.util.Map;

public class FarmerListComProcurementActivity extends AppCompatActivity {
    private ImageView backBtn;
    private SwipeRefreshLayout refreshLayout;
    private ListView farmerList;
    private NetworkChange networkChange;
    
    FarmerListAdapter farmerListAdapter;
    public static  ArrayList<FarmerListModel> farmerListModelArrayList;
    FarmerListModel farmerListModel;

    private String farmerId, farmerName,farmerDate, farmerBioQuantity, farmerBioQuantityPrice, paymentStatus, paymentAmount
            , paymentDate, paymentRefNo, orgId, startDate, endDate;
    UserSessionManager userSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_list_com_procurement);

        userSessionManager = new UserSessionManager(getApplicationContext());

        networkChange = new NetworkChange();
        backBtn = (ImageView) findViewById(R.id.backBtn);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        farmerList = (ListView) findViewById(R.id.farmerList);

        orgId = userSessionManager.getOrgId();
        startDate = getIntent().getStringExtra(Constants.KEY_PROCUREMENT_SEARCH.KEY_START_DATE);
        endDate = getIntent().getStringExtra(Constants.KEY_PROCUREMENT_SEARCH.KEY_END_DATE);

        farmerListModelArrayList = new ArrayList<>();
        
        farmerListAdapter = new FarmerListAdapter(getApplicationContext(), farmerListModelArrayList);
        farmerList.setAdapter(farmerListAdapter);

        dataExtract();

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

        String JSON_URL_LIST = Url.PROCUREMENT_LIST;
        //String JSON_URL_LIST = "https://api2.boxfarming.in/wsapi/v1/index.php/Aroma/bx_ph_procurement_list";

        HashMap<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put(Constants.KEY_PROCUREMENT_LIST.KEY_ORG_ID, orgId);
        jsonParams.put(Constants.KEY_PROCUREMENT_LIST.KEY_START_DATE, startDate);
        jsonParams.put(Constants.KEY_PROCUREMENT_LIST.KEY_END_DATE, endDate);

        JSONArray mJSONArray = new JSONArray(Arrays.asList(jsonParams));
        Log.d("mJSONArray", "mJSONArray: " + mJSONArray);

        farmerListModelArrayList.clear();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, JSON_URL_LIST,
                mJSONArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0;i<response.length();i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        farmerName = jsonObject.getString(Constants.KEY_PROCUREMENT_LIST.KEY_FARMER_NAME);
                        farmerId = jsonObject.getString(Constants.KEY_PROCUREMENT_LIST.KEY_FARMER_ID);
                        farmerBioQuantity = jsonObject.getString(Constants.KEY_PROCUREMENT_LIST.KEY_QTY);
                        farmerBioQuantityPrice = jsonObject.getString(Constants.KEY_PROCUREMENT_LIST.KEY_AMOUNT);
                        farmerDate = jsonObject.getString(Constants.KEY_PROCUREMENT_LIST.KEY_TEN_DATE);

                        //dateConvert
                        DateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd");
                        DateFormat outputFormat = new SimpleDateFormat("dd-mm-yyyy");
                        String inputDateStr = farmerDate;
                        Date date = inputFormat.parse(inputDateStr);
                        String outputDateStr = outputFormat.format(date);

                        farmerListModel = new FarmerListModel(farmerId, farmerName,  outputDateStr,  farmerBioQuantity,  farmerBioQuantityPrice);
                        farmerListModelArrayList.add(farmerListModel);
                        farmerListAdapter.notifyDataSetChanged();
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