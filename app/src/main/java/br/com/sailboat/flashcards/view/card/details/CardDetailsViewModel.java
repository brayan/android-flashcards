package br.com.sailboat.flashcards.view.card.details;

import java.util.ArrayList;
import java.util.List;

import br.com.sailboat.canoe.recycler.RecyclerItem;
import br.com.sailboat.flashcards.model.Card;
import br.com.sailboat.flashcards.model.view.CardMetrics;

public class CardDetailsViewModel {

    private long cardId;
    private Card card;
    private CardMetrics cardMetrics = new CardMetrics();
    private final List<RecyclerItem> recyclerItemList = new ArrayList<>();

    public long getCardId() {
        return cardId;
    }

    public void setCardId(long cardId) {
        this.cardId = cardId;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public CardMetrics getCardMetrics() {
        return cardMetrics;
    }

    public void setCardMetrics(CardMetrics cardMetrics) {
        this.cardMetrics = cardMetrics;
    }

    public List<RecyclerItem> getRecyclerItemList() {
        return recyclerItemList;
    }
}
