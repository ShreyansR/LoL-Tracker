package com.example.lol_tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class userScreen extends AppCompatActivity {

    ImageView background;
    Intent intent;
    String game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_screen);
        background = findViewById(R.id.background);
        intent = getIntent();
        game = intent.getStringExtra("game");
        if(game.equals("tft")){
            background.setImageResource(R.drawable.tftbackground);
            background.setScaleType(ImageView.ScaleType.FIT_XY);        }
        else if(game.equals("lol")) {
            background.setImageResource(R.drawable.lolbackground);
            background.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }


}
