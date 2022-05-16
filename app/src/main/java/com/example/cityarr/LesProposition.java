package com.example.cityarr;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cityarr.Dialog.AddPropositionDialog;
import com.example.cityarr.entity.Proposition;
import com.example.cityarr.Adapter.AdapterProp;
import com.example.cityarr.touchListner.RecyclerTouchListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LesProposition extends AppCompatActivity implements AddPropositionDialog.AddPropositionCallBack{

    private AdapterProp mAdapter;
    private RecyclerView mRecyclerView;

    private List<Proposition> propositionsList ;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference mDatabaseRef2;

    // a static variable to get a reference of our application context
    public static Context contextOfApplication;
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }

    private Uri image ;
    private ImageView temppropositionPic;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //fragment pb
        contextOfApplication = getApplicationContext();

        setContentView(R.layout.mes_proposition);

        initView();

        initData();
    }


    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
    }


    private void initData() {
        mAdapter = new AdapterProp(getApplicationContext());
        mDatabaseRef  = FirebaseDatabase.getInstance().getReference("Proposition");
        mDatabaseRef2  = FirebaseDatabase.getInstance().getReference("UserPP");

        propositionsList = new ArrayList<Proposition>() ;

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Proposition proposition = postSnapshot.getValue(Proposition.class);

                    propositionsList.add(proposition);
                }

                mAdapter.addAll(propositionsList);
                propositionsList.clear();
                LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(LesProposition.this);
                mRecyclerView.setLayoutManager(mLinearLayoutManager);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                        Toast.makeText(LesProposition.this,"click" + position , Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                        Toast.makeText(LesProposition.this,"Longclick" + position , Toast.LENGTH_LONG).show();

                    }
                }));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onImageAdded(Intent intent, ImageView propositionPic){
        startActivityForResult(intent,1);
        temppropositionPic = propositionPic ;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            image = data.getData();
            temppropositionPic.setImageURI(image);

            AddPropositionDialog.image = image;
        }
    }

    @Override
    public void onPropositionAdded(Proposition proposition) {
        propositionsList.add(proposition);
        mAdapter.add(proposition);
    }


}
