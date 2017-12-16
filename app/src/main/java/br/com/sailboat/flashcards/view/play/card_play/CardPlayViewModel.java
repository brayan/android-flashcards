package br.com.sailboat.flashcards.view.play.card_play;

import java.io.Serializable;

import br.com.sailboat.flashcards.model.Card;
import br.com.sailboat.flashcards.model.view.CardPlay;

public class CardPlayViewModel implements Serializable {

    private CardPlay cardPlay;
    private Card card;
    private boolean showingBackOfTheCard;

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

}
