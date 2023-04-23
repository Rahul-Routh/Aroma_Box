package com.purpulingo.aromabox.Model;

public class NotificationListModel {

    String notificationNo, notificationDate, cultivationArea, farmerCount, orgId;

    public NotificationListModel( String notificationNo, String notificationDate, String cultivationArea, String farmerCount, String orgId){
        this.notificationDate = notificationDate;
        this.notificationNo = notificationNo;
        this.cultivationArea = cultivationArea;
        this.farmerCount = farmerCount;
        this.orgId = orgId;
    }
    public NotificationListModel(){

    }

    public String getNotificationNo() {
        return notificationNo;
    }
    public void setNotificationNo(String notificationNo) {
        this.notificationNo = notificationNo;
    }

    public String getNotificationDate() {
        return notificationDate;
    }
    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }

    public String getCultivationArea() {
        return cultivationArea;
    }
    public void setCultivationArea(String cultivationArea) {
        this.cultivationArea = cultivationArea;
    }

    public String getFarmerCount() {
        return farmerCount;
    }
    public void setFarmerCount(String farmerCount) {
        this.farmerCount = farmerCount;
    }

    public String getOrgId() {
        return orgId;
    }
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
