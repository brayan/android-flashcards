package br.com.sailboat.flashcards.model;

import br.com.sailboat.canoe.helper.EntityHelper;
import br.com.sailboat.canoe.recycler.RecyclerItem;
import br.com.sailboat.flashcards.helper.ViewType;

public class Tag implements RecyclerItem {

    private long id = EntityHelper.NO_ID;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getViewType() {
        return ViewType.TAG;
    }
}
