package br.com.sailboat.flashcards.view.tag.list;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;

import br.com.sailboat.canoe.base.BaseFragment;
import br.com.sailboat.canoe.dialog.InputTextDialog;
import br.com.sailboat.canoe.helper.ScrollHelper;
import br.com.sailboat.flashcards.R;
import br.com.sailboat.flashcards.view.tag.details.TagDetailsActivity;


public class TagListFragment extends BaseFragment<TagListPresenter> implements TagListPresenter.View {

    @Override
    protected int getLayoutId() {
        return R.layout.frg_tag_list;
    }

    @Override
    protected TagListPresenter newPresenterInstance() {
        return new TagListPresenter(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    protected void initEmptyViewMessages() {
        setEmptyViewMessage1(getString(R.string.no_tags_found));
        setEmptyViewMessage2(getString(R.string.ept_click_to_add));
    }

    @Override
    protected void onInitToolbar() {
        toolbar.setTitle(R.string.app_name);
    }

    @Override
    protected void onInitRecycler() {
        recycler.setAdapter(new TagListAdapter(getPresenter()));
    }

    @Override
    protected void onInitFab() {
        ScrollHelper.hideFabWhenScrolling(recycler, fab);
    }

    @Override
    public void startTagDetailsActivity(long taskId) {
        TagDetailsActivity.start(this, taskId);
    }

    @Override
    public void startNewTagDialog() {
        InputTextDialog.show(getFragmentManager(), null, new InputTextDialog.Callback() {
            @Override
            public void onClickOk(String text) {
                presenter.onClickOkInputTag(text);
            }
        });
    }

    @Override
    protected void postActivityResult(int requestCode, Intent data) {
        getPresenter().postActivityResult();
    }

}