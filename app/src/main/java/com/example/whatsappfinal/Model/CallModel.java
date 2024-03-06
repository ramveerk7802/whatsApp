package com.example.whatsappfinal.Model;

public class CallModel {
    private String callerName,callType,callerImg;
    private long time;

    public CallModel(String callerName, String callType, String callerImg, long time) {
        this.callerName = callerName;
        this.callType = callType;
        this.callerImg = callerImg;
        this.time = time;
    }

    public String getCallerName() {
        return callerName;
    }

    public void setCallerName(String callerName) {
        this.callerName = callerName;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getCallerImg() {
        return callerImg;
    }

    public void setCallerImg(String callerImg) {
        this.callerImg = callerImg;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
