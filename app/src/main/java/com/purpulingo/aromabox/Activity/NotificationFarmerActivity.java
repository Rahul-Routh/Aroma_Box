package com.purpulingo.aromabox.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.purpulingo.aromabox.Adapter.NotificationFarmerListAdapter;
import com.purpulingo.aromabox.Constants.Constants;
import com.purpulingo.aromabox.Global.NetworkChange;
import com.purpulingo.aromabox.Global.Url;
import com.purpulingo.aromabox.Global.UserSessionManager;
import com.purpulingo.aromabox.Model.FarmerList;
import com.purpulingo.aromabox.Model.FarmerMain;
import com.purpulingo.aromabox.Model.NotificationFarmerListModel;
import com.purpulingo.aromabox.Model.NotificationListModel;
import com.purpulingo.aromabox.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationFarmerActivity extends AppCompatActivity implements NotificationFarmerListAdapter.CheckBoxCheckedListener{

    private ImageView backBtn;
    private SwipeRefreshLayout refreshLayout;
    private ListView notificationFarmerListView;
    private Button totalCultivationSelected;
    private TextView totalCultivationAreaTextView;
    UserSessionManager userSessionManager;
    String userId, indentId, orgId;
    NotificationFarmerListAdapter notificationFarmerListAdapter;
    private NetworkChange networkChange;
    public static ArrayList<NotificationFarmerListModel> notificationFarmerListModelArrayList = new ArrayList<>();

    String str_indentDetailsId, str_indentHeaderId, str_farmerName, str_farmerId , str_cuttingDate, str_cuttingArea;
    String farmerPicture;
    String farmerID, farmerName, cultivationArea, farmerHeaderId, str_lastCutting;

    NotificationFarmerListModel notificationFarmerListModel;

    private LinearLayout selectedTag;
    //double total;
    double totalCultivationArea = 0.00;
    static int add=0;
    List<FarmerList> farmerLists;
    Gson gson;
    FarmerMain farmerMain;
    String json;

    static ArrayList<String> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_farmer);

        arrayList = new ArrayList<String>();
        networkChange = new NetworkChange();
        backBtn = (ImageView) findViewById(R.id.backBtn);
        totalCultivationSelected = (Button) findViewById(R.id.totalCultivationSelected);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        notificationFarmerListView = (ListView) findViewById(R.id.notificationFarmerListView);
        totalCultivationAreaTextView = (TextView)  findViewById(R.id.totalCultivationAreaTextView);

        selectedTag = (LinearLayout) findViewById(R.id.selectedTag);

        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        userSessionManager = new UserSessionManager(getApplicationContext());
        userId = userSessionManager.getUserID();
        orgId = userSessionManager.getOrgId();

        //indentId = getIntent().getStringExtra(Constants.NOTIFICATION_LIST_DETAILS.KEY_INDENT_ID);
        indentId = "0";

        notificationFarmerListAdapter = new NotificationFarmerListAdapter(getApplicationContext(), notificationFarmerListModelArrayList);
        notificationFarmerListView.setAdapter(notificationFarmerListAdapter);

        extractNotification();

        notificationFarmerListAdapter.setCheckedListener(this);

        gson = new Gson();
        farmerLists = new ArrayList<>();


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
            }
        });

        totalCultivationSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("totalCultivationArea", "totalCultivationArea = " + totalCultivationArea);
                Log.d("farmerJson", "farmerJson = " + json);
                AlertDialog.Builder builder = new AlertDialog.Builder(NotificationFarmerActivity.this);
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

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void extractNotification(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");

        progressDialog.show();
        //String NOTIFICATION_LIST_DETAILS_URL = "https://api2.boxfarming.in/wsapi/v1/index.php/Farmer/aroma_indent_list_details"
        String JSON_URL_LIST = Url.NOTIFICATION_LIST_DETAILS_URL;

        HashMap<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put(Constants.NOTIFICATION_LIST_DETAILS.KEY_USER_ID, userId);
        jsonParams.put(Constants.NOTIFICATION_LIST_DETAILS.KEY_INDENT_ID, indentId);
        jsonParams.put(Constants.NOTIFICATION_LIST_DETAILS.KEY_ORG_ID, orgId);
        jsonParams.put(Constants.NOTIFICATION_LIST_DETAILS.KEY_OPS_TYPE, "1");

        JSONArray mJSONArray = new JSONArray(Arrays.asList(jsonParams));
        Log.d("mJSONArray", "mJSONArray114: " + mJSONArray);

        notificationFarmerListModelArrayList.clear();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, JSON_URL_LIST,
                mJSONArray, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                progressDialog.dismiss();
                //dataModelArrayList.clear();
                Log.d("data", "Data from server ListView: " + response);
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject2 = response.getJSONObject(i);

                        farmerPicture = jsonObject2.getString(Constants.NOTIFICATION_LIST_DETAILS.KEY_FARMER_IMG);;
                        //farmerPicture = "https://api2.boxfarming.in//wsapi//v1//assets//fr5g757bdf//123.png";
                        farmerID = jsonObject2.getString(Constants.NOTIFICATION_LIST_DETAILS.KEY_FARMER_ID);
                        farmerHeaderId = jsonObject2.getString(Constants.NOTIFICATION_LIST_DETAILS.KEY_INDENT_HEADER_ID);
                        farmerName = jsonObject2.getString(Constants.NOTIFICATION_LIST_DETAILS.KEY_FARMER_NAME);
                        cultivationArea = jsonObject2.getString(Constants.NOTIFICATION_LIST_DETAILS.KEY_INDENT_AREA);
                        str_lastCutting = jsonObject2.getString(Constants.NOTIFICATION_LIST_DETAILS.KEY_CUTTING_DATE);

                        notificationFarmerListModel = new NotificationFarmerListModel(farmerPicture, farmerID, farmerName, cultivationArea, farmerHeaderId, str_lastCutting);
                        notificationFarmerListModelArrayList.add(notificationFarmerListModel);
                        notificationFarmerListAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
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

    public void selectedFarmer(){

        //https://api2.boxfarming.in/wsapi/v1/index.php/Aroma/aroma_indent_submit
        //{"farmer_list":[{"farmer_id":"124"},{"farmer_id":"123"}],
        // "indent_header_id":"123","org_id":"111","total_area":20.0,"user_id":"128"}

        final ProgressDialog progressDialog = new ProgressDialog(NotificationFarmerActivity.this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();

        String JSON_URL = Url.SELECTED_FARMER_LIST;
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

                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d("error", "error: " + error);
                AlertDialog.Builder builder = new AlertDialog.Builder(NotificationFarmerActivity.this);
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
        notificationFarmerListModel = notificationFarmerListModelArrayList.get(position);

        //str_indentDetailsId = notificationFarmerListModel.getFarmerID();
        str_indentHeaderId = notificationFarmerListModel.getFarmerHeaderId() ;
        str_farmerName =  notificationFarmerListModel.getFarmerName();
        str_farmerId = notificationFarmerListModel.getFarmerID();

        str_cuttingDate = notificationFarmerListModel.getLastCutting();
        str_cuttingArea = notificationFarmerListModel.getCultivationArea();

        totalCultivationArea += Double.parseDouble(notificationFarmerListModel.getCultivationArea());

        farmerLists.add(new FarmerList(str_farmerId, str_cuttingArea ,str_cuttingDate));

        farmerMain = new FarmerMain(userId,str_indentHeaderId,totalCultivationArea,orgId,farmerLists);
        json= gson.toJson(farmerMain);

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
        notificationFarmerListModel = notificationFarmerListModelArrayList.get(position);

        str_indentHeaderId = notificationFarmerListModel.getFarmerHeaderId() ;
        str_farmerName =  notificationFarmerListModel.getFarmerName();
        str_farmerId = notificationFarmerListModel.getFarmerID();

        totalCultivationArea -= Double.parseDouble(notificationFarmerListModel.getCultivationArea());

        farmerLists.remove(position);

        json= gson.toJson(farmerMain);
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
        super.onStart();
    }
    @Override
    protected void onStop() {
        unregisterReceiver(networkChange);
        super.onStop();
    }
}