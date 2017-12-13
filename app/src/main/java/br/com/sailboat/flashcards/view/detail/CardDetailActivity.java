package br.com.sailboat.flashcards.view.detail;

import android.content.Intent;
import android.support.v4.app.Fragment;

import br.com.sailboat.canoe.base.BaseActivitySingleFragment;
import br.com.sailboat.flashcards.helper.ExtrasHelper;
import br.com.sailboat.flashcards.helper.RequestCodeHelper;

public class CardDetailActivity extends BaseActivitySingleFragment<CardDetailFragment> {

    public static void start(Fragment fromFragment, long cardId) {
        Intent intent = new Intent(fromFragment.getActivity(), CardDetailActivity.class);
        ExtrasHelper.putCardId(cardId, intent);
        fromFragment.startActivityForResult(intent, RequestCodeHelper.TASK_DETAILS);
    }

    @Override
    protected CardDetailFragment newFragmentInstance() {
        long cardId = ExtrasHelper.getCardId(getIntent());
        return CardDetailFragment.newInstance(cardId);
    }

}
