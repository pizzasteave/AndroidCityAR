package com.example.cityarr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUp extends AppCompatActivity {


    public static Pair[] pairs ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        //get references
        EditText firstName = findViewById(R.id.name);
        EditText mail=findViewById(R.id.email);
        EditText pass=findViewById(R.id.password);
        EditText repass=findViewById(R.id.password2);
        EditText phone=findViewById(R.id.telephone);


        Button btn=findViewById(R.id.go);


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
                // Check if email id is valid    or not
                if (!isEmailValid(Email)){
                    mail.setError("Not an Email");
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


                Intent intent = new Intent(getApplicationContext(), SignUp2.class);

                pairs = new Pair[4];
                pairs[0] = new Pair<String, String>(FirstName.toString(), "name");
                pairs[1] = new Pair<String, String>(Email.toString(), "email");
                pairs[2] = new Pair<String, String>(Password.toString(), "password");
                pairs[3] = new Pair<String, String>(Phone.toString(), "telephone");

                startActivity(intent);

            }
        });

    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}