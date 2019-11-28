package com.example.lol_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button tftButton;
    Button lolButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tftButton = findViewById(R.id.tftStatBtn);
        lolButton = findViewById(R.id.lolBtn);

        tftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, userScreen.class);
                intent.putExtra("game", "tft");
                startActivity(intent);
            }
        });

        lolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, userScreen.class);
                intent.putExtra("game", "lol");
                startActivity(intent);
            }
        });
    }
}
