package com.example.cityarr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        EditText mail=findViewById(R.id.EmailAdress);
        Button btn=findViewById(R.id.repass);
        mAuth = FirebaseAuth.getInstance();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=mail.getText().toString().trim();
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ResetPassword.this,"check your email we send the verification",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ResetPassword.this,LogIn.class));
                        }
                        else{
                            Toast.makeText(ResetPassword.this,"Try again something wrong happend!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}