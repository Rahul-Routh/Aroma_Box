package com.purpulingo.aromabox.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.purpulingo.aromabox.Adapter.CultivationFarmerListAdapter2;
import com.purpulingo.aromabox.Adapter.PopUpFarmerListAdapter;
import com.purpulingo.aromabox.Constants.Constants;
import com.purpulingo.aromabox.Global.NetworkChange;
import com.purpulingo.aromabox.Global.Url;
import com.purpulingo.aromabox.Global.UserSessionManager;
import com.purpulingo.aromabox.Global.VersionName;
import com.purpulingo.aromabox.Model.CultivationFarmerListModel2;
import com.purpulingo.aromabox.Model.NotificationListModel;
import com.purpulingo.aromabox.Model.PopUpFarmerListModel;
import com.purpulingo.aromabox.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private CardView cultivationBtn, commercialBtn, notificationBtn, processionBtn;
    private NetworkChange networkChange;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView user_name,user_role, countNotification, version;
    String orgId, userId, role, str_first, str_last;
    UserSessionManager userSessionManager;
    private ImageView calendarBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

       // name = findViewById(R.id.name);

        //name.setText("dhfjsdhf");

        version = (TextView) findViewById(R.id.version);
        version.setText(getResources().getString(R.string.version, VersionName.VERSION_NAME));

        userSessionManager = new UserSessionManager(getApplicationContext());
        orgId = userSessionManager.getOrgId();
        userId = userSessionManager.getUserID();
        role = userSessionManager.getRole();
        str_first = userSessionManager.getUserFirstName();

        calendarBtn = (ImageView) findViewById(R.id.calendarBtn);
        //name = (TextView) findViewById(R.id.name);
        countNotification = (TextView) findViewById(R.id.countNotification);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);

        //name.setText(""+str_first);
        networkChange = new NetworkChange();

        cultivationBtn = (CardView) findViewById(R.id.cultivationBtn);
        commercialBtn = (CardView) findViewById(R.id.commercialBtn);
        notificationBtn = (CardView) findViewById(R.id.notificationBtn);
        processionBtn = (CardView) findViewById(R.id.processionBtn);
        //menu
       // navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.menu_profile:
                        Intent intent2 = new Intent(getApplicationContext(), ProfileActivity.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intent2);
                        break;
                    case R.id.menu_logout:
                        logOut();
                        break;

                    case R.id.menu_privacy:
                        Toast.makeText(getApplicationContext(),"Privacy",Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });
        //navigationView.setItemIconTintList(null);
        View header = navigationView.getHeaderView(0);
        user_name= header.findViewById(R.id.user_name);
        user_role= header.findViewById(R.id.user_role);

        user_name.setText(str_first);
        user_role.setText(" ("+role+")");

        //getDate form

        if(role.equals("AVM")){
            cultivationBtn.setVisibility(View.VISIBLE);
            notificationBtn.setVisibility(View.VISIBLE);
            calendarBtn.setVisibility(View.INVISIBLE);
            //calendarBtn.setVisibility(View.VISIBLE);
        }
        if(role.equals("OP")){
            processionBtn.setVisibility(View.VISIBLE);
        }
        if(role.equals("PM")){
            cultivationBtn.setVisibility(View.VISIBLE);
            commercialBtn.setVisibility(View.VISIBLE);
            calendarBtn.setVisibility(View.VISIBLE);
        }


        cultivationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CultivationActivity.class);
                startActivity(intent);

            }
        });

        commercialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CommercialActivity.class);
                startActivity(intent);

            }
        });

        notificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NotificationFarmerActivity.class);
                startActivity(intent);
            }
        });

        processionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProcessionActivity.class);
                startActivity(intent);
            }
        });

        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CalendarViewActivity.class);
                startActivity(intent);
            }
        });


    }

    public void dataGet(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");

        progressDialog.show();
        //String NOTIFICATION_LIST_URL = "https://api2.boxfarming.in/wsapi/v1/index.php/Aroma/aroma_indent_list";
        String JSON_URL_LIST = Url.NOTIFICATION_LIST_DETAILS_URL;

        HashMap<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put(Constants.NOTIFICATION_LIST_DETAILS.KEY_USER_ID, userId);
        jsonParams.put(Constants.NOTIFICATION_LIST_DETAILS.KEY_INDENT_ID, "0");
        jsonParams.put(Constants.NOTIFICATION_LIST_DETAILS.KEY_ORG_ID, orgId);
        jsonParams.put(Constants.NOTIFICATION_LIST_DETAILS.KEY_OPS_TYPE, "1");

        JSONArray mJSONArray = new JSONArray(Arrays.asList(jsonParams));
        Log.d("mJSONArray", "mJSONArray32: " + mJSONArray);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, JSON_URL_LIST,
                mJSONArray, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                progressDialog.dismiss();
                //dataModelArrayList.clear();
                Log.d("data", "Data from server ListView: " + response);
                for (int i = 0; i < response.length(); i++) {
                        //sum2 = i2++;
                }
                Log.d("sum", "sum from server: " + response.length());
                countNotification.setText("("+response.length()+")");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(),R.string.noOrderListInsert,Toast.LENGTH_SHORT).show();
                Log.d("Error", "ErrorListView: " + error);
                progressDialog.dismiss();
                countNotification.setText("");
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
    public void logOut(){
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("लॉग आउट")
                .setCancelable(false)
                .setMessage(R.string.logout)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userSessionManager.setLogin(false);
                        userSessionManager.setUserID("");
                        userSessionManager.setUserFirstName("");
                        userSessionManager.setUserLastName("");
                        userSessionManager.setEmailId("");
                        userSessionManager.setOrgName("");
                        userSessionManager.setRole("");
                        userSessionManager.setOrgId("");
                        userSessionManager.setUserType("");

                        drawerLayout.closeDrawer(GravityCompat.START);

                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                drawerLayout.closeDrawer(GravityCompat.START);
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setMessage(R.string.alertMsg)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
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
    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChange, filter);
        dataGet();
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChange);
        super.onStop();
    }
}