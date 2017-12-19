package br.com.sailboat.flashcards.view.card.list;

import android.app.Activity;
import android.content.Intent;

import br.com.sailboat.canoe.base.BaseActivitySingleFragment;
import br.com.sailboat.flashcards.helper.RequestCodeHelper;

public class CardListActivity extends BaseActivitySingleFragment<CardListFragment> {

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, CardListActivity.class);
        activity.startActivityForResult(intent, RequestCodeHelper.CARD_LIST);
    }

    @Override
    protected CardListFragment newFragmentInstance() {
        return new CardListFragment();
    }

}
