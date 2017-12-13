package br.com.sailboat.flashcards.view.tag.selector;

import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.List;

import br.com.sailboat.canoe.base.BaseActivitySingleFragment;
import br.com.sailboat.flashcards.helper.ExtrasHelper;
import br.com.sailboat.flashcards.helper.RequestCodeHelper;
import br.com.sailboat.flashcards.model.Tag;


public class TagSelectorActivity extends BaseActivitySingleFragment<TagSelectorFragment> {

    public static void start(Fragment fragment, List<Tag> tags) {
        Intent starter = new Intent(fragment.getActivity(), TagSelectorActivity.class);
        ExtrasHelper.putTags(tags, starter);
        fragment.startActivityForResult(starter, RequestCodeHelper.SELECTOR_TAG);
    }

    @Override
    protected TagSelectorFragment newFragmentInstance() {
        List<Tag> tags = ExtrasHelper.getTags(getIntent());
        return TagSelectorFragment.newInstance(tags);
    }

}
