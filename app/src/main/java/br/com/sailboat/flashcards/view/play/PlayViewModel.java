package br.com.sailboat.flashcards.view.play;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import br.com.sailboat.canoe.helper.EntityHelper;
import br.com.sailboat.flashcards.model.view.CardPlay;


public class PlayViewModel implements Serializable{

    private long tagId = EntityHelper.NO_ID;
    private int rightAnswers = 0;
    private int wrongAnswers = 0;
    private int notAnswerd = 0;
    private final LinkedHashMap<Long, CardPlay> selectedCards = new LinkedHashMap<>();
    private final List<CardPlay> cardPlayList = new ArrayList<>();


    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public LinkedHashMap<Long, CardPlay> getSelectedCards() {
        return selectedCards;
    }

    public List<CardPlay> getCardPlayList() {
        return cardPlayList;
    }

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
