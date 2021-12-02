package com.example.finalassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.finalassignment.ViewModel.DataViewModel;

import java.util.Objects;

public class SplashScreen extends AppCompatActivity {

    ImageView exam_icon;
    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        // hiding action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        exam_icon = findViewById(R.id.exam_icon);
        exam_icon.startAnimation(AnimationUtils.loadAnimation(this,R.anim.slide));

         runnable = ()->{
             startActivity(new Intent(SplashScreen.this,Home.class));
             finish();
         };

        if(savedInstanceState == null){

            handler = new Handler();
            handler.postDelayed(runnable, 1500);

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(runnable);
    }



}