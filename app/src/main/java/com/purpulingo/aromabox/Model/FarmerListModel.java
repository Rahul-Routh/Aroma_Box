package com.purpulingo.aromabox.Model;

public class FarmerListModel {
    String farmerId, farmerName,farmerDate, farmerBioQuantity, farmerBioQuantityPrice;

    public FarmerListModel(String farmerId, String farmerName, String farmerDate, String farmerBioQuantity, String farmerBioQuantityPrice) {
        this.farmerId = farmerId;
        this.farmerName = farmerName;
        this.farmerDate = farmerDate;
        this.farmerBioQuantity = farmerBioQuantity;
        this.farmerBioQuantityPrice = farmerBioQuantityPrice;

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

    public String getFarmerDate() {
        return farmerDate;
    }
    public void setFarmerDate(String farmerDate) {
        this.farmerDate = farmerDate;
    }

    public String getFarmerBioQuantity() {
        return farmerBioQuantity;
    }
    public void setFarmerBioQuantity(String farmerBioQuantity) {
        this.farmerBioQuantity = farmerBioQuantity;
    }

    public String getFarmerBioQuantityPrice() {
        return farmerBioQuantityPrice;
    }
    public void setFarmerBioQuantityPrice(String farmerBioQuantityPrice) {
        this.farmerBioQuantityPrice = farmerBioQuantityPrice;
    }
}
