package com.mobile.maxmoneynewapplication.Activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mobile.maxmoneynewapplication.Activity.ForeignExchangeDetailss.ForeignMapsActivity;
import com.mobile.maxmoneynewapplication.Activity.Introduction.IntroScreenActivity;
import com.mobile.maxmoneynewapplication.Common.TypeFaceClass;
import com.mobile.maxmoneynewapplication.R;

public class SplashScreen extends AppCompatActivity {
    TextView textView_title1,textView_title2;
    ProgressBar progressBar;
    ImageView imageView_logo;

    private int progressStatus = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //declare
        progressBar = findViewById(R.id.progressBar);
        textView_title1 = findViewById(R.id.textView_title1);
        textView_title2 = findViewById(R.id.textView_title2);
        imageView_logo = findViewById(R.id.imageView_logo);

        //change font
        TypeFaceClass.setTypeFaceTextView(textView_title1,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_title2,getApplicationContext());


        progressBarFunctionality();

    }

    private void progressBarFunctionality() {
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1;
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                        }
                    });
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (progressStatus == 100) {
                    Intent next = new Intent(getApplicationContext(),IntroScreenActivity.class);
                    startActivity(next);
                }
            }
        }).start();
    }
}
