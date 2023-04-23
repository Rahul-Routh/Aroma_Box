package com.purpulingo.aromabox.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.purpulingo.aromabox.Adapter.FarmerListAdapter;
import com.purpulingo.aromabox.Adapter.OilListAdapter;
import com.purpulingo.aromabox.Constants.Constants;
import com.purpulingo.aromabox.Global.NetworkChange;
import com.purpulingo.aromabox.Global.Url;
import com.purpulingo.aromabox.Global.UserSessionManager;
import com.purpulingo.aromabox.Model.FarmerListModel;
import com.purpulingo.aromabox.Model.OilListModel;
import com.purpulingo.aromabox.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OilQuantityListActivity extends AppCompatActivity {

    private ImageView backBtn;
    private NetworkChange networkChange;
    private SwipeRefreshLayout refreshLayout;
    private ListView oilList;
    private String oilDate, oilQuantity, orgId, startDate, endDate;

    OilListAdapter oilListAdapter;
    public static ArrayList<OilListModel> oilListModelArrayList;
    OilListModel oilListModel;

    UserSessionManager userSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oil_quantity_list);

        userSessionManager = new UserSessionManager(getApplicationContext());
        orgId = userSessionManager.getOrgId();

        networkChange = new NetworkChange();
        backBtn = (ImageView) findViewById(R.id.backBtn);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        oilList = (ListView) findViewById(R.id.oilList);

        startDate = getIntent().getStringExtra(Constants.KEY_OIL_LIST.KEY_START_DATE);
        endDate = getIntent().getStringExtra(Constants.KEY_OIL_LIST.KEY_END_DATE);

        oilListModelArrayList = new ArrayList<>();

        oilListAdapter = new OilListAdapter(getApplicationContext(), oilListModelArrayList);
        oilList.setAdapter(oilListAdapter);

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

        String JSON_URL_LIST = Url.OIL_LIST;
        //String JSON_URL_LIST = "https://api2.boxfarming.in/wsapi/v1/index.php/Aroma/aroma_distillation_list";

        HashMap<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put(Constants.KEY_OIL_LIST.KEY_ORG_ID, orgId);
        jsonParams.put(Constants.KEY_OIL_LIST.KEY_START_DATE, startDate);
        jsonParams.put(Constants.KEY_OIL_LIST.KEY_END_DATE, endDate);
        jsonParams.put(Constants.KEY_OIL_LIST.KEY_POS_ID, "1");

        JSONArray mJSONArray = new JSONArray(Arrays.asList(jsonParams));
        Log.d("mJSONArray", "mJSONArray: " + mJSONArray);

        oilListModelArrayList.clear();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, JSON_URL_LIST,
                mJSONArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0;i<response.length();i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        oilDate = jsonObject.getString(Constants.KEY_OIL_LIST.KEY_END_DATE);
                        oilQuantity = jsonObject.getString(Constants.KEY_OIL_LIST.KEY_QUANTITY);

                        DateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd");
                        DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");
                        String inputDateStr = oilDate;
                        Date date = inputFormat.parse(inputDateStr);
                        String outputDateStr = outputFormat.format(date);


                        oilListModel = new OilListModel(outputDateStr, oilQuantity);
                        oilListModelArrayList.add(oilListModel);
                        oilListAdapter.notifyDataSetChanged();
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