package br.com.sailboat.flashcards.view.tag.list;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import br.com.sailboat.canoe.base.BaseFragment;
import br.com.sailboat.canoe.helper.ScrollHelper;
import br.com.sailboat.flashcards.R;
import br.com.sailboat.flashcards.view.play.PlayActivity;
import br.com.sailboat.flashcards.view.tag.details.TagDetailsActivity;
import br.com.sailboat.flashcards.view.tag.insert.InsertTagActivity;


public class TagListFragment extends BaseFragment<TagListPresenter> implements TagListPresenter.View {

    @Override
    protected int getLayoutId() {
        return R.layout.frg_list;
    }

    @Override
    protected TagListPresenter newPresenterInstance() {
        return new TagListPresenter(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_tag_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_play: {
                presenter.onClickMenuPlay();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }

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
    public void startTagDetailsActivity(long tagId) {
        TagDetailsActivity.start(this, tagId);
    }

    @Override
    public void startPlayActivity() {
        PlayActivity.start(this);
    }

    @Override
    public void startNewTagDialog() {
        InsertTagActivity.start(this);
    }

    @Override
    protected void postActivityResult(int requestCode, Intent data) {
        getPresenter().postActivityResult();
    }

}