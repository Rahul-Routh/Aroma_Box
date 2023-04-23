package com.purpulingo.aromabox.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.purpulingo.aromabox.Adapter.CultivationFarmerListAdapter2;
import com.purpulingo.aromabox.Adapter.PopUpFarmerListAdapter;
import com.purpulingo.aromabox.Constants.Constants;
import com.purpulingo.aromabox.Global.NetworkChange;
import com.purpulingo.aromabox.Global.Url;
import com.purpulingo.aromabox.Global.UserSessionManager;
import com.purpulingo.aromabox.Model.CultivationFarmerListModel2;
import com.purpulingo.aromabox.Model.PopUpFarmerListModel;
import com.purpulingo.aromabox.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.OnDateSelectedListener;
import org.naishadhparmar.zcustomcalendar.OnNavigationButtonClickedListener;
import org.naishadhparmar.zcustomcalendar.Property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CalendarViewActivity extends AppCompatActivity implements OnNavigationButtonClickedListener {
    private NetworkChange networkChange;
    private ImageView backBtn, calenderImageBtn, cancelIcon;
    CustomCalendar customCalendar;
    String orgId, userId, yearId, monthId, day, cutting, str_Date, str_viewDate, farmerName, farmerId, farmerVillage, sDateJson, showDate;
    UserSessionManager userSessionManager;
    Calendar calendar2;
    String jsonString = "";
    Button submitBtn;
    TextInputEditText selectDate;
    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    ListView popFarmerList;
    TextView dateText, noDataFound;
    PopUpFarmerListAdapter popUpFarmerListAdapter;
    public static ArrayList<PopUpFarmerListModel> popUpFarmerListModelArrayList;
    PopUpFarmerListModel popUpFarmerListModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);

        userSessionManager = new UserSessionManager(getApplicationContext());
        orgId = userSessionManager.getOrgId();
        userId = userSessionManager.getUserID();

        networkChange = new NetworkChange();
        backBtn = (ImageView) findViewById(R.id.backBtn);
        calenderImageBtn = (ImageView) findViewById(R.id.calenderImageBtn);
        customCalendar = findViewById(R.id.custom_calendar);
        submitBtn = (Button) findViewById(R.id.submit_btn);
        selectDate = (TextInputEditText) findViewById(R.id.selectDate);
        calendar2 = Calendar.getInstance();

        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        int year2 = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        HashMap<Object, Property> descHashMap = new HashMap<>();
        Property defaultProperty = new Property();
        //initialize default resource
        defaultProperty.layoutResource = R.layout.default_calendar_view;
        //initialize and assign resource
        defaultProperty.dateTextViewResource = R.id.text_view;
        //put object and property
        descHashMap.put("default", defaultProperty);

        //For current date
        Property currentProperty = new Property();
        currentProperty.layoutResource = R.layout.current_calendar_view;
        currentProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("current", currentProperty);

        //For less then 3 Date
        Property presentProperty = new Property();
        presentProperty.layoutResource = R.layout.present_calendar_view;
        presentProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("lt3", presentProperty);

        //For geter then 2 date
        Property absentProperty = new Property();
        absentProperty.layoutResource = R.layout.absent_calender_view;
        absentProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("gt2", absentProperty);

        //set desc hash map on custom calender
        customCalendar.setMapDescToProp(descHashMap);

        int year = calendar.get(Calendar.YEAR);
        yearId = String.valueOf(year);
        Log.d("YEAR","YEAR"+year);
        monthId = String.valueOf(Calendar.DAY_OF_MONTH);



        customCalendar.setOnNavigationButtonClickedListener(CustomCalendar.PREVIOUS, this);

        calenderImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CalendarViewActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String str_month = null;
                                String str_day = null;
                                month = month+1;
                                if((month+"").length()<2){
                                    str_month = "0"+month;
                                }else {
                                    str_month = ""+month;
                                }
                                if ((dayOfMonth+"").length()<2){
                                    str_day = "0"+dayOfMonth;
                                }else {
                                    str_day = ""+dayOfMonth;
                                }

                                str_Date = year + "-" + str_month + "-" + str_day;
                                str_viewDate = str_day + "-" + str_month + "-" + year;

                                if (str_Date.isEmpty() == false) {
                                    selectDate.setText(str_viewDate);
                                }
                            }
                        }, year2, month, day);
                calendar.set(calendar.DAY_OF_MONTH, day+14);
                calendar2.set(calendar2.DAY_OF_MONTH, day+1);

                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.getDatePicker().setMinDate(calendar2.getTimeInMillis());
                //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CalenderPGListActivity.class);
                intent.putExtra("Date",str_Date);
                startActivity(intent);
            }
        });


    }

    public void CalenderView(){

        //Initialize date hash map
        HashMap<Integer, Object> dateHashMap = new HashMap<>();

        dateHashMap.put(calendar2.get(Calendar.DAY_OF_MONTH),"current");

        String JSON_URL_LIST = Url.CUTTING_LIST_CAL;

        HashMap<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put(Constants.KEY_CUTTING_LIST_CAL.KEY_USER_ID, userId);
        jsonParams.put(Constants.KEY_CUTTING_LIST_CAL.KEY_ORG_ID, orgId);
        jsonParams.put(Constants.KEY_CUTTING_LIST_CAL.KEY_POP_ID, "0");
        jsonParams.put(Constants.KEY_CUTTING_LIST_CAL.KEY_MONTH_ID, monthId);
        jsonParams.put(Constants.KEY_CUTTING_LIST_CAL.KEY_YEAR_ID, yearId);

        JSONArray mJSONArray = new JSONArray(Arrays.asList(jsonParams));
        Log.d("mJSONArray", "mJSONArray32: " + mJSONArray);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, JSON_URL_LIST,
                mJSONArray, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                //dataModelArrayList.clear();
                Log.d("data", "Data from server ListView: " + response);
                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        day = jsonObject.getString(Constants.KEY_CUTTING_LIST_CAL.KEY_DAY);

                        cutting = jsonObject.getString(Constants.KEY_CUTTING_LIST_CAL.KEY_CUTTING);

                        if(Integer.valueOf(cutting) < 3 && Integer.valueOf(cutting) > 0){
                            dateHashMap.put(Integer.valueOf(day),"lt3");
                        }else if(Integer.valueOf(cutting) > 2) {
                            dateHashMap.put(Integer.valueOf(day), "gt2");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                customCalendar.setDate(calendar2, dateHashMap);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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



        customCalendar.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(View view, Calendar selectedDate, Object desc) {
                String sDate = selectedDate.get(Calendar.DAY_OF_MONTH)
                        +"/"+(selectedDate.get(Calendar.MONTH)+1)
                        +"/"+selectedDate.get(Calendar.YEAR);
//                Toast.makeText(getApplicationContext(), sDate,Toast.LENGTH_SHORT).show();

                String str_month = null;
                String str_day = null;
                int month = selectedDate.get(Calendar.MONTH)+1;
                int dayOfMonth = selectedDate.get(Calendar.DAY_OF_MONTH);
                if((month+"").length()<2){
                    str_month = "0"+month;
                }else {
                    str_month = ""+month;
                }
                if ((dayOfMonth+"").length()<2){
                    str_day = "0"+dayOfMonth;
                }else {
                    str_day = ""+dayOfMonth;
                }

                sDateJson = selectedDate.get(Calendar.YEAR) + "-" + str_month + "-" + str_day;
                showDate =  str_day + "-"  + str_month +"-"+ selectedDate.get(Calendar.YEAR);
                //Toast.makeText(getApplicationContext(), showDate,Toast.LENGTH_SHORT).show();

                createDialog();
//                    Intent intent = new Intent(getApplicationContext(), CalenderPGListActivity.class);
//                    intent.putExtra("Date",sDateJson);
//                    startActivity(intent);


            }
        });

        customCalendar.setOnNavigationButtonClickedListener(CustomCalendar.PREVIOUS, this);
        customCalendar.setOnNavigationButtonClickedListener(CustomCalendar.NEXT, this);
    }

    @Override
    public Map<Integer, Object>[] onNavigationButtonClicked(int whichButton, Calendar newMonth) {
        Map<Integer, Object>[] arr_cal = new Map[2];

        int monthIdInt = newMonth.get(Calendar.MONTH)+1;
        monthId = String.valueOf(monthIdInt);
        Log.d("monthid","month: "+monthId);

//        if(monthIdInt == Calendar.DAY_OF_MONTH){
//           // HashMap<Integer, Object> dateHashMap = new HashMap<>();
//            arr_cal[0].put(calendar.get(Calendar.DAY_OF_MONTH), "current");
//            //dateHashMap.put(calendar.get(Calendar.DAY_OF_MONTH),"current");
//        }

        if((monthIdInt - 1)!=13) {

            arr_cal[0] = new HashMap<>();

            String JSON_URL_LIST = Url.CUTTING_LIST_CAL;

            HashMap<String, String> jsonParams = new HashMap<String, String>();
            jsonParams.put(Constants.KEY_CUTTING_LIST_CAL.KEY_USER_ID, userId);
            jsonParams.put(Constants.KEY_CUTTING_LIST_CAL.KEY_ORG_ID, orgId);
            jsonParams.put(Constants.KEY_CUTTING_LIST_CAL.KEY_POP_ID, "0");
            jsonParams.put(Constants.KEY_CUTTING_LIST_CAL.KEY_MONTH_ID, monthId);
            jsonParams.put(Constants.KEY_CUTTING_LIST_CAL.KEY_YEAR_ID, yearId);

            JSONArray mJSONArray = new JSONArray(Arrays.asList(jsonParams));
            Log.d("mJSONArray", "mJSONArray32: " + mJSONArray);

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, JSON_URL_LIST,
                    mJSONArray, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    jsonString = String.valueOf(response);
                    Log.d("data", "Data from server ListView: " + response);
//                    for (int i = 0; i < response.length(); i++) {
//
//                        JSONObject jsonObject=null;
//                        try {
//                            jsonObject = response.getJSONObject(i);
//
//                            day = jsonObject.getString(Constants.KEY_CUTTING_LIST_CAL.KEY_DAY);
//
//                            cutting = jsonObject.getString(Constants.KEY_CUTTING_LIST_CAL.KEY_CUTTING);
//
//                            //arrList.add(Integer.valueOf(day));
//                            //Log.d("data", "Data from server ListView: " + arrList.add(Integer.valueOf(day)));
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getApplicationContext(), R.string.noOrderListInsert, Toast.LENGTH_SHORT).show();
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


            try {
                JSONArray array = new JSONArray(jsonString);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);

                    day = object.getString(Constants.KEY_CUTTING_LIST_CAL.KEY_DAY);
                    cutting = object.getString(Constants.KEY_CUTTING_LIST_CAL.KEY_CUTTING);

                    if(Integer.valueOf(cutting) < 3) {
                        arr_cal[0].put(Integer.valueOf(day), "present");
                    }else{
                        arr_cal[0].put(Integer.valueOf(day), "absent");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return arr_cal;
    }


    public void createDialog(){

        dialogBuilder = new AlertDialog.Builder(this);
        final View popUpView = getLayoutInflater().inflate(R.layout.pop_calender_farmer_list, null);
        popFarmerList = (ListView) popUpView.findViewById(R.id.popFarmerList);
        cancelIcon = (ImageView) popUpView.findViewById(R.id.cancel_icon);
        dateText = (TextView) popUpView.findViewById(R.id.dateText);
        noDataFound = (TextView) popUpView.findViewById(R.id.noDataFound);

        dialogBuilder.setView(popUpView);
        dialog = dialogBuilder.create();
        dialog.setCancelable(false);
        dialog.show();

        dateText.setText(showDate);
        cancelIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        popUpFarmerListModelArrayList = new ArrayList<>();
        popUpFarmerListAdapter = new PopUpFarmerListAdapter(getApplicationContext(), popUpFarmerListModelArrayList);
        popFarmerList.setAdapter(popUpFarmerListAdapter);


        //https://api2.boxfarming.in/wsapi/v1/index.php/Aroma/aroma_cutting_sch_view
        String JSON_URL_LIST = Url.FARMER_LIST_POPUP;
        HashMap<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put(Constants.KEY_FARMER_LIST_POPUP.KEY_ORG_ID, orgId);
        jsonParams.put(Constants.KEY_FARMER_LIST_POPUP.KEY_USER_ID, userId);
        jsonParams.put(Constants.KEY_FARMER_LIST_POPUP.KEY_SEARCH_DATE, sDateJson);

        JSONArray mJSONArray = new JSONArray(Arrays.asList(jsonParams));
        Log.d("mJSONArray", "mJSONArray: " + mJSONArray);
        popUpFarmerListModelArrayList.clear();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, JSON_URL_LIST,
                mJSONArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                noDataFound.setVisibility(View.GONE);
                for (int i=0;i<response.length();i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        farmerName = jsonObject.getString(Constants.KEY_FARMER_LIST_POPUP.KEY_FARMER_NAME);
                        farmerId = jsonObject.getString(Constants.KEY_FARMER_LIST_POPUP.KEY_FARMER_ID);
                        farmerVillage = jsonObject.getString(Constants.KEY_FARMER_LIST_POPUP.KEY_VILLAGE);


                        popUpFarmerListModel = new PopUpFarmerListModel(farmerName, farmerId, farmerVillage);
                        popUpFarmerListModelArrayList.add(popUpFarmerListModel);
                        popUpFarmerListAdapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                noDataFound.setVisibility(View.VISIBLE);
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
        CalenderView();
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChange);
        super.onStop();
    }


}