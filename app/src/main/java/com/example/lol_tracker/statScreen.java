package com.example.lol_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class statScreen extends AppCompatActivity {

    ImageButton tftBtn;
    ImageButton lolBtn;
    Intent intent;
    String game;
    String name;
    String puuid;
    Integer summonerLevel;
    String accountId;
    String id;
    Long revisionDate;
    ImageView background;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat_screen);

        tftBtn = findViewById(R.id.tftBtn);
        lolBtn = findViewById(R.id.lolBtn);
        background = findViewById(R.id.statBackground);
        textView = findViewById(R.id.textView);

        intent = getIntent();
        game = intent.getStringExtra("game");
        name = intent.getStringExtra("name");
        puuid = intent.getStringExtra("puuid");
        summonerLevel = intent.getIntExtra("summonerLevel", 0);
        accountId = intent.getStringExtra("accountId");
        id = intent.getStringExtra("id");
        revisionDate = intent.getLongExtra("revisionDate", 0);

        if (game.equals("tft")){
            tftBtn.setAlpha(0.75f);
            lolBtn.setAlpha(0.3f);
            background.setImageResource(R.drawable.tftbackground);
            background.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        else if (game.equals("lol")) {
            lolBtn.setAlpha(0.75f);
            tftBtn.setAlpha(0.3f);
            background.setImageResource(R.drawable.diana);
            background.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        tftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tftBtn.setAlpha(0.75f);
                lolBtn.setAlpha(0.3f);
                background.setImageResource(R.drawable.tftbackground);
                background.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        });

        lolBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lolBtn.setAlpha(0.75f);
                tftBtn.setAlpha(0.3f);
                background.setImageResource(R.drawable.diana);
                background.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        });

        textView.setText("Name: " + name
        + "\nPUUID: " + puuid
        + "\nSummoner Level: " + summonerLevel
        + "\nAccount ID: " + accountId
        + "\nID: " + id
        + "\nRevision Date: " + revisionDate);
    }
}
