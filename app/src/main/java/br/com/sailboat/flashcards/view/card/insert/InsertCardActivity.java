package br.com.sailboat.flashcards.view.card.insert;

import android.content.Intent;
import android.support.v4.app.Fragment;

import br.com.sailboat.canoe.base.BaseActivitySingleFragment;
import br.com.sailboat.flashcards.helper.ExtrasHelper;
import br.com.sailboat.flashcards.helper.RequestCodeHelper;

public class InsertCardActivity extends BaseActivitySingleFragment<InsertCardFragment> {

    public static void start(Fragment fromFragment) {
        Intent intent = new Intent(fromFragment.getActivity(), InsertCardActivity.class);
        fromFragment.startActivityForResult(intent, RequestCodeHelper.INSERT_CARD);
    }

    public static void startToEdit(Fragment fromFragment, long cardId) {
        Intent intent = new Intent(fromFragment.getActivity(), InsertCardActivity.class);
        ExtrasHelper.putCardId(cardId, intent);
        fromFragment.startActivityForResult(intent, RequestCodeHelper.INSERT_CARD);
    }

    @Override
    protected InsertCardFragment newFragmentInstance() {
        if (ExtrasHelper.hasCardId(getIntent())) {
            long cardId = ExtrasHelper.getCardId(getIntent());
            return InsertCardFragment.newInstanceWithCardToEdit(cardId);

        } else {
            return InsertCardFragment.newInstance();
        }
    }

}
