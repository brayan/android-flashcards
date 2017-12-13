package br.com.sailboat.flashcards.view.tag.details;

import android.content.Intent;
import android.support.v4.app.Fragment;

import br.com.sailboat.canoe.base.BaseActivitySingleFragment;
import br.com.sailboat.flashcards.helper.ExtrasHelper;
import br.com.sailboat.flashcards.helper.RequestCodeHelper;

public class TagDetailsActivity extends BaseActivitySingleFragment<TagDetailsFragment> {

    public static void start(Fragment fromFragment, long tagId) {
        Intent intent = new Intent(fromFragment.getActivity(), TagDetailsActivity.class);
        ExtrasHelper.putTagId(tagId, intent);
        fromFragment.startActivityForResult(intent, RequestCodeHelper.TAG_DETAILS);
    }

    @Override
    protected TagDetailsFragment newFragmentInstance() {
        long tagId = ExtrasHelper.getTagId(getIntent());
        return TagDetailsFragment.newInstance(tagId);
    }

}
