package com.m2u.pushnotif.consumer.models;

public class JsonModel {
    private String content;

    public JsonModel() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String toString(){
        return content;
    }
}
