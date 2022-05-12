package com.example.cityarr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {
    private FirebaseAuth mAuth;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();
        Button btn=findViewById(R.id.repass);
        EditText email=findViewById(R.id.EmailAdress);
        EditText password=findViewById(R.id.telephone);
        TextView text=findViewById(R.id.textView);
        FrameLayout frame=findViewById(R.id.frame);
        //frame.setVisibility(View.INVISIBLE);
        TextView text2=findViewById(R.id.textView3);
        sharedpreferences = getSharedPreferences("Data_Session", Context.MODE_PRIVATE);
        String settedEmail=sharedpreferences.getString("MailKey","e");
        String settedPass=sharedpreferences.getString("PassKey","e");
        mAuth.signInWithEmailAndPassword(settedEmail,settedPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LogIn.this, "Welcome back!", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(LogIn.this,Menu.class);
                    startActivity(i);
                }else{
                    frame.setVisibility(View.INVISIBLE);
                    btn.setVisibility(View.VISIBLE);
                }
            }
        });

        SharedPreferences.Editor editor = sharedpreferences.edit();

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogIn.this,SignUp.class));
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emaill=email.getText().toString().trim();
                String pass=password.getText().toString();
                mAuth.signInWithEmailAndPassword(emaill,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            if(mAuth.getCurrentUser().isEmailVerified()){
                                Toast.makeText(LogIn.this, "Logged in!", Toast.LENGTH_SHORT).show();

                                Intent i=new Intent(LogIn.this,Menu.class);
                                editor.putString("MailKey",emaill);
                                editor.putString("PassKey",pass);
                                editor.commit();
                                editor.apply();

                                i.putExtra("email",emaill);
                                i.putExtra("password",pass);
                                startActivity(i);

                            }else {
                                Toast.makeText(LogIn.this, "Please Verify your Email", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(LogIn.this, "error!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogIn.this,ResetPassword.class));
            }
        });



    }
}