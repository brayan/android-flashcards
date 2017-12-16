package br.com.sailboat.flashcards.model.view;

import java.io.Serializable;

import br.com.sailboat.canoe.helper.EntityHelper;

public class CardPlay implements Serializable {

    private long cardId = EntityHelper.NO_ID;
    private int answer = EntityHelper.NO_ID;


    public long getCardId() {
        return cardId;
    }

    public void setCardId(long cardId) {
        this.cardId = cardId;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
