package br.com.sailboat.flashcards.view.play.card_play;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.sailboat.canoe.recycler.RecyclerItem;
import br.com.sailboat.flashcards.model.Card;
import br.com.sailboat.flashcards.model.view.CardPlay;

public class CardPlayViewModel implements Serializable {

    private CardPlay cardPlay;
    private Card card;
    private boolean showingBackOfTheCard;
    private final List<RecyclerItem> recyclerItemList = new ArrayList<>();

    public CardPlay getCardPlay() {
        return cardPlay;
    }

    public void setCardPlay(CardPlay cardPlay) {
        this.cardPlay = cardPlay;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public boolean isShowingBackOfTheCard() {
        return showingBackOfTheCard;
    }

    public void setShowingBackOfTheCard(boolean showingBackOfTheCard) {
        this.showingBackOfTheCard = showingBackOfTheCard;
    }

    public List<RecyclerItem> getRecyclerItemList() {
        return recyclerItemList;
    }

}
