package br.com.sailboat.flashcards.view.card.list;

import br.com.sailboat.canoe.base.BaseActivitySingleFragment;

public class CardListActivity extends BaseActivitySingleFragment<CardListFragment> {

    @Override
    protected CardListFragment newFragmentInstance() {
        return new CardListFragment();
    }

}
