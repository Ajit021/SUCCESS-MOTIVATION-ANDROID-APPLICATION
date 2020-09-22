package com.ajit.android.posting;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MainActivity extends AppCompatActivity {

    RecyclerView mrecyleview;
    FirebaseDatabase mfirebasedatabase;
    DatabaseReference mRef;
    LinearLayoutManager mlinear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(com.ajit.android.posting.R.layout.activity_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //actionBar.setTitle("post list");
        //checking

        mlinear=new LinearLayoutManager(this);
        mlinear.setReverseLayout(true);
        mlinear.setStackFromEnd(true);
        mrecyleview =findViewById(com.ajit.android.posting.R.id.recyclerView);
        mrecyleview.setHasFixedSize(true);
        mrecyleview.setLayoutManager(mlinear);
        mfirebasedatabase = FirebaseDatabase.getInstance();
        mRef=mfirebasedatabase.getReference("Data");
        mRef.keepSynced(true);

    }

    @Override
    protected void onStart() {
        super.onStart();
       // Query sortpost= mRef.orderByChild("coounter");
        FirebaseRecyclerAdapter<Model,ViewHolder> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<Model, ViewHolder>(Model.class, com.ajit.android.posting.R.layout.m,ViewHolder.class,mRef) {
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, Model model, int position) {
                        viewHolder.setDetails(MainActivity.this,model.getTitle(),model.getImage());
                    }
                };
        mrecyleview.setAdapter(firebaseRecyclerAdapter);
    }
}
