package com.example.whatsappfinal.Model;
public class StatusModel {
    private String name;
    long timeStamp;
    public StatusModel(){}
    public StatusModel(String name){
        this.name=name;
    }
    public void setName(String name){this.name=name;}
    public String getName(){return this.name;}

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
