package br.com.sailboat.flashcards.view.tag.details;

import android.os.AsyncTask;
import android.os.Bundle;

import java.util.List;

import br.com.sailboat.canoe.base.BasePresenter;
import br.com.sailboat.canoe.helper.AsyncHelper;
import br.com.sailboat.canoe.helper.LogHelper;
import br.com.sailboat.canoe.recycler.RecyclerItem;
import br.com.sailboat.flashcards.helper.ExtrasHelper;
import br.com.sailboat.flashcards.interactor.TagDetailsLoader;
import br.com.sailboat.flashcards.model.Card;
import br.com.sailboat.flashcards.model.Tag;
import br.com.sailboat.flashcards.persistence.sqlite.CardTagSQLite;
import br.com.sailboat.flashcards.persistence.sqlite.TagSQLite;


public class TagDetailsPresenter extends BasePresenter<TagDetailsPresenter.View> {

    private TagDetailsViewModel viewModel = new TagDetailsViewModel();

    public TagDetailsPresenter(View view) {
        super(view);
    }

    @Override
    public void extractExtrasFromArguments(Bundle arguments) {
        long tagId = ExtrasHelper.getTagId(arguments);
        getViewModel().setTagId(tagId);
    }

    @Override
    protected void postResume() {
        loadDetails();
    }

    @Override
    public void onClickFab() {
        // TODO
        getView().startInsertTag(getViewModel().getTagId());
    }

    public void onClickMenuDelete() {
        getView().showDialogDeleteTag();
    }

    public void postActivityResult() {
        loadDetails();
    }

    public void onClickDeleteTag() {
        showProgress();

        AsyncHelper.execute(new AsyncHelper.Callback() {

            @Override
            public void doInBackground() throws Exception {
                long tagId = getViewModel().getTagId();
                CardTagSQLite.newInstance(getContext()).deleteByTagId(tagId);
                TagSQLite.newInstance(getContext()).delete(tagId);
            }

            @Override
            public void onSuccess() {
                dismissProgress();
                closeActivityWithResultOk();
            }

            @Override
            public void onFail(Exception e) {
                dismissProgress();
                printLogAndShowDialog(e);
            }

        });

    }

    public List<RecyclerItem> getRecyclerItemList() {
        return viewModel.getRecyclerItemList();
    }

    public void onClickCard(int position) {
        Card card = (Card) viewModel.getRecyclerItemList().get(position);
        view.startCardDetails(card.getId());
    }

    private void loadDetails() {
        AsyncHelper.execute(AsyncTask.THREAD_POOL_EXECUTOR, new AsyncHelper.Callback() {

            Tag tag;
            List<RecyclerItem> tagDetails;

            @Override
            public void doInBackground() throws Exception {
                long tagId = getViewModel().getTagId();

                tag = TagSQLite.newInstance(getContext()).getTagById(tagId);
                tagDetails = TagDetailsLoader.loadDetails(getContext(), tagId);

            }

            @Override
            public void onSuccess() {
                updateViewModel();
                updateContentViews();
            }

            @Override
            public void onFail(Exception e) {
                LogHelper.logException(e);
                view.showToast(e.getMessage());
                closeActivityWithResultCanceled();
            }

            private void updateViewModel() {
                getViewModel().setTag(tag);

                getViewModel().getRecyclerItemList().clear();
                getViewModel().getRecyclerItemList().addAll(tagDetails);
            }

        });

    }

    private void updateContentViews() {
        view.updateRecycler();
    }

    private TagDetailsViewModel getViewModel() {
        return viewModel;
    }

    public interface View extends BasePresenter.View {
        void showDialogDeleteTag();
        void startInsertTag(long taskId);
        void startCardDetails(long cardId);
    }

}
