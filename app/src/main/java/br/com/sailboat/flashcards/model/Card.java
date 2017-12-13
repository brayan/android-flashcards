package br.com.sailboat.flashcards.model;

import br.com.sailboat.canoe.helper.EntityHelper;
import br.com.sailboat.canoe.recycler.RecyclerItem;
import br.com.sailboat.flashcards.helper.ViewType;

public class Card implements RecyclerItem {

    private long id = EntityHelper.NO_ID;
    private String front;
    private String back;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public int getViewType() {
        return ViewType.CARD;
    }
}
