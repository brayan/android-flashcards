package br.com.sailboat.flashcards.view.list;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;

import br.com.sailboat.canoe.base.BaseFragment;
import br.com.sailboat.canoe.helper.ScrollHelper;
import br.com.sailboat.flashcards.R;
import br.com.sailboat.flashcards.view.detail.CardDetailActivity;
import br.com.sailboat.flashcards.view.insert.InsertCardActivity;


public class CardListFragment extends BaseFragment<CardListPresenter> implements CardListPresenter.View {

    @Override
    protected int getLayoutId() {
        return R.layout.frg_card_list;
    }

    @Override
    protected CardListPresenter newPresenterInstance() {
        return new CardListPresenter(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    protected void initEmptyViewMessages() {
        setEmptyViewMessage1(getString(R.string.no_cards_found));
        setEmptyViewMessage2(getString(R.string.ept_click_to_add));
    }

    @Override
    protected void onInitToolbar() {
        toolbar.setTitle(R.string.app_name);
    }

    @Override
    protected void onInitRecycler() {
        recycler.setAdapter(new CardListAdapter(getPresenter()));
    }

    @Override
    protected void onInitFab() {
        ScrollHelper.hideFabWhenScrolling(recycler, fab);
    }

    @Override
    public void startCardDetailsActivity(long taskId) {
        CardDetailActivity.start(this, taskId);
    }

    @Override
    public void startNewCardActivity() {
        InsertCardActivity.start(this);
    }

    @Override
    protected void postActivityResult(int requestCode, Intent data) {
        getPresenter().postActivityResult();
    }

}