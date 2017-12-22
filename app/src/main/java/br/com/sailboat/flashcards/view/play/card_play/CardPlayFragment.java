package br.com.sailboat.flashcards.view.play.card_play;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import br.com.sailboat.canoe.base.BaseFragment;
import br.com.sailboat.canoe.helper.AnimationHelper;
import br.com.sailboat.canoe.helper.ImageViewColorHelper;
import br.com.sailboat.canoe.recycler.RecyclerItem;
import br.com.sailboat.flashcards.R;
import br.com.sailboat.flashcards.helper.ExtrasHelper;
import br.com.sailboat.flashcards.model.view.CardPlay;
import br.com.sailboat.flashcards.view.tag.details.TagDetailsActivity;
import br.com.sailboat.flashcards.view.tag.list.TagListAdapter;


public class CardPlayFragment extends BaseFragment<CardPlayPresenter> implements CardPlayPresenter.View, TagListAdapter.Callback {

    private LinearLayout llShowBackContent;
    private LinearLayout llRightAnswer;
    private LinearLayout llWrongAnswer;
    private TextView tvFrontOfTheCard;
    private TextView tvBackOfTheCard;
    private RelativeLayout rlShowBack;
    private ImageView ivRightAnswer;
    private ImageView ivWrongAnswer;

    private CardPlayFragment.Callback callback;


    public static CardPlayFragment newInstance(CardPlay cardPlay, CardPlayFragment.Callback callback) {
        Bundle bundle = new Bundle();
        ExtrasHelper.putCardPlay(cardPlay, bundle);

        CardPlayFragment fragment = new CardPlayFragment();
        fragment.setArguments(bundle);
        fragment.setCallback(callback);

        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_card_play;
    }

    @Override
    protected CardPlayPresenter newPresenterInstance() {
        return new CardPlayPresenter(this);
    }

    @Override
    protected void onInitRecycler() {
        recycler.setAdapter(new TagListAdapter(this));
        recycler.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initViews() {
        rlShowBack = getView().findViewById(R.id.frg_card_play__rl__show_back);
        llShowBackContent = getView().findViewById(R.id.frg_card_play__ll__show_back_content);
        llRightAnswer = getView().findViewById(R.id.frg_card_play__ll__right);
        llWrongAnswer = getView().findViewById(R.id.frg_card_play__ll__wrong);
        tvFrontOfTheCard = getView().findViewById(R.id.frg_card_play__tv__front);
        tvBackOfTheCard = getView().findViewById(R.id.frg_card_play__tv__back);
        ivRightAnswer = getView().findViewById(R.id.frg_card_play__iv__right);
        ivWrongAnswer = getView().findViewById(R.id.frg_card_play__iv__wrong);
    }

    @Override
    protected void initListeners() {
        rlShowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickShowBackOfTheCard();
            }
        });

        llRightAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickRightAnswer();
            }
        });

        llWrongAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickWrongAnswer();
            }
        });
    }

    @Override
    protected void postActivityResult(int requestCode, Intent data) {
        getPresenter().postActivityResult();
    }

    @Override
    public void showBackOfTheCard() {
        llShowBackContent.setVisibility(View.VISIBLE);
    }

    @Override
    public void showBackOfTheCardWithAnimation() {
        AnimationHelper.expand(llShowBackContent);
    }

    @Override
    public void hideBackOfTheCard() {
        llShowBackContent.setVisibility(View.GONE);
    }

    @Override
    public void hideBackOfTheCardWithAnimation() {
        AnimationHelper.collapse(llShowBackContent);
    }

    @Override
    public void setFrontOfTheCard(String front) {
        tvFrontOfTheCard.setText(front);
    }

    @Override
    public void setBackOfTheCard(String back) {
        tvBackOfTheCard.setText(back);
    }

    @Override
    public void setRightAnswerAsSelected() {
        llRightAnswer.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.md_teal_300));
        ImageViewColorHelper.setColorOfVector(getActivity(), ivRightAnswer, R.color.md_white_1000);
    }

    @Override
    public void setWrongAnswerAsNotSelected() {
        llWrongAnswer.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.md_white_1000));
        ImageViewColorHelper.setColorOfVector(getActivity(), ivWrongAnswer, R.color.md_blue_grey_200);
    }

    @Override
    public void setWrongAnswerAsSelected() {
        llWrongAnswer.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.md_red_300));
        ImageViewColorHelper.setColorOfVector(getActivity(), ivWrongAnswer, R.color.md_white_1000);
    }

    @Override
    public void setRightAnswerAsNotSelected() {
        llRightAnswer.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.md_white_1000));
        ImageViewColorHelper.setColorOfVector(getActivity(), ivRightAnswer, R.color.md_blue_grey_200);
    }

    @Override
    public void onClickAnswer(CardPlay cardPlay) {
        callback.onClickAnswer(cardPlay);
    }

    @Override
    public void setRightAnswerAsSelectedWithAnimation() {
        ImageViewColorHelper.setColorOfVector(getActivity(), ivRightAnswer, R.color.md_white_1000);

        int colorFrom = ContextCompat.getColor(getActivity(), R.color.md_white_1000);
        int colorTo = ContextCompat.getColor(getActivity(), R.color.md_teal_300);

        AnimationHelper.performAnimationBackgroundColor(getActivity(), colorFrom, colorTo, 1000,
                new AnimationHelper.BackgroundColorCallback() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                llRightAnswer.setBackgroundColor((int) animator.getAnimatedValue());
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setWrongAnswerAsNotSelected();
                presenter.onAnimationEnd();
            }
        });
    }

    @Override
    public void setWrongAnswerAsSelectedWithAnimation() {
        ImageViewColorHelper.setColorOfVector(getActivity(), ivWrongAnswer, R.color.md_white_1000);

        int colorFrom = ContextCompat.getColor(getActivity(), R.color.md_white_1000);
        int colorTo = ContextCompat.getColor(getActivity(), R.color.md_red_300);

        AnimationHelper.performAnimationBackgroundColor(getActivity(), colorFrom, colorTo, 1000,
                new AnimationHelper.BackgroundColorCallback() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                llWrongAnswer.setBackgroundColor((int) animator.getAnimatedValue());
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setRightAnswerAsNotSelected();
                presenter.onAnimationEnd();
            }
        });
    }

    @Override
    public void startTagDetailsActivity(long tagId) {
        TagDetailsActivity.start(this, tagId);
    }

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void onClickTag(int position) {
        presenter.onClickTag(position);
    }

    @Override
    public List<RecyclerItem> getRecyclerItemList() {
        return presenter.getRecyclerItemList();
    }


    public interface Callback {
        void onClickAnswer(CardPlay cardPlay);
    }


}
