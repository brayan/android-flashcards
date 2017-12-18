package br.com.sailboat.flashcards.model.view;

import java.io.Serializable;

public class CardMetrics implements Serializable {

    private int rightAnswers;
    private int wrongAnswers;
    private int notAnswerd;

    public int getRightAnswers() {
        return rightAnswers;
    }

    public void setRightAnswers(int rightAnswers) {
        this.rightAnswers = rightAnswers;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(int wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public int getNotAnswerd() {
        return notAnswerd;
    }

    public void setNotAnswerd(int notAnswerd) {
        this.notAnswerd = notAnswerd;
    }

}
