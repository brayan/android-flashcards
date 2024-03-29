package br.com.sailboat.flashcards.view.card.list;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import br.com.sailboat.canoe.base.BaseFragment;
import br.com.sailboat.canoe.helper.ScrollHelper;
import br.com.sailboat.flashcards.R;
import br.com.sailboat.flashcards.helper.ExtrasHelper;
import br.com.sailboat.flashcards.view.card.details.CardDetailsActivity;
import br.com.sailboat.flashcards.view.card.insert.InsertCardActivity;
import br.com.sailboat.flashcards.view.play.PlayActivity;
import br.com.sailboat.flashcards.view.tag.list.TagListActivity;


public class CardListFragment extends BaseFragment<CardListPresenter> implements CardListPresenter.View {

    @Override
    protected int getLayoutId() {
        return R.layout.frg_list;
    }

    @Override
    protected CardListPresenter newPresenterInstance() {
        return new CardListPresenter(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (ExtrasHelper.wasStartedFromMenu(getActivity().getIntent())) {
            inflater.inflate(R.menu.menu_search_play, menu);
        } else {
            inflater.inflate(R.menu.menu_card_list, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_play: {
                PlayActivity.start(this);
                return true;
            }
            case R.id.menu_card_list__tags: {
                TagListActivity.startFromMenu(this);
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
        if (ExtrasHelper.wasStartedFromMenu(getActivity().getIntent())) {
            toolbar.setTitle(R.string.title_cards);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().onBackPressed();
                }
            });
        } else {
            toolbar.setTitle(R.string.app_name);
        }
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
    public void startCardDetailsActivity(long cardId) {
        CardDetailsActivity.start(this, cardId);
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