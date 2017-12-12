package br.com.sailboat.flashcards.view.insert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.sailboat.canoe.helper.EntityHelper;
import br.com.sailboat.flashcards.model.Tag;

public class InsertCardViewModel implements Serializable {

    private long cardId = EntityHelper.NO_ID;
    private String front;
    private String back;
    private final List<Tag> tags = new ArrayList<>();

    public long getCardId() {
        return cardId;
    }

    public void setCardId(long cardId) {
        this.cardId = cardId;
    }

    public String getFront() {
        return front;
    }

    public void setFront(String front) {
        this.front = front;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public List<Tag> getTags() {
        return tags;
    }

}
