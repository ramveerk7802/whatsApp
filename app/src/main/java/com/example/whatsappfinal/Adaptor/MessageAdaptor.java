package com.example.whatsappfinal.Adaptor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappfinal.Model.MessageModel;
import com.example.whatsappfinal.R;
import com.example.whatsappfinal.ViewType.ChatItemViewType;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class MessageAdaptor extends RecyclerView.Adapter{

    private final  ArrayList<MessageModel> list;

    private final Context context;
    private final String receiverId;

    private static final int INCOMING_VIEW_TYPE=100;
    private  static final int OUTGOING_VIEW_TYPE=200;


    public MessageAdaptor(Context context,ArrayList<MessageModel> list,String receiverId){
        this.context=context;
        this.list= list;
        this.receiverId=receiverId;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==INCOMING_VIEW_TYPE){
            View view = LayoutInflater.from(context).inflate(R.layout.incoming_layout,parent,false);
            return new IncomingViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(context).inflate(R.layout.outgoing_layout,parent,false);
            return new OutgoingViewHolder(view);
        }



    }

    @Override
    public int getItemViewType(int position) {

        if(list.get(position).getCurrUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            return OUTGOING_VIEW_TYPE;
        }
        else {
            return INCOMING_VIEW_TYPE;
        }
//        return INCOMING_VIEW_TYPE;



    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel model = list.get(position);
//        ((IncomingViewHolder)holder).incomingMsg.setText(FirebaseAuth.getInstance().getUid());
        if(holder.getClass()== IncomingViewHolder.class) {
            SimpleDateFormat time = new SimpleDateFormat("HH:mm: a");
            String timeData = time.format(model.getTimeStamp());
           ((IncomingViewHolder) holder).incomingMsg.setText(model.getMessage());
//             ((IncomingViewHolder) holder).incomingMsg.setText(FirebaseAuth.getInstance().getCurrentUser().getUid());
           ((IncomingViewHolder) holder).incomingTime.setText(timeData);
        }
        else{
            SimpleDateFormat time = new SimpleDateFormat("HH:mm: a");
            String timeData = time.format(model.getTimeStamp());
            ((OutgoingViewHolder)holder).outgoingMsg.setText(model.getMessage());
//            ((OutgoingViewHolder)holder).outgoingMsg.setText(FirebaseAuth.getInstance().getCurrentUser().getUid());
            ((OutgoingViewHolder) holder).outgoingTime.setText(timeData);
        }


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class OutgoingViewHolder extends RecyclerView.ViewHolder{
        AppCompatTextView outgoingMsg,outgoingTime;
        public OutgoingViewHolder(@NonNull View itemView) {
            super(itemView);
            outgoingMsg = itemView.findViewById(R.id.txtOutgoingMsg);
            outgoingTime = itemView.findViewById(R.id.txtOutgoingTime);
        }
    }
    public static class IncomingViewHolder extends RecyclerView.ViewHolder{
        AppCompatTextView incomingMsg;
        AppCompatTextView incomingTime;
        public IncomingViewHolder(@NonNull View itemView) {
            super(itemView);
            incomingMsg = itemView.findViewById(R.id.txtIncomingMsg);
            incomingTime = itemView.findViewById(R.id.txtIncomingTime);
        }
    }
}

