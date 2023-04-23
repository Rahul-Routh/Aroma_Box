package com.purpulingo.aromabox.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.purpulingo.aromabox.Adapter.CultivationFarmerListAdapter;
import com.purpulingo.aromabox.Constants.Constants;
import com.purpulingo.aromabox.Global.NetworkChange;
import com.purpulingo.aromabox.Global.Url;
import com.purpulingo.aromabox.Global.UserSessionManager;
import com.purpulingo.aromabox.Model.CultivationFarmerListModel;
import com.purpulingo.aromabox.Model.FarmerPaidListModel;
import com.purpulingo.aromabox.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CultivationFarmerListActivity extends AppCompatActivity {

    private ImageView backBtn;
    private SwipeRefreshLayout refreshLayout;
    private ListView cultivationFarmerList;
    private NetworkChange networkChange;

    CultivationFarmerListAdapter cultivationFarmerListAdapter;
    public static ArrayList<CultivationFarmerListModel> cultivationFarmerListModelArrayList;
    CultivationFarmerListModel cultivationFarmerListModel;
    String pgName, pgId, pgCount, pgArea,  orgId;

    UserSessionManager userSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cultivation_farmer_list);

        userSessionManager = new UserSessionManager(getApplicationContext());
        networkChange = new NetworkChange();
        backBtn = (ImageView) findViewById(R.id.backBtn);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        cultivationFarmerList = (ListView) findViewById(R.id.cultivationFarmerList);

        orgId = userSessionManager.getOrgId();

        cultivationFarmerListModelArrayList = new ArrayList<>();
        cultivationFarmerListAdapter = new CultivationFarmerListAdapter(getApplicationContext(), cultivationFarmerListModelArrayList);
        cultivationFarmerList.setAdapter(cultivationFarmerListAdapter);
//
        dataExtract();

        cultivationFarmerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cultivationFarmerListModel = cultivationFarmerListModelArrayList.get(position);
                Intent intent = new Intent(getApplicationContext(),CultivationFarmerList2Activity.class);
                intent.putExtra(Constants.KEY_FARMER_LIST.KEY_PG_ID,cultivationFarmerListModel.getPgId());
                intent.putExtra(Constants.KEY_FARMER_LIST.KEY_PG_NAME,cultivationFarmerListModel.getPgName());
                Log.d("pgId", "pgId: " + pgId);
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

        //https://api2.boxfarming.in/wsapi/v1/index.php/Aroma/bx_pg_list
        String JSON_URL_LIST = Url.FARMER_LIST;
        HashMap<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put(Constants.KEY_FARMER_LIST.KEY_ORG_ID, orgId);

        JSONArray mJSONArray = new JSONArray(Arrays.asList(jsonParams));
        Log.d("mJSONArray", "mJSONArray: " + mJSONArray);
        cultivationFarmerListModelArrayList.clear();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, JSON_URL_LIST,
                mJSONArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0;i<response.length();i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        pgName = jsonObject.getString(Constants.KEY_FARMER_LIST.KEY_PG_NAME);
                        pgId = jsonObject.getString(Constants.KEY_FARMER_LIST.KEY_PG_ID);
                        pgCount = jsonObject.getString(Constants.KEY_FARMER_LIST.KEY_COUNT);
                        pgArea = jsonObject.getString(Constants.KEY_FARMER_LIST.KEY_AREA);

                        cultivationFarmerListModel = new CultivationFarmerListModel(pgName, pgId, pgCount, pgArea);
                        cultivationFarmerListModelArrayList.add(cultivationFarmerListModel);
                        cultivationFarmerListAdapter.notifyDataSetChanged();

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