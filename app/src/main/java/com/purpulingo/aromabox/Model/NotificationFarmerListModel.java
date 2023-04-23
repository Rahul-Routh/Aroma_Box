package com.purpulingo.aromabox.Model;

public class NotificationFarmerListModel {

    String  farmerPicture, farmerID, farmerName, cultivationArea, farmerHeaderId, lastCutting;

    public NotificationFarmerListModel(String farmerPicture, String farmerID, String farmerName, String cultivationArea, String farmerHeaderId, String lastCutting ){
        this.farmerPicture = farmerPicture;
        this.farmerID = farmerID;
        this.farmerName = farmerName;
        this.cultivationArea = cultivationArea;
        this.farmerHeaderId = farmerHeaderId;
        this.lastCutting = lastCutting;
    }
    public NotificationFarmerListModel(){

    }

    public String getFarmerPicture() {
        return farmerPicture;
    }
    public void setFarmerPicture(String farmerPicture) {
        this.farmerPicture = farmerPicture;
    }

    //public int getFarmerPicture() {
    //    return farmerPicture;
    //}
    //public void setFarmerPicture(int farmerPicture) {
    //    this.farmerPicture = farmerPicture;
    //}

    public String getFarmerID() {
        return farmerID;
    }
    public void setFarmerID(String farmerID) {
        this.farmerID = farmerID;
    }

    public String getFarmerName() {
        return farmerName;
    }
    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public String getCultivationArea() {
        return cultivationArea;
    }
    public void setCultivationArea(String cultivationArea) {
        this.cultivationArea = cultivationArea;
    }

    public String getFarmerHeaderId() {
        return farmerHeaderId;
    }
    public void setFarmerHeaderId(String farmerHeaderId) {
        this.farmerHeaderId = farmerHeaderId;
    }

    public String getLastCutting() {
        return lastCutting;
    }
    public void setLastCutting(String lastCutting) {
        this.lastCutting = lastCutting;
    }
}
