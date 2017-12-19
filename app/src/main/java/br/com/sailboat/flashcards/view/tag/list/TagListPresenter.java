package br.com.sailboat.flashcards.view.tag.list;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import br.com.sailboat.canoe.base.BasePresenter;
import br.com.sailboat.canoe.filter.Filter;
import br.com.sailboat.canoe.helper.AsyncHelper;
import br.com.sailboat.canoe.recycler.RecyclerItem;
import br.com.sailboat.flashcards.model.Tag;
import br.com.sailboat.flashcards.persistence.sqlite.TagSQLite;

public class TagListPresenter extends BasePresenter<TagListPresenter.View> implements TagListAdapter.Callback {

    private TagListViewModel viewModel = new TagListViewModel();

    public TagListPresenter(TagListPresenter.View view) {
        super(view);
        initFilter();
    }

    @Override
    protected void postResume() {
        loadTags();
    }

    @Override
    public void onClickTag(int position) {
        Tag tag = (Tag) getRecyclerItemList().get(position);
        view.startTagDetailsActivity(tag.getId());
    }

    @Override
    public void onClickFab() {
        view.startNewTagDialog();
    }

    public void onClickOkInsertTag() {
        loadTags();
    }

    public void postActivityResult() {
        loadTags();
    }

    @Override
    public List<RecyclerItem> getRecyclerItemList() {
        return viewModel.getRecyclerItemList();
    }

    @Override
    protected void onQueryTextChange() {
        viewModel.getFilter().setSearchText(getSearchText());
        loadTags();
    }

    private void initFilter() {
        viewModel.setFilter(new Filter());
    }

    private void loadTags() {
        AsyncHelper.execute(AsyncTask.THREAD_POOL_EXECUTOR, new AsyncHelper.Callback() {

            List<RecyclerItem> tags = new ArrayList<>();

            @Override
            public void doInBackground() throws Exception {
                List<Tag> tagsList = TagSQLite.newInstance(getContext()).getAll(viewModel.getFilter());
                tags.addAll(tagsList);
            }

            @Override
            public void onSuccess() {
                getRecyclerItemList().clear();
                getRecyclerItemList().addAll(tags);
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
        if (isRecyclerEmpty()) {
            view.hideRecycler();
            view.showEmptyView();
            view.expandToolbar();
        } else {
            view.showRecycler();
            view.hideEmptyView();
        }
    }

    protected boolean isRecyclerEmpty() {
        return getRecyclerItemList().isEmpty();
    }

    public void onClickMenuPlay() {
        view.startPlayActivity();
    }


    public interface View extends BasePresenter.View {
        void startNewTagDialog();
        void startTagDetailsActivity(long tagId);
        void startPlayActivity();
    }


}
