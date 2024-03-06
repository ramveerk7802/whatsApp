package com.example.whatsappfinal.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappfinal.Model.CallModel;
import com.example.whatsappfinal.R;
import com.example.whatsappfinal.databinding.CallLayoutBinding;

import java.util.LinkedList;

public class CallAdaptor extends RecyclerView.Adapter<CallAdaptor.ViewHolder>{
    CallLayoutBinding binding;
    LinkedList<CallModel> list;
    Context context;
    public  CallAdaptor(Context context,LinkedList<CallModel> list){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = CallLayoutBinding.inflate(inflater,parent,false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CallModel model = list.get(position);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
