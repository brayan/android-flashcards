package br.com.sailboat.flashcards.view.card.selector;

import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import br.com.sailboat.canoe.base.BasePresenter;
import br.com.sailboat.canoe.helper.AsyncHelper;
import br.com.sailboat.canoe.helper.LogHelper;
import br.com.sailboat.canoe.recycler.RecyclerItem;
import br.com.sailboat.flashcards.R;
import br.com.sailboat.flashcards.helper.ExtrasHelper;
import br.com.sailboat.flashcards.model.Card;
import br.com.sailboat.flashcards.persistence.sqlite.CardSQLite;

public class CardSelectorPresenter extends BasePresenter<CardSelectorPresenter.View> {

    private CardSelectorViewModel viewModel = new CardSelectorViewModel();

    public CardSelectorPresenter(CardSelectorPresenter.View view) {
        super(view);
    }

    @Override
    public void extractExtrasFromArguments(Bundle arguments) {
        List<Card> cards = ExtrasHelper.getCards(arguments);
        addSelectedItems(cards);
    }

    @Override
    protected void postResume() {
        loadItems();
    }

    public void onClickItem(int position) {
        Card card = (Card) getItemList().get(position);

        updateSelectedItemArray(card);
        updateTitle();
        updateItemView(position);
    }

    public void onClickConfirm() {
        List<Card> items = getItemsListFromLinkedHashMap();
        getView().closeActivityResultOk(items);
    }

    @Override
    public void onClickFab() {
        view.startInsertCard();
    }

    public List<RecyclerItem> getItemList() {
        return viewModel.getRecyclerItemList();
    }

    public LinkedHashMap<Long, Long> getSelectedItems() {
        return viewModel.getSelectedItems();
    }

    public boolean isItemSelected(Card card) {
        return (getSelectedItems().get(card.getId()) != null);
    }

    @Override
    protected void onQueryTextChange() {
        viewModel.getFilter().setSearchText(getSearchText());
        loadItems();
    }

    public void postResult() {
        loadItems();
    }

    private void loadItems() {

        AsyncHelper.execute(new AsyncHelper.Callback() {

            List<RecyclerItem> cards = new ArrayList<>();

            @Override
            public void doInBackground() throws Exception {
                cards = CardSQLite.newInstance(getContext()).getAll(viewModel.getFilter());
            }

            @Override
            public void onSuccess() {
                viewModel.getRecyclerItemList().clear();
                viewModel.getRecyclerItemList().addAll(cards);
                updateContentViews();
            }

            @Override
            public void onFail(Exception e) {
                printLogAndShowDialog(e);
            }
        });

    }

    private void updateTitle() {
        int size = viewModel.getSelectedItems().size();

        String title = null;

        if (size == 0) {
            title = getString(R.string.no_items_selected);
        } else if (size == 1) {
            title = "1 " + getString(R.string.item);
        } else {
            title = size + " " + getString(R.string.items);
        }

        getView().setTitle(title);
    }

    private void updateContentViews() {
        updateTitle();
        getView().updateRecycler();
        updateVisibilityOfViews();
    }

    private void updateVisibilityOfViews() {
        if (getItemList().isEmpty()) {
            getView().hideRecycler();
            getView().showEmptyView();
        } else {
            getView().showRecycler();
            getView().hideEmptyView();
        }
    }

    private void addSelectedItems(List<Card> cards) {
        LinkedHashMap<Long, Long> selectedCards = viewModel.getSelectedItems();

        for (Card t : cards) {
            selectedCards.put(t.getId(), t.getId());
        }
    }

    private void updateItemView(int position) {
        getView().updateRecyclerItemChanged(position);
    }

    private void updateSelectedItemArray(Card item) {
        LinkedHashMap<Long, Long> selectedCards = viewModel.getSelectedItems();

        if (isItemSelected(item)) {
            selectedCards.remove(item.getId());
        } else {
            selectedCards.put(item.getId(), item.getId());
        }
    }

    @NonNull
    private List<Card> getItemsListFromLinkedHashMap() {
        LinkedHashMap<Long, Long> selectedCards = viewModel.getSelectedItems();

        List<Card> cards = new ArrayList<>();

        for (long id : selectedCards.values()) {
            try {
                Card card = CardSQLite.newInstance(getContext()).getCardById(id);
                cards.add(card);
            } catch (Exception e) {
                LogHelper.logException(e);
            }
        }

        return cards;
    }


    public interface View extends BasePresenter.View {
        void closeActivityResultOk(List<Card> items);
        void startInsertCard();
    }

}
