package br.com.sailboat.flashcards.view.insert;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import br.com.sailboat.canoe.base.BaseFragment;
import br.com.sailboat.flashcards.R;
import br.com.sailboat.flashcards.helper.ExtrasHelper;
import br.com.sailboat.flashcards.helper.RequestCodeHelper;


public class InsertCardFragment extends BaseFragment<InsertCardPresenter> implements InsertCardPresenter.View {

    private EditText etFront;
    private EditText etBack;


    public static InsertCardFragment newInstance() {
        return new InsertCardFragment();
    }

    public static InsertCardFragment newInstanceWithTaskToEdit(long taskId) {
        Bundle bundle = new Bundle();
        ExtrasHelper.putCardId(taskId, bundle);

        InsertCardFragment fragment = new InsertCardFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    public static InsertCardFragment newInstanceWithProject(long projectId) {
        Bundle bundle = new Bundle();
        ExtrasHelper.putTagId(projectId, bundle);

        InsertCardFragment fragment = new InsertCardFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_insert_card;
    }

    @Override
    protected InsertCardPresenter newPresenterInstance() {
        return new InsertCardPresenter(this);
    }

    @Override
    protected void initViews() {
        initEditTexts();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_save, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save: {
                getPresenter().onClickMenuSave();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }

    }

    @Override
    protected void onActivityResultOk(int requestCode, Intent data) {

        switch (requestCode) {
            case RequestCodeHelper.SELECT_PROJECT: {
                getPresenter().onActivityResultOkSelectTag(data);
                return;
            }
        }

    }

    @Override
    public void setFront(String front) {
        etFront.setText(front);
        etFront.setSelection(etFront.length());
    }

    @Override
    public void setBack(String back) {
        etBack.setText(back);
    }

    @Override
    public String getFront() {
        return etFront.getText().toString();
    }

    @Override
    public String getBack() {
        return etBack.getText().toString();
    }

    private void initEditTexts() {
        etFront = getView().findViewById(R.id.frg_insert_card__et__front);
        etBack = getView().findViewById(R.id.frg_insert_card__et__back);

        etFront.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    closeKeyboard();
                    return true;
                }

                return false;
            }
        });
    }

    @Override
    protected void onInitToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

}
