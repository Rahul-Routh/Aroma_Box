package com.purpulingo.aromabox.Model;

public class PopUpFarmerListModel {
    String farmerName, farmerId, farmerVillage;

    public PopUpFarmerListModel(String farmerName, String farmerId, String farmerVillage) {
        this.farmerName = farmerName;
        this.farmerId = farmerId;
        this.farmerVillage = farmerVillage;
    }

    public String getFarmerName() {
        return farmerName;
    }
    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public String getFarmerId() {
        return farmerId;
    }
    public void setFarmerId(String farmerId) {
        this.farmerId = farmerId;
    }

    public String getFarmerVillage() {
        return farmerVillage;
    }
    public void setFarmerVillage(String farmerVillage) {
        this.farmerVillage = farmerVillage;
    }
}
