package br.com.sailboat.flashcards.view.card.list;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import br.com.sailboat.canoe.base.BasePresenter;
import br.com.sailboat.canoe.helper.AsyncHelper;
import br.com.sailboat.canoe.recycler.RecyclerItem;
import br.com.sailboat.flashcards.model.Card;
import br.com.sailboat.flashcards.persistence.filter.Filter;
import br.com.sailboat.flashcards.persistence.sqlite.CardSQLite;

public class CardListPresenter extends BasePresenter<CardListPresenter.View> implements CardListAdapter.Callback {

    private CardListViewModel viewModel = new CardListViewModel();

    public CardListPresenter(CardListPresenter.View view) {
        super(view);
        initFilter();
    }

    @Override
    protected void postResume() {
        loadCards();
    }

    @Override
    public void onLongClickCard(int position) {
        Card card = (Card) getRecyclerItemList().get(position);
        view.startCardDetailsActivity(card.getId());
    }

    @Override
    public void onClickFab() {
        view.startNewCardActivity();
    }

    public void postActivityResult() {
        loadCards();
    }

    @Override
    public List<RecyclerItem> getRecyclerItemList() {
        return viewModel.getRecyclerItemList();
    }

    @Override
    protected void onQueryTextChange() {
        viewModel.getFilter().setSearchText(getSearchText());
        loadCards();
    }

    private void initFilter() {
        viewModel.setFilter(new Filter());
    }

    private void loadCards() {
        AsyncHelper.execute(AsyncTask.THREAD_POOL_EXECUTOR, new AsyncHelper.Callback() {

            List<RecyclerItem> cards = new ArrayList<>();

            @Override
            public void doInBackground() throws Exception {
                cards = CardSQLite.newInstance(getContext()).getAll(viewModel.getFilter());
            }

            @Override
            public void onSuccess() {
                getRecyclerItemList().clear();
                getRecyclerItemList().addAll(cards);
                updateContentViews();
            }

            @Override
            public void onFail(Exception e) {
                printLogAndShowDialog(e);
                updateContentViews();
            }

        });
    }

    private void updateContentViews() {
        view.updateRecycler();
        updateRecyclerVisibility();
    }

    private void updateRecyclerVisibility() {
        if (isCardsEmpty()) {
            view.hideRecycler();
            view.showEmptyView();
            view.expandToolbar();
        } else {
            view.showRecycler();
            view.hideEmptyView();
        }
    }

    private boolean isCardsEmpty() {
        return getRecyclerItemList().isEmpty();
    }

    public interface View extends BasePresenter.View {
        void startNewCardActivity();
        void startCardDetailsActivity(long taskId);
    }

}
