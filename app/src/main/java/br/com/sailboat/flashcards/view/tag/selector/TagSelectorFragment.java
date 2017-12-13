package br.com.sailboat.flashcards.view.tag.selector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import br.com.sailboat.canoe.base.BaseFragment;
import br.com.sailboat.canoe.dialog.InputTextDialog;
import br.com.sailboat.canoe.recycler.RecyclerItem;
import br.com.sailboat.flashcards.R;
import br.com.sailboat.flashcards.helper.ExtrasHelper;
import br.com.sailboat.flashcards.model.Tag;


public class TagSelectorFragment extends BaseFragment<TagSelectorPresenter> implements TagSelectorPresenter.View, TagSelectorAdapter.Callback {


    public static TagSelectorFragment newInstance(List<Tag> tags) {
        Bundle args = new Bundle();
        ExtrasHelper.putTags(tags, args);
        TagSelectorFragment fragment = new TagSelectorFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.frg_tag_selector;
    }

    @Override
    protected TagSelectorPresenter newPresenterInstance() {
        return new TagSelectorPresenter(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_tag_selector, menu);
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
    public void closeActivityResultOk(List<Tag> tasks) {
        Intent data = new Intent();
        ExtrasHelper.putTags(tasks, data);
        getActivity().setResult(Activity.RESULT_OK, data);
        getActivity().finish();
    }

    @Override
    public void startInsertTag() {
        InputTextDialog.show(getFragmentManager(), null, new InputTextDialog.Callback() {
            @Override
            public void onClickOk(String text) {
                presenter.onClickOkInsertTag(text);
            }
        });
    }

    @Override
    public void onClickTag(int position) {
        presenter.onClickItem(position);
    }

    @Override
    protected void postActivityResult(int requestCode, Intent data) {
        presenter.postResult();
    }

    @Override
    public boolean isTagSelected(Tag item) {
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
        recycler.setAdapter(new TagSelectorAdapter(this));
    }

    @Override
    public List<RecyclerItem> getRecyclerItemList() {
        return presenter.getItemList();
    }


}
