package br.com.sailboat.flashcards.view.tag.insert;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.List;

import br.com.sailboat.canoe.base.BaseFragment;
import br.com.sailboat.canoe.recycler.RecyclerItem;
import br.com.sailboat.canoe.recycler.SwipeLeftRight;
import br.com.sailboat.flashcards.R;
import br.com.sailboat.flashcards.helper.ExtrasHelper;
import br.com.sailboat.flashcards.helper.RequestCodeHelper;
import br.com.sailboat.flashcards.model.Card;
import br.com.sailboat.flashcards.view.card.details.CardDetailsActivity;
import br.com.sailboat.flashcards.view.card.list.CardListAdapter;
import br.com.sailboat.flashcards.view.card.selector.CardSelectorActivity;


public class InsertTagFragment extends BaseFragment<InsertTagPresenter> implements InsertTagPresenter.View, CardListAdapter.Callback {

    private EditText etTag;


    public static InsertTagFragment newInstance() {
        return new InsertTagFragment();
    }

    public static InsertTagFragment newInstanceWithTagToEdit(long tagId) {
        Bundle bundle = new Bundle();
        ExtrasHelper.putTagId(tagId, bundle);

        InsertTagFragment fragment = new InsertTagFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    public static InsertTagFragment newInstanceWithCard(long cardId) {
        Bundle bundle = new Bundle();
        ExtrasHelper.putCardId(cardId, bundle);

        InsertTagFragment fragment = new InsertTagFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_insert_tag;
    }

    @Override
    protected InsertTagPresenter newPresenterInstance() {
        return new InsertTagPresenter(this);
    }

    @Override
    protected void initEmptyViewMessages() {
        setEmptyViewMessage1(getString(R.string.no_cards_found));
        setEmptyViewMessage2(getString(R.string.ept_click_to_add));
    }

    @Override
    protected void initViews() {
        initEditTexts();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_save, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save: {
                getPresenter().onClickMenuSave();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }

    }

    @Override
    protected void onActivityResultOk(int requestCode, Intent data) {

        switch (requestCode) {
            case RequestCodeHelper.SELECTOR_CARD: {
                getPresenter().onActivityResultOkSelectCard(data);
                return;
            }
        }

    }

    @Override
    public void setTagText(String tag) {
        etTag.setText(tag);
        etTag.setSelection(etTag.length());
    }

    @Override
    public String getTagText() {
        return etTag.getText().toString();
    }

    @Override
    public void startCardDetails(long cardId) {
        CardDetailsActivity.start(this, cardId);
    }

    @Override
    public void startCardSelector(List<Card> selectedCards) {
        CardSelectorActivity.start(this, selectedCards);
    }

    private void initEditTexts() {
        etTag = getView().findViewById(R.id.frg_insert_tag__et__tag);
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
        recycler.setAdapter(new CardListAdapter(this));
        ItemTouchHelper.Callback callback = new SwipeLeftRight(getActivity(), new SwipeLeftRight.Callback() {
            @Override
            public void onItemDismiss(int position) {
                presenter.onCardDismiss(position);
            }
        });
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recycler);
    }

    @Override
    public void onClickCard(int position) {
        presenter.onClickCard(position);
    }

    @Override
    public List<RecyclerItem> getRecyclerItemList() {
        return presenter.getRecyclerItemList();
    }
}
