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
import br.com.sailboat.canoe.recycler.RecyclerItem;
import br.com.sailboat.flashcards.R;
import br.com.sailboat.flashcards.helper.ExtrasHelper;
import br.com.sailboat.flashcards.view.card.details.CardDetailsActivity;

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
        inflater.inflate(R.menu.menu_delete, menu);
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
        TwoOptionsDialog dialog = new TwoOptionsDialog();
        dialog.setMessage(getString(R.string.are_you_sure));
        dialog.setPositiveMsg(getString(R.string.delete));
        dialog.setPositiveCallback(new TwoOptionsDialog.PositiveCallback() {
            @Override
            public void onClickPositiveOption() {
                getPresenter().onClickDeleteTag();
            }
        });
        dialog.show(getFragmentManager(), TwoOptionsDialog.class.getCanonicalName());
    }

    @Override
    public void startInsertTag(long cardId) {
        // TODO:
    }

    @Override
    public void startCardDetails(long cardId) {
        CardDetailsActivity.start(this, cardId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
    public void onLongClickCard(int position) {
        presenter.onClickCard(position);
    }
}
