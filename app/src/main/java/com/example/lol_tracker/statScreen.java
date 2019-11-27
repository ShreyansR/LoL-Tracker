package com.example.lol_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.lol_tracker.userScreen.readJsonFromUrl;

public class statScreen extends AppCompatActivity {

    ImageButton tftBtn;
    ImageButton lolBtn;
    ImageButton homeBtn;
    ImageButton searchBtn;
    ImageButton statsBtn;
    ImageButton leaderboardBtn;
    ImageButton settingsBtn;
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
    TextView textView2;
    TextView textView3;
    ScrollView ldrScroll;
    ScrollView homeScroll;

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
        homeList = findViewById(R.id.champsList);
        ldrScroll = findViewById(R.id.ldrScroll);
        homeScroll = findViewById(R.id.homeScroll);



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
            setInitialInvisibility();
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
                    textView2.setText("");
                    textView.setText("Name: " + name + "\nSummoner Level: " + summonerLevel);
                    String APICall = "https://na1.api.riotgames.com/lol/league/v4/entries/by-summoner/" + id + "?api_key=" + APIKey;
                    new RetrieveJSONArrayTask().execute(APICall);
                }
                else if (game.equals("tft")){
                    setInitialInvisibility();
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
                        textView.setVisibility(View.VISIBLE);
                        textView2.setVisibility(View.VISIBLE);
                        textView3.setVisibility(View.VISIBLE);
                        ldrScroll.setVisibility(View.GONE);
                        homeScroll.setVisibility(View.GONE);
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

            textView.setVisibility(View.INVISIBLE);
            textView2.setVisibility(View.INVISIBLE);
            textView3.setVisibility(View.INVISIBLE);
            //ldrBoardList.setVisibility(View.VISIBLE);
            ldrScroll.setVisibility(View.VISIBLE);
            //homeList.setVisibility(View.GONE);
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
            //ArrayList<Integer> champs = new ArrayList<>();
            String[] pic;
            textView.setVisibility(View.INVISIBLE);
            textView2.setVisibility(View.INVISIBLE);
            textView3.setVisibility(View.INVISIBLE);
            //ldrBoardList.setVisibility(View.GONE);
            ldrScroll.setVisibility(View.GONE);
            //homeList.setVisibility(View.VISIBLE);
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
            textView.setVisibility(View.GONE);
            textView2.setVisibility(View.INVISIBLE);
            textView3.setVisibility(View.INVISIBLE);
            homeScroll.setVisibility(View.INVISIBLE);
        }
        else if(game.equals("lol")) {
            homeScroll.setVisibility(View.VISIBLE);
            ldrScroll.setVisibility(View.GONE);
            textView.setVisibility(View.INVISIBLE);
            textView2.setVisibility(View.INVISIBLE);
            textView3.setVisibility(View.INVISIBLE);
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
