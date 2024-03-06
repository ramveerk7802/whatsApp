package com.example.whatsappfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.whatsappfinal.databinding.ActivityVerifyOtpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;
import android.Manifest;
public class VerifyOtpActivity extends AppCompatActivity {

    ActivityVerifyOtpBinding binding;
    private FirebaseAuth mAuth;
    private String verificationId;
    private ProgressDialog dialog;
    private ProgressBar progressBar;
    private FirebaseDatabase database;
    private static final int SMS_PERMISSION_REQUEST_CODE = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        if(ContextCompat.checkSelfPermission(VerifyOtpActivity.this,(Manifest.permission.RECEIVE_SMS))!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(VerifyOtpActivity.this,new String[]{Manifest.permission.RECEIVE_SMS},SMS_PERMISSION_REQUEST_CODE);
        }
        else{
            Toast.makeText(VerifyOtpActivity.this,"initiate",Toast.LENGTH_SHORT).show();
            initiatePhoneNumberVerification();
        }


        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(binding.edtOtp.getText().toString()))
                    Toast.makeText(VerifyOtpActivity.this,"Enter the OTP",Toast.LENGTH_SHORT).show();
                else {
                    if(verificationId!=null) {
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, binding.edtOtp.getText().toString());
                        signInWithAuthPhone(credential);
                    }
                    else {
                        Toast.makeText(VerifyOtpActivity.this, "Verification ID is null", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void initiatePhoneNumberVerification(){
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading");
        dialog.setMessage("Please wait ...");
        dialog.setCancelable(false);
        dialog.show();

        String number = getIntent().getStringExtra("sendMobileNumber");
        if(TextUtils.isEmpty(number)){
            binding.txtNumberShow.setText("number not fetch");
        }
        else {
            binding.txtNumberShow.setText("Verification Code will be sent on number ********" + number.substring(8, 10));
        }


        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+number)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                dialog.dismiss();
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                dialog.dismiss();
                                Toast.makeText(VerifyOtpActivity.this, "Verification failed: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }


                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                verificationId = s;
                                dialog.dismiss();
                            }
                        })
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }

    public void signInWithAuthPhone(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            String id = task.getResult().getUser().getUid();
                            Intent profile = new Intent(VerifyOtpActivity.this,ProfileActivity.class);
                            profile.putExtra("UserUid",id);
                            startActivity(profile);
                            finish();
                        }
                        else{
                            dialog.dismiss();
                            Toast.makeText(VerifyOtpActivity.this, "Authentication failed: " + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==SMS_PERMISSION_REQUEST_CODE ){
            dialog.dismiss();
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED ){
                initiatePhoneNumberVerification();
            }
            else {
                Toast.makeText(VerifyOtpActivity.this,"SMS Denied",Toast.LENGTH_SHORT).show();
            }
        }
    }
}




