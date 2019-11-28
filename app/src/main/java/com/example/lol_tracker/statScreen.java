package com.example.lol_tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.lol_tracker.userScreen.readJsonFromUrl;

public class statScreen extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference dbRef;
    ImageButton tftBtn, lolBtn, homeBtn, searchBtn, statsBtn, leaderboardBtn, settingsBtn;
    ListView ldrBoardList;
    ListView homeList;
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
    TextView rankSolo;
    TextView rankFlex;
    ScrollView ldrScroll;
    ScrollView homeScroll;
    ScrollView matchesScroll;
    TextView summonerName;
    LinearLayout statTop;
    LinearLayout soloLayout;
    LinearLayout flexLayout;
    TextView summonerLvl;
    ImageView soloPic;
    ImageView flexPic;
    ImageView matchImg1, matchImg2, matchImg3, matchImg4;
    TextView matchChamp1, matchChamp2, matchChamp3, matchChamp4;
    TextView matchLane1, matchLane2, matchLane3, matchLane4;
    TextView matchKills1, matchKills2, matchKills3, matchKills4;
    TextView matchDeaths1, matchDeaths2, matchDeaths3, matchDeaths4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat_screen);


        tftBtn = findViewById(R.id.tftBtn);
        lolBtn = findViewById(R.id.lolBtn);
        background = findViewById(R.id.statBackground);
        textView = findViewById(R.id.summonerName);
        rankSolo = findViewById(R.id.rankSolo);
        rankFlex = findViewById(R.id.rankFlex);
        homeBtn = findViewById(R.id.homeBtn);
        statsBtn = findViewById(R.id.statsBtn);
        searchBtn = findViewById(R.id.searchBtn);
        leaderboardBtn = findViewById(R.id.leaderboardBtn);
        settingsBtn = findViewById(R.id.settingsBtn);
        ldrBoardList = findViewById(R.id.ldrboardList);
        homeList = findViewById(R.id.champsList);
        ldrScroll = findViewById(R.id.ldrScroll);
        homeScroll = findViewById(R.id.homeScroll);
        matchesScroll = findViewById(R.id.matchesScroll);
        summonerName = findViewById(R.id.summonerName);
        summonerLvl = findViewById(R.id.summonerLevel);
        statTop = findViewById(R.id.statTop);
        soloLayout = findViewById(R.id.soloLayout);
        flexLayout = findViewById(R.id.flexLayout);
        soloPic = findViewById(R.id.soloPic);
        flexPic = findViewById(R.id.flexPic);
        matchImg1 = findViewById(R.id.matchImg1);
        matchChamp1 = findViewById(R.id.matchChamp1);
        matchLane1 = findViewById(R.id.matchLane1);
        matchKills1 = findViewById(R.id.matchKills1);
        matchDeaths1 = findViewById(R.id.matchDeaths1);

        matchImg2 = findViewById(R.id.matchImg2);
        matchChamp2 = findViewById(R.id.matchChamp2);
        matchLane2 = findViewById(R.id.matchLane2);
        matchKills2 = findViewById(R.id.matchKills2);
        matchDeaths2 = findViewById(R.id.matchDeaths2);

        matchImg3 = findViewById(R.id.matchImg3);
        matchChamp3 = findViewById(R.id.matchChamp3);
        matchLane3 = findViewById(R.id.matchLane3);
        matchKills3 = findViewById(R.id.matchKills3);
        matchDeaths3 = findViewById(R.id.matchDeaths3);

        matchImg4 = findViewById(R.id.matchImg4);
        matchChamp4 = findViewById(R.id.matchChamp4);
        matchLane4 = findViewById(R.id.matchLane4);
        matchKills4 = findViewById(R.id.matchKills4);
        matchDeaths4 = findViewById(R.id.matchDeaths4);

        intent = getIntent();
        game = intent.getStringExtra("game");
        name = intent.getStringExtra("name");
        puuid = intent.getStringExtra("puuid");
        summonerLevel = intent.getIntExtra("summonerLevel", 0);
        accountId = intent.getStringExtra("accountId");
        id = intent.getStringExtra("id");
        revisionDate = intent.getLongExtra("revisionDate", 0);
        APIKey = intent.getStringExtra("APIKey");

        System.out.println("-----------------" + id);


        if (game.equals("tft")){
            tftBtn.setAlpha(0.75f);
            lolBtn.setAlpha(0.3f);
            background.setImageResource(R.drawable.tftbackground);
            background.setScaleType(ImageView.ScaleType.FIT_XY);
            setInitialInvisibility();
            database = FirebaseDatabase.getInstance();
            dbRef = database.getReference("builds");

            dbRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    for(DataSnapshot item: dataSnapshot.getChildren()){
//                        Log.d("TAG","Value is " + item.child("champions").getValue().toString() );
//                        Log.d("TAG","Value is " + item.child("synergies").getValue().toString() );
                        Log.d("TAG","Value is " + item.child("name").getValue().toString());
                        String temp = item.child("champions").toString();
                        String champions = temp.substring(1,temp.length()-2);

                        List<String> champs = Arrays.asList(champions.split(","));

                        String tmp = item.child("synergies").toString();
                        String synergies = tmp.substring(1,tmp.length()-2);

                        List<String> syns = Arrays.asList(synergies.split(","));



                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w("TAG", "Failed to read value", databaseError.toException());
                }
            });
        }
        else if (game.equals("lol")) {
            lolBtn.setAlpha(0.75f);
            tftBtn.setAlpha(0.3f);
            setInitialInvisibility();
            background.setImageResource(R.drawable.dianab);
            background.setScaleType(ImageView.ScaleType.FIT_XY);
            String APICall = "https://na1.api.riotgames.com/lol/platform/v3/champion-rotations?api_key=" + APIKey;
            new RetrieveFreeChampions().execute(APICall);
        }



        tftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game = "tft";
                tftBtn.setAlpha(0.75f);
                lolBtn.setAlpha(0.3f);
                background.setImageResource(R.drawable.tftbackground);
                background.setScaleType(ImageView.ScaleType.FIT_XY);
                setInitialInvisibility();
                resetBottomTabs(0.7f, 0.25f,0.25f, 0.25f, 0.25f);
            }
        });

        lolBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game = "lol";
                lolBtn.setAlpha(0.75f);
                tftBtn.setAlpha(0.3f);
                background.setImageResource(R.drawable.dianab);
                background.setScaleType(ImageView.ScaleType.FIT_XY);
                setInitialInvisibility();
                resetBottomTabs(0.7f, 0.25f,0.25f, 0.25f, 0.25f);
                String APICall = "https://na1.api.riotgames.com/lol/platform/v3/champion-rotations?api_key=" + APIKey;
                new RetrieveFreeChampions().execute(APICall);
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBottomTabs(0.7f, 0.25f,0.25f, 0.25f, 0.25f);

                if(game.equals("lol")) {
                    homeList.setVisibility(View.VISIBLE);
                    String APICall = "https://na1.api.riotgames.com/lol/platform/v3/champion-rotations?api_key=" + APIKey;
                    new RetrieveFreeChampions().execute(APICall);
                }
                else if (game.equals("tft")) {
                    homeList.setVisibility(View.INVISIBLE);
                }

            }
        });

        statsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBottomTabs(0.25f, 0.7f,0.25f, 0.25f, 0.25f);

                if(game.equals("lol")) {
                    textView.setText("");
                    rankSolo.setText("");
                    //textView.setText("Name: " + name + "\nSummoner Level: " + summonerLevel);
                    summonerName.setText("Summoner: " + name);
                    summonerLvl.setText("Level: " + summonerLevel);
                    String APICall = "https://na1.api.riotgames.com/lol/league/v4/entries/by-summoner/" + id + "?api_key=" + APIKey;
                    new RetrieveJSONArrayTask().execute(APICall);
                }
                else if (game.equals("tft")){
                    textView.setText("");
                    rankSolo.setText("");
                    summonerName.setText("Summoner: " + name);
                    summonerLvl.setText("Level: " + summonerLevel);
                    String APICall = "https://na1.api.riotgames.com/tft/league/v1/entries/by-summoner/" + id + "?api_key=" + APIKey;
                    new RetrieveJSONArrayTask().execute(APICall);
                }
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBottomTabs(0.25f, 0.25f,0.7f, 0.25f, 0.25f);

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
                resetBottomTabs(0.25f, 0.25f,0.25f, 0.7f, 0.25f);

                if (game.equals("lol")){
                    String APICall = "https://na1.api.riotgames.com/lol/league-exp/v4/entries/RANKED_SOLO_5x5/CHALLENGER/I?page=1&api_key=" + APIKey;
                    new RetrieveLeaderboard().execute(APICall);
                }

                else if (game.equals("tft")) {
                    String APICall = "https://na1.api.riotgames.com/tft/league/v1/entries/PLATINUM/I?page=1&api_key=" + APIKey;
                    new RetrieveLeaderboard().execute(APICall);
                }
            }
        });

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBottomTabs(0.25f, 0.25f,0.25f, 0.25f, 0.7f);
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

    //reads all the characters coming in and builds it into ldrScroll string
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
                        homeScroll.setVisibility(View.INVISIBLE);
                        ldrScroll.setVisibility(View.GONE);
                        matchesScroll.setVisibility(View.VISIBLE);
                        statTop.setVisibility(View.VISIBLE);
                        soloLayout.setVisibility(View.VISIBLE);
                        flexLayout.setVisibility(View.VISIBLE);

                        rankSolo.setText("RANKED SOLO 5x5"
                                + "\nWins: " + "N/A"
                                + "\nLosses: " + "N/A"
                                + "\nRank: " + "UNRANKED "
                                + "\nLeague Points: " + "N/A");

                        rankFlex.setText("RANKED FLEX SR"
                                + "\nWins: " + "N/A"
                                + "\nLosses: " + "N/A"
                                + "\nRank: " + "UNRANKED "
                                + "\nLeague Points: " + "N/A");

                        for(int i = 0; i < json.length(); i++) {
                            JSONObject jsonObject = json.getJSONObject(i);
                            String queueType = (String) jsonObject.get("queueType");
                            Integer wins = (Integer) jsonObject.get("wins");
                            Integer losses = (Integer) jsonObject.get("losses");
                            String rank = (String) jsonObject.get("rank");
                            String tier = (String) jsonObject.get("tier");
                            Integer leaguePoints = (Integer) jsonObject.get("leaguePoints");

                            System.out.println(queueType);
                            if(queueType.equals("RANKED_SOLO_5x5")) {

                                if (tier.equals("BRONZE")){
                                    soloPic.setImageResource(R.drawable.bronze);
                                }
                                if (tier.equals("CHALLENGER")){
                                    soloPic.setImageResource(R.drawable.challenger);
                                }
                                if (tier.equals("DIAMOND")){
                                    soloPic.setImageResource(R.drawable.diamond);
                                }
                                if (tier.equals("GOLD")){
                                    soloPic.setImageResource(R.drawable.gold);
                                }
                                if (tier.equals("GRANDMASTER")){
                                    soloPic.setImageResource(R.drawable.grandmaster);
                                }
                                if (tier.equals("IRON")){
                                    soloPic.setImageResource(R.drawable.iron);
                                }
                                if (tier.equals("MASTER")){
                                    soloPic.setImageResource(R.drawable.master);
                                }
                                if (tier.equals("PLATINUM")){
                                    soloPic.setImageResource(R.drawable.platinum);
                                }
                                if (tier.equals("SILVER")){
                                    soloPic.setImageResource(R.drawable.silver);
                                }

                                rankSolo.setText(queueType.replace("_", " ")
                                        + "\nWins: " + wins
                                        + "\nLosses: " + losses
                                        + "\nRank: " + tier
                                        + " " + rank
                                        + "\nLeague Points: " + leaguePoints);
                            }

                            else if(queueType.equals("RANKED_FLEX_SR")) {

                                if (tier.equals("BRONZE")){
                                    flexPic.setImageResource(R.drawable.bronze);
                                }
                                if (tier.equals("CHALLENGER")){
                                    flexPic.setImageResource(R.drawable.challenger);
                                }
                                if (tier.equals("DIAMOND")){
                                    flexPic.setImageResource(R.drawable.diamond);
                                }
                                if (tier.equals("GOLD")){
                                    flexPic.setImageResource(R.drawable.gold);
                                }
                                if (tier.equals("GRANDMASTER")){
                                    flexPic.setImageResource(R.drawable.grandmaster);
                                }
                                if (tier.equals("IRON")){
                                    flexPic.setImageResource(R.drawable.iron);
                                }
                                if (tier.equals("MASTER")){
                                    flexPic.setImageResource(R.drawable.master);
                                }
                                if (tier.equals("PLATINUM")){
                                    flexPic.setImageResource(R.drawable.platinum);
                                }
                                if (tier.equals("SILVER")){
                                    flexPic.setImageResource(R.drawable.silver);
                                }

                                rankFlex.setText(queueType.replace("_", " ")
                                        + "\nWins: " + wins
                                        + "\nLosses: " + losses
                                        + "\nRank: " + tier
                                        + " " + rank
                                        + "\nLeague Points: " + leaguePoints);
                            }

                            String APICall = "https://na1.api.riotgames.com/lol/match/v4/matchlists/by-account/" + accountId + "?api_key=" + APIKey;
                            new RetrieveMatchList().execute(APICall);
                        }

                    }

                    else if (game.equals("tft")) {
                        homeScroll.setVisibility(View.INVISIBLE);
                        ldrScroll.setVisibility(View.GONE);
                        matchesScroll.setVisibility(View.VISIBLE);
                        statTop.setVisibility(View.VISIBLE);
                        soloLayout.setVisibility(View.VISIBLE);
                        flexLayout.setVisibility(View.INVISIBLE);

                        rankSolo.setText("RANKED TFT"
                                + "\nWins: " + "N/A"
                                + "\nLosses: " + "N/A"
                                + "\nRank: " + "UNRANKED "
                                + "\nLeague Points: " + "N/A");


                        for(int i = 0; i < json.length(); i++) {
                            JSONObject jsonObject = json.getJSONObject(i);
                            String queueType = (String) jsonObject.get("queueType");
                            Integer wins = (Integer) jsonObject.get("wins");
                            Integer losses = (Integer) jsonObject.get("losses");
                            String rank = (String) jsonObject.get("rank");
                            String tier = (String) jsonObject.get("tier");
                            Integer leaguePoints = (Integer) jsonObject.get("leaguePoints");

                            System.out.println(queueType);
                            if(queueType.equals("RANKED_TFT")) {

                                if (tier.equals("BRONZE")){
                                    soloPic.setImageResource(R.drawable.bronze);
                                }
                                if (tier.equals("CHALLENGER")){
                                    soloPic.setImageResource(R.drawable.challenger);
                                }
                                if (tier.equals("DIAMOND")){
                                    soloPic.setImageResource(R.drawable.diamond);
                                }
                                if (tier.equals("GOLD")){
                                    soloPic.setImageResource(R.drawable.gold);
                                }
                                if (tier.equals("GRANDMASTER")){
                                    soloPic.setImageResource(R.drawable.grandmaster);
                                }
                                if (tier.equals("IRON")){
                                    soloPic.setImageResource(R.drawable.iron);
                                }
                                if (tier.equals("MASTER")){
                                    soloPic.setImageResource(R.drawable.master);
                                }
                                if (tier.equals("PLATINUM")){
                                    soloPic.setImageResource(R.drawable.platinum);
                                }
                                if (tier.equals("SILVER")){
                                    soloPic.setImageResource(R.drawable.silver);
                                }

                                rankSolo.setText(queueType.replace("_", " ")
                                        + "\nWins: " + wins
                                        + "\nLosses: " + losses
                                        + "\nRank: " + tier
                                        + " " + rank
                                        + "\nLeague Points: " + leaguePoints);
                            }

                            String APICall = "https://americas.api.riotgames.com/tft/match/v1/matches/by-puuid/" + puuid + "/ids?count=5&api_key=" + APIKey;
                            new RetrieveTftMatchList().execute(APICall);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class RetrieveTftMatchList extends AsyncTask<String, Void, JSONArray> {
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

                    String gameId = json.get(0).toString();

                    String APICall = "https://americas.api.riotgames.com/tft/match/v1/matches/" + gameId + "?api_key=" + APIKey;
                    new RetrieveMatch1().execute(APICall);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class RetrieveMatchList extends AsyncTask<String, Void, JSONObject> {
        private Exception exception;
        protected JSONObject doInBackground(String... APICall) {
            try {
                JSONObject json = readJsonObjectFromUrl(APICall[0]);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        protected void onPostExecute(JSONObject json) {
            if (json == null) {
                Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_LONG).show();
            } else {
                if (game.equals("lol")) {
                    try {
                        JSONArray jsonArray = (JSONArray) json.get("matches");
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String lane = (String) jsonObject.get("lane");
                        Long gameId = (Long) jsonObject.get("gameId");
                        Integer champion = (Integer) jsonObject.get("champion");
                        Integer queue = (Integer) jsonObject.get("queue");
                        matchImg1.setImageResource(getResources().getIdentifier(get_name(champion).replace("'", "").toLowerCase(), "drawable", getPackageName()));
                        matchLane1.setText("Lane: " + lane);
                        matchChamp1.setText(get_name(champion));

                        String APICall = "https://na1.api.riotgames.com/lol/match/v4/matches/" + gameId + "?api_key=" + APIKey;
                        new RetrieveMatch1().execute(APICall);

                        jsonObject = jsonArray.getJSONObject(2);
                        lane = (String) jsonObject.get("lane");
                        gameId = (Long) jsonObject.get("gameId");
                        champion = (Integer) jsonObject.get("champion");
                        queue = (Integer) jsonObject.get("queue");

                        matchImg2.setImageResource(getResources().getIdentifier(get_name(champion).replace("'", "").toLowerCase(), "drawable", getPackageName()));
                        matchLane2.setText("Lane: " + lane);
                        matchChamp2.setText(get_name(champion));

                        APICall = "https://na1.api.riotgames.com/lol/match/v4/matches/" + gameId + "?api_key=" + APIKey;
                        new RetrieveMatch2().execute(APICall);

                        jsonObject = jsonArray.getJSONObject(3);
                        lane = (String) jsonObject.get("lane");
                        gameId = (Long) jsonObject.get("gameId");
                        champion = (Integer) jsonObject.get("champion");
                        queue = (Integer) jsonObject.get("queue");

                        matchImg3.setImageResource(getResources().getIdentifier(get_name(champion).replace("'", "").toLowerCase(), "drawable", getPackageName()));
                        matchLane3.setText("Lane: " + lane);
                        matchChamp3.setText(get_name(champion));

                        APICall = "https://na1.api.riotgames.com/lol/match/v4/matches/" + gameId + "?api_key=" + APIKey;
                        new RetrieveMatch3().execute(APICall);

                        jsonObject = jsonArray.getJSONObject(4);
                        lane = (String) jsonObject.get("lane");
                        gameId = (Long) jsonObject.get("gameId");
                        champion = (Integer) jsonObject.get("champion");
                        queue = (Integer) jsonObject.get("queue");

                        matchImg4.setImageResource(getResources().getIdentifier(get_name(champion).replace("'", "").toLowerCase(), "drawable", getPackageName()));
                        matchLane4.setText("Lane: " + lane);
                        matchChamp4.setText(get_name(champion));

                        APICall = "https://na1.api.riotgames.com/lol/match/v4/matches/" + gameId + "?api_key=" + APIKey;
                        new RetrieveMatch4().execute(APICall);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    class RetrieveMatch1 extends AsyncTask<String, Void, JSONObject> {
        private Exception exception;
        protected JSONObject doInBackground(String... APICall) {
            try {
                JSONObject json = readJsonObjectFromUrl(APICall[0]);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        protected void onPostExecute(JSONObject json) {
            if (json == null) {
                Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_LONG).show();
            } else {
                if (game.equals("lol")) {
                    try {
                        Integer participantId = null;
                        Integer kills;
                        Integer deaths;
                        Integer assists;
                        Integer champLevel;
                        Boolean win;
                        JSONArray jsonArray = (JSONArray) json.get("participantIdentities");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject player = jsonArray.getJSONObject(i);
                            if (accountId.equals(player.getJSONObject("player").get("accountId"))) {
                                participantId = (Integer) player.get("participantId");
                            }
                        }
                        jsonArray = (JSONArray) json.get("participants");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject player = jsonArray.getJSONObject(i);
                            if (participantId == (Integer) player.get("participantId")) {
                                JSONObject statsObject = (JSONObject) player.getJSONObject("stats");
                                kills = (Integer) statsObject.get("kills");
                                deaths = (Integer) statsObject.get("kills");
                                assists = (Integer) statsObject.get("assists");
                                champLevel = (Integer) statsObject.get("champLevel");
                                win = (Boolean) statsObject.get("win");
                                matchKills1.setText(String.valueOf("Kills: " + kills));
                                matchDeaths1.setText(String.valueOf("Deaths: " + deaths));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                else if (game.equals("tft")) {
                    try {
                        Integer participantId = null;
                        Integer kills;
                        Integer deaths;
                        Integer assists;
                        Integer champLevel;
                        Boolean win;
                        JSONObject jsonObject = json.getJSONObject("info");
                        JSONArray jsonArr = jsonObject.getJSONArray("participants");
                        System.out.println(jsonArr);


                        for (int i = 0; i < jsonArr.length(); i++) {
                            JSONObject player = jsonArr.getJSONObject(i);
                            if (puuid.equals(player.get("puuid"))) {
                                matchChamp1.setText(player.getJSONObject("companion").get("species").toString());
                                System.out.println(player.getJSONObject("companion").get("species").toString());
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    class RetrieveMatch2 extends AsyncTask<String, Void, JSONObject> {
        private Exception exception;
        protected JSONObject doInBackground(String... APICall) {
            try {
                JSONObject json = readJsonObjectFromUrl(APICall[0]);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        protected void onPostExecute(JSONObject json) {
            if (json == null) {
                Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_LONG).show();
            } else {
                try {
                    Integer participantId = null;
                    Integer kills;
                    Integer deaths;
                    Integer assists;
                    Integer champLevel;
                    Boolean win;
                    JSONArray jsonArray = (JSONArray) json.get("participantIdentities");
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject player = jsonArray.getJSONObject(i);
                        if(accountId.equals(player.getJSONObject("player").get("accountId"))) {
                            participantId = (Integer) player.get("participantId");
                        }
                    }
                    jsonArray = (JSONArray) json.get("participants");
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject player = jsonArray.getJSONObject(i);
                        if(participantId == (Integer) player.get("participantId")) {
                            JSONObject statsObject = (JSONObject) player.getJSONObject("stats");
                            kills = (Integer) statsObject.get("kills");
                            deaths = (Integer) statsObject.get("kills");
                            assists = (Integer) statsObject.get("assists");
                            champLevel = (Integer) statsObject.get("champLevel");
                            win = (Boolean) statsObject.get("win");
                            matchKills2.setText(String.valueOf("Kills: " + kills));
                            matchDeaths2.setText(String.valueOf("Deaths: " + deaths));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class RetrieveMatch3 extends AsyncTask<String, Void, JSONObject> {
        private Exception exception;
        protected JSONObject doInBackground(String... APICall) {
            try {
                JSONObject json = readJsonObjectFromUrl(APICall[0]);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        protected void onPostExecute(JSONObject json) {
            if (json == null) {
                Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_LONG).show();
            } else {
                try {
                    Integer participantId = null;
                    Integer kills;
                    Integer deaths;
                    Integer assists;
                    Integer champLevel;
                    Boolean win;
                    JSONArray jsonArray = (JSONArray) json.get("participantIdentities");
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject player = jsonArray.getJSONObject(i);
                        if(accountId.equals(player.getJSONObject("player").get("accountId"))) {
                            participantId = (Integer) player.get("participantId");
                        }
                    }
                    jsonArray = (JSONArray) json.get("participants");
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject player = jsonArray.getJSONObject(i);
                        if(participantId == (Integer) player.get("participantId")) {
                            JSONObject statsObject = (JSONObject) player.getJSONObject("stats");
                            kills = (Integer) statsObject.get("kills");
                            deaths = (Integer) statsObject.get("kills");
                            assists = (Integer) statsObject.get("assists");
                            champLevel = (Integer) statsObject.get("champLevel");
                            win = (Boolean) statsObject.get("win");
                            matchKills3.setText(String.valueOf("Kills: " + kills));
                            matchDeaths3.setText(String.valueOf("Deaths: " + deaths));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class RetrieveMatch4 extends AsyncTask<String, Void, JSONObject> {
        private Exception exception;
        protected JSONObject doInBackground(String... APICall) {
            try {
                JSONObject json = readJsonObjectFromUrl(APICall[0]);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        protected void onPostExecute(JSONObject json) {
            if (json == null) {
                Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_LONG).show();
            } else {
                try {
                    Integer participantId = null;
                    Integer kills;
                    Integer deaths;
                    Integer assists;
                    Integer champLevel;
                    Boolean win;
                    JSONArray jsonArray = (JSONArray) json.get("participantIdentities");
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject player = jsonArray.getJSONObject(i);
                        if(accountId.equals(player.getJSONObject("player").get("accountId"))) {
                            participantId = (Integer) player.get("participantId");
                        }
                    }
                    jsonArray = (JSONArray) json.get("participants");
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject player = jsonArray.getJSONObject(i);
                        if(participantId == (Integer) player.get("participantId")) {
                            JSONObject statsObject = (JSONObject) player.getJSONObject("stats");
                            kills = (Integer) statsObject.get("kills");
                            deaths = (Integer) statsObject.get("kills");
                            assists = (Integer) statsObject.get("assists");
                            champLevel = (Integer) statsObject.get("champLevel");
                            win = (Boolean) statsObject.get("win");
                            matchKills4.setText(String.valueOf("Kills: " + kills));
                            matchDeaths4.setText(String.valueOf("Deaths: " + deaths));
                        }
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
        ArrayList<Integer> leaguePts = new ArrayList<>();
        ArrayList<String> rank = new ArrayList<>();
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

            statTop.setVisibility(View.INVISIBLE);
            soloLayout.setVisibility(View.INVISIBLE);
            flexLayout.setVisibility(View.INVISIBLE);
            matchesScroll.setVisibility(View.GONE);
            ldrScroll.setVisibility(View.VISIBLE);
            homeScroll.setVisibility(View.GONE);
            JSONArray sortedArray = new JSONArray();
            try {

                List<JSONObject> jsonValues = new ArrayList<JSONObject>();
                for (int i = 0; i < json.length(); i ++) {
                    jsonValues.add(json.getJSONObject(i));
                }

                Collections.sort(jsonValues, new Comparator<JSONObject>() {
                    private static final String KEY_NAME = "leaguePoints";

                    @Override
                    public int compare(JSONObject o1, JSONObject o2) {
                        Integer valA = 0;
                        Integer valB = 0;

                        try {
                            valA = (Integer) o1.get(KEY_NAME);
                            valB = (Integer) o2.get(KEY_NAME);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return -valA.compareTo(valB);
                    }
                });
                for (int i = 0; i < json.length(); i++){
                    sortedArray.put(jsonValues.get(i));
                }

                System.out.println(sortedArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            if (json == null) {
                Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_LONG).show();
            } else {
                try {
                    for(int i = 0; i < sortedArray.length(); i++) {
                        JSONObject jsonObject = sortedArray.getJSONObject(i);
                        String summonerName = (String) jsonObject.get("summonerName");
                        int leaguePoints = (Integer) jsonObject.get("leaguePoints");
                        String tier = (String) jsonObject.get("tier");
                        leaders.add(summonerName);
                        leaguePts.add(leaguePoints);
                        rank.add(tier);
                    }

                    ldrBoardAdapter adapter = new ldrBoardAdapter(statScreen.this, leaders, leaguePts, rank);
                    ldrBoardList.setAdapter(adapter);
                    System.out.println(leaders);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class RetrieveFreeChampions extends AsyncTask<String, Void, JSONObject>  {
        private Exception exception;
        ArrayList<String> champs = new ArrayList<>();
        String[] champNames = new String[15];
        protected JSONObject doInBackground(String... APICall) {
            try {
                JSONObject json =  readJsonFromUrl(APICall[0]);
                return json;
            } catch (Exception e) {
                return null;
            }
        }

        protected void onPostExecute(JSONObject json) throws NullPointerException{


            statTop.setVisibility(View.INVISIBLE);
            soloLayout.setVisibility(View.INVISIBLE);
            flexLayout.setVisibility(View.INVISIBLE);
            ldrScroll.setVisibility(View.GONE);
            matchesScroll.setVisibility(View.GONE);
            homeScroll.setVisibility(View.VISIBLE);

            if (json == null) {
                Toast.makeText(getApplicationContext(), "Invalid Summoner Name", Toast.LENGTH_LONG).show();
            } else {
                try {
                    JSONArray champ_ids = json.getJSONArray("freeChampionIds");

                    for (int i = 0; i < champ_ids.length(); i++) {
                        //champs.add(champ_ids.getInt(i));
                        System.out.println(champ_ids.getInt(i));
                        //get_name(champ_ids.getInt(i));
                        champs.add(get_name(champ_ids.getInt(i)));
                        champNames[i] = get_name(champ_ids.getInt(i));
                    }

                    Integer[] imgid = new Integer[15];
                    for (int i = 0; i < champNames.length; i++) {
                        imgid[i] = getResources().getIdentifier(champNames[i].replace("'", "").toLowerCase(), "drawable", getPackageName());
                        System.out.println(imgid[i]);
                    }
                    MyListAdapter adapter = new MyListAdapter(statScreen.this, champNames, imgid);
                    homeList.setAdapter(adapter);
                    System.out.println(champs);
                    //System.out.println(champs.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String get_name (int id) {
        BufferedReader reader;
        String line;
        String champName = "";
        try {
            final InputStream file = getAssets().open("champions.txt");
            reader = new BufferedReader(new InputStreamReader(file));
            line = reader.readLine();
            while(line != null) {
                String[] data = line.split(":", 2);
                if (data[0].equals(Integer.toString(id))){
                    System.out.println(data[1]);
                    champName = data[1];
                    break;
                }
                else
                    line = reader.readLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return champName;
    }

    public void setInitialInvisibility(){
        if(game.equals("tft")) {
            ldrScroll.setVisibility(View.GONE);
            homeScroll.setVisibility(View.INVISIBLE);
            statTop.setVisibility(View.INVISIBLE);
            soloLayout.setVisibility(View.INVISIBLE);
            flexLayout.setVisibility(View.INVISIBLE);
            matchesScroll.setVisibility(View.GONE);
        }
        else if(game.equals("lol")) {
            homeScroll.setVisibility(View.VISIBLE);
            ldrScroll.setVisibility(View.GONE);
            matchesScroll.setVisibility(View.GONE);
            statTop.setVisibility(View.INVISIBLE);
            soloLayout.setVisibility(View.INVISIBLE);
            flexLayout.setVisibility(View.INVISIBLE);
        }
    }

    public void resetBottomTabs (float a, float b, float c, float d, float e) {
        homeBtn.setAlpha(a);
        statsBtn.setAlpha(b);
        searchBtn.setAlpha(c);
        leaderboardBtn.setAlpha(d);
        settingsBtn.setAlpha(e);
    }

}
