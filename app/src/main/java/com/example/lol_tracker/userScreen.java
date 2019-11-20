package com.example.lol_tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class userScreen extends AppCompatActivity {

    ImageView background;
    Intent intent;
    String game;
    Button searchBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_screen);
        background = findViewById(R.id.background);
        searchBtn = findViewById(R.id.search);

        intent = getIntent();
        game = intent.getStringExtra("game");
        if(game.equals("tft")){
            background.setImageResource(R.drawable.tftbackground);
            background.setScaleType(ImageView.ScaleType.FIT_XY);        }
        else if(game.equals("lol")) {
            background.setImageResource(R.drawable.lolbackground);
            background.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(userScreen.this, statScreen.class);
                intent.putExtra("game", game);
                startActivity(intent);
            }
        });
    }


}
