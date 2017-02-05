package com.hackbrown.teamwildcard.refresh;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class SearcherActivity extends AppCompatActivity {

    ProgressBar progressBar;
    //TextView responseView;
    EditText foodText;
    String food;

    private static final String API_URL = "http://food2fork.com/api/get?";
    private static final String API_KEY = "2a3c96536d99fb00d1179a126a20219f";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searcher);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //responseView = (TextView) findViewById(R.id.responseView);


    }



    public void onQueryButtonClicked(View view){
        foodText = (EditText) findViewById(R.id.foodText);
        String foodEntry = foodText.getText().toString();
        String[] foodWords = foodEntry.split(" ");
        food = foodWords[0];
        for(int i = 0; i < foodWords.length; i++){
            if(i > 1){
                food = food + "%20" + foodWords[i];
            }
        }
        new RetrieveFeedTask().execute();
    }

    private class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        private Exception exception;



        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(Void... urls) {
            // Do some validation here

            try {
                URL url = new URL("http://food2fork.com/api/search?key=" + API_KEY + "&q=" + food);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if (response == null) {
                response = "THERE WAS AN ERROR";
            }
            progressBar.setVisibility(View.GONE);
            Log.i("INFO", response);

            String[] display = response.split(",");
            //responseView.setText(display[0] + "\n" + display[1]);

            Intent explicitIntent = new Intent(SearcherActivity.this, RecipeActivity.class);

            String[] wordsInLine;
            ArrayList<String> finalDisplay = new ArrayList<String>();

            ArrayList<String> finalDisplayURLs = new ArrayList<String>();

            for (int i = 0; i < display.length; i++) {
                wordsInLine = display[i].split("\"");
                if(Arrays.asList(wordsInLine).contains("title")){

                    String[] words = display[i].split(":");
                    String[] elements = words[1].split("\"");
                    finalDisplay.add(elements[1]);
                }

                if(Arrays.asList(wordsInLine).contains("source_url")){

                    String[] words = display[i].split(":");
                    String website = words[1] + ":" + words[2];
                    String[] elements = website.split("\"");
                    finalDisplayURLs.add(elements[1]);
                }


            }
            explicitIntent.putStringArrayListExtra("RECIPES", finalDisplay);
            explicitIntent.putStringArrayListExtra("URLS", finalDisplayURLs);

            startActivity(explicitIntent);


        }
    }

    public void onButtonClicked(View view){
        Intent explicitIntent = new Intent(this, MainActivity.class);
        startActivity(explicitIntent);

    }
}
