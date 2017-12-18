package br.com.sailboat.flashcards.view.session_ended;

import android.os.Bundle;

import java.util.List;

import br.com.sailboat.canoe.base.BasePresenter;
import br.com.sailboat.flashcards.helper.ExtrasHelper;
import br.com.sailboat.flashcards.model.view.CardMetrics;
import br.com.sailboat.flashcards.model.view.CardPlay;

public class SessionEndedPresenter extends BasePresenter<SessionEndedPresenter.View> {

    private SessionEndedViewModel viewModel = new SessionEndedViewModel();

    public SessionEndedPresenter(SessionEndedPresenter.View view) {
        super(view);
    }

    @Override
    public void extractExtrasFromArguments(Bundle arguments) {
        CardMetrics cardMetrics = ExtrasHelper.getCardMetrics(arguments);
        List<CardPlay> cardPlayList = ExtrasHelper.getCardPlayList(arguments);

        viewModel.setCardMetrics(cardMetrics);

        viewModel.getCardPlayList().clear();
        viewModel.getCardPlayList().addAll(cardPlayList);
    }

    @Override
    protected void postResume() {
        updateContentViews();
    }

    private void updateContentViews() {
        view.updateRecycler();
        updateMetrics();
    }

    private void updateMetrics() {
        CardMetrics cardMetrics = viewModel.getCardMetrics();
        view.setRightAnswers(String.valueOf(cardMetrics.getRightAnswers()));
        view.setWrongAnswers(String.valueOf(cardMetrics.getWrongAnswers()));
        view.setNotAnswered(String.valueOf(cardMetrics.getNotAnswerd()));
    }

    public List<CardPlay> getCardPlayList() {
        return viewModel.getCardPlayList();
    }



    public interface View extends BasePresenter.View {
        void setRightAnswers(String right);
        void setWrongAnswers(String wrong);
        void setNotAnswered(String notAnswerd);
    }


}
