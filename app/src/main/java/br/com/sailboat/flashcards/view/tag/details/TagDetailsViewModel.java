package br.com.sailboat.flashcards.view.tag.details;

import java.util.ArrayList;
import java.util.List;

import br.com.sailboat.canoe.recycler.RecyclerItem;
import br.com.sailboat.flashcards.model.Tag;

public class TagDetailsViewModel {

    private long tagId;
    private Tag tag;
    private final List<RecyclerItem> recyclerItemList = new ArrayList<>();


    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public List<RecyclerItem> getRecyclerItemList() {
        return recyclerItemList;
    }
}
