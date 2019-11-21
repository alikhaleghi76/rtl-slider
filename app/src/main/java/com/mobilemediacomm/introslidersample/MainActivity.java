package com.mobilemediacomm.introslidersample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;

import ali.khaleghi.rtlintroslider.RTLIntroSlider;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RTLIntroSlider introSlider = findViewById(R.id.introSlider);

        introSlider.addPage(
                "TITLE1",
                "DETAIL1",
                ContextCompat.getColor(this, R.color.intro_text_color1),
                ContextCompat.getColor(this, R.color.intro_background_color1),
                R.drawable.ic_launcher_foreground
        );

        introSlider.addPage(
                "TITLE 2",
                "DETAIL 2",
                ContextCompat.getColor(this, R.color.intro_text_color1),
                ContextCompat.getColor(this, R.color.intro_background_color2),
                -1
        );
    }
}
