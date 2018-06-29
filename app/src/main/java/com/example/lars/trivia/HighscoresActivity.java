package com.example.lars.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;

import java.util.ArrayList;

public class HighscoresActivity extends AppCompatActivity implements HighscoresHelper.Callback {

    FirebaseDatabase database;
    DatabaseReference myRef;
    HighscoresHelper highscoresHelper;
    TextView playerScoreText;
    EditText highscoreNameEdit;
    int playerScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        Bundle extras = getIntent().getExtras();
        playerScore = extras.getInt("playerScore");
        highscoresHelper = new HighscoresHelper(this);
        database = FirebaseDatabase.getInstance();
        database.setLogLevel(Logger.Level.DEBUG);
        myRef = database.getReference("highscores");
        highscoreNameEdit = findViewById(R.id.highscoreNameEdit);
        playerScoreText = findViewById(R.id.playerScoreText);
        playerScoreText.setText("Score: " + playerScore);
        highscoresHelper.getHighscores(this);
    }

    @Override
    public void gotHighscores(ArrayList<Highscore> highscores) {

        //fill listview with menu items and sets on click listener
        Log.d("triviaLogGotHighscores", "reached");
        HighscoresAdapter adapter = new HighscoresAdapter(this, R.layout.highscore_listitem, highscores);
        ListView listView = findViewById(R.id.highscoresListView);
        listView.setAdapter(adapter);
    }

    @Override
    public void gotError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void newQuiz(View view) {
        Intent intent = new Intent(HighscoresActivity.this, GameActivity.class);
        startActivity(intent);
        finish();
    }

    public void submitScore(View view) {
        Highscore highscore = new Highscore(String.valueOf(highscoreNameEdit.getText()), playerScore);
        highscoresHelper.postNewHighScore(myRef, highscore);
        highscoresHelper.getHighscores(this);
    }
}
