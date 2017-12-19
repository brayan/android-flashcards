package br.com.sailboat.flashcards.view.card.selector;

import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.List;

import br.com.sailboat.canoe.base.BaseActivitySingleFragment;
import br.com.sailboat.flashcards.helper.ExtrasHelper;
import br.com.sailboat.flashcards.helper.RequestCodeHelper;
import br.com.sailboat.flashcards.model.Card;


public class CardSelectorActivity extends BaseActivitySingleFragment<CardSelectorFragment> {

    public static void start(Fragment fragment, List<Card> cards) {
        Intent starter = new Intent(fragment.getActivity(), CardSelectorActivity.class);
        ExtrasHelper.putCards(cards, starter);
        fragment.startActivityForResult(starter, RequestCodeHelper.SELECTOR_CARD);
    }

    @Override
    protected CardSelectorFragment newFragmentInstance() {
        List<Card> cards = ExtrasHelper.getCards(getIntent());
        return CardSelectorFragment.newInstance(cards);
    }

}
