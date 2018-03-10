package com.example.abimanyu.apiproject;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class Main2Activity extends Activity {

    TextView epicName;
    TextView wins;
    TextView averagesurvivaltime;
    TextView killdeath;
    TextView kills;
    TextView matches;
    TextView time;


    String string;
    JSONObject stringJSON;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        epicName = (TextView)findViewById(R.id.id_TV_name);
        wins = (TextView)findViewById(R.id.wins);
        averagesurvivaltime = (TextView)findViewById(R.id.avg);
        killdeath = (TextView)findViewById(R.id.kill_death);
        kills = (TextView)findViewById(R.id.kills);
        matches = (TextView)findViewById(R.id.matches_played);
        time = (TextView)findViewById(R.id.time_played);

        new AsyncThread().execute(getIntent().getStringExtra("TEST"));
        Toast.makeText(this, "If nothing shows up, wait for the stats to load.", Toast.LENGTH_SHORT).show();
    }

    public class AsyncThread extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL("https://api.fortnitetracker.com/v1/profile/"+strings[0]);
                HttpURLConnection _connection = (HttpURLConnection)url.openConnection();
                _connection.setRequestMethod("GET"); //POST
                _connection.setDoOutput(false);
                _connection.setRequestProperty("TRN-Api-Key","9b5e6cb9-4e9b-42e3-8df6-4119d6070d2c");
                _connection.connect();

                InputStream inputStream = _connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String reader;
                while ((reader = bufferedReader.readLine()) != null) {
                    string += reader;
                }
                String test = string.substring(4);
                stringJSON = new JSONObject(test);
                Log.d("TAG",stringJSON.toString());
            } catch (Exception e) {
                e.printStackTrace();
                //Toast.makeText(Main2Activity.this, "You have entered invalid information.", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "error "+e);
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void v) {
            try {
                JSONArray jsonRoot = stringJSON.getJSONArray("lifeTimeStats");
                Log.d("TAG_ROOT", jsonRoot.toString());
                String username = stringJSON.getString("epicUserHandle");
                Log.d("TAG_USERNAME",username);
                int platform = stringJSON.getInt("platformId");
                JSONObject jsonMatches = jsonRoot.getJSONObject(7);
                String matchesPlayed = jsonMatches.getString("value");
                JSONObject jsonKD = jsonRoot.getJSONObject(11);
                String kd = jsonKD.getString("value");
                JSONObject jsonKills = jsonRoot.getJSONObject(10);
                String k = jsonKills.getString("value");
                JSONObject jsonWins = jsonRoot.getJSONObject(8);
                String w = jsonWins.getString("value");
                JSONObject jsonTime = jsonRoot.getJSONObject(13);
                String t = jsonTime.getString("value");
                JSONObject jsonAvg = jsonRoot.getJSONObject(14);
                String avg = jsonAvg.getString("value");

                Log.d("TAG_AFTERGETTINGVALUES", avg);
                epicName.setText(username);
                if(platform==1)epicName.setTextColor(Color.parseColor("#107c10"));
                if(platform==2)epicName.setTextColor(Color.parseColor("#003791"));
                matches.setText("Matches Played: "+matchesPlayed);
                kills.setText("Kills: "+k);
                killdeath.setText("K/D: "+kd);
                wins.setText("Wins: "+w);
                time.setText("Time Spent in Game: "+t);
                averagesurvivaltime.setText("Average Survival Time: "+avg);

            }
            catch (Exception e){e.printStackTrace();}
        }
    }
}
