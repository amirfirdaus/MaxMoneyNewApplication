package com.mobile.maxmoneynewapplication.Activity.CameraTwo.VideoRecording;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.mobile.maxmoneynewapplication.R;

public class VideoBeforeActivity extends AppCompatActivity {
    Button button_video;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_before);

        button_video = findViewById(R.id.button_video);

    }
}
