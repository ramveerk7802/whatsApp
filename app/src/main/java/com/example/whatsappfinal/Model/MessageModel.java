package com.example.whatsappfinal.Model;

public class MessageModel {
    private String message,messageId,currUid;
    private long timeStamp;

    public  MessageModel(){}

    public MessageModel(String message, String currUid, long timeStamp) {
        this.message = message;
        this.currUid = currUid;
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getCurrUid() {
        return currUid;
    }

    public void setCurrUid(String currUid) {
        this.currUid = currUid;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
