package com.purpulingo.aromabox.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FarmerMain {
    @SerializedName("user_id")
    private String mUser_id;
    @SerializedName("total_area")
    private Double mTotal_area;
    @SerializedName("indent_header_id")
    private String mIndent_header_id;
    @SerializedName("org_id")
    private String mOrg_id;
    @SerializedName("farmer_list")
    private List<FarmerList> mFarmerList;

    public FarmerMain(String user_id, String indent_header_id, double total_area, String org_id, List<FarmerList> farmerLists ){
        mUser_id = user_id;
        mIndent_header_id = indent_header_id;
        mTotal_area = total_area;
        mOrg_id = org_id;
        mFarmerList = farmerLists;
    }


}
