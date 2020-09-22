package com.ajit.android.posting;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by this pc on 3/21/2020.
 */

public class posting extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
