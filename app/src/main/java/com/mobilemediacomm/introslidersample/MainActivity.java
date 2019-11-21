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

        // observe skip button click
        introSlider.addOnSkipClickListener(() -> finish());


        introSlider.addPage(
                "RTL Intro Slider",
                getString(R.string.rtl_detail),
                ContextCompat.getColor(this, R.color.intro_text_color1),
                ContextCompat.getColor(this, R.color.intro_background_color1),
                R.drawable.ic_call_merge_black_24dp
        );

        introSlider.addPage(
                "",
                getString(R.string.enjoy),
                ContextCompat.getColor(this, R.color.intro_text_color1),
                ContextCompat.getColor(this, R.color.intro_background_color2),
                R.drawable.ic_sentiment_satisfied_black_24dp
        );
    }
}
