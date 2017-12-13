package br.com.sailboat.flashcards.view.card.details;

import android.content.Intent;
import android.support.v4.app.Fragment;

import br.com.sailboat.canoe.base.BaseActivitySingleFragment;
import br.com.sailboat.flashcards.helper.ExtrasHelper;
import br.com.sailboat.flashcards.helper.RequestCodeHelper;

public class CardDetailsActivity extends BaseActivitySingleFragment<CardDetailsFragment> {

    public static void start(Fragment fromFragment, long cardId) {
        Intent intent = new Intent(fromFragment.getActivity(), CardDetailsActivity.class);
        ExtrasHelper.putCardId(cardId, intent);
        fromFragment.startActivityForResult(intent, RequestCodeHelper.CARD_DETAILS);
    }

    @Override
    protected CardDetailsFragment newFragmentInstance() {
        long cardId = ExtrasHelper.getCardId(getIntent());
        return CardDetailsFragment.newInstance(cardId);
    }

}
