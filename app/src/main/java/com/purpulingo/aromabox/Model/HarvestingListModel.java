package com.purpulingo.aromabox.Model;

public class HarvestingListModel {
    String year, month, totalArea, areaUnite;

    public HarvestingListModel(String year, String month, String totalArea, String areaUnite) {
        this.year = year;
        this.month = month;
        this.totalArea = totalArea;
        this.areaUnite = areaUnite;
    }

    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }
    public void setMonth(String month) {
        this.month = month;
    }

    public String getTotalArea() {
        return totalArea;
    }
    public void setTotalArea(String totalArea) {
        this.totalArea = totalArea;
    }

    public String getAreaUnite() {
        return areaUnite;
    }
    public void setAreaUnite(String areaUnite) {
        this.areaUnite = areaUnite;
    }
}
