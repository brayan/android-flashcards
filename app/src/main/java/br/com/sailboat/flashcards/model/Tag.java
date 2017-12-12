package br.com.sailboat.flashcards.model;

import java.io.Serializable;

import br.com.sailboat.canoe.helper.EntityHelper;

public class Tag implements Serializable {

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

}
