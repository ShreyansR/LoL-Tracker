package com.example.lol_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;


public class userScreen extends AppCompatActivity {
    ImageView background;
    Intent intent;
    String game;
    Button searchBtn;
    EditText summonerInput;
    String APIKey = "RGAPI-98ebcb7a-1364-4e89-bca3-32a69b0f3b34";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_screen);

        background = findViewById(R.id.background);
        searchBtn = findViewById(R.id.search);
        summonerInput = findViewById(R.id.summonerInput);

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
                String summonerName = summonerInput.getText().toString();
                String APICall = "https://na1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + summonerName + "?api_key=" + APIKey;
                new RetrieveJSONObjectTask().execute(APICall);
            }
        });
    }

    //creates JSON objects after getting data from URL
    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
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

    //reads all the characters coming in and builds it into a string
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    class RetrieveJSONObjectTask extends AsyncTask<String, Void, JSONObject> {
        private Exception exception;
        protected JSONObject doInBackground(String... APICall) {
            try {
                JSONObject json =  readJsonFromUrl(APICall[0]);
                return json;
            } catch (Exception e) {
                return null;
            }
        }

        protected void onPostExecute(JSONObject json) {
            if (json == null) {
                Toast.makeText(getApplicationContext(), "Invalid Summoner Name", Toast.LENGTH_LONG).show();
            } else {
                try {
                    Integer profileIconId = (Integer) json.get("profileIconId");
                    String name = (String) json.get("name");
                    String puuid = (String) json.get("puuid");
                    Integer summonerLevel = (Integer) json.get("summonerLevel");
                    String accountId = (String) json.get("accountId");
                    String id = (String) json.get("id");
                    Long revisionDate = (Long) json.get("revisionDate");

                    System.out.println(profileIconId);
                    System.out.println(name);
                    System.out.println(puuid);
                    System.out.println(summonerLevel);
                    System.out.println(accountId);
                    System.out.println(id);
                    System.out.println(revisionDate);

                    Intent intent = new Intent(userScreen.this, statScreen.class);
                    intent.putExtra("profileIconId", profileIconId);
                    intent.putExtra("name", name);
                    intent.putExtra("puuid", puuid);
                    intent.putExtra("summonerLevel", summonerLevel);
                    intent.putExtra("accountId", accountId);
                    intent.putExtra("id", id);
                    intent.putExtra("revisionDate", revisionDate);
                    intent.putExtra("game", game);
                    intent.putExtra("APIKey", APIKey);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
