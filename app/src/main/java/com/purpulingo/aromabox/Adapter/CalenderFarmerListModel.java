package com.purpulingo.aromabox.Adapter;

public class CalenderFarmerListModel {
    String farmerName, farmerId, farmerVillage, farmerImg, farmerArea, lastCutting, nextCutting, lastIrrigation, lastFertigation;

    public CalenderFarmerListModel(String farmerName, String farmerId, String farmerVillage, String farmerImg, String farmerArea,
                                   String lastCutting, String nextCutting, String lastIrrigation, String lastFertigation) {
        this.farmerName = farmerName;
        this.farmerId = farmerId;
        this.farmerVillage = farmerVillage;
        this.farmerImg = farmerImg;
        this.farmerArea = farmerArea;
        this.lastCutting = lastCutting;
        this.nextCutting = nextCutting;
        this.lastIrrigation = lastIrrigation;
        this.lastFertigation = lastFertigation;
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

    public String getFarmerImg() {
        return farmerImg;
    }
    public void setFarmerImg(String farmerImg) {
        this.farmerImg = farmerImg;
    }

    public String getFarmerArea() {
        return farmerArea;
    }
    public void setFarmerArea(String farmerArea) {
        this.farmerArea = farmerArea;
    }

    public String getLastCutting() {
        return lastCutting;
    }
    public void setLastCutting(String lastCutting) {
        this.lastCutting = lastCutting;
    }

    public String getNextCutting() {
        return nextCutting;
    }
    public void setNextCutting(String nextCutting) {
        this.nextCutting = nextCutting;
    }

    public String getLastIrrigation() {
        return lastIrrigation;
    }
    public void setLastIrrigation(String lastIrrigation) {
        this.lastIrrigation = lastIrrigation;
    }

    public String getLastFertigation() {
        return lastFertigation;
    }
    public void setLastFertigation(String lastFertigation) {
        this.lastFertigation = lastFertigation;
    }
}
