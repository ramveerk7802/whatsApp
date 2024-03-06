package com.example.whatsappfinal.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whatsappfinal.Adaptor.UserRecycleAdaptor;
import com.example.whatsappfinal.Model.UserModel;
import com.example.whatsappfinal.R;
import com.example.whatsappfinal.databinding.FragmentChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;


public class ChatFragment extends Fragment {



    FragmentChatBinding binding;
    FirebaseDatabase database;
    LinkedList<UserModel> list;
    public ChatFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentChatBinding.inflate(inflater,container,false);
        database =FirebaseDatabase.getInstance();
        list = new LinkedList<>();

        UserRecycleAdaptor adaptor = new UserRecycleAdaptor(getContext(),list);
        binding.userRecycleView.setAdapter(adaptor);
        binding.userRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));

        database.getReference().child("UserTable").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    UserModel user = new UserModel();
                    user.setName(dataSnapshot.child("name").getValue(String.class));
                    user.setPic(dataSnapshot.child("pic").getValue(String.class));
                    user.setMsg(dataSnapshot.child("msg").getValue(String.class));
                    user.setUserUid(dataSnapshot.child("userUid").getValue(String.class));
                    if(user.getUserUid()!=FirebaseAuth.getInstance().getUid())
                        list.add(user);
                }

                adaptor.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return  binding.getRoot();

    }
}