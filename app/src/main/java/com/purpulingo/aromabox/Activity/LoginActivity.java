package com.purpulingo.aromabox.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.purpulingo.aromabox.Constants.Constants;
import com.purpulingo.aromabox.Global.NetworkChange;
import com.purpulingo.aromabox.Global.Url;
import com.purpulingo.aromabox.Global.UserSessionManager;
import com.purpulingo.aromabox.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText userId, userPassword;
    private Button login;
    private NetworkChange networkChange;
    private String str_userId, str_userPassword;
    private String  status, first_name, last_name, org_name, role, org_id, user_id, email, user_type;

    UserSessionManager userSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        networkChange = new NetworkChange();
        userSessionManager = new UserSessionManager(getApplicationContext());

        login = (Button) findViewById(R.id.login);
        userId = (EditText) findViewById(R.id.userId);
        userPassword = (EditText) findViewById(R.id.userPassword);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int radioId = radioGroup.getCheckedRadioButtonId();
                //radioButton = findViewById(radioId);

                if (userId.getText().toString().length() == 0) {
                    userId.requestFocus();
                    userId.setError(""+R.string.enterValidUserId);
                } else if (userPassword.getText().toString().length() == 0) {
                    userPassword.requestFocus();
                    userPassword.setError(""+R.string.enterValidPassword);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage(R.string.login)
                            .setCancelable(false)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    login();
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
            }
        });
    }

    public void login(){
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();

        str_userId = userId.getText().toString().trim();
//        str_orgId = orgId.getText().toString().trim();
        str_userPassword = userPassword.getText().toString().trim();

        //https://api2.boxfarming.in/wsapi/v1/index.php/Boxfarming_mobile/jSON_User_verification
                    /*{"user_id":"128","org_id":"0","user_password":"muser"}
                    {
                        "first_name": "M-User",
                            "last_name": "",
                            "org_name": "Vernajyoti - Bungkulung",

                            "role": "ME",
                            "org_id": "111",
                            "user_id": "128",
                            "email": "muser@a.com",
                            "user_type": "2",
                            "json_status": "success"
                    }*/

        String JSON_URL = Url.LOGIN_URL;

        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put(Constants.LOGIN_USER.KEY_USER_ID, str_userId);
        jsonParams.put(Constants.LOGIN_USER.KEY_ORG_ID, "0");
        jsonParams.put(Constants.LOGIN_USER.KEY_PASSWORD, str_userPassword);
        //JSONObject jsonParam = new JSONObject(jsonParams);
        JSONArray mJSONArray = new JSONArray(Arrays.asList(jsonParams));

        Log.d("response123", "response" + mJSONArray);
        //Log.d("json", "json" + jsonParam);
        JsonArrayRequest postRequest = new JsonArrayRequest(Request.Method.POST, JSON_URL,
                mJSONArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        progressDialog.dismiss();

                        Log.d("response", "response" + response);

                        JSONObject jsonObject2 = response.getJSONObject(i);

                        status = jsonObject2.getString(Constants.GET_LOGIN_USER.KEY_JSON_STATUS);
                        first_name = jsonObject2.getString(Constants.GET_LOGIN_USER.KEY_FIRST_NAME);
                        last_name = jsonObject2.getString(Constants.GET_LOGIN_USER.KEY_LAST_NAME);
                        org_name = jsonObject2.getString(Constants.GET_LOGIN_USER.KEY_ORG_NAME);
                        role = jsonObject2.getString(Constants.GET_LOGIN_USER.KEY_ROLE);
                        org_id = jsonObject2.getString(Constants.GET_LOGIN_USER.KEY_ORG_ID);
                        user_id = jsonObject2.getString(Constants.GET_LOGIN_USER.KEY_USER_ID);
                        email = jsonObject2.getString(Constants.GET_LOGIN_USER.KEY_EMAIL);
                        user_type = jsonObject2.getString(Constants.GET_LOGIN_USER.KEY_USER_TYPE);

//                        String  s = "{"+status+","+first_name+" "+last_name+","+org_name+"}";
//                        Log.d("string", "string" + s);
//                        Log.d("status123", "status" + status);

                        if (status.equals("success") == true) {
                            userSessionManager.setLogin(true);
                            userSessionManager.setUserID(user_id);
                            userSessionManager.setUserFirstName(first_name);
                            userSessionManager.setUserLastName(last_name);
                            userSessionManager.setEmailId(email);
                            userSessionManager.setOrgName(org_name);
                            userSessionManager.setRole(role);
                            userSessionManager.setOrgId(org_id);
                            userSessionManager.setUserType(user_type);

                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(LoginActivity.this, "Please Enter Valid Details", Toast.LENGTH_SHORT).show();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }



                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Please Enter Valid Details", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(postRequest);
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

//    public void checkButton(View view){
//        int radioId = radioGroup.getCheckedRadioButtonId();
//        radioButton = findViewById(radioId);
//
//        Toast.makeText(this,"SelectRadio Button: "+radioButton.getText(), Toast.LENGTH_SHORT).show();
//
//    }
}