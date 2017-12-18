package br.com.sailboat.flashcards.view.session_ended;

import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.List;

import br.com.sailboat.canoe.base.BaseActivitySingleFragment;
import br.com.sailboat.flashcards.helper.ExtrasHelper;
import br.com.sailboat.flashcards.helper.RequestCodeHelper;
import br.com.sailboat.flashcards.model.view.CardMetrics;
import br.com.sailboat.flashcards.model.view.CardPlay;

public class SessionEndedActivity extends BaseActivitySingleFragment<SessionEndedFragment> {

    private SessionEndedFragment fragment;

    public static void start(Fragment fragment) {
        Intent intent = new Intent(fragment.getActivity(), SessionEndedActivity.class);
        fragment.startActivityForResult(intent, RequestCodeHelper.PLAY);
    }

    public static void start(Fragment fragment, CardMetrics cardMetrics, List<CardPlay> cards) {
        Intent intent = new Intent(fragment.getActivity(), SessionEndedActivity.class);
        ExtrasHelper.putCardMetrics(cardMetrics, intent);
        ExtrasHelper.putCardPlayList(cards, intent);
        fragment.startActivityForResult(intent, RequestCodeHelper.SESSION_ENDED);
    }


    @Override
    protected SessionEndedFragment newFragmentInstance() {
        CardMetrics cardMetrics = ExtrasHelper.getCardMetrics(getIntent());
        List<CardPlay> cards = ExtrasHelper.getCardPlayList(getIntent());

        fragment = SessionEndedFragment.newInstance(cardMetrics, cards);

        return fragment;
    }


}
