package com.purpulingo.aromabox.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.purpulingo.aromabox.Global.UserSessionManager;
import com.purpulingo.aromabox.R;

public class ProfileActivity extends AppCompatActivity {

    private String  str_first_name, str_last_name, str_org_name, str_role, str_org_id, str_user_id, str_email, str_user_type;

    private TextView firstName, lastName, role, userId, emailId, orgName, orgId, logout;
    private ImageView backBtn;

    UserSessionManager userSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firstName = (TextView) findViewById(R.id.firstName);
        lastName = (TextView) findViewById(R.id.lastName);
        role = (TextView) findViewById(R.id.role);
        userId = (TextView) findViewById(R.id.userId);
        emailId = (TextView) findViewById(R.id.emailId);
        orgName = (TextView) findViewById(R.id.orgName);
        orgId = (TextView) findViewById(R.id.orgId);
        logout =(TextView) findViewById(R.id.logout);
        backBtn = (ImageView) findViewById(R.id.backBtn);

        userSessionManager = new UserSessionManager(getApplicationContext());

        str_first_name = userSessionManager.getUserFirstName();
        str_last_name = userSessionManager.getUserLastName();
        str_org_name = userSessionManager.getOrgName();
        str_role = userSessionManager.getRole();
        str_org_id = userSessionManager.getOrgId();
        str_user_id = userSessionManager.getUserID();
        str_email = userSessionManager.getEmailId();
        str_user_type = userSessionManager.getUserType();


        firstName .setText(str_first_name);
        lastName.setText(str_last_name);
        role.setText(str_role);
        userId.setText(str_user_id);
        emailId.setText(str_email);
        orgName.setText(str_org_name);
        orgId.setText(str_org_id);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });



    }
    public void logOut(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("लॉग आउट");
        builder.setMessage(R.string.logout)
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
}