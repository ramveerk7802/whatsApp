package com.example.whatsappfinal.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.whatsappfinal.MainActivity;
import com.example.whatsappfinal.R;
import com.example.whatsappfinal.databinding.ActivitySingInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {

    ActivitySingInBinding binding;
    private FirebaseAuth auth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivitySingInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null){
            startActivity(new Intent(SignInActivity.this, MainActivity.class));
            finish();
        }
        binding.txtAccount.setOnClickListener(v -> {
            startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
            finish();
        });
        binding.btnSignIn.setOnClickListener(v -> {
            if(TextUtils.isEmpty(binding.edtEmail.getText().toString().trim())|| TextUtils.isEmpty(binding.edtPassword.getText().toString().trim())){
                Toast.makeText(this,"Fill the credential detail",Toast.LENGTH_LONG).show();
            }
            else{
                signInFunction();
            }
        });



    }

    private void signInFunction(){
        auth.signInWithEmailAndPassword(binding.edtEmail.getText().toString().trim(),binding.edtPassword.getText().toString().trim())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(SignInActivity.this,MainActivity.class));
                            finish();
                        }
                        else {Toast.makeText(SignInActivity.this,task.getException().toString(),Toast.LENGTH_SHORT).show();}
                    }
                });
    }
}