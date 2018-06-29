package com.example.lars.trivia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.simmetrics.StringMetric;
import org.simmetrics.metrics.StringMetrics;

public class GameActivity extends AppCompatActivity implements TriviaHelper.Callback{

    TriviaHelper triviaHelper;
    TextView questionView, questionCounterView, scoreView, categoryView;
    EditText answerView;
    Question currentQuestion;
    int questionCounter, playerScore, bonusScore, totalQuestions;
    StringMetric metric;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        triviaHelper = new TriviaHelper(this);
        setContentView(R.layout.activity_game);
        Log.d("triviaLogOnCreate", "reached");
        questionView = findViewById(R.id.questionView);
        questionCounterView = findViewById(R.id.questionCounterView);
        scoreView = findViewById(R.id.scoreView);
        answerView = findViewById(R.id.answerView);
        categoryView = findViewById(R.id.categoryView);
        questionCounter = 0;
        playerScore = 0;
        bonusScore = 0;
        totalQuestions = 5;
        metric = StringMetrics.levenshtein();
        nextQuestion();
    }

    public void nextQuestion(){
        triviaHelper.getNextQuestion(this);
    }

    @Override
    public void gotQuestion(Question question) {
        questionCounter = questionCounter + 1;
        questionCounterView.setText("Question #" + questionCounter);
        scoreView.setText("Score:" + playerScore);
        questionView.setText(question.question);
        categoryView.setText(question.category);
        currentQuestion = question;
    }

    @Override
    public void gotError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void submitAnswer(View view) {
        if (metric.compare(String.valueOf(answerView.getText()),currentQuestion.correctAnswer) > 0.8) {
            playerScore = playerScore + bonusScore + 10;
            bonusScore = bonusScore + 10;
        }
        else {
            bonusScore = 0;
        }
        answerView.setText("");
        quizCheck();

    }

    public void quizCheck() {
        if (questionCounter < totalQuestions) {
            nextQuestion();
        }
        else {
            Intent intent = new Intent(GameActivity.this, HighscoresActivity.class);
            intent.putExtra("playerScore", playerScore);
            startActivity(intent);
            finish();
        }

    }

    public void passQuestion(View view) {
        quizCheck();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("question", currentQuestion);
        outState.putInt("score", playerScore);
        outState.putInt("bonus", bonusScore);
        outState.putInt("count", questionCounter);
        Log.d("triviaLogSaveInstance", "reached");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("triviaLogRestoreInstance", "reached");
        currentQuestion = (Question) savedInstanceState.getSerializable("question");
        playerScore = savedInstanceState.getInt("score");
        bonusScore = savedInstanceState.getInt("bonus");
        questionCounter = savedInstanceState.getInt("count");
        questionCounterView.setText("Question #" + questionCounter);
        scoreView.setText("Score:" + playerScore);
        questionView.setText(currentQuestion.question);
    }

}
