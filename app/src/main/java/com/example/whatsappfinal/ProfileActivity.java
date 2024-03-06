package com.example.whatsappfinal;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.whatsappfinal.Model.UserModel;
import com.example.whatsappfinal.databinding.ActivityProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseStorage storage;
    private Uri profilePath;
    private final int GALLERY_REQUEST_CODE=100;
    private final ActivityResultLauncher<String> galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
            result ->{
                    if(result!=null){

                        binding.profilePic.setImageURI(result);
                        profilePath = result;
                    }

            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        database =FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        binding.profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGAllery();
            }
        });

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.edtName.getText().toString().isEmpty()){
                    Toast.makeText(ProfileActivity.this,"Enter the name",Toast.LENGTH_SHORT).show();
                }
                else {
                    uploadData();
                }
            }
        });
    }

    public void openGAllery(){
        galleryLauncher.launch("image/*");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_REQUEST_CODE && resultCode==RESULT_OK && data.getData()!=null && data!=null){
            binding.profilePic.setImageURI(data.getData());
        }
    }
    public void uploadData(){
        Date date = new Date();
        Long time = date.getTime();

        StorageReference reference = storage.getReference().child("Profile").child(time.toString());
        reference.putFile(profilePath)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    uploadInfo(uri.toString());
                                }
                            });
                        }
                    }
                });

    }
    public void uploadInfo(String imageUrl){
        //FirebaseUser curr = mAuth.getCurrentUser();
        UserModel user =new UserModel();
        user.setName(binding.edtName.getText().toString());
        user.setNumber(getIntent().getStringExtra("number"));
//        user.setNumber(mAuth.getCurrentUser().getPhoneNumber());
        user.setPic(imageUrl);
        user.setUserUid(mAuth.getUid().toString());
        database.getReference().child("UserTable").child(mAuth.getUid().toString()).setValue(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(ProfileActivity.this,"Data inserted",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                                finish();
                            }
                        });

    }
}

