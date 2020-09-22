package com.ajit.android.posting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by this pc on 8/26/2020.
 */

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2500;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {

                startActivity(new Intent(SplashScreen.this,MainActivity.class));
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }



       // startActivity(new Intent(this,MainActivity.class));
    }

