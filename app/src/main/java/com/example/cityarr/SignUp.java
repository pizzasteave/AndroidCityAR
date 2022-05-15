package com.example.cityarr;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
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
        EditText firstName = findViewById(R.id.gov);
        EditText mail=findViewById(R.id.min);
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
                pairs[0] = new Pair<View, String>(firstName, "EmailAdress");
                pairs[1] = new Pair<View, String>(mail, "mail");
                pairs[2] = new Pair<View, String>(pass, "password");
                pairs[3] = new Pair<View, String>(repass, "password2");
                pairs[4] = new Pair<View, String>(phone, "telephone");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this, pairs);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }

            }
        });

    }
}