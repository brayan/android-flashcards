package br.com.sailboat.flashcards.view.play.card_play;

import android.os.AsyncTask;
import android.os.Bundle;

import br.com.sailboat.canoe.base.BasePresenter;
import br.com.sailboat.canoe.helper.AsyncHelper;
import br.com.sailboat.canoe.helper.EntityHelper;
import br.com.sailboat.canoe.helper.LogHelper;
import br.com.sailboat.flashcards.helper.ExtrasHelper;
import br.com.sailboat.flashcards.model.Card;
import br.com.sailboat.flashcards.model.view.CardAnswer;
import br.com.sailboat.flashcards.model.view.CardPlay;
import br.com.sailboat.flashcards.persistence.sqlite.CardSQLite;


public class CardPlayPresenter extends BasePresenter<CardPlayPresenter.View> {

    private CardPlayViewModel viewModel = new CardPlayViewModel();

    public CardPlayPresenter(View view) {
        super(view);
    }

    @Override
    public void extractExtrasFromArguments(Bundle arguments) {
        extractCard(arguments);
    }

    @Override
    protected void postResume() {
        loadInfo();
    }

    private void loadInfo() {
        AsyncHelper.execute(AsyncTask.THREAD_POOL_EXECUTOR, new AsyncHelper.Callback() {

            Card card;

            @Override
            public void doInBackground() throws Exception {
                long cardId = viewModel.getCardPlay().getCardId();
                card = CardSQLite.newInstance(getContext()).getCardById(cardId);
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
                viewModel.setCard(card);
            }

        });
    }

    public void onClickShowBackOfTheCard() {
        viewModel.setShowingBackOfTheCard(!isShowingBackOfTheCard());

        if (isShowingBackOfTheCard()) {
            getView().showBackOfTheCardWithAnimation();
        } else {
            getView().hideBackOfTheCardWithAnimation();
        }
    }

    private void updateContentViews() {
        view.setFrontOfTheCard(viewModel.getCard().getFront());
        view.setBackOfTheCard(viewModel.getCard().getBack());
        updateAlarmViews();
        updateRightOrWrongButtons();
    }

    private void updateRightOrWrongButtons() {
        if (viewModel.getCardPlay().getAnswer() == CardAnswer.RIGHT) {
            view.setRightAnswerAsSelected();
            view.setWrongAnswerAsNotSelected();
        } else if (viewModel.getCardPlay().getAnswer() == CardAnswer.WRONG) {
            view.setWrongAnswerAsSelected();
            view.setRightAnswerAsNotSelected();
        } else {
            view.setRightAnswerAsNotSelected();
            view.setWrongAnswerAsNotSelected();
        }
    }

    private void updateRightOrWrongButtonsWithAnimation() {
        if (viewModel.getCardPlay().getAnswer() == CardAnswer.RIGHT) {
            view.setWrongAnswerAsNotSelected();
            view.setRightAnswerAsSelectedWithAnimation();
        } else if (viewModel.getCardPlay().getAnswer() == CardAnswer.WRONG) {
            view.setRightAnswerAsNotSelected();
            view.setWrongAnswerAsSelectedWithAnimation();
        } else {
            view.setRightAnswerAsNotSelected();
            view.setWrongAnswerAsNotSelected();
        }
    }

    private void updateAlarmViews() {
        if (viewModel.getCardPlay().getAnswer() != EntityHelper.NO_ID) {
            viewModel.setShowingBackOfTheCard(true);
        }

        if (isShowingBackOfTheCard()) {
            getView().showBackOfTheCard();
        } else {
            getView().hideBackOfTheCard();
        }
    }

    private boolean isShowingBackOfTheCard() {
        return viewModel.isShowingBackOfTheCard();
    }

    private void extractCard(Bundle bundle) {
        CardPlay cardPlay = ExtrasHelper.getCardPlay(bundle);
        viewModel.setCardPlay(cardPlay);
    }

    public void onClickRightAnswer() {
        if (viewModel.getCardPlay().getAnswer() != CardAnswer.RIGHT) {
            viewModel.getCardPlay().setAnswer(CardAnswer.RIGHT);
            updateRightOrWrongButtonsWithAnimation();
        }
    }

    public void onClickWrongAnswer() {
        if (viewModel.getCardPlay().getAnswer() != CardAnswer.WRONG) {
            viewModel.getCardPlay().setAnswer(CardAnswer.WRONG);
            updateRightOrWrongButtonsWithAnimation();
        }
    }

    public void onAnimationEnd() {
        view.onClickAnswer(viewModel.getCardPlay());
    }


    public interface View extends BasePresenter.View {
        void showBackOfTheCard();
        void showBackOfTheCardWithAnimation();
        void hideBackOfTheCard();
        void hideBackOfTheCardWithAnimation();
        void setFrontOfTheCard(String front);
        void setBackOfTheCard(String back);
        void setRightAnswerAsSelected();
        void setWrongAnswerAsNotSelected();
        void setWrongAnswerAsSelected();
        void setRightAnswerAsNotSelected();
        void onClickAnswer(CardPlay cardPlay);
        void setRightAnswerAsSelectedWithAnimation();
        void setWrongAnswerAsSelectedWithAnimation();
    }


}
