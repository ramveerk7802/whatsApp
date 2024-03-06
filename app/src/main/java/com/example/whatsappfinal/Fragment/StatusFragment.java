package com.example.whatsappfinal.Fragment;

import android.app.Dialog;
import android.os.Bundle;


import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.whatsappfinal.Adaptor.StatusAdaptor;
import com.example.whatsappfinal.Model.StatusModel;
import com.example.whatsappfinal.R;
import com.example.whatsappfinal.databinding.FragmentStatusBinding;

import java.util.ArrayList;


public class StatusFragment extends Fragment {

    FragmentStatusBinding binding;
    private ArrayList<StatusModel> list;
    private Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStatusBinding.inflate(inflater,container,false);

        Toolbar toolbar = binding.getRoot().findViewById(R.id.statusFragmentToolbar);

        toolbar.setOnMenuItemClickListener(item -> {
            if(item.getItemId()==R.id.status_privacy_menu){
                Toast.makeText(getContext(),"Clicked Status Privacy",Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });
        list= new ArrayList<>();
        list.add(new StatusModel("Rohit kumar"));
        list.add(new StatusModel("Amit"));
        final StatusAdaptor adaptor = new StatusAdaptor(getContext(),list);
        binding.statusRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.statusRecycleView.setAdapter(adaptor);
        return binding.getRoot();
    }



}