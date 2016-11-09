package com.fhzc.app.android.models;

import java.io.Serializable;

/**
 * Created by yanbo on 2016/8/1.
 */
public class OrderRightModel implements Serializable {
    String cover;
    long markDate;
    int rightsId;
    int status;//0:
    int id;
    int spendScore;
    int level;
    String name;
    String levelName;

    public long getMarkDate() {
        return markDate;
    }

    public void setMarkDate(long markDate) {
        this.markDate = markDate;
    }

    public int getRightsId() {
        return rightsId;
    }

    public void setRightsId(int rightsId) {
        this.rightsId = rightsId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSpendScore() {
        return spendScore;
    }

    public void setSpendScore(int spendScore) {
        this.spendScore = spendScore;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public long getMark_date() {
        return markDate;
    }

    public void setMark_date(long mark_date) {
        this.markDate = mark_date;
    }

    public int getRights_id() {
        return rightsId;
    }

    public void setRights_id(int rights_id) {
        this.rightsId = rights_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public OrderRightModel() {
        super();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "OrderRightModel{" +
                "cover='" + cover + '\'' +
                ", markDate=" + markDate +
                ", rightsId=" + rightsId +
                ", status=" + status +
                ", id=" + id +
                ", spendScore=" + spendScore +
                ", level=" + level +
                ", name='" + name + '\'' +
                ", levelName='" + levelName + '\'' +
                '}';
    }
}
