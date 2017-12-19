package br.com.sailboat.flashcards.view.tag.insert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.sailboat.canoe.helper.EntityHelper;
import br.com.sailboat.canoe.recycler.RecyclerItem;

public class InsertTagViewModel implements Serializable {

    private long tagId = EntityHelper.NO_ID;
    private String tagText;
    private final List<RecyclerItem> cards = new ArrayList<>();

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public String getTagText() {
        return tagText;
    }

    public void setTagText(String tagText) {
        this.tagText = tagText;
    }

    public List<RecyclerItem> getCards() {
        return cards;
    }
}
