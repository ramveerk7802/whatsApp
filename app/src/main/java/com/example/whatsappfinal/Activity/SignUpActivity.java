package com.example.whatsappfinal.Activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.whatsappfinal.MainActivity;
import com.example.whatsappfinal.Model.UserModel;
import com.example.whatsappfinal.R;
import com.example.whatsappfinal.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Date;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private FirebaseStorage storage;
    private ProgressBar progressBar;
    private Uri imgUrl;
    private static final int GALLERY_REQUEST_CODE=1001;
    ActivityResultLauncher<String> galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),result->{
        if(result!=null) {
            Picasso.get().load(result).placeholder(R.drawable.b).into(binding.imgProfile);
            imgUrl =result;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        database  = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        binding.txtAlreadyAccount.setOnClickListener(v -> {
            startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
            finish();
        });

        binding.imgProfile.setOnClickListener(v -> {
            openGallery();
        });


        binding.btnSignUp.setOnClickListener(v -> {
            binding.pb.setVisibility(View.VISIBLE);
            binding.btnSignUp.setVisibility(View.INVISIBLE);
            if(TextUtils.isEmpty(binding.edtName.getText().toString()) || TextUtils.isEmpty(binding.edtEmail.getText().toString())
            || TextUtils.isEmpty(binding.edtPassword.getText().toString()) || TextUtils.isEmpty(binding.edtConfirmPassword.getText().toString())){
                Toast.makeText(this,"All fields are required",Toast.LENGTH_LONG).show();
                binding.btnSignUp.setVisibility(View.VISIBLE);
                binding.pb.setVisibility(View.INVISIBLE);

            }
            else if(binding.edtEmail.getText().toString().length()<12){
                Toast.makeText(this,"Email address is short !!",Toast.LENGTH_SHORT).show();
                binding.btnSignUp.setVisibility(View.VISIBLE);
                binding.pb.setVisibility(View.INVISIBLE);
            } else if (binding.edtPassword.getText().toString().trim().length()<7) {
                Toast.makeText(this,"8 character Minimum required for Password",Toast.LENGTH_SHORT).show();
                binding.edtPassword.setText("");
                binding.edtConfirmPassword.setText("");
                binding.btnSignUp.setVisibility(View.VISIBLE);
                binding.pb.setVisibility(View.INVISIBLE);
            }
            else {
                if(binding.edtPassword.getText().toString().trim().equals(binding.edtConfirmPassword.getText().toString().trim())){
                    binding.btnSignUp.setVisibility(View.INVISIBLE);
                    binding.pb.setVisibility(View.VISIBLE);
                    auth.createUserWithEmailAndPassword(binding.edtEmail.getText().toString().trim(),binding.edtConfirmPassword.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        uploadData();


                                    }else{
                                        binding.btnSignUp.setVisibility(View.VISIBLE);
                                        binding.pb.setVisibility(View.INVISIBLE);
                                        Toast.makeText(SignUpActivity.this,task.getException().toString(),Toast.LENGTH_LONG).show();
                                    }
                                }
                            });




                }
                else {
                    binding.btnSignUp.setVisibility(View.VISIBLE);
                    binding.pb.setVisibility(View.INVISIBLE);
                    Toast.makeText(this,"Password does not match",Toast.LENGTH_SHORT).show();
                }
            }
        });







    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_REQUEST_CODE && resultCode==RESULT_OK && data!=null){
            Picasso.get().load(data.getData()).into(binding.imgProfile);
        }
    }

    private void uploadData(){
        Date date = new Date();
        Long time = date.getTime();
       StorageReference reference = storage.getReference().child("Profile").child(time.toString());
       reference.putFile(imgUrl)
               .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                       if(task.isSuccessful()){
                           reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                               @Override
                               public void onSuccess(Uri uri) {
                                   uploadInfo(uri);
                               }
                           });
                       }
                   }
               });
    }
    private void openGallery(){
        galleryLauncher.launch("image/*");
    }
    private void uploadInfo(Uri imgUrl){
        UserModel model = new UserModel();
        model.setName(binding.edtName.getText().toString().trim());
        model.setEmail(binding.edtEmail.getText().toString().trim());
        model.setPassword(binding.edtConfirmPassword.getText().toString().trim());
        model.setPic(imgUrl.toString());
        model.setUserUid(auth.getUid());
        database.getReference().child("UserTable").child(auth.getUid().toString()).setValue(model)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        binding.pb.setVisibility(View.INVISIBLE);
                        Toast.makeText(SignUpActivity.this,"Signed Up successfully ",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                        finish();
                    }
                });
    }

}