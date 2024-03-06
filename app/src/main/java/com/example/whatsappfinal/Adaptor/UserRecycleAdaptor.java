package com.example.whatsappfinal.Adaptor;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappfinal.ChatDetailActivity;
import com.example.whatsappfinal.Model.UserModel;
import com.example.whatsappfinal.R;
import com.example.whatsappfinal.databinding.UserLayoutBinding;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;


public class UserRecycleAdaptor extends RecyclerView.Adapter<UserRecycleAdaptor.ViewHolder>{

    LinkedList<UserModel>list;
    Context context;
    UserLayoutBinding binding;
    Dialog dialog;
    public UserRecycleAdaptor(Context context,LinkedList<UserModel> list){
        this.list=list;
        this.context= context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = UserLayoutBinding.inflate(inflater,parent,false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         UserModel model= list.get(position);
         if(model.getUserUid().equals(FirebaseAuth.getInstance().getUid())) {
             binding.chatPersonName.setText(model.getName()+" (You)");
         }
         else{
             binding.chatPersonName.setText(model.getName());
             }
        binding.chatPersonMsg.setText(model.getMsg());
        Picasso.get().load(model.getPic()).placeholder(R.drawable.b).into(binding.imgPerson);


        binding.imgPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog =new Dialog(context);
                dialog.setContentView(R.layout.img_click_layout);
                dialog.show();

                AppCompatImageView imageView = dialog.findViewById(R.id.imgDialog);
                AppCompatImageButton btnMessage = dialog.findViewById(R.id.btnMsg);
                AppCompatImageButton btnAudio = dialog.findViewById(R.id.btnAudioCall);
                AppCompatImageButton btnVideo = dialog.findViewById(R.id.btnVideoCall);
                AppCompatImageButton btnInfo = dialog.findViewById(R.id.btnInfo);

                Picasso.get().load(model.getPic()).placeholder(R.drawable.b).into(imageView);

                btnMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context,"Clicked on btn message",Toast.LENGTH_SHORT).show();
                    }
                });

                btnAudio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context,"Clicked on btn audio",Toast.LENGTH_SHORT).show();
                    }
                });

                btnVideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context,"Clicked on btn video",Toast.LENGTH_SHORT).show();
                    }
                });

                btnInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context,"Clicked on btn Info",Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chatDetail = new Intent(context,ChatDetailActivity.class);
                chatDetail.putExtra("userName",model.getName());
                chatDetail.putExtra("userPic",model.getPic());
                chatDetail.putExtra("userId",model.getUserUid());
                context.startActivity(chatDetail);
            }
        });




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
