package com.purpulingo.aromabox.Model;

import com.google.gson.annotations.SerializedName;

public class FarmerList2 {
    @SerializedName("farmer_id")
    private String mFarmer_id;

    @SerializedName("cutting_area")
    private String mCutting_area;

    public FarmerList2(String farmer_id,String cutting_area){

        mFarmer_id = farmer_id;
        mCutting_area = cutting_area;

    }
}
