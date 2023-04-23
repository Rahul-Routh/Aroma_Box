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
import com.android.volley.toolbox.Volley;
import com.purpulingo.aromabox.Adapter.ExitingListAdapter;
import com.purpulingo.aromabox.Constants.Constants;
import com.purpulingo.aromabox.Global.NetworkChange;
import com.purpulingo.aromabox.Global.Url;
import com.purpulingo.aromabox.Global.UserSessionManager;
import com.purpulingo.aromabox.Model.ExitingBatchListModel;
import com.purpulingo.aromabox.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExitingBatchListActivity extends AppCompatActivity {
    private ImageView backBtn;
    private SwipeRefreshLayout refreshLayout;
    private ListView exitingBatchList;
    private NetworkChange networkChange;

    ExitingListAdapter exitingListAdapter;
    public static ArrayList<ExitingBatchListModel> exitingBatchListModelArrayList;
    private String machineId, startDate, startTime, quantity, batchId, startDateFor, grade;
    ExitingBatchListModel exitingBatchListModel;
    UserSessionManager userSessionManager;
    String userId, orgId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exiting_batch_list);

        userSessionManager = new UserSessionManager(getApplicationContext());
        userId = userSessionManager.getUserID();
        orgId = userSessionManager.getOrgId();

        networkChange = new NetworkChange();
        backBtn = (ImageView) findViewById(R.id.backBtn);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        exitingBatchList = (ListView) findViewById(R.id.exitingBatchList);

        exitingBatchListModelArrayList = new ArrayList<>();

        exitingListAdapter = new ExitingListAdapter(getApplicationContext(), exitingBatchListModelArrayList);
        exitingBatchList.setAdapter(exitingListAdapter);

        //dataExtract();

        exitingBatchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                exitingBatchListModel = exitingBatchListModelArrayList.get(position);
                Intent intent = new Intent(getApplicationContext(),ExitingBatchActivity.class);
                intent.putExtra(Constants.EXITING_DISTILLATION_BATCH.KEY_POS_ID, exitingBatchListModel.getMachineId());
                intent.putExtra(Constants.EXITING_DISTILLATION_BATCH.KEY_BATCH_ID, exitingBatchListModel.getBatchId());
                intent.putExtra(Constants.EXITING_DISTILLATION_BATCH.KEY_START_DATE, exitingBatchListModel.getStartDate());
                intent.putExtra(Constants.EXITING_DISTILLATION_BATCH.KEY_START_TIME, exitingBatchListModel.getStartTime());
                intent.putExtra(Constants.EXITING_DISTILLATION_BATCH.KEY_QUANTITY, exitingBatchListModel.getQuantity());

                //intent.putExtra(Constants.NOTIFICATION_LIST_DETAILS.KEY_USER_ID,"1");
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
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");

        progressDialog.show();

        String JSON_URL_LIST = Url.DISTILLATION_BATCH_LIST;
        //https://api2.boxfarming.in/wsapi/v1/index.php/Aroma/aroma_distillation_batch
        //[{"user_id":"128","org_id":"111","pos_id":"2"}]
        HashMap<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put(Constants.EXITING_DISTILLATION_BATCH.KEY_USER_ID, userId);
        jsonParams.put(Constants.EXITING_DISTILLATION_BATCH.KEY_ORG_ID, orgId);
        jsonParams.put(Constants.EXITING_DISTILLATION_BATCH.KEY_POS_ID, "2");

        JSONArray mJSONArray = new JSONArray(Arrays.asList(jsonParams));
        Log.d("mJSONArray", "mJSONArray: " + mJSONArray);

        exitingBatchListModelArrayList.clear();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, JSON_URL_LIST,
                mJSONArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("mJSONArray", "mJSONArray: " + response);
                for (int i = 0; i < response.length(); i++) {
                    try {
                        progressDialog.dismiss();
                        JSONObject jsonObject = response.getJSONObject(i);

                        //SimpleDateFormat sd1 = new SimpleDateFormat("dd/MM/yyyy");
                        //Date d1 = sd1.parse(startDate);
                        //startDateFor = "123";
                        //machineId = jsonObject.getString(Constants.EXITING_DISTILLATION_BATCH.KEY_POS_ID);
                        machineId = jsonObject.getString(Constants.EXITING_DISTILLATION_BATCH.KEY_POS_ID);
                        startDate = jsonObject.getString(Constants.EXITING_DISTILLATION_BATCH.KEY_START_DATE);

                        startTime = jsonObject.getString(Constants.EXITING_DISTILLATION_BATCH.KEY_START_TIME);
                        quantity = jsonObject.getString(Constants.EXITING_DISTILLATION_BATCH.KEY_QUANTITY);
                        batchId = jsonObject.getString(Constants.EXITING_DISTILLATION_BATCH.KEY_BATCH_ID);
                        grade = jsonObject.getString(Constants.EXITING_DISTILLATION_BATCH.KEY_GRADE);

                        //dateConvert
                        DateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd");
                        DateFormat outputFormat = new SimpleDateFormat("dd-mm-yyyy");
                        String inputDateStr = startDate;
                        Date date = inputFormat.parse(inputDateStr);
                        String outputDateStr = outputFormat.format(date);

                        String str_grade = "";

                        if(grade.equals("1")){
                            str_grade = "ग्रेड A";
                        }
                        if(grade.equals("2")){
                            str_grade = "ग्रेड B";
                        }
                        if(grade.equals("3")){
                            str_grade = "ग्रेड C";
                        }
                        if(grade.equals("0")){
                            str_grade = "NA";
                        }


                        exitingBatchListModel = new ExitingBatchListModel(machineId, outputDateStr, startTime, quantity, batchId, str_grade);
                        exitingBatchListModelArrayList.add(exitingBatchListModel);
                        exitingListAdapter.notifyDataSetChanged();

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