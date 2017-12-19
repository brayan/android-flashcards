package br.com.sailboat.flashcards.view.card.selector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import br.com.sailboat.canoe.base.BaseFragment;
import br.com.sailboat.canoe.recycler.RecyclerItem;
import br.com.sailboat.flashcards.R;
import br.com.sailboat.flashcards.helper.ExtrasHelper;
import br.com.sailboat.flashcards.model.Card;
import br.com.sailboat.flashcards.view.card.insert.InsertCardActivity;


public class CardSelectorFragment extends BaseFragment<CardSelectorPresenter> implements CardSelectorPresenter.View, CardSelectorAdapter.Callback {


    public static CardSelectorFragment newInstance(List<Card> cards) {
        Bundle args = new Bundle();
        ExtrasHelper.putCards(cards, args);
        CardSelectorFragment fragment = new CardSelectorFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.frg_list;
    }

    @Override
    protected CardSelectorPresenter newPresenterInstance() {
        return new CardSelectorPresenter(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_selector, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_confirm: {
                getPresenter().onClickConfirm();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }

    }

    @Override
    public void closeActivityResultOk(List<Card> cards) {
        Intent data = new Intent();
        ExtrasHelper.putCards(cards, data);
        getActivity().setResult(Activity.RESULT_OK, data);
        getActivity().finish();
    }

    @Override
    public void startInsertCard() {
        InsertCardActivity.start(this);
    }

    @Override
    public void onClickCard(int position) {
        presenter.onClickItem(position);
    }

    @Override
    protected void postActivityResult(int requestCode, Intent data) {
        presenter.postResult();
    }

    @Override
    public boolean isItemSelected(Card item) {
        return getPresenter().isItemSelected(item);
    }

    @Override
    protected void onInitToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    protected void onInitRecycler() {
        recycler.setAdapter(new CardSelectorAdapter(this));
    }

    @Override
    public List<RecyclerItem> getRecyclerItemList() {
        return presenter.getItemList();
    }


}
