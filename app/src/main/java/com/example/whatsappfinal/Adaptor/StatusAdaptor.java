package com.example.whatsappfinal.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappfinal.Model.StatusModel;
import com.example.whatsappfinal.R;
import com.example.whatsappfinal.databinding.StatusLayoutBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class StatusAdaptor extends RecyclerView.Adapter<StatusAdaptor.ViewHolder>{

    StatusLayoutBinding binding;
    private Context context;
    private ArrayList<StatusModel> statusList;

    public StatusAdaptor(Context context,ArrayList<StatusModel> statusList) {
        this.statusList = statusList;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = StatusLayoutBinding.inflate(inflater,parent,false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StatusModel model = statusList.get(position);
        binding.statusPersonName.setText(model.getName());
        binding.statusTime.setText("12:00 PM");

    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
