package com.purpulingo.aromabox.Model;

public class CalenderPGListModel {

    String pgName, pgId, pgCount, pgArea;

    public CalenderPGListModel(String pgName, String pgId, String pgCount, String pgArea) {
        this.pgName = pgName;
        this.pgId = pgId;
        this.pgCount = pgCount;
        this.pgArea = pgArea;
    }

    public String getPgName() {
        return pgName;
    }
    public void setPgName(String pgName) {
        this.pgName = pgName;
    }

    public String getPgId() {
        return pgId;
    }
    public void setPgId(String pgId) {
        this.pgId = pgId;
    }

    public String getPgCount() {
        return pgCount;
    }
    public void setPgCount(String pgCount) {
        this.pgCount = pgCount;
    }

    public String getPgArea() {
        return pgArea;
    }
    public void setPgArea(String pgArea) {
        this.pgArea = pgArea;
    }
}
