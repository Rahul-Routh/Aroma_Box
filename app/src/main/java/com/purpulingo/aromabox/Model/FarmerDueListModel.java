package com.purpulingo.aromabox.Model;

public class FarmerDueListModel {
    String farmerId, farmerName,dueAmount;

    public FarmerDueListModel(String farmerId, String farmerName, String dueAmount) {
        this.farmerId = farmerId;
        this.farmerName = farmerName;
        this.dueAmount = dueAmount;
    }

    public String getFarmerId() {
        return farmerId;
    }
    public void setFarmerId(String farmerId) {
        this.farmerId = farmerId;
    }

    public String getFarmerName() {
        return farmerName;
    }
    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public String getDueAmount() {
        return dueAmount;
    }
    public void setDueAmount(String dueAmount) {
        this.dueAmount = dueAmount;
    }
}
