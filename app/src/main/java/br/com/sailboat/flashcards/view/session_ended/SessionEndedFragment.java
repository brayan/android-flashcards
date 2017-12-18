package br.com.sailboat.flashcards.view.session_ended;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import br.com.sailboat.canoe.base.BaseFragment;
import br.com.sailboat.flashcards.R;
import br.com.sailboat.flashcards.helper.ExtrasHelper;
import br.com.sailboat.flashcards.model.view.CardMetrics;
import br.com.sailboat.flashcards.model.view.CardPlay;
import br.com.sailboat.flashcards.view.tag.list.TagListActivity;


public class SessionEndedFragment extends BaseFragment<SessionEndedPresenter> implements SessionEndedPresenter.View, SessionEndedAdapter.Callback {


    private TextView tvRightAnswers;
    private TextView tvWrongAnswers;
    private TextView tvNotAnswerd;
    private LinearLayout llNotAnswerd;

    public static SessionEndedFragment newInstance(CardMetrics cardMetrics, List<CardPlay> cards) {
        Bundle bundle = new Bundle();
        ExtrasHelper.putCardMetrics(cardMetrics, bundle);
        ExtrasHelper.putCardPlayList(cards, bundle);

        SessionEndedFragment fragment = new SessionEndedFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_session_ended;
    }

    @Override
    protected SessionEndedPresenter newPresenterInstance() {
        return new SessionEndedPresenter(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_card_list__tags: {
                TagListActivity.start(this);
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }

    }

    @Override
    protected void onInitToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        toolbar.setTitle(R.string.session_ended);
    }

    @Override
    protected void onInitRecycler() {
        recycler.setAdapter(new SessionEndedAdapter(this));
    }


    @Override
    protected void initViews() {
        initMetrics();
    }

    private void initMetrics() {
        tvRightAnswers = getView().findViewById(R.id.card_metrics__tv__right);
        tvWrongAnswers = getView().findViewById(R.id.card_metrics__tv__wrong);
        tvNotAnswerd = getView().findViewById(R.id.card_metrics__tv__not_answerd);
        llNotAnswerd = getView().findViewById(R.id.card_metrics__ll__not_answerd);
        llNotAnswerd.setVisibility(View.VISIBLE);
    }

    @Override
    public void setRightAnswers(String right) {
        tvRightAnswers.setText(right);
    }

    @Override
    public void setWrongAnswers(String wrong) {
        tvWrongAnswers.setText(wrong);
    }

    @Override
    public void setNotAnswered(String notAnswerd) {
        tvNotAnswerd.setText(notAnswerd);
    }

    @Override
    public List<CardPlay> getCardPlayList() {
        return presenter.getCardPlayList();
    }

}