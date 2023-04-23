package com.purpulingo.aromabox.Model;

public class OilListModel {

    String date, oilQuantity;

    public OilListModel(String date, String oilQuantity) {
        this.date = date;
        this.oilQuantity = oilQuantity;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getOilQuantity() {
        return oilQuantity;
    }
    public void setOilQuantity(String oilQuantity) {
        this.oilQuantity = oilQuantity;
    }
}
