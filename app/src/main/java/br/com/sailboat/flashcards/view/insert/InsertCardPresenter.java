package br.com.sailboat.flashcards.view.insert;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.List;

import br.com.sailboat.canoe.base.BasePresenter;
import br.com.sailboat.canoe.exception.RequiredFieldNotFilledException;
import br.com.sailboat.canoe.helper.AsyncHelper;
import br.com.sailboat.canoe.helper.EntityHelper;
import br.com.sailboat.canoe.helper.LogHelper;
import br.com.sailboat.canoe.helper.StringHelper;
import br.com.sailboat.flashcards.R;
import br.com.sailboat.flashcards.helper.ExtrasHelper;
import br.com.sailboat.flashcards.model.Card;
import br.com.sailboat.flashcards.model.Tag;
import br.com.sailboat.flashcards.persistence.sqlite.CardSQLite;
import br.com.sailboat.flashcards.persistence.sqlite.TagSQLite;


public class InsertCardPresenter extends BasePresenter<InsertCardPresenter.View> {

    private InsertCardViewModel viewModel = new InsertCardViewModel();

    public InsertCardPresenter(View view) {
        super(view);
    }

    @Override
    public void extractExtrasFromArguments(Bundle arguments) {
        extractCardToEdit(arguments);
        extractTagToLink(arguments);
    }

    @Override
    protected void onResumeFirstSession() {
        if (hasCardToEdit()) {
            startEditingCard();
        } else {
            updateEditTexts();
            updateContentViews();
        }
    }

    @Override
    protected void onResumeAfterRestart() {
        updateContentViews();
    }

    public void onClickMenuSave() {
        try {
            closeKeyboard();
            extractInfoFromViews();
            performValidations();
            saveRecords();

        } catch (RequiredFieldNotFilledException e) {
            showMessage(e.getMessage());

        } catch (Exception e) {
            printLogAndShowDialog(e);
        }
    }

    public void onActivityResultOkSelectTag(Intent data) {
        List<Tag> selectedTags = ExtrasHelper.getTags(data);

        getViewModel().getTags().clear();
        getViewModel().getTags().addAll(selectedTags);
    }

    private void startEditingCard() {
        showProgress();

        AsyncHelper.execute(new AsyncHelper.Callback() {

            @Override
            public void doInBackground() throws Exception {
                long cardId = getViewModel().getCardId();
                loadCard(cardId);
                loadTags(cardId);
            }

            @Override
            public void onSuccess() {
                dismissProgress();
                getView().setActivityToHideKeyboard();
                updateEditTexts();
                updateContentViews();
            }

            @Override
            public void onFail(Exception e) {
                dismissProgress();
                printLogAndShowDialog(e);
                closeActivityWithResultCanceled();
            }

        });
    }

    private void loadCard(long cardId) throws Exception {
        Card card = CardSQLite.newInstance(getContext()).getCardById(cardId);
        getViewModel().setFront(card.getFront());
        getViewModel().setBack(card.getBack());
    }

    private void loadTags(long cardId) throws Exception {
        getViewModel().getTags().clear();
        getViewModel().getTags().addAll(TagSQLite.newInstance(getContext()).getByCard(cardId));
    }

    private void updateContentViews() {
        updateTitle();
    }

    private void updateEditTexts() {
        getView().setFront(getViewModel().getFront());
        getView().setBack(getViewModel().getBack());
    }

    private void updateTitle() {
        if (hasCardToEdit()) {
            getView().setTitle(getString(R.string.title_edit_card));
        } else {
            getView().setTitle(getString(R.string.title_new_card));
        }
    }

    private void saveRecords() {
        AsyncHelper.execute(new AsyncHelper.Callback() {

            Card card;

            @Override
            public void doInBackground() throws Exception {
                prepareAndSaveCard();
                prepareAndSaveTags();
            }

            @Override
            public void onSuccess() {
                closeActivityWithResultOk();
            }

            @Override
            public void onFail(Exception e) {
                printLogAndShowDialog(e);
            }

            private void prepareAndSaveCard() throws Exception {
                card = getCardFromViews();

                if (hasCardToEdit()) {
                    CardSQLite.newInstance(getContext()).update(card);
                } else {
                    CardSQLite.newInstance(getContext()).save(card);
                }
            }

            private void prepareAndSaveTags() {
                TagSQLite dao = TagSQLite.newInstance(getContext());
                dao.deleteRelationshipsByCard(card.getId());

                for (Tag tag : getViewModel().getTags()) {
                    dao.addCardTag(card.getId(), tag.getId());
                }
            }

        });
    }

    @NonNull
    private Card getCardFromViews() {
        Card card = new Card();
        card.setFront(getViewModel().getFront());
        card.setBack(getViewModel().getBack());
        card.setEnabled(true);

        if (hasCardToEdit()) {
            card.setId(getViewModel().getCardId());
        }

        return card;
    }

    private void extractInfoFromViews() {
        getViewModel().setFront(getView().getFront());
        getViewModel().setBack(getView().getBack());
    }

    private void performValidations() throws Exception {
        validateFront();
        validateBack();
    }

    private void validateFront() throws Exception {
        String front = getViewModel().getFront();

        if (StringHelper.isNullOrEmpty(front)) {
            throw new RequiredFieldNotFilledException(getString(R.string.msg_insert_front));
        }
    }

    private void validateBack() throws Exception {
        String back = getViewModel().getBack();

        if (StringHelper.isNullOrEmpty(back)) {
            throw new RequiredFieldNotFilledException(getString(R.string.msg_insert_back));
        }
    }

    private boolean hasCardToEdit() {
        return getViewModel().getCardId() != EntityHelper.NO_ID;
    }

    private InsertCardViewModel getViewModel() {
        return viewModel;
    }

    private void extractTagToLink(Bundle bundle) {
        try {
            long tagId = ExtrasHelper.getTagId(bundle);
            if (tagId != EntityHelper.NO_ID) {
                Tag tag = TagSQLite.newInstance(getContext()).getTagById(tagId);
                getViewModel().getTags().add(tag);
            }
        } catch (Exception e) {
            LogHelper.logException(e);
        }
    }

    private void extractCardToEdit(Bundle bundle) {
        long cardId = ExtrasHelper.getCardId(bundle);
        getViewModel().setCardId(cardId);
    }



    public interface View extends BasePresenter.View {
        void setFront(String name);
        void setBack(String notes);
        String getFront();
        String getBack();
    }


}