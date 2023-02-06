package com.m2u.pushnotif.consumer.models;

public class PushModel {

    private String collapseKey;
    private String appId;
    private String gcifIds;
    private String title;
    private String msg;
    private AdditionalModel additionalData;

    public String getCollapseKey() {
        return collapseKey;
    }

    public void setCollapseKey(String collapseKey) {
        this.collapseKey = collapseKey;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getGcifIds() {
        return gcifIds;
    }

    public void setGcifIds(String gcifIds) {
        this.gcifIds = gcifIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public AdditionalModel getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(AdditionalModel additionalData) {
        this.additionalData = additionalData;
    }
}
