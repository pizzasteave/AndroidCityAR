package com.example.cityarr;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cityarr.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUp2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    //Variables

    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    Spinner spinnerG;
    Spinner spinnerM;

    private String TAG = "SignUp2";
    TextView titleText, slideText;
    Button signUpBtn ;
    private String name,email,password,telephone;

    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_2);

        InitView();
        InitSpinner();


        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = (String) SignUp.pairs[0].first;
                email = (String) SignUp.pairs[1].first;
                password = (String) SignUp.pairs[2].first;
                telephone = (String) SignUp.pairs[3].first;

                String gov_data = spinnerG.getSelectedItem().toString();
                String municip_data = spinnerM.getSelectedItem().toString();

                user = new User(name,email,telephone,municip_data,gov_data);
                registerUser();
            }
        });

    }

    private void InitSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Gov_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerG.setAdapter(adapter);
        spinnerG.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.Mun_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerM.setAdapter(adapter2);
        spinnerM.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
    }

    private void InitView() {

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("Users");
        mAuth = FirebaseAuth.getInstance();

        spinnerG = findViewById(R.id.gov);
        spinnerM = findViewById(R.id.min);

        signUpBtn = findViewById(R.id.go);
        titleText = findViewById(R.id.name);
        slideText = findViewById(R.id.email);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public void registerUser() {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(SignUp2.this, "User Created validate your Email", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignUp2.this,LogIn.class));
                                    }
                                    else{
                                        Toast.makeText(SignUp2.this, "Error !"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUp2.this, "Error !"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    public void updateUI(FirebaseUser currentUser) {
        mDatabase.child(currentUser.getUid()).setValue(user); //adding user info to database
    }

}
