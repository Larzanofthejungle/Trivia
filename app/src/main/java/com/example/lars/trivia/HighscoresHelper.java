package com.example.lars.trivia;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;


public class HighscoresHelper implements Response.Listener<JSONObject>, Response.ErrorListener {

    //initialisation
    Context context;
    Callback callback;

    public HighscoresHelper(Context context) {
        this.context = context;
    }

    public interface Callback {
        void gotHighscores(ArrayList<Highscore> highscores);
        void gotError(String message);
    }

    public void postNewHighScore(DatabaseReference mDatabase,Highscore highscore) {
        mDatabase.child(highscore.name).setValue(highscore);
    }

    public void getHighscores(Callback activity) {
        Log.d("triviaLogGetHighscores", "reached");
        callback = activity;
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://trivia-6f945.firebaseio.com/highscores.json", null, this, this);
        queue.add(jsonObjectRequest);
    }

    public void onErrorResponse(VolleyError error) {
        Log.d("triviaLogQuestion", "error");
        Log.d("triviaLogQuestion", String.valueOf(error));
        callback.gotError(error.getMessage());
    }

    public void onResponse(JSONObject response) {
        //retrieves the json request
        try {
            //retrieves the json request
            Log.d("triviaLogHighscoreResponse", String.valueOf(response));
            Iterator<String> x = response.keys();
            JSONArray jsonArray = new JSONArray();

            while (x.hasNext()){
                String key = x.next();
                jsonArray.put(response.get(key));
            }
            Log.d("triviaLogHighscoreJSONArray", String.valueOf(jsonArray));
            ArrayList<Highscore> data = new ArrayList<>(jsonArray.length());
            String tempName;
            int tempScore;
            //formats the json to an arraylist of MenuItems
            for (int i = 0; i < jsonArray.length(); i++) {
                tempName = jsonArray.getJSONObject(i).getString("name");
                tempScore = jsonArray.getJSONObject(i).getInt("score");
                data.add(new Highscore(tempName, tempScore));
            }
            Log.d("triviaLogHighscoresUnsorted", String.valueOf(data));
            Collections.sort(data, Highscore.HighscoreComparator);
            Log.d("triviaLogHighscoresSorted", String.valueOf(data));
            callback.gotHighscores(data);
        } catch (JSONException e) {
            e.printStackTrace();
            callback.gotError(e.getMessage());
        }

    }
}
