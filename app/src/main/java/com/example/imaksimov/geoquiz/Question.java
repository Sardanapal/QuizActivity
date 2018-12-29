package com.example.imaksimov.geoquiz;

/**
 * Created by imaksimov on 15.03.2018.
 */

public class Question {
    private int textResourceId;
    private boolean answerTrue;
    private boolean answered;

    public Question(int textResourceId, boolean answerTrue) {
        this.textResourceId = textResourceId;
        this.answerTrue = answerTrue;
        this.answered = false;
    }

    public int getTextResourceId() {
        return textResourceId;
    }

    public void setTextResourceId(int textResourceId) {
        this.textResourceId = textResourceId;
    }

    public boolean isAnswerTrue() {
        return answerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        this.answerTrue = answerTrue;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }
}
