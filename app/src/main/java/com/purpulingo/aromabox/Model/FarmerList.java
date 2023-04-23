package com.purpulingo.aromabox.Model;

import com.google.gson.annotations.SerializedName;

public class FarmerList {
    @SerializedName("farmer_id")
    private String mFarmer_id;
    @SerializedName("cutting_area")
    private String mCutting_area;
    @SerializedName("cutting_date")
    private String mCutting_date;


    public FarmerList(String farmer_id, String cutting_area, String cutting_date ){

        mFarmer_id = farmer_id;
        mCutting_area = cutting_area;
        mCutting_date = cutting_date;


    }
}
