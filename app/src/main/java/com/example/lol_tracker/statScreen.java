package com.example.lol_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class statScreen extends AppCompatActivity {

    ImageButton tftBtn;
    ImageButton lolBtn;
    ImageButton homeBtn;
    ImageButton searchBtn;
    ImageButton statsBtn;
    ImageButton leaderboardBtn;
    ImageButton settingsBtn;
    ListView ldrBoardList;
    Intent intent;
    String game;
    String name;
    String puuid;
    Integer summonerLevel;
    String accountId;
    String id;
    Long revisionDate;
    String APIKey;
    ImageView background;
    TextView textView;
    TextView textView2;
    TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat_screen);

        tftBtn = findViewById(R.id.tftBtn);
        lolBtn = findViewById(R.id.lolBtn);
        background = findViewById(R.id.statBackground);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        homeBtn = findViewById(R.id.homeBtn);
        statsBtn = findViewById(R.id.statsBtn);
        searchBtn = findViewById(R.id.searchBtn);
        leaderboardBtn = findViewById(R.id.leaderboardBtn);
        settingsBtn = findViewById(R.id.settingsBtn);
        ldrBoardList = findViewById(R.id.ldrboardList);
        //ArrayList<String> leaders = new ArrayList<>();


        intent = getIntent();
        game = intent.getStringExtra("game");
        name = intent.getStringExtra("name");
        puuid = intent.getStringExtra("puuid");
        summonerLevel = intent.getIntExtra("summonerLevel", 0);
        accountId = intent.getStringExtra("accountId");
        id = intent.getStringExtra("id");
        revisionDate = intent.getLongExtra("revisionDate", 0);
        APIKey = intent.getStringExtra("APIKey");

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
                game = "tft";
                tftBtn.setAlpha(0.75f);
                lolBtn.setAlpha(0.3f);
                background.setImageResource(R.drawable.tftbackground);
                background.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        });

        lolBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game = "lol";
                lolBtn.setAlpha(0.75f);
                tftBtn.setAlpha(0.3f);
                background.setImageResource(R.drawable.diana);
                background.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeBtn.setAlpha(0.7f);
                statsBtn.setAlpha(0.25f);
                searchBtn.setAlpha(0.25f);
                leaderboardBtn.setAlpha(0.25f);
                settingsBtn.setAlpha(0.25f);

            }
        });

        statsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeBtn.setAlpha(0.25f);
                statsBtn.setAlpha(0.7f);
                searchBtn.setAlpha(0.25f);
                leaderboardBtn.setAlpha(0.25f);
                settingsBtn.setAlpha(0.25f);
                textView.setText("");
                textView2.setText("");
                textView.setText("Name: " + name + "\nSummoner Level: " + summonerLevel);
                String APICall = "https://na1.api.riotgames.com/lol/league/v4/entries/by-summoner/" + id + "?api_key=" + APIKey;
                new RetrieveJSONArrayTask().execute(APICall);
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeBtn.setAlpha(0.25f);
                statsBtn.setAlpha(0.25f);
                searchBtn.setAlpha(0.7f);
                leaderboardBtn.setAlpha(0.25f);
                settingsBtn.setAlpha(0.25f);

                if (game.equals("tft")){
                    Intent intent = new Intent(statScreen.this, userScreen.class);
                    intent.putExtra("game", "tft");
                    startActivity(intent);
                }
                else if (game.equals("lol")){
                    Intent intent = new Intent(statScreen.this, userScreen.class);
                    intent.putExtra("game", "lol");
                    startActivity(intent);
                }
            }
        });

        leaderboardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeBtn.setAlpha(0.25f);
                statsBtn.setAlpha(0.25f);
                searchBtn.setAlpha(0.25f);
                leaderboardBtn.setAlpha(0.7f);
                settingsBtn.setAlpha(0.25f);

                if (game.equals("lol")){
                    String APICall = "https://na1.api.riotgames.com/lol/league-exp/v4/entries/RANKED_SOLO_5x5/CHALLENGER/I?page=1&api_key=" + APIKey;
                    new RetrieveLeaderboard().execute(APICall);
                }
            }
        });

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeBtn.setAlpha(0.25f);
                statsBtn.setAlpha(0.25f);
                searchBtn.setAlpha(0.25f);
                leaderboardBtn.setAlpha(0.25f);
                settingsBtn.setAlpha(0.7f);
            }
        });
    }

    //creates JSON objects after getting data from URL
    public static JSONObject readJsonObjectFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    //creates JSON objects after getting data from URL
    public static JSONArray readJsonArrayFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONArray json = new JSONArray(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    //reads all the characters coming in and builds it into a string
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    class RetrieveJSONArrayTask extends AsyncTask<String, Void, JSONArray> {
        private Exception exception;
        protected JSONArray doInBackground(String... APICall) {
            try {
                JSONArray json = readJsonArrayFromUrl(APICall[0]);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        protected void onPostExecute(JSONArray json) {
            if (json == null) {
                Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_LONG).show();
            } else {
                try {
                    if(game.equals("lol")) {
                        textView.setVisibility(View.VISIBLE);
                        textView2.setVisibility(View.VISIBLE);
                        textView3.setVisibility(View.VISIBLE);
                        ldrBoardList.setVisibility(View.INVISIBLE);
                        JSONObject jsonObject = json.getJSONObject(0);

                        String queueType = (String) jsonObject.get("queueType");
                        Integer wins = (Integer) jsonObject.get("wins");
                        Integer losses = (Integer) jsonObject.get("losses");
                        String rank = (String) jsonObject.get("rank");
                        String tier = (String) jsonObject.get("tier");
                        Integer leaguePoints = (Integer) jsonObject.get("leaguePoints");
                        textView2.setText("Queue Type: " + queueType
                                + "\nWins: " + wins
                                + "\nLosses: " + losses
                                + "\nRank: " + rank
                                + " " + tier
                                + "\nLeague Points: " + leaguePoints);

                        jsonObject = json.getJSONObject(1);
                        queueType = (String) jsonObject.get("queueType");
                        wins = (Integer) jsonObject.get("wins");
                        losses = (Integer) jsonObject.get("losses");
                        rank = (String) jsonObject.get("rank");
                        tier = (String) jsonObject.get("tier");
                        leaguePoints = (Integer) jsonObject.get("leaguePoints");
                        textView3.setText("Queue Type: " + queueType
                                + "\nWins: " + wins
                                + "\nLosses: " + losses
                                + "\nRank: " + rank
                                + " " + tier
                                + "\nLeague Points: " + leaguePoints);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class RetrieveLeaderboard extends AsyncTask<String, Void, JSONArray> {
        private Exception exception;
        ArrayList<String> leaders = new ArrayList<>();
        protected JSONArray doInBackground(String... APICall) {
            try {
                JSONArray json = readJsonArrayFromUrl(APICall[0]);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        protected void onPostExecute(JSONArray json) {

            textView.setVisibility(View.INVISIBLE);
            textView2.setVisibility(View.INVISIBLE);
            textView3.setVisibility(View.INVISIBLE);
            ldrBoardList.setVisibility(View.VISIBLE);
            if (json == null) {
                Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_LONG).show();
            } else {
                try {
                    for(int i = 0; i < json.length(); i++) {
                        JSONObject jsonObject = json.getJSONObject(i);
                        String summonerName = (String) jsonObject.get("summonerName");
                        leaders.add(summonerName);
                        //textView2.append("Summoner Name: " + summonerName);
                    }
                    ArrayAdapter adapter = new ArrayAdapter<String>(statScreen.this, android.R.layout.simple_list_item_1,leaders);
                    ldrBoardList.setAdapter(adapter);
                    System.out.println(leaders);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
