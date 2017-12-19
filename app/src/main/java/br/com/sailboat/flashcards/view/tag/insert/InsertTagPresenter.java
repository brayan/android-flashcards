package br.com.sailboat.flashcards.view.tag.insert;

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
import br.com.sailboat.canoe.recycler.RecyclerItem;
import br.com.sailboat.flashcards.R;
import br.com.sailboat.flashcards.helper.ExtrasHelper;
import br.com.sailboat.flashcards.model.Card;
import br.com.sailboat.flashcards.model.Tag;
import br.com.sailboat.flashcards.persistence.sqlite.CardSQLite;
import br.com.sailboat.flashcards.persistence.sqlite.CardTagSQLite;
import br.com.sailboat.flashcards.persistence.sqlite.TagSQLite;


public class InsertTagPresenter extends BasePresenter<InsertTagPresenter.View> {

    private InsertTagViewModel viewModel = new InsertTagViewModel();

    public InsertTagPresenter(View view) {
        super(view);
    }

    @Override
    public void extractExtrasFromArguments(Bundle arguments) {
        extractTagToEdit(arguments);
        extractCardToLink(arguments);
    }

    @Override
    protected void onResumeFirstSession() {
        if (hasTagToEdit()) {
            startEditingTag();
        } else {
            updateEditTexts();
            updateContentViews();
        }
    }

    @Override
    protected void onResumeAfterRestart() {
        updateContentViews();
    }

    @Override
    public void onClickFab() {
        List<Card> selectedTags = (List) viewModel.getCards();
        view.startCardSelector(selectedTags);
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

    public void onActivityResultOkSelectCard(Intent data) {
        List<Card> selectedTags = ExtrasHelper.getCards(data);

        getViewModel().getCards().clear();
        getViewModel().getCards().addAll(selectedTags);
    }

    private void startEditingTag() {
        showProgress();

        AsyncHelper.execute(new AsyncHelper.Callback() {

            @Override
            public void doInBackground() throws Exception {
                long tagId = getViewModel().getTagId();
                loadTag(tagId);
                loadCards(tagId);
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

    private void loadTag(long tagId) throws Exception {
        Tag tag = TagSQLite.newInstance(getContext()).getTagById(tagId);
        getViewModel().setTagText(tag.getName());
    }

    private void loadCards(long tagId) throws Exception {
        getViewModel().getCards().clear();
        getViewModel().getCards().addAll(CardSQLite.newInstance(getContext()).getAllByTag(tagId));
    }

    private void updateContentViews() {
        updateTitle();
        updateCards();
    }

    private void updateEditTexts() {
        getView().setTagText(getViewModel().getTagText());
    }

    private void updateTitle() {
        if (hasTagToEdit()) {
            getView().setTitle(getString(R.string.title_edit_tag));
        } else {
            getView().setTitle(getString(R.string.title_new_tag));
        }
    }

    private void updateCards() {
        view.updateRecycler();

        if (viewModel.getCards().isEmpty()) {
            view.hideRecycler();
            view.showEmptyView();
        } else {
            view.showRecycler();
            view.hideEmptyView();
        }
    }

    private void saveRecords() {
        AsyncHelper.execute(new AsyncHelper.Callback() {

            Tag tag;

            @Override
            public void doInBackground() throws Exception {
                prepareAndSaveTag();
                prepareAndSaveCards();
            }

            @Override
            public void onSuccess() {
                closeActivityWithResultOk();
            }

            @Override
            public void onFail(Exception e) {
                printLogAndShowDialog(e);
            }

            private void prepareAndSaveTag() throws Exception {
                tag = getTagFromViews();

                if (hasTagToEdit()) {
                    TagSQLite.newInstance(getContext()).update(tag);
                } else {
                    TagSQLite.newInstance(getContext()).save(tag);
                }
            }

            private void prepareAndSaveCards() {
                CardTagSQLite dao = CardTagSQLite.newInstance(getContext());
                dao.deleteByTagId(tag.getId());

                for (RecyclerItem card : getViewModel().getCards()) {
                    dao.save(((Card) card).getId(), tag.getId());
                }
            }

        });
    }

    @NonNull
    private Tag getTagFromViews() {
        Tag tag = new Tag();
        tag.setName(getViewModel().getTagText());

        if (hasTagToEdit()) {
            tag.setId(getViewModel().getTagId());
        }

        return tag;
    }

    private void extractInfoFromViews() {
        getViewModel().setTagText(getView().getTagText());
    }

    private void performValidations() throws Exception {
        validateTag();
        validadeCards();
    }

    private void validateTag() throws Exception {
        String tag = getViewModel().getTagText();

        if (StringHelper.isNullOrEmpty(tag)) {
            throw new RequiredFieldNotFilledException(getString(R.string.msg_insert_tag_name));
        }
    }

    private void validadeCards() throws RequiredFieldNotFilledException {
        if (viewModel.getCards().isEmpty()) {
            throw new RequiredFieldNotFilledException(getString(R.string.msg_add_card));
        }
    }

    private boolean hasTagToEdit() {
        return getViewModel().getTagId() != EntityHelper.NO_ID;
    }

    private InsertTagViewModel getViewModel() {
        return viewModel;
    }

    private void extractCardToLink(Bundle bundle) {
        try {
            long cardId = ExtrasHelper.getCardId(bundle);
            if (cardId != EntityHelper.NO_ID) {
                Card card = CardSQLite.newInstance(getContext()).getCardById(cardId);
                getViewModel().getCards().add(card);
            }
        } catch (Exception e) {
            LogHelper.logException(e);
        }
    }

    private void extractTagToEdit(Bundle bundle) {
        long tagId = ExtrasHelper.getTagId(bundle);
        getViewModel().setTagId(tagId);
    }

    public void onClickCard(int position) {
    }

    public List<RecyclerItem> getRecyclerItemList() {
        return viewModel.getCards();
    }

    public void onCardDismiss(int position) {
        getRecyclerItemList().remove(position);
        updateContentViews();
    }


    public interface View extends BasePresenter.View {
        void setTagText(String name);
        String getTagText();
        void startCardDetails(long cardId);
        void startCardSelector(List<Card> selectedCards);
    }


}
