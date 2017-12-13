package br.com.sailboat.flashcards.view.tag.list;

import android.content.Intent;
import android.support.v4.app.Fragment;

import br.com.sailboat.canoe.base.BaseActivitySingleFragment;
import br.com.sailboat.flashcards.helper.RequestCodeHelper;

public class TagListActivity extends BaseActivitySingleFragment<TagListFragment> {

    public static void start(Fragment fragment) {
        Intent intent = new Intent(fragment.getActivity(), TagListActivity.class);
        fragment.startActivityForResult(intent, RequestCodeHelper.TAG_LIST);
    }

    @Override
    protected TagListFragment newFragmentInstance() {
        return new TagListFragment();
    }

}
