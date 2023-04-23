package com.purpulingo.aromabox.Model;

public class FarmerPaidListModel {

    String farmerId, farmerName, paymentDate, paymentAmount;

    public FarmerPaidListModel(String farmerId, String farmerName, String paymentDate, String paymentAmount) {
        this.farmerId = farmerId;
        this.farmerName = farmerName;
        this.paymentDate = paymentDate;
        this.paymentAmount = paymentAmount;
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

    public String getPaymentDate() {
        return paymentDate;
    }
    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }
    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
}
