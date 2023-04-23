package com.purpulingo.aromabox.Model;

public class ExitingBatchListModel {

    String machineId, startDate, startTime, quantity, batchId, grade;

    public ExitingBatchListModel(String machineId, String startDate, String startTime, String quantity, String batchId, String  grade){
        this.machineId = machineId;
        this.quantity = quantity;
        this.batchId = batchId;
        this.startDate = startDate;
        this.startTime = startTime;
        this.grade = grade;
    }

    public String getMachineId() {
        return machineId;
    }
    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getQuantity() {
        return quantity;
    }
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getBatchId() {
        return batchId;
    }
    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getGrade() {
        return grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }
}
