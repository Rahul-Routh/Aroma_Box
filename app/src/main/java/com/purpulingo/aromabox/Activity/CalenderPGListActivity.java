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
import com.purpulingo.aromabox.Constants.Constants;
import com.purpulingo.aromabox.Global.NetworkChange;
import com.purpulingo.aromabox.Global.Url;
import com.purpulingo.aromabox.Global.UserSessionManager;
import com.purpulingo.aromabox.Adapter.CalenderPGListAdapter;
import com.purpulingo.aromabox.Model.CalenderPGListModel;
import com.purpulingo.aromabox.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CalenderPGListActivity extends AppCompatActivity {
    private ImageView backBtn;
    private SwipeRefreshLayout refreshLayout;
    private ListView calenderPGList;
    private NetworkChange networkChange;

    CalenderPGListAdapter calenderPGListAdapter;
    public static ArrayList<CalenderPGListModel> calenderPGListModelArrayList;
    CalenderPGListModel calenderPGListModel;
    String pgName, pgId, pgCount, pgArea,  orgId, str_date;

    UserSessionManager userSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_pglist);

        str_date = getIntent().getStringExtra("Date");
        userSessionManager = new UserSessionManager(getApplicationContext());
        networkChange = new NetworkChange();
        backBtn = (ImageView) findViewById(R.id.backBtn);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        calenderPGList = (ListView) findViewById(R.id.calenderPGList);

        orgId = userSessionManager.getOrgId();

        calenderPGListModelArrayList = new ArrayList<>();
        calenderPGListAdapter = new CalenderPGListAdapter(getApplicationContext(), calenderPGListModelArrayList);
        calenderPGList.setAdapter(calenderPGListAdapter);
//
        dataExtract();

        calenderPGList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                calenderPGListModel = calenderPGListModelArrayList.get(position);
                Intent intent = new Intent(getApplicationContext(),CalenderFarmerListActivity.class);
                intent.putExtra(Constants.KEY_FARMER_LIST.KEY_PG_ID,calenderPGListModel.getPgId());
                intent.putExtra(Constants.KEY_FARMER_LIST.KEY_PG_NAME,calenderPGListModel.getPgName());
                intent.putExtra("Date",str_date);
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
        calenderPGListModelArrayList.clear();
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

                        calenderPGListModel = new CalenderPGListModel(pgName, pgId, pgCount, pgArea);
                        calenderPGListModelArrayList.add(calenderPGListModel);
                        calenderPGListAdapter.notifyDataSetChanged();

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