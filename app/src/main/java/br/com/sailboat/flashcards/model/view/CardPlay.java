package br.com.sailboat.flashcards.model.view;

import br.com.sailboat.canoe.helper.EntityHelper;
import br.com.sailboat.canoe.recycler.RecyclerItem;
import br.com.sailboat.flashcards.helper.ViewType;

public class CardPlay implements RecyclerItem {

    private long cardId = EntityHelper.NO_ID;
    private int answer = EntityHelper.NO_ID;


    public long getCardId() {
        return cardId;
    }

    public void setCardId(long cardId) {
        this.cardId = cardId;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    @Override
    public int getViewType() {
        return ViewType.CARD_PLAY;
    }

}
