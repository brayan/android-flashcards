package br.com.sailboat.flashcards.view.detail;

import android.os.AsyncTask;
import android.os.Bundle;

import java.util.List;

import br.com.sailboat.canoe.base.BasePresenter;
import br.com.sailboat.canoe.helper.AsyncHelper;
import br.com.sailboat.canoe.recycler.RecyclerItem;
import br.com.sailboat.flashcards.helper.ExtrasHelper;
import br.com.sailboat.flashcards.interactor.CardDetailsLoader;
import br.com.sailboat.flashcards.model.Card;
import br.com.sailboat.flashcards.model.Tag;
import br.com.sailboat.flashcards.persistence.sqlite.CardSQLite;
import br.com.sailboat.flashcards.persistence.sqlite.CardTagSQLite;


public class CardDetailPresenter extends BasePresenter<CardDetailPresenter.View> {

    private CardDetailViewModel viewModel = new CardDetailViewModel();

    public CardDetailPresenter(View view) {
        super(view);
    }

    @Override
    public void extractExtrasFromArguments(Bundle arguments) {
        long cardId = ExtrasHelper.getCardId(arguments);
        getViewModel().setCardId(cardId);
    }

    @Override
    protected void postResume() {
        loadDetails();
    }

    @Override
    public void onClickFab() {
        getView().startInsertCardActivity(getViewModel().getCardId());
    }

    public void onClickMenuDelete() {
        getView().showDialogDeleteCard();
    }

    public void postActivityResult() {
        loadDetails();
    }

    public void onClickDeleteTask() {
        showProgress();

        AsyncHelper.execute(new AsyncHelper.Callback() {

            @Override
            public void doInBackground() throws Exception {
                long cardId = getViewModel().getCardId();
                CardTagSQLite.newInstance(getContext()).deleteByCardId(cardId);
                CardSQLite.newInstance(getContext()).delete(cardId);
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

    public void onClickTag(int position) {
        Tag tag = (Tag) viewModel.getRecyclerItemList().get(position);
        view.startTagDetails(tag.getId());
    }

    private void updateMetricsViews() {
        getView().setRightAnswers(String.valueOf(getViewModel().getCardMetrics().getRightAnswers()));
        getView().setWrongAnswers(String.valueOf(getViewModel().getCardMetrics().getWrongAnswers()));
        getView().setTitle("");
    }

    private void loadDetails() {
        AsyncHelper.execute(AsyncTask.THREAD_POOL_EXECUTOR, new AsyncHelper.Callback() {

            Card card;
            List<RecyclerItem> cardDetails;

            @Override
            public void doInBackground() throws Exception {
                long cardId = getViewModel().getCardId();

                card = CardSQLite.newInstance(getContext()).getCardById(cardId);
                cardDetails = CardDetailsLoader.loadCardDetails(getContext(), cardId);

//                TaskHistorySQLite sqlite = TaskHistorySQLite.newInstance(getContext());
//                getViewModel().getTaskMetrics().setDoneTasks(sqlite.getAmountOfDoneTasks(cardId));
//                getViewModel().getTaskMetrics().setNotDoneTasks(sqlite.getAmountOfNotDoneTasks(cardId));

            }

            @Override
            public void onSuccess() {
                updateViewModel();
                updateContentViews();
            }

            @Override
            public void onFail(Exception e) {
                printLogAndShowDialog(e);
                closeActivityWithResultCanceled();
            }

            private void updateViewModel() {
                getViewModel().setCard(card);

                getViewModel().getRecyclerItemList().clear();
                getViewModel().getRecyclerItemList().addAll(cardDetails);
            }

        });

    }

    private void updateContentViews() {
        updateMetricsViews();
        view.updateRecycler();
    }

    private CardDetailViewModel getViewModel() {
        return viewModel;
    }

    public interface View extends BasePresenter.View {
        void setRightAnswers(String amount);
        void setWrongAnswers(String amount);
        void showDialogDeleteCard();
        void startInsertCardActivity(long taskId);
        void startTagDetails(long projectId);
    }

}
