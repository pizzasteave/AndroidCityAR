package com.example.cityarr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Menu extends AppCompatActivity {

    private Button propositionBtn;
    private Button lespropositionBtn;
    private Button logOutBTN;
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        propositionBtn = findViewById(R.id.lesproposition);

        propositionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LesProposition.class));
            }
        });

        lespropositionBtn = findViewById(R.id.mesproposition);

        lespropositionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MesProposition.class));
            }
        });

        logOutBTN = findViewById(R.id.logOut);
        //log out and go back to login and clear shared pref
        logOutBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();

                startActivity(new Intent(Menu.this,LogIn.class));

                sharedpreferences = getSharedPreferences("Data_Session", Context.MODE_PRIVATE);
                sharedpreferences.edit().clear().commit();

            }
        });

    }


    }

