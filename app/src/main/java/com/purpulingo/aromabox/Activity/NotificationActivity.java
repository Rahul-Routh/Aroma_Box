package com.purpulingo.aromabox.Activity;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatDelegate;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.purpulingo.aromabox.Adapter.NotificationListAdapter;
import com.purpulingo.aromabox.Constants.Constants;
import com.purpulingo.aromabox.Global.NetworkChange;
import com.purpulingo.aromabox.Global.Url;
import com.purpulingo.aromabox.Global.UserSessionManager;
import com.purpulingo.aromabox.Model.NotificationListModel;
import com.purpulingo.aromabox.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class NotificationActivity extends AppCompatActivity {

    private ImageView backBtn;
    private SwipeRefreshLayout refreshLayout;
    private ListView notificationList;
    NotificationListAdapter notificationListAdapter;

    public static ArrayList<NotificationListModel> notificationListModelArrayList = new ArrayList<>();

    private String notificationNo, notificationDate, cultivationArea, farmerCount , orgId, userId;

    NotificationListModel notificationListModel;
    private NetworkChange networkChange;
    UserSessionManager userSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        userSessionManager = new UserSessionManager(getApplicationContext());
        userId = userSessionManager.getUserID();
        orgId = userSessionManager.getOrgId();

        networkChange = new NetworkChange();
        backBtn = (ImageView) findViewById(R.id.backBtn);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        notificationList = (ListView) findViewById(R.id.notificationList);

        notificationListAdapter = new NotificationListAdapter(getApplicationContext(), notificationListModelArrayList);
        notificationList.setAdapter(notificationListAdapter);

        extractNotification();


        notificationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                notificationListModel = notificationListModelArrayList.get(position);

                Intent intent = new Intent(getApplicationContext(),NotificationFarmerActivity.class);
                intent.putExtra(Constants.NOTIFICATION_LIST_DETAILS.KEY_USER_ID,userId);
                intent.putExtra(Constants.NOTIFICATION_LIST_DETAILS.KEY_INDENT_ID, notificationListModel.getNotificationNo());
                intent.putExtra(Constants.NOTIFICATION_LIST_DETAILS.KEY_ORG_ID, notificationListModel.getOrgId());
                //Toast.makeText(getApplicationContext(),"Area",Toast.LENGTH_LONG).show();

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
        //recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));



    }

    private void extractNotification(){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");

        progressDialog.show();
        //String NOTIFICATION_LIST_URL = "https://api2.boxfarming.in/wsapi/v1/index.php/Aroma/aroma_indent_list";
        String JSON_URL_LIST = Url.NOTIFICATION_LIST_URL;

        HashMap<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put(Constants.NOTIFICATION_LIST.KEY_USER_ID, userId);
        jsonParams.put(Constants.NOTIFICATION_LIST.KEY_ORG_ID, orgId);

        JSONArray mJSONArray = new JSONArray(Arrays.asList(jsonParams));
        Log.d("mJSONArray", "mJSONArray123: " + mJSONArray);

        notificationListModelArrayList.clear();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, JSON_URL_LIST,
                mJSONArray, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                //dataModelArrayList.clear();
                Log.d("data", "Data from server ListView: " + response);
                int i2=1;
                int sum=0;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        progressDialog.dismiss();
                        JSONObject jsonObject2 = response.getJSONObject(i);

                        sum = i2++;

                        notificationNo = jsonObject2.getString(Constants.NOTIFICATION_LIST.KEY_INDENT_HEADER_ID);
                        notificationDate = jsonObject2.getString(Constants.NOTIFICATION_LIST.KEY_INDENT_DATE_ID);
                        cultivationArea = jsonObject2.getString(Constants.NOTIFICATION_LIST.KEY_INDENT_AREA_ID);
                        farmerCount = jsonObject2.getString(Constants.NOTIFICATION_LIST.KEY_FARMER_COUNT_ID);
                        Log.d("notificationNo", "notificationNo ListView: " + notificationNo);

                        //
                        notificationListModel = new NotificationListModel(notificationNo, notificationDate, cultivationArea, farmerCount, orgId);
                        notificationListModelArrayList.add(notificationListModel);
                        notificationListAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                Log.d("sum", "sum from server: " + sum);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),R.string.noOrderListInsert,Toast.LENGTH_SHORT).show();
                Log.d("Error", "ErrorListView: " + error);
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