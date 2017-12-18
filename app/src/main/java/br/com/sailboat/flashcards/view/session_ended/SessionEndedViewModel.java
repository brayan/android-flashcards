package br.com.sailboat.flashcards.view.session_ended;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.sailboat.flashcards.model.view.CardMetrics;
import br.com.sailboat.flashcards.model.view.CardPlay;


public class SessionEndedViewModel implements Serializable{

    private CardMetrics cardMetrics;
    private final List<CardPlay> cardPlayList = new ArrayList<>();

    public CardMetrics getCardMetrics() {
        return cardMetrics;
    }

    public void setCardMetrics(CardMetrics cardMetrics) {
        this.cardMetrics = cardMetrics;
    }

    public List<CardPlay> getCardPlayList() {
        return cardPlayList;
    }

}
