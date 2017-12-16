package br.com.sailboat.flashcards.view.tag.details;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import br.com.sailboat.canoe.base.BaseFragment;
import br.com.sailboat.canoe.dialog.TwoOptionsDialog;
import br.com.sailboat.canoe.helper.DialogHelper;
import br.com.sailboat.canoe.recycler.RecyclerItem;
import br.com.sailboat.flashcards.R;
import br.com.sailboat.flashcards.helper.ExtrasHelper;
import br.com.sailboat.flashcards.model.Tag;
import br.com.sailboat.flashcards.view.card.details.CardDetailsActivity;
import br.com.sailboat.flashcards.view.play.PlayActivity;
import br.com.sailboat.flashcards.view.tag.InsertTagDialog;

public class TagDetailsFragment extends BaseFragment<TagDetailsPresenter> implements TagDetailsPresenter.View, TagDetailsAdapter.Callback {


    public static TagDetailsFragment newInstance(long tagId) {
        Bundle bundle = new Bundle();
        ExtrasHelper.putTagId(tagId, bundle);

        TagDetailsFragment fragment = new TagDetailsFragment();
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    protected TagDetailsPresenter newPresenterInstance() {
        return new TagDetailsPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_tag_details;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_tag_details, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    protected void onInitToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        toolbar.setTitle(R.string.title_tag_details);
    }

    @Override
    protected void onInitRecycler() {
        recycler.setAdapter(new TagDetailsAdapter(this));
    }

    @Override
    protected void onInitFab() {
        fab.setImageResource(R.drawable.ic_edit_white_24dp);
    }

    @Override
    protected void postActivityResult(int requestCode, Intent data) {
        getPresenter().postActivityResult();
    }

    public void onClickTag(int position) {
        presenter.onClickCard(position);
    }

    @Override
    public void showDialogDeleteTag() {
        DialogHelper.showDeleteDialog(getFragmentManager(), getActivity(), new TwoOptionsDialog.PositiveCallback() {
            @Override
            public void onClickPositiveOption() {
                getPresenter().onClickDeleteTag();
            }
        });
    }

    @Override
    public void startInsertTag(Tag tag) {
        InsertTagDialog.show(getFragmentManager(), tag, new InsertTagDialog.Callback() {
            @Override
            public void onClickOk() {
                presenter.onInsertTag();
            }
        });
    }

    @Override
    public void startCardDetails(long cardId) {
        CardDetailsActivity.start(this, cardId);
    }

    @Override
    public void startPlayActivity(long tagId) {
        PlayActivity.start(this, tagId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_play: {
                presenter.onClickMenuPlay();
                return true;
            }
            case R.id.menu_delete: {
                getPresenter().onClickMenuDelete();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public List<RecyclerItem> getRecyclerItemList() {
        return presenter.getRecyclerItemList();
    }

    @Override
    public void onClickCard(int position) {
        presenter.onClickCard(position);
    }
}
