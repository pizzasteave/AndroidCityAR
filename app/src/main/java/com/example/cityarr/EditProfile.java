package com.example.cityarr;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cityarr.Dialog.AddPropositionDialog;
import com.google.firebase.auth.FirebaseAuth;

public class EditProfile extends AppCompatActivity {

    private Button logOutBTN;
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

       InitView();
       InitEvent();
    }

    private void InitView() {
            logOutBTN = findViewById(R.id.logOut);
    }


    private void InitEvent() {
        //log out and go back to login and clear shared pref
        logOutBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();

                startActivity(new Intent(EditProfile.this,LogIn.class));

                sharedpreferences = getSharedPreferences("Data_Session", Context.MODE_PRIVATE);
                sharedpreferences.edit().clear().commit();

            }
        });
    }
}
