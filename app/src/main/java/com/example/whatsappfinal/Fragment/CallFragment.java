package com.example.whatsappfinal.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whatsappfinal.Adaptor.CallAdaptor;
import com.example.whatsappfinal.Model.CallModel;
import com.example.whatsappfinal.R;
import com.example.whatsappfinal.databinding.FragmentCallBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.LinkedList;


public class CallFragment extends Fragment {

    FragmentCallBinding binding;
    private FirebaseDatabase database;
    LinkedList<CallModel> list;
    public CallFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCallBinding.inflate(inflater,container,false);
        list = new LinkedList<>();
        database = FirebaseDatabase.getInstance();
        list.add(new CallModel("Vishal","Video","A",142455144));
        list.add(new CallModel("Rohit","Video","A",142455144));
        list.add(new CallModel("Sudip","Video","A",142455144));
        CallAdaptor adaptor = new CallAdaptor(getContext(),list);
        binding.callRecycleView.setAdapter(adaptor);
        binding.callRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));




        return binding.getRoot();
    }
}