package br.com.sailboat.flashcards.view.view_holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.sailboat.canoe.base.BaseViewHolder;
import br.com.sailboat.canoe.helper.ImageViewColorHelper;
import br.com.sailboat.canoe.helper.LogHelper;
import br.com.sailboat.flashcards.R;
import br.com.sailboat.flashcards.model.Card;
import br.com.sailboat.flashcards.model.view.CardAnswer;
import br.com.sailboat.flashcards.model.view.CardPlay;
import br.com.sailboat.flashcards.persistence.sqlite.CardSQLite;

public class CardPlayViewHolder extends BaseViewHolder {

    private TextView tvFront;
    private ImageView ivAnswer;


    public static CardPlayViewHolder newInstance(ViewGroup parent) {
        View view = inflateLayout(parent, R.layout.vh_card_play);
        return new CardPlayViewHolder(view);
    }


    public CardPlayViewHolder(View itemView) {
        super(itemView);
        initViews(itemView);
    }

    public void bindData(CardPlay cardPlay) {
        tvFront.setText(getFrontOfTheCard(cardPlay.getCardId()));
        setStatus(cardPlay);
    }

    private void setStatus(CardPlay cardPlay) {
        if (cardPlay.getAnswer() == CardAnswer.RIGHT) {
            ivAnswer.setImageResource(R.drawable.ic_thumb_up_white_24dp);
            ImageViewColorHelper.setBackgroundTint(itemView.getContext(), ivAnswer, R.color.md_teal_300);

        } else if (cardPlay.getAnswer() == CardAnswer.WRONG) {
            ivAnswer.setImageResource(R.drawable.ic_thumb_down_white_24dp);
            ImageViewColorHelper.setBackgroundTint(itemView.getContext(), ivAnswer, R.color.md_red_300);

        } else {
            ivAnswer.setImageResource(R.drawable.ic_help_white_24dp);
            ImageViewColorHelper.setBackgroundTint(itemView.getContext(), ivAnswer, R.color.md_blue_300);
        }

    }

    private void initViews(View itemView) {
        tvFront = itemView.findViewById(R.id.vh_card_play__tv__front);
        ivAnswer = itemView.findViewById(R.id.vh_card_play__iv__answer);
    }

    public String getFrontOfTheCard(long cardId) {
        try {
            Card card = CardSQLite.newInstance(itemView.getContext()).getCardById(cardId);
            return card.getFront();
        } catch (Exception e) {
            LogHelper.logException(e);
            return "";
        }
    }


}
