package br.com.sailboat.flashcards.view.card.details;

import android.os.AsyncTask;
import android.os.Bundle;

import java.util.List;

import br.com.sailboat.canoe.base.BasePresenter;
import br.com.sailboat.canoe.helper.AsyncHelper;
import br.com.sailboat.canoe.recycler.RecyclerItem;
import br.com.sailboat.flashcards.helper.ExtrasHelper;
import br.com.sailboat.flashcards.interactor.loader.CardDetailsLoader;
import br.com.sailboat.flashcards.model.Card;
import br.com.sailboat.flashcards.model.Tag;
import br.com.sailboat.flashcards.model.view.CardMetrics;
import br.com.sailboat.flashcards.persistence.sqlite.CardHistorySQLite;
import br.com.sailboat.flashcards.persistence.sqlite.CardSQLite;
import br.com.sailboat.flashcards.persistence.sqlite.CardTagSQLite;


public class CardDetailsPresenter extends BasePresenter<CardDetailsPresenter.View> {

    private CardDetailsViewModel viewModel = new CardDetailsViewModel();

    public CardDetailsPresenter(View view) {
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

    public void onClickDeleteCard() {
        showProgress();

        AsyncHelper.execute(new AsyncHelper.Callback() {

            @Override
            public void doInBackground() throws Exception {
                long cardId = getViewModel().getCardId();
                CardTagSQLite.newInstance(getContext()).deleteByCard(cardId);
                CardHistorySQLite.newInstance(getContext()).deleteByCard(cardId);
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

                CardMetrics cardMetrics = viewModel.getCardMetrics();

                CardHistorySQLite sqlite = CardHistorySQLite.newInstance(getContext());
                cardMetrics.setRightAnswers(sqlite.getAmountOfRightAnswers(cardId));
                cardMetrics.setWrongAnswers(sqlite.getAmountOfWrongAnswers(cardId));
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

    private CardDetailsViewModel getViewModel() {
        return viewModel;
    }


    public interface View extends BasePresenter.View {
        void setRightAnswers(String amount);
        void setWrongAnswers(String amount);
        void showDialogDeleteCard();
        void startInsertCardActivity(long cardId);
        void startTagDetails(long tagId);
    }

}
