package br.com.sailboat.flashcards.view.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import br.com.sailboat.canoe.base.BaseFragment;
import br.com.sailboat.canoe.dialog.TwoOptionsDialog;
import br.com.sailboat.canoe.recycler.RecyclerItem;
import br.com.sailboat.flashcards.R;
import br.com.sailboat.flashcards.helper.ExtrasHelper;
import br.com.sailboat.flashcards.view.insert.InsertCardActivity;

public class CardDetailFragment extends BaseFragment<CardDetailPresenter> implements CardDetailPresenter.View, CardDetailsAdapter.Callback {

    private View viewMetrics;
    private TextView tvNotDone;
    private TextView tvDone;


    public static CardDetailFragment newInstance(long cardId) {
        Bundle bundle = new Bundle();
        ExtrasHelper.putCardId(cardId, bundle);

        CardDetailFragment fragment = new CardDetailFragment();
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    protected CardDetailPresenter newPresenterInstance() {
        return new CardDetailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_card_detail;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_delete, menu);
        super.onCreateOptionsMenu(menu, inflater);
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
    }

    @Override
    protected void onInitRecycler() {
        recycler.setAdapter(new CardDetailsAdapter(this));
    }

    @Override
    protected void onInitFab() {
        fab.setImageResource(R.drawable.ic_edit_white_24dp);
    }

    @Override
    protected void initViews() {
        initMetricViews();
    }

    @Override
    protected void postActivityResult(int requestCode, Intent data) {
        getPresenter().postActivityResult();
    }

    public void onClickTag(int position) {
        presenter.onClickTag(position);
    }

    @Override
    public void setRightAnswers(String amount) {
        tvDone.setText(amount);
    }

    @Override
    public void setWrongAnswers(String amount) {
        tvNotDone.setText(amount);
    }

    @Override
    public void showDialogDeleteCard() {
        TwoOptionsDialog dialog = new TwoOptionsDialog();
        dialog.setMessage(getString(R.string.are_you_sure));
        dialog.setPositiveMsg(getString(R.string.delete));
        dialog.setPositiveCallback(new TwoOptionsDialog.PositiveCallback() {
            @Override
            public void onClickPositiveOption() {
                getPresenter().onClickDeleteTask();
            }
        });
        dialog.show(getFragmentManager(), "DELETE_TASK");
    }

    @Override
    public void startInsertCardActivity(long cardId) {
        InsertCardActivity.startToEdit(this, cardId);
    }

    @Override
    public void startTagDetails(long projectId) {
//        ProjectDetailActivity.start(this, projectId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete: {
                getPresenter().onClickMenuDelete();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public List<RecyclerItem> getRecyclerItemList() {
        return presenter.getRecyclerItemList();
    }

    private void initMetricViews() {
        viewMetrics = getView().findViewById(R.id.appbar_card_details__fl__metrics);
        tvNotDone = getView().findViewById(R.id.card_metrics__tv__not_done);
        tvDone = getView().findViewById(R.id.card_metrics__tv__done);
    }

}
