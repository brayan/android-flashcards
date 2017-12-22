package br.com.sailboat.flashcards.interactor.validation;

import android.content.Context;

import br.com.sailboat.canoe.exception.RequiredFieldNotFilledException;
import br.com.sailboat.canoe.helper.StringHelper;
import br.com.sailboat.flashcards.R;
import br.com.sailboat.flashcards.model.Card;

public class CardValidation {

    public static void validate(Context context, Card card) throws RequiredFieldNotFilledException {
        CardValidation validation = new CardValidation();
        validation.validateFront(context, card);
        validation.validateBack(context, card);
    }

    private void validateFront(Context context, Card card) throws RequiredFieldNotFilledException {
        if (card == null || StringHelper.isNullOrEmpty(card.getFront())) {
            throw new RequiredFieldNotFilledException(context.getString(R.string.msg_insert_front));
        }
    }

    private void validateBack(Context context, Card card) throws RequiredFieldNotFilledException {
        if (card == null || StringHelper.isNullOrEmpty(card.getBack())) {
            throw new RequiredFieldNotFilledException(context.getString(R.string.msg_insert_back));
        }
    }
}
