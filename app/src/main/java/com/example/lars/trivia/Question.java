package com.example.lars.trivia;

import java.io.Serializable;

public class Question implements Serializable{
    String  question, correctAnswer, category;


    public Question(String question, String correctAnswer, String category) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.category = category;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
