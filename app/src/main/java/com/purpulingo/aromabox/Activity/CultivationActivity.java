package com.purpulingo.aromabox.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.purpulingo.aromabox.Adapter.HarvestingListAdapter;
import com.purpulingo.aromabox.Constants.Constants;
import com.purpulingo.aromabox.Global.NetworkChange;
import com.purpulingo.aromabox.Global.Url;
import com.purpulingo.aromabox.Global.UserSessionManager;
import com.purpulingo.aromabox.Model.ExitingBatchListModel;
import com.purpulingo.aromabox.Model.FarmerPaidListModel;
import com.purpulingo.aromabox.Model.HarvestingListModel;
import com.purpulingo.aromabox.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CultivationActivity extends AppCompatActivity {

    private ImageView backBtn;
    private CardView totalFarmer, totalOil;
    private ListView harvestingList;
    private NetworkChange networkChange;
    private TextView totalOilQuantity, totalCultivationArea, totalFarmerCount;

    HarvestingListAdapter harvestingListAdapter;
    public static ArrayList<HarvestingListModel> harvestingListModelArrayList;
    HarvestingListModel harvestingListModel;
    String year, month, totalArea, areaUnite, orgId, role, str_farmerCount, str_totalArea, str_totalOilStock ;
    UserSessionManager userSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cultivation);

        userSessionManager = new UserSessionManager(getApplicationContext());
        networkChange = new NetworkChange();
        backBtn = (ImageView) findViewById(R.id.backBtn);
        totalFarmer = (CardView) findViewById(R.id.totalFarmer);
        totalOil = (CardView) findViewById(R.id.totalOil);
        harvestingList = (ListView) findViewById(R.id.harvestingList);
        totalFarmerCount = (TextView) findViewById(R.id.totalFarmerCount);
        totalCultivationArea = (TextView) findViewById(R.id.totalCultivationArea);
        totalOilQuantity = (TextView) findViewById(R.id.totalOilQuantity);

        orgId = userSessionManager.getOrgId();
        role = userSessionManager.getRole();

        if(role.equals("AVM")){
            totalOil.setVisibility(View.GONE);
        }else {
            totalOil.setVisibility(View.VISIBLE);
        }

        harvestingListModelArrayList = new ArrayList<>();
        harvestingListAdapter = new HarvestingListAdapter(getApplicationContext(), harvestingListModelArrayList);
        harvestingList.setAdapter(harvestingListAdapter);

        totalFarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CultivationFarmerListActivity.class);
                startActivity(intent);
            }
        });

        dataExtract();
        dataExtract2();

        totalOil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OilQuantityActivity.class);
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
    public void dataExtract(){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");

        progressDialog.show();
        String JSON_URL = Url.CULTIVATION_URL;
        HashMap<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put(Constants.KEY_CULTIVATION.KEY_ORG_ID, orgId);

        JSONArray mJSONArray = new JSONArray(Arrays.asList(jsonParams));
        Log.d("mJSONArray", "mJSONArray: " + mJSONArray);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, JSON_URL,
                mJSONArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        progressDialog.dismiss();
                        JSONObject jsonObject = response.getJSONObject(i);

                        str_farmerCount = jsonObject.getString(Constants.KEY_CULTIVATION.KEY_FARMER_COUNT);
                        str_totalArea = jsonObject.getString(Constants.KEY_CULTIVATION.KEY_AREA);
                        str_totalOilStock = jsonObject.getString(Constants.KEY_CULTIVATION.KEY_OIL_STOCK);
                        totalFarmerCount.setText(str_farmerCount);


                        if (str_totalArea.equals("0") == true || str_totalArea.equals("null") == true) {
                            totalCultivationArea.setText("0");
                        }else {
                            totalCultivationArea.setText(str_totalArea +" "+ getText(R.string.acre));

                        }

                        if (str_totalOilStock.equals("0") == true || str_totalOilStock.equals("null") == true) {
                            totalOilQuantity.setText("0");
                        }else {
                            totalOilQuantity.setText(str_totalOilStock + " "+getText(R.string.liter));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "ErrorListView: " + error);
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

    public void dataExtract2() {
        for (int i=0;i<20;i++){

            year = "2021";
            month = "May";
            totalArea = "10";
            areaUnite = "Acre";

            harvestingListModel = new HarvestingListModel(year,  month,  totalArea,  areaUnite);
            harvestingListModelArrayList.add(harvestingListModel);
            harvestingListAdapter.notifyDataSetChanged();
        }
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