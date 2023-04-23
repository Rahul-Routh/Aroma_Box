package com.purpulingo.aromabox.Global;

import android.content.Context;
import android.content.SharedPreferences;

import com.purpulingo.aromabox.Constants.Constants;

public class UserSessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public UserSessionManager(Context context){
        sharedPreferences = context.getSharedPreferences(Constants.USER_SESSION_MANAGEMENT.KEY_PREFER_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }
    //login
    public void setLogin(boolean login){
        editor.putBoolean("KEY_LOGIN",login);
        editor.commit();
    }
    public boolean getLogin(){
        return sharedPreferences.getBoolean("KEY_LOGIN",false);
    }

    //user id
    public void setUserID(String userId){ editor.putString(Constants.USER_SESSION_MANAGEMENT.KEY_USER_ID, userId);
        editor.commit(); }
    public String getUserID(){ return sharedPreferences.getString(Constants.USER_SESSION_MANAGEMENT.KEY_USER_ID,"");}

    //firstName
    public void setUserFirstName(String userFirstName){ editor.putString(Constants.USER_SESSION_MANAGEMENT.KEY_FIRST_NAME, userFirstName);
        editor.commit(); }
    public String getUserFirstName(){ return sharedPreferences.getString(Constants.USER_SESSION_MANAGEMENT.KEY_FIRST_NAME,"");}
    //lastName
    public void setUserLastName(String userLastName){ editor.putString(Constants.USER_SESSION_MANAGEMENT.KEY_LAST_NAME, userLastName);
        editor.commit(); }
    public String getUserLastName(){ return sharedPreferences.getString(Constants.USER_SESSION_MANAGEMENT.KEY_LAST_NAME,"");}

    //email id
    public void setEmailId(String emailId){ editor.putString(Constants.USER_SESSION_MANAGEMENT.KEY_EMAIL,emailId);
        editor.commit(); }
    public String getEmailId(){ return sharedPreferences.getString(Constants.USER_SESSION_MANAGEMENT.KEY_EMAIL,"");}


    //orgName
    public void setOrgName(String orgName){ editor.putString(Constants.USER_SESSION_MANAGEMENT.KEY_ORG_NAME, orgName);
        editor.commit(); }
    public String getOrgName(){ return sharedPreferences.getString(Constants.USER_SESSION_MANAGEMENT.KEY_ORG_NAME,"");}

    //role
    public void setRole(String role){ editor.putString(Constants.USER_SESSION_MANAGEMENT.KEY_ROLE, role);
        editor.commit(); }
    public String getRole(){ return sharedPreferences.getString(Constants.USER_SESSION_MANAGEMENT.KEY_ROLE,"");}

    //orgId
    public void setOrgId(String orgId){ editor.putString(Constants.USER_SESSION_MANAGEMENT.KEY_ORG_ID, orgId);
        editor.commit(); }
    public String getOrgId(){ return sharedPreferences.getString(Constants.USER_SESSION_MANAGEMENT.KEY_ORG_ID,"");}

    //userType
    public void setUserType(String userType){ editor.putString(Constants.USER_SESSION_MANAGEMENT.KEY_USER_TYPE, userType);
        editor.commit(); }
    public String getUserType(){ return sharedPreferences.getString(Constants.USER_SESSION_MANAGEMENT.KEY_USER_TYPE,"");}





}
