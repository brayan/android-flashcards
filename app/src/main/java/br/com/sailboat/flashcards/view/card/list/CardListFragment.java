package br.com.sailboat.flashcards.view.card.list;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import br.com.sailboat.canoe.base.BaseFragment;
import br.com.sailboat.canoe.helper.ScrollHelper;
import br.com.sailboat.flashcards.R;
import br.com.sailboat.flashcards.view.card.details.CardDetailsActivity;
import br.com.sailboat.flashcards.view.card.insert.InsertCardActivity;
import br.com.sailboat.flashcards.view.tag.list.TagListActivity;


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
        inflater.inflate(R.menu.menu_card_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_card_list__tags: {
                TagListActivity.start(this);
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }

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
        CardDetailsActivity.start(this, taskId);
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