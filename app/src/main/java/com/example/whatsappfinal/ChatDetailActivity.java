package com.example.whatsappfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.whatsappfinal.Adaptor.MessageAdaptor;
import com.example.whatsappfinal.Model.MessageModel;
import com.example.whatsappfinal.databinding.ActivityChatDetailBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChatDetailActivity extends AppCompatActivity {

    ActivityChatDetailBinding binding;
    private FirebaseAuth mAuth;
    ArrayList<MessageModel> list;
    private String senderId;
    private String senderRoom;
    private String receiverRoom;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        senderId=mAuth.getCurrentUser().getUid();
        setSupportActionBar(binding.toolbarChatDetail);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        final String receiverId = getIntent().getStringExtra("userId");
        Picasso.get().load(getIntent().getStringExtra("userPic")).placeholder(R.drawable.b).into(binding.imgUser);


        binding.txtUsername.setText(getIntent().getStringExtra("userName"));


         list = new ArrayList<>();

        final MessageAdaptor adaptor = new MessageAdaptor(ChatDetailActivity.this, list,receiverId);
        binding.msgRecycleView.setAdapter(adaptor);
        binding.msgRecycleView.setLayoutManager(new LinearLayoutManager(ChatDetailActivity.this));
        senderRoom = senderId + receiverId;
        receiverRoom = receiverId + senderId;

        database.getReference().child("Chats")
                .child(senderRoom)
                .child("message")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                list.clear();
                                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    MessageModel obj = dataSnapshot.getValue(MessageModel.class);
                                    list.add(obj);
                                }
//                                adaptor.notifyDataSetChanged();
                                adaptor.notifyItemInserted(list.size()-1);
                                binding.msgRecycleView.scrollToPosition(list.size()-1);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });




        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(binding.edtTxtMsg.getText().toString())){
                    String message = binding.edtTxtMsg.getText().toString();
                    long time = new Date().getTime();
                    MessageModel model = new MessageModel();
                    model.setMessage(message);
                    model.setCurrUid(senderId);
                    model.setTimeStamp(time);

                    String keys = database.getReference().push().getKey();
                    database.getReference().child("Chats")
                            .child(senderRoom)
                            .child("message")
                            .child(keys)
                            .setValue(model)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    database.getReference().child("Chats")
                                                    .child(receiverRoom)
                                                            .child("message")
                                                                    .child(keys)
                                                                            .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(ChatDetailActivity.this,"Message send successfully",Toast.LENGTH_SHORT).show();
                                                    binding.edtTxtMsg.setText("");
                                                }
                                            });

                                }
                            });
                }
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chat_detail_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.chatDetailVideoCallMenu){
            Toast.makeText(ChatDetailActivity.this,"Clicked on videoCall",Toast.LENGTH_SHORT).show();
        } else if (itemId==R.id.chatDetailAudioCallMenu) {
            Toast.makeText(ChatDetailActivity.this,"Clicked on Audio call",Toast.LENGTH_SHORT).show();
        } else if (itemId==R.id.chatDetailViewConatctMenu) {
            Toast.makeText(ChatDetailActivity.this,"Clicked on View Contact",Toast.LENGTH_SHORT).show();
        }
        else if (itemId==R.id.chatDetailWallpaperMenu) {
            Toast.makeText(ChatDetailActivity.this,"Clicked on Wallpaper ",Toast.LENGTH_SHORT).show();
        }
        // home button id find
//        if(itemId==android.R.id.home)
        else{
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
