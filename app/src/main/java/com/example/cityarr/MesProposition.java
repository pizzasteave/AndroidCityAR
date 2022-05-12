package com.example.cityarr;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cityarr.Dialog.AddPropositionDialog;
import com.example.cityarr.entity.Proposition;
import com.example.cityarr.Adapter.AdapterProp;
import com.example.cityarr.touchListner.RecyclerTouchListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MesProposition extends AppCompatActivity implements AddPropositionDialog.AddPropositionCallBack{

    private AdapterProp mAdapter;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mAddPropositionBtn ;
    private List<Proposition> propositionsList  = new ArrayList<Proposition>();


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
        initEvent();
        initData();
    }


    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mAddPropositionBtn = findViewById(R.id.addPropositionBtn);
    }

    private void initEvent() {
        mAddPropositionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPropositionDialog dialog = new AddPropositionDialog(MesProposition.this,MesProposition.this, MesProposition.this);
                dialog.show();
            }
        });
    }

    private void initData() {

        mAdapter = new AdapterProp(getApplicationContext());
        mAdapter.addAll(propositionsList);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(MesProposition.this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Toast.makeText(MesProposition.this,"click" + position , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongClick(View view, int position) {

                Toast.makeText(MesProposition.this,"Longclick" + position , Toast.LENGTH_LONG).show();

            }
        }));

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
