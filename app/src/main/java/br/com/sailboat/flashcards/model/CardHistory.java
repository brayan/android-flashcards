package br.com.sailboat.flashcards.model;

import java.io.Serializable;

import br.com.sailboat.canoe.helper.EntityHelper;

public class CardHistory implements Serializable {

    private long id = EntityHelper.NO_ID;
    private long cardId;
    private int answer;
    private String insertingDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getInsertingDate() {
        return insertingDate;
    }

    public void setInsertingDate(String insertingDate) {
        this.insertingDate = insertingDate;
    }

}
