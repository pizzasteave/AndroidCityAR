package com.example.cityarr;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class SignUp2 extends AppCompatActivity {


    //Variables

    TextView titleText, slideText;
    Button signUpBtn ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup_2);


        //Hooks
        signUpBtn = findViewById(R.id.go);


        titleText = findViewById(R.id.gov);
        slideText = findViewById(R.id.min);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SignUp.pairs



            }
        });




    }






}
