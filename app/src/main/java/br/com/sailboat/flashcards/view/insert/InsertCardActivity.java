package br.com.sailboat.flashcards.view.insert;

import android.content.Intent;
import android.support.v4.app.Fragment;

import br.com.sailboat.canoe.base.BaseActivitySingleFragment;
import br.com.sailboat.flashcards.helper.ExtrasHelper;
import br.com.sailboat.flashcards.helper.RequestCodeHelper;

public class InsertCardActivity extends BaseActivitySingleFragment<InsertCardFragment> {

    public static void start(Fragment fromFragment) {
        Intent intent = new Intent(fromFragment.getActivity(), InsertCardActivity.class);
        fromFragment.startActivityForResult(intent, RequestCodeHelper.INSERT_CARD);
    }

    public static void startWithProject(Fragment fromFragment, long projectId) {
        Intent intent = new Intent(fromFragment.getActivity(), InsertCardActivity.class);
        ExtrasHelper.putTagId(projectId, intent);
        fromFragment.startActivityForResult(intent, RequestCodeHelper.INSERT_CARD);
    }

    public static void startToEdit(Fragment fromFragment, long taskId) {
        Intent intent = new Intent(fromFragment.getActivity(), InsertCardActivity.class);
        ExtrasHelper.putCardId(taskId, intent);
        fromFragment.startActivityForResult(intent, RequestCodeHelper.INSERT_CARD);
    }

    @Override
    protected InsertCardFragment newFragmentInstance() {
        if (ExtrasHelper.hasTaskId(getIntent())) {
            long taskId = ExtrasHelper.getCardId(getIntent());
            return InsertCardFragment.newInstanceWithTaskToEdit(taskId);

        } else if (ExtrasHelper.hasProjectId(getIntent())) {
            long projectId = ExtrasHelper.getTagId(getIntent());
            return InsertCardFragment.newInstanceWithProject(projectId);

        } else {
            return InsertCardFragment.newInstance();
        }
    }

}
