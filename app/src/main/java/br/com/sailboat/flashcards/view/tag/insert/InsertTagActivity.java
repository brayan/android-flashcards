package br.com.sailboat.flashcards.view.tag.insert;

import android.content.Intent;
import android.support.v4.app.Fragment;

import br.com.sailboat.canoe.base.BaseActivitySingleFragment;
import br.com.sailboat.flashcards.helper.ExtrasHelper;
import br.com.sailboat.flashcards.helper.RequestCodeHelper;

public class InsertTagActivity extends BaseActivitySingleFragment<InsertTagFragment> {

    public static void start(Fragment fromFragment) {
        Intent intent = new Intent(fromFragment.getActivity(), InsertTagActivity.class);
        fromFragment.startActivityForResult(intent, RequestCodeHelper.INSERT_TAG);
    }

    public static void startToEdit(Fragment fromFragment, long tagId) {
        Intent intent = new Intent(fromFragment.getActivity(), InsertTagActivity.class);
        ExtrasHelper.putTagId(tagId, intent);
        fromFragment.startActivityForResult(intent, RequestCodeHelper.INSERT_TAG);
    }

    @Override
    protected InsertTagFragment newFragmentInstance() {
        if (ExtrasHelper.hasTagId(getIntent())) {
            long tagId = ExtrasHelper.getTagId(getIntent());
            return InsertTagFragment.newInstanceWithTagToEdit(tagId);

        } else if (ExtrasHelper.hasCardId(getIntent())) {
            long cardId = ExtrasHelper.getCardId(getIntent());
            return InsertTagFragment.newInstanceWithCard(cardId);

        } else {
            return InsertTagFragment.newInstance();
        }
    }

}
