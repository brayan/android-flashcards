package br.com.sailboat.flashcards.view.play;

import android.os.AsyncTask;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.sailboat.canoe.base.BasePresenter;
import br.com.sailboat.canoe.helper.AsyncHelper;
import br.com.sailboat.canoe.helper.EntityHelper;
import br.com.sailboat.flashcards.helper.ExtrasHelper;
import br.com.sailboat.flashcards.model.CardHistory;
import br.com.sailboat.flashcards.model.view.CardAnswer;
import br.com.sailboat.flashcards.model.view.CardMetrics;
import br.com.sailboat.flashcards.model.view.CardPlay;
import br.com.sailboat.flashcards.persistence.sqlite.CardHistorySQLite;
import br.com.sailboat.flashcards.persistence.sqlite.CardSQLite;

public class PlayPresenter extends BasePresenter<PlayPresenter.View> {

    private PlayViewModel viewModel = new PlayViewModel();

    public PlayPresenter(PlayPresenter.View view) {
        super(view);
    }

    @Override
    public void extractExtrasFromArguments(Bundle arguments) {
        long tagId = ExtrasHelper.getTagId(arguments);
        viewModel.setTagId(tagId);
    }

    @Override
    protected void onResumeFirstSession() {
        loadCardPlayList();
    }

    @Override
    protected void onResumeAfterRestart() {
        updateContentViews();
    }

    public void postActivityResult() {
        loadCardPlayList();
    }

    private void loadCardPlayList() {
        AsyncHelper.execute(AsyncTask.THREAD_POOL_EXECUTOR, new AsyncHelper.Callback() {

            List<CardPlay> cards = new ArrayList<>();

            @Override
            public void doInBackground() throws Exception {
                if (viewModel.getTagId() != EntityHelper.NO_ID) {
                    cards = CardSQLite.newInstance(getContext()).getCardPlayListByTag(viewModel.getTagId());
                } else {
                    cards = CardSQLite.newInstance(getContext()).getCardPlayList();
                }

            }

            @Override
            public void onSuccess() {
                Collections.shuffle(cards);
                viewModel.getCardPlayList().clear();
                viewModel.getCardPlayList().addAll(cards);
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
        view.updateViewPager();
        updateMetrics();
    }

    private void updateMetrics() {
        int rightAnswers = 0;
        int wrongAnswers = 0;

        List<CardPlay> selectedCards = new ArrayList<>(viewModel.getSelectedCards().values());

        for (CardPlay c : selectedCards) {
            if (c.getAnswer() == CardAnswer.RIGHT) {
                rightAnswers++;

            } else if (c.getAnswer() == CardAnswer.WRONG) {
                wrongAnswers++;
            }
        }

        int notAnswerd = viewModel.getCardPlayList().size() - (rightAnswers + wrongAnswers);

        viewModel.getCardMetrics().setRightAnswers(rightAnswers);
        viewModel.getCardMetrics().setWrongAnswers(wrongAnswers);
        viewModel.getCardMetrics().setNotAnswerd(notAnswerd);

        view.setRightAnswers(String.valueOf(rightAnswers));
        view.setWrongAnswers(String.valueOf(wrongAnswers));
        view.setNotAnswered(String.valueOf(notAnswerd));
    }

    public List<CardPlay> getCardPlayList() {
        return viewModel.getCardPlayList();
    }

    public void onClickAnswer(CardPlay cardPlay) {
        if (viewModel.getSelectedCards().containsKey(cardPlay.getCardId())) {
            viewModel.getSelectedCards().remove(cardPlay.getCardId());
        }

        viewModel.getSelectedCards().put(cardPlay.getCardId(), cardPlay);

        int index = -1;

        for (int i = 0; i < viewModel.getCardPlayList().size(); i++) {

            CardPlay card = viewModel.getCardPlayList().get(i);

            if (card.getCardId() == cardPlay.getCardId()) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            viewModel.getCardPlayList().set(index, cardPlay);
        }

        updateMetrics();


        if (viewModel.getCardMetrics().getNotAnswerd() == 0) {
            finishSession();
        } else {
            int notAnswerdIndex = -1;
            for (int i = 0; i < viewModel.getCardPlayList().size(); i++) {

                CardPlay card = viewModel.getCardPlayList().get(i);

                if (card.getAnswer() == EntityHelper.NO_ID) {
                    notAnswerdIndex = i;
                    break;
                }
            }

            if (notAnswerdIndex != -1) {
                view.swipeToNotAnswerdCard(notAnswerdIndex);
            }
        }

    }

    public void onClickMenuFinishSession() {
        finishSession();
    }

    private void finishSession() {
        saveHistory();
        showFlashCardsResult();
    }

    private void showFlashCardsResult() {
        view.startSessionEndedActivity(viewModel.getCardMetrics(), viewModel.getCardPlayList());
        view.finish();
    }

    private void saveHistory() {
        try {
            List<CardPlay> selectedCards = new ArrayList<>(viewModel.getSelectedCards().values());

            CardHistorySQLite dao = CardHistorySQLite.newInstance(getContext());
            for (CardPlay card : selectedCards) {
                CardHistory history = new CardHistory();
                history.setCardId(card.getCardId());
                history.setAnswer(card.getAnswer());

                dao.save(history);
            }

        } catch (Exception e) {
            printLogAndShowDialog(e);
        }
    }


    public interface View extends BasePresenter.View {
        void updateViewPager();
        void setRightAnswers(String right);
        void setWrongAnswers(String wrong);
        void setNotAnswered(String notAnswerd);
        void swipeToNotAnswerdCard(int notAnswerdIndex);
        void finish();
        void startSessionEndedActivity(CardMetrics cardMetrics, List<CardPlay> cardPlayList);
    }


}
