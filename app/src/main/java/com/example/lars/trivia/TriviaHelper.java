package com.example.lars.trivia;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import static com.android.volley.Request.Method.GET;


public class TriviaHelper implements Response.Listener<JSONArray>, Response.ErrorListener {

    //initialisation
    Context context;
    Callback callback;

    public TriviaHelper(Context context) {
        this.context = context;
    }

    public interface Callback {
        void gotQuestion(Question question);
        void gotError(String message);
    }

    public void getNextQuestion(Callback activity) {

        //sends request for getting a random question
        Log.d("triviaLogNextQuestion", "reached");
        callback = activity;
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(GET,"http://jservice.io/api/random", null, this, this);
        queue.add(jsonArrayRequest);
    }

    public void onErrorResponse(VolleyError error) {
        Log.d("triviaLogQuestion", "error");
        Log.d("triviaLogQuestion", String.valueOf(error));
        callback.gotError(error.getMessage());
    }

    public void onResponse(JSONArray response) {
        try {
            //retrieves the json request
            String question, answer, category;
            JSONObject jsonObject = (JSONObject) response.opt(0);
            Log.d("triviaLogQuestion", String.valueOf(response));
            question = jsonObject.getString("question");
            answer = jsonObject.getString("answer");
            category = jsonObject.getJSONObject("category").getString("title");
            Question data = new Question(question, answer, category);
            callback.gotQuestion(data);
        } catch (JSONException e) {
            e.printStackTrace();
            callback.gotError(e.getMessage());
        }
    }
}
