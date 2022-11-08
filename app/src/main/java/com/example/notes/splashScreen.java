package com.example.notes;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class splashScreen extends AppCompatActivity {
    MediaPlayer mysong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mysong=MediaPlayer.create(splashScreen.this,R.raw.song);
        mysong.start();

//        handler class to delay the intent code this is parallel threading or Asynchonization

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//  This work after time is given completed 40000
                Intent iNext = new Intent(splashScreen.this, MainActivity.class);
                startActivity(iNext);
                mysong.release();
                finish();

            }
        }, 3000);
    }
}