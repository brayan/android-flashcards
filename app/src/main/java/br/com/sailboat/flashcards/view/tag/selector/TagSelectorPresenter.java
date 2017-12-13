package br.com.sailboat.flashcards.view.tag.selector;

import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import br.com.sailboat.canoe.base.BasePresenter;
import br.com.sailboat.canoe.helper.AsyncHelper;
import br.com.sailboat.canoe.helper.LogHelper;
import br.com.sailboat.canoe.helper.StringHelper;
import br.com.sailboat.canoe.recycler.RecyclerItem;
import br.com.sailboat.flashcards.R;
import br.com.sailboat.flashcards.helper.ExtrasHelper;
import br.com.sailboat.flashcards.model.Tag;
import br.com.sailboat.flashcards.persistence.filter.Filter;
import br.com.sailboat.flashcards.persistence.sqlite.TagSQLite;

public class TagSelectorPresenter extends BasePresenter<TagSelectorPresenter.View> {

    private TagSelectorViewModel viewModel = new TagSelectorViewModel();

    public TagSelectorPresenter(TagSelectorPresenter.View view) {
        super(view);
        initFilter();
    }

    @Override
    public void extractExtrasFromArguments(Bundle arguments) {
        List<Tag> tags = ExtrasHelper.getTags(arguments);
        addSelectedItems(tags);
    }

    @Override
    protected void postResume() {
        loadItems();
    }

    public void onClickItem(int position) {
        Tag tag = (Tag) getItemList().get(position);

        updateSelectedItemArray(tag);
        updateTitle();
        updateItemView(position);
    }

    public void onClickConfirm() {
        List<Tag> items = getItemsListFromLinkedHashMap();
        getView().closeActivityResultOk(items);
    }

    @Override
    public void onClickFab() {
        view.startInsertTag();
    }

    public List<RecyclerItem> getItemList() {
        return viewModel.getRecyclerItemList();
    }

    public LinkedHashMap<Long, Long> getSelectedItems() {
        return viewModel.getSelectedItems();
    }

    public boolean isItemSelected(Tag tag) {
        return (getSelectedItems().get(tag.getId()) != null);
    }

    @Override
    protected void onQueryTextChange() {
        viewModel.getFilter().setSearchText(getSearchText());
        loadItems();
    }

    public void postResult() {
        loadItems();
    }

    private void initFilter() {
        viewModel.setFilter(new Filter());
    }

    private void loadItems() {

        AsyncHelper.execute(new AsyncHelper.Callback() {

            List<Tag> tags = new ArrayList<>();

            @Override
            public void doInBackground() throws Exception {
                tags = TagSQLite.newInstance(getContext()).getAll(viewModel.getFilter());
            }

            @Override
            public void onSuccess() {
                viewModel.getRecyclerItemList().clear();
                viewModel.getRecyclerItemList().addAll(tags);
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

    private void addSelectedItems(List<Tag> tags) {
        LinkedHashMap<Long, Long> selectedTags = viewModel.getSelectedItems();

        for (Tag t : tags) {
            selectedTags.put(t.getId(), t.getId());
        }
    }

    private void updateItemView(int position) {
        getView().updateRecyclerItemChanged(position);
    }

    private void updateSelectedItemArray(Tag item) {
        LinkedHashMap<Long, Long> selectedTags = viewModel.getSelectedItems();

        if (isItemSelected(item)) {
            selectedTags.remove(item.getId());
        } else {
            selectedTags.put(item.getId(), item.getId());
        }
    }

    @NonNull
    private List<Tag> getItemsListFromLinkedHashMap() {
        LinkedHashMap<Long, Long> selectedTags = viewModel.getSelectedItems();

        List<Tag> tags = new ArrayList<>();

        for (long id : selectedTags.values()) {
            try {
                Tag tag = TagSQLite.newInstance(getContext()).getTagById(id);
                tags.add(tag);
            } catch (Exception e) {
                LogHelper.logException(e);
            }
        }

        return tags;
    }

    public void onClickOkInsertTag(String text) {
        try {
            if (StringHelper.isNotEmpty(text)) {
                Tag tag = new Tag();
                tag.setName(text);

                TagSQLite.newInstance(getContext()).save(tag);

                loadItems();

            } else {
                view.showMessageDialog(getString(R.string.msg_insert_tag));
            }

        } catch (Exception e) {
            LogHelper.logException(e);
            view.showMessageDialog(e.getMessage());
        }
    }


    public interface View extends BasePresenter.View {
        void closeActivityResultOk(List<Tag> items);
        void startInsertTag();
    }

}
