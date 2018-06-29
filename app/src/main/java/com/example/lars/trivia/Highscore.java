package com.example.lars.trivia;

import java.util.Comparator;

public class Highscore {
    String name;
    int score;

    public Highscore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public static Comparator<Highscore> HighscoreComparator = new Comparator<Highscore>() {

        public int compare(Highscore s1, Highscore s2) {

            int score1 = s1.getScore();
            int score2 = s2.getScore();

            /*In descending order*/
            return score2-score1;

        }};
}
