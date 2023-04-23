package com.purpulingo.aromabox.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
import com.purpulingo.aromabox.Adapter.ProcurementFarmerListAdapter;
import com.purpulingo.aromabox.Model.ProcurementFarmerListModel;
import com.purpulingo.aromabox.Constants.Constants;
import com.purpulingo.aromabox.Global.NetworkChange;
import com.purpulingo.aromabox.Global.Url;
import com.purpulingo.aromabox.Global.UserSessionManager;
import com.purpulingo.aromabox.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ProcurementActivity extends AppCompatActivity {

    private ImageView backBtn;
    private CardView qrScannerBtn;
    private NetworkChange networkChange;

    private EditText farmerId;
    private Button search;
    private String userId, indentId,orgId, str_farmerId;
    private ListView procurementFarmerList;
    private SwipeRefreshLayout refreshLayout;
    ProcurementFarmerListModel procurementFarmerListModel;
    ProcurementFarmerListAdapter procurementFarmerListAdapter;
    UserSessionManager userSessionManager;
    public static ArrayList<ProcurementFarmerListModel> procurementFarmerListModelArrayList;

    String str_indentDetailsId, str_indentHeaderId, str_farmerName , str_cuttingDate, str_cuttingArea;
    String farmerPicture;
    String farmerID, farmerName, cultivationArea, farmerHeaderId, str_lastCutting, str_villageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procurement);

        procurementFarmerListModelArrayList = new ArrayList<>();

        userSessionManager = new UserSessionManager(getApplicationContext());
        orgId = userSessionManager.getOrgId();
        userId = userSessionManager.getUserID();
        indentId = "0";

        networkChange = new NetworkChange();
        backBtn = (ImageView) findViewById(R.id.backBtn);
        qrScannerBtn = (CardView) findViewById(R.id.qrScannerBtn);
        procurementFarmerList = (ListView) findViewById(R.id.procurementFarmerList);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);

        procurementFarmerListAdapter = new ProcurementFarmerListAdapter(getApplicationContext(), procurementFarmerListModelArrayList);
        procurementFarmerList.setAdapter(procurementFarmerListAdapter);

        //farmerId = (EditText) findViewById(R.id.farmerId);
        //search = (Button) findViewById(R.id.search);

        QrScanner();


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
            }
        });

        procurementFarmerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                procurementFarmerListModel = procurementFarmerListModelArrayList.get(position);
                Intent intent = new Intent(getApplicationContext(),FarmerProfileQRActivity.class);
                intent.putExtra(Constants.QR_SCANNER_PROFILE.KEY_QRVAL,orgId+"|"+procurementFarmerListModel.getFarmerID());
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

    public void QrScanner(){
        qrScannerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(ProcurementActivity.this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ProcurementActivity.this,
                            new String[]{
                                    Manifest.permission.CAMERA
                            },1000);

                }else {
                    Intent intent = new Intent(getApplicationContext(), ScannerViewActivity.class);
                    startActivity(intent);
                    finish();
                    //Toast.makeText(ScannerViewActivity.this,)
                }

            }
        });

//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (farmerId.getText().toString().length() == 0) {
//                    farmerId.requestFocus();
//                    farmerId.setError("कृपया किसान आईडी दर्ज करें");
//                }else {
//                    //String farmer = String.valueOf(farmerId);
//                    str_farmerId = orgId+"|"+farmerId.getText().toString();
//                    Intent intent = new Intent(ProcurementActivity.this, FarmerProfileQRActivity.class);
//                    intent.putExtra(Constants.QR_SCANNER_PROFILE.KEY_QRVAL, str_farmerId);
//
//                    startActivity(intent);
//                    finish();
//                }
//            }
//        });
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
        jsonParams.put(Constants.NOTIFICATION_LIST_DETAILS.KEY_OPS_TYPE, "2");

        JSONArray mJSONArray = new JSONArray(Arrays.asList(jsonParams));
        Log.d("mJSONArray", "mJSONArray114: " + mJSONArray);

        procurementFarmerListModelArrayList.clear();
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

                        farmerPicture = jsonObject2.getString(Constants.NOTIFICATION_LIST_DETAILS.KEY_FARMER_IMG);
                        //farmerPicture = "https://api2.boxfarming.in//wsapi//v1//assets//fr5g757bdf//123.png";
                        farmerID = jsonObject2.getString(Constants.NOTIFICATION_LIST_DETAILS.KEY_FARMER_ID);
                        farmerHeaderId = jsonObject2.getString(Constants.NOTIFICATION_LIST_DETAILS.KEY_INDENT_HEADER_ID);
                        farmerName = jsonObject2.getString(Constants.NOTIFICATION_LIST_DETAILS.KEY_FARMER_NAME);
                        cultivationArea = jsonObject2.getString(Constants.NOTIFICATION_LIST_DETAILS.KEY_INDENT_AREA);
                        str_lastCutting = jsonObject2.getString(Constants.NOTIFICATION_LIST_DETAILS.KEY_CUTTING_DATE);
                        str_villageName = jsonObject2.getString(Constants.NOTIFICATION_LIST_DETAILS.KEY_VILLAGE_NAME);

                        DateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd");
                        DateFormat outputFormat = new SimpleDateFormat("dd-mm-yyyy");
                        String inputDateStr = str_lastCutting;
                        Date date = inputFormat.parse(inputDateStr);
                        str_lastCutting = outputFormat.format(date);

                        procurementFarmerListModel = new ProcurementFarmerListModel(farmerPicture, farmerID, farmerName, cultivationArea, farmerHeaderId, str_lastCutting, str_villageName);
                        procurementFarmerListModelArrayList.add(procurementFarmerListModel);
                        procurementFarmerListAdapter.notifyDataSetChanged();

                    } catch (JSONException | ParseException e) {
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
    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChange, filter);
        extractNotification();
        super.onStart();
    }
    @Override
    protected void onStop() {
        unregisterReceiver(networkChange);
        super.onStop();
    }
}