package com.example.whatsappfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.whatsappfinal.databinding.ActivitySendOtpBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SendOtpActivity extends AppCompatActivity {

    private ActivitySendOtpBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySendOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(SendOtpActivity.this,MainActivity.class));
            finish();
        }
//        binding.btnSend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(binding.edtMobileNumber.getText().toString().length()==10){
//                    Intent verify = new Intent(SendOtpActivity.this,VerifyOtpActivity.class);
//                    verify.putExtra("sendMobileNumber",binding.edtMobileNumber.getText().toString());
//                    startActivity(verify);
//                    finish();
//                }
//                else
//                    Toast.makeText(SendOtpActivity.this,"Enter the Valid Mobile number",Toast.LENGTH_SHORT).show();
//            }
//        });
        binding.btnSend.setOnClickListener(v -> {
            if(binding.edtMobileNumber.getText().toString().isEmpty() || binding.edtMobileNumber.getText().toString().length()!=10){
                Toast.makeText(SendOtpActivity.this,"Enter valid mobile number",Toast.LENGTH_LONG).show();
            }
            else {
                Intent verify = new Intent(SendOtpActivity.this,VerifyOtpActivity.class);
                verify.putExtra("sendMobileNumber",binding.edtMobileNumber.getText().toString());
                startActivity(verify);
                finish();
            }
        });
    }
}

