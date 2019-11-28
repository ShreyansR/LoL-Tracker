package com.example.lol_tracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BuildDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_details);
        Intent intent = getIntent();

        build build_temp = (build) intent.getSerializableExtra("build");

        TextView titleText = findViewById(R.id.build_name);
        ImageView[] imageView = {findViewById(R.id.champ1), findViewById(R.id.champ2),
                findViewById(R.id.champ3), findViewById(R.id.champ4),
                findViewById(R.id.champ5), findViewById(R.id.champ6),
                findViewById(R.id.champ7), findViewById(R.id.champ8)};
        TextView synsText = findViewById(R.id.build_syns);

        titleText.setText(build_temp.name);
        for (int i = 0; i < imageView.length -1 ; i++){
            imageView[i].setImageResource(build_temp.imgid[i]);
        }
        synsText.setText("Synergies: \n");
        for (String syn : build_temp.synergy){
            synsText.append(syn + "\n");
        }

    }
}
