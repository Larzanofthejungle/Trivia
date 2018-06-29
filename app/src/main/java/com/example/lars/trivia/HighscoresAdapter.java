package com.example.lars.trivia;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HighscoresAdapter extends ArrayAdapter<Highscore> {

    TextView scoreListName, scoreListScore;
    ArrayList<Highscore> highscores;

    public HighscoresAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Highscore> list) {
        super(context, resource, list);
        Log.d("triviaLogHighscoresAdapter", "reached");
        highscores = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.highscore_listitem, parent, false);
        }
        scoreListName = convertView.findViewById(R.id.scoreListName);
        scoreListScore = convertView.findViewById(R.id.scoreListScore);
        scoreListName.setText(highscores.get(position).getName());
        scoreListScore.setText(String.valueOf(highscores.get(position).getScore()));
        return convertView;
    }

}
