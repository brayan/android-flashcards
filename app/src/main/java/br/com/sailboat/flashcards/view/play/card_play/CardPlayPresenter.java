package br.com.sailboat.flashcards.view.play.card_play;

import android.os.AsyncTask;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import br.com.sailboat.canoe.base.BasePresenter;
import br.com.sailboat.canoe.helper.AsyncHelper;
import br.com.sailboat.canoe.helper.EntityHelper;
import br.com.sailboat.canoe.helper.LogHelper;
import br.com.sailboat.canoe.recycler.RecyclerItem;
import br.com.sailboat.canoe.recycler.item.LabelRecyclerItem;
import br.com.sailboat.flashcards.R;
import br.com.sailboat.flashcards.helper.ExtrasHelper;
import br.com.sailboat.flashcards.helper.ViewType;
import br.com.sailboat.flashcards.model.Card;
import br.com.sailboat.flashcards.model.Tag;
import br.com.sailboat.flashcards.model.view.CardAnswer;
import br.com.sailboat.flashcards.model.view.CardPlay;
import br.com.sailboat.flashcards.persistence.sqlite.CardSQLite;
import br.com.sailboat.flashcards.persistence.sqlite.TagSQLite;


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

    public void postActivityResult() {
        loadInfo();
    }

    public void onClickShowBackOfTheCard() {
        viewModel.setShowingBackOfTheCard(!isShowingBackOfTheCard());

        if (isShowingBackOfTheCard()) {
            getView().showBackOfTheCardWithAnimation();
        } else {
            getView().hideBackOfTheCardWithAnimation();
        }
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

    public void onClickTag(int position) {
        Tag tag = (Tag) getRecyclerItemList().get(position);
        view.startTagDetailsActivity(tag.getId());
    }

    private void loadInfo() {
        AsyncHelper.execute(AsyncTask.THREAD_POOL_EXECUTOR, new AsyncHelper.Callback() {

            Card card;
            List<RecyclerItem> recyclerItemList = new ArrayList<>();

            @Override
            public void doInBackground() throws Exception {
                long cardId = viewModel.getCardPlay().getCardId();
                loadCard(cardId);
                loadTags(cardId);
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

            private void loadCard(long cardId) throws Exception {
                card = CardSQLite.newInstance(getContext()).getCardById(cardId);
            }

            private void loadTags(long cardId) throws Exception {
                List<Tag> tags = TagSQLite.newInstance(getContext()).getByCard(cardId);

                if (!tags.isEmpty()) {
                    LabelRecyclerItem label = new LabelRecyclerItem(ViewType.LABEL);
                    label.setLabel(getString(R.string.label_tags));

                    recyclerItemList.add(label);
                    recyclerItemList.addAll(tags);
                }
            }

            private void updateViewModel() {
                viewModel.setCard(card);
                viewModel.getRecyclerItemList().clear();
                viewModel.getRecyclerItemList().addAll(recyclerItemList);
            }

        });
    }

    private void updateContentViews() {
        view.updateRecycler();
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

    public List<RecyclerItem> getRecyclerItemList() {
        return viewModel.getRecyclerItemList();
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
        void startTagDetailsActivity(long tagId);
    }


}
