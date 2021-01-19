package com.example.ruzun.betaqti;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class Setting extends AppCompatActivity {

    static boolean darkMode;
    static int fontSize;
    Button saveSettingButton;
    boolean clicked = false;

    SeekBar bar;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        bar = findViewById(R.id.seekbar);
        bar.setMax(20);
        bar.setMin(16);
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                fontSize = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        saveSettingButton = findViewById(R.id.save_setting);
        saveSettingButton.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {

                                                     if (clicked){
                                                         darkMode= true;
                                                         saveSettingButton.setBackgroundResource(R.drawable.round_button_dark_mode);
                                                         saveSettingButton.setTextAppearance(Setting.this, R.style.button_dark_mode);
                                                         Button b = findViewById(R.id.retain);
                                                         b.setBackgroundResource(R.drawable.round_button_dark_mode);
                                                         b.setTextAppearance(Setting.this, R.style.button_dark_mode);
                                                         findViewById(R.id.background1).setBackgroundResource(R.drawable.round_button_grey_dark);
                                                         findViewById(R.id.background2).setBackgroundResource(R.drawable.round_button_grey_dark);
                                                         findViewById(R.id.backgroundMain).setBackgroundColor(Color.rgb(59, 59, 66));
                                                         findViewById(R.id.backgroundMain).setBackgroundColor(Color.rgb(59, 59, 66));
                                                         TextView tx = findViewById(R.id.l1);
                                                         tx.setTextColor(Color.WHITE);
                                                         tx = findViewById(R.id.l2);
                                                         tx.setTextSize(fontSize);
                                                         tx.setTextColor(Color.WHITE);
                                                         tx = findViewById(R.id.l3);
                                                         tx.setTextSize(fontSize);

                                                         tx.setTextColor(Color.WHITE);
                                                         tx = findViewById(R.id.l4);
                                                         tx.setTextSize(fontSize);
                                                         tx.setTextColor(Color.WHITE);
                                                         tx = findViewById(R.id.l5);
                                                         tx.setTextSize(fontSize);
                                                         tx.setTextColor(Color.WHITE);
                                                         tx = findViewById(R.id.l6);
                                                         tx.setTextSize(fontSize);
                                                         tx.setTextColor(Color.WHITE);
                                                         tx = findViewById(R.id.l7);
                                                         tx.setTextSize(fontSize);
                                                         tx.setTextColor(Color.WHITE);
                                                         tx = findViewById(R.id.l8);
                                                         tx.setTextSize(fontSize);
                                                         tx.setTextColor(Color.WHITE);
                                                         tx.setTextSize(fontSize);
                                                     }

                                                 }});
    }

    public void clicked(View view){
        clicked = !clicked;
    }
}
