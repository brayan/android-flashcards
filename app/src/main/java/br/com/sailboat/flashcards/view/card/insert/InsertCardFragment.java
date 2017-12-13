package br.com.sailboat.flashcards.view.card.insert;

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
import br.com.sailboat.flashcards.model.Tag;
import br.com.sailboat.flashcards.view.tag.details.TagDetailsActivity;
import br.com.sailboat.flashcards.view.tag.list.TagListAdapter;
import br.com.sailboat.flashcards.view.tag.selector.TagSelectorActivity;


public class InsertCardFragment extends BaseFragment<InsertCardPresenter> implements InsertCardPresenter.View, TagListAdapter.Callback {

    private EditText etFront;
    private EditText etBack;


    public static InsertCardFragment newInstance() {
        return new InsertCardFragment();
    }

    public static InsertCardFragment newInstanceWithTaskToEdit(long taskId) {
        Bundle bundle = new Bundle();
        ExtrasHelper.putCardId(taskId, bundle);

        InsertCardFragment fragment = new InsertCardFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    public static InsertCardFragment newInstanceWithProject(long projectId) {
        Bundle bundle = new Bundle();
        ExtrasHelper.putTagId(projectId, bundle);

        InsertCardFragment fragment = new InsertCardFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_insert_card;
    }

    @Override
    protected InsertCardPresenter newPresenterInstance() {
        return new InsertCardPresenter(this);
    }

    @Override
    protected void initEmptyViewMessages() {
        setEmptyViewMessage1(getString(R.string.no_tags_found));
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
            case RequestCodeHelper.SELECTOR_TAG: {
                getPresenter().onActivityResultOkSelectTag(data);
                return;
            }
        }

    }

    @Override
    public void setFront(String front) {
        etFront.setText(front);
        etFront.setSelection(etFront.length());
    }

    @Override
    public void setBack(String back) {
        etBack.setText(back);
    }

    @Override
    public String getFront() {
        return etFront.getText().toString();
    }

    @Override
    public String getBack() {
        return etBack.getText().toString();
    }

    @Override
    public void startTagDetails(long tagId) {
        TagDetailsActivity.start(this, tagId);
    }

    @Override
    public void startTagSelector(List<Tag> selectedTags) {
        TagSelectorActivity.start(this, selectedTags);
    }

    private void initEditTexts() {
        etFront = getView().findViewById(R.id.frg_insert_card__et__front);
        etBack = getView().findViewById(R.id.frg_insert_card__et__back);
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
        recycler.setAdapter(new TagListAdapter(this));
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
    public void onClickTag(int position) {
        presenter.onClickTag(position);
    }

    @Override
    public List<RecyclerItem> getRecyclerItemList() {
        return presenter.getRecyclerItemList();
    }
}
