package com.purpulingo.aromabox.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FarmerMain2 {
    @SerializedName("user_id")
    private String mUser_id;
    @SerializedName("total_area")
    private Double mTotal_area;
    @SerializedName("schedule_date")
    private String mSchedule_date;
    @SerializedName("org_id")
    private String mOrg_id;
    @SerializedName("farmer_list")
    private List<FarmerList2> mFarmerList2;

    public FarmerMain2(String user_id, String schedule_date, String org_id, List<FarmerList2> farmerLists2 ){
        mUser_id = user_id;
       // mIndent_header_id = indent_header_id;
        mSchedule_date = schedule_date;
        mOrg_id = org_id;
        mFarmerList2 = farmerLists2;
    }
}
