package com.example.whatsappfinal.ViewType;

public class ChatItemViewType {
    public static final int OUTGOING_TEXT_MSG_LAYOUT=0;
    public static final int INCOMING_TEXT_MSG_LAYOUT=1;
    public static final int OUTGOING_IMAGE_LAYOUT=3;
    public static final int INCOMING_IMAGE_LAYOUT=4;

    private int viewType;
    private String text;
    public int data;

    public ChatItemViewType(int viewType, String text) {
        this.viewType = viewType;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
