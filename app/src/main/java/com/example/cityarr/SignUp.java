package com.example.cityarr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        //get references
        EditText firstName = findViewById(R.id.EmailAdress);
        EditText mail=findViewById(R.id.mail);
        EditText pass=findViewById(R.id.password);
        EditText repass=findViewById(R.id.password2);
        EditText phone=findViewById(R.id.telephone);

       /* Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String email = extras.getString("email");
            System.out.println(email);
            String password = extras.getString("password");
            System.out.println(password);
            t.setText(email);
        }
*/

        Button btn=findViewById(R.id.repass);

        //instance of firebase auth
        FirebaseAuth fAuth=FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String FirstName=firstName.getText().toString().trim();
                String Email=mail.getText().toString().trim();
                String Password=pass.getText().toString().trim();
                String Repass=repass.getText().toString().trim();
                String Phone=phone.getText().toString().trim();


                if(TextUtils.isEmpty(FirstName)){
                    firstName.setError("first name is Required");
                    return;
                }

                if(TextUtils.isEmpty(Email)){
                    mail.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(Password)){
                    pass.setError("password is Required");
                    return;
                }
                if(TextUtils.isEmpty(Repass)){
                    repass.setError("confirm password is Required");
                    return;
                }

                if(Password.length()<6){
                    pass.setError("password must be greater than 6");
                    return;
                }

                if(TextUtils.isEmpty(Phone)){
                    phone.setError("Phone number is Required");
                    return;
                }
                //if(password!=Rpass){
                //    pass.setError("password and repassword are not the same");
                //    return;
                //}

                fAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            fAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(SignUp.this, "User Created validate your Email", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(),LogIn.class));
                                    }
                                    else{
                                        Toast.makeText(SignUp.this, "Error !"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                        else{
                            Toast.makeText(SignUp.this, "Error !"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }
}