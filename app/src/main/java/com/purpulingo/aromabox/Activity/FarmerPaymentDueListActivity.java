package com.purpulingo.aromabox.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.android.volley.toolbox.Volley;
import com.purpulingo.aromabox.Adapter.FarmerDueListAdapter;
import com.purpulingo.aromabox.Constants.Constants;
import com.purpulingo.aromabox.Global.NetworkChange;
import com.purpulingo.aromabox.Global.Url;
import com.purpulingo.aromabox.Global.UserSessionManager;
import com.purpulingo.aromabox.Model.FarmerDueListModel;
import com.purpulingo.aromabox.Model.FarmerListModel;
import com.purpulingo.aromabox.Model.FarmerPaidListModel;
import com.purpulingo.aromabox.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FarmerPaymentDueListActivity extends AppCompatActivity {
    private ImageView backBtn;
    private SwipeRefreshLayout refreshLayout;
    private ListView farmerDueList;
    private NetworkChange networkChange;
    UserSessionManager userSessionManager;

    FarmerDueListAdapter farmerDueListAdapter;
    public static ArrayList<FarmerDueListModel> farmerDueListModelArrayList;
    FarmerDueListModel farmerDueListModel;

    private String farmerId, farmerName, dueAmount, orgId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_payment_due_list);

        userSessionManager = new UserSessionManager(getApplicationContext());
        networkChange = new NetworkChange();
        backBtn = (ImageView) findViewById(R.id.backBtn);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        farmerDueList = (ListView) findViewById(R.id.farmerDueList);

        orgId = userSessionManager.getOrgId();
        farmerDueListModelArrayList = new ArrayList<>();

        farmerDueListAdapter = new FarmerDueListAdapter(getApplicationContext(), farmerDueListModelArrayList);
        farmerDueList.setAdapter(farmerDueListAdapter);

        dataExtract();

        farmerDueList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                farmerDueListModel = farmerDueListModelArrayList.get(position);
                Intent intent = new Intent(getApplicationContext(),PaymentSubmitActivity.class);
                intent.putExtra(Constants.KEY_PAYMENT.KEY_FARMER_ID,farmerDueListModel.getFarmerId());
                intent.putExtra(Constants.KEY_PAYMENT.KEY_FARMER_NAME,farmerDueListModel.getFarmerName());
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
        //https://api2.boxfarming.in/wsapi/v1/index.php/Aroma/bx_ph_farmer_list_due
        String JSON_URL_LIST = Url.PAYMENT_DUE_LIST;
        HashMap<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put(Constants.KEY_DUE_LIST.KEY_ORG_ID, orgId);

        JSONArray mJSONArray = new JSONArray(Arrays.asList(jsonParams));
        Log.d("mJSONArray", "mJSONArray: " + mJSONArray);
        farmerDueListModelArrayList.clear();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, JSON_URL_LIST,
                mJSONArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        farmerName = jsonObject.getString(Constants.KEY_DUE_LIST.KEY_FARMER_NAME);
                        farmerId = jsonObject.getString(Constants.KEY_DUE_LIST.KEY_FARMER_ID);
                        dueAmount = jsonObject.getString(Constants.KEY_DUE_LIST.KEY_DUE_AMOUNT);

                        farmerDueListModel = new FarmerDueListModel(farmerId, farmerName, dueAmount);
                        farmerDueListModelArrayList.add(farmerDueListModel);
                        farmerDueListAdapter.notifyDataSetChanged();
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