package br.com.sailboat.flashcards.view.play;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import br.com.sailboat.canoe.base.BaseFragment;
import br.com.sailboat.canoe.dialog.TwoOptionsDialog;
import br.com.sailboat.canoe.helper.DialogHelper;
import br.com.sailboat.flashcards.R;
import br.com.sailboat.flashcards.helper.ExtrasHelper;
import br.com.sailboat.flashcards.model.view.CardMetrics;
import br.com.sailboat.flashcards.model.view.CardPlay;
import br.com.sailboat.flashcards.view.session_ended.SessionEndedActivity;

import static br.com.sailboat.flashcards.R.id.pager;


public class PlayFragment extends BaseFragment<PlayPresenter> implements PlayPresenter.View, PlayPagerAdapter.Callback {

    private ViewPager viewPager;
    private PlayPagerAdapter playPagerAdapter;

    private TextView tvRightAnswers;
    private TextView tvWrongAnswers;
    private TextView tvNotAnswerd;
    private LinearLayout llNotAnswerd;

    public static PlayFragment newInstance(long tagId) {
        Bundle bundle = new Bundle();
        ExtrasHelper.putTagId(tagId, bundle);

        PlayFragment fragment = new PlayFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_play;
    }

    @Override
    protected PlayPresenter newPresenterInstance() {
        return new PlayPresenter(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_play, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_play_finish_session: {
                presenter.onClickMenuFinishSession();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }

    }

    @Override
    protected void onInitToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBack();
            }
        });

    }

    @Override
    protected void initViews() {
        viewPager = getView().findViewById(pager);
        playPagerAdapter = new PlayPagerAdapter(getFragmentManager(), this);

        viewPager.setAdapter(playPagerAdapter);
//        viewPager.setOffscreenPageLimit(2);

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
    protected void postActivityResult(int requestCode, Intent data) {
        getPresenter().postActivityResult();
    }

    @Override
    public List<CardPlay> getCardPlayList() {
        return presenter.getCardPlayList();
    }

    @Override
    public void updateViewPager() {
        playPagerAdapter.notifyDataSetChanged();
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
    public void swipeToNotAnswerdCard(int notAnswerdIndex) {
        viewPager.setCurrentItem(notAnswerdIndex, true);
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public void startSessionEndedActivity(CardMetrics cardMetrics, List<CardPlay> cardPlayList) {
        SessionEndedActivity.start(this, cardMetrics, cardPlayList);
    }

    public void onBackPressed() {
        onClickBack();
    }

    private void onClickBack() {
        DialogHelper.showYesOrNoDialog(getFragmentManager(), getActivity(), new TwoOptionsDialog.PositiveCallback() {
            @Override
            public void onClickPositiveOption() {
                getActivity().finish();
            }
        });
    }

    @Override
    public void onClickAnswer(CardPlay cardPlay) {
        presenter.onClickAnswer(cardPlay);
    }
}