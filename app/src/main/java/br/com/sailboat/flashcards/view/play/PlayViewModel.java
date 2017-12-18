package br.com.sailboat.flashcards.view.play;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import br.com.sailboat.canoe.helper.EntityHelper;
import br.com.sailboat.flashcards.model.view.CardMetrics;
import br.com.sailboat.flashcards.model.view.CardPlay;


public class PlayViewModel implements Serializable{

    private long tagId = EntityHelper.NO_ID;
    private CardMetrics cardMetrics = new CardMetrics();
    private final LinkedHashMap<Long, CardPlay> selectedCards = new LinkedHashMap<>();
    private final List<CardPlay> cardPlayList = new ArrayList<>();

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public CardMetrics getCardMetrics() {
        return cardMetrics;
    }

    public void setCardMetrics(CardMetrics cardMetrics) {
        this.cardMetrics = cardMetrics;
    }

    public LinkedHashMap<Long, CardPlay> getSelectedCards() {
        return selectedCards;
    }

    public List<CardPlay> getCardPlayList() {
        return cardPlayList;
    }

}
