package br.com.sailboat.flashcards.view.play;

import android.content.Intent;
import android.support.v4.app.Fragment;

import br.com.sailboat.canoe.base.BaseActivitySingleFragment;
import br.com.sailboat.flashcards.helper.ExtrasHelper;
import br.com.sailboat.flashcards.helper.RequestCodeHelper;

public class PlayActivity extends BaseActivitySingleFragment<PlayFragment> {

    private PlayFragment playFragment;

    public static void start(Fragment fragment) {
        Intent intent = new Intent(fragment.getActivity(), PlayActivity.class);
        fragment.startActivityForResult(intent, RequestCodeHelper.PLAY);
    }

    public static void start(Fragment fragment, long tagId) {
        Intent intent = new Intent(fragment.getActivity(), PlayActivity.class);
        ExtrasHelper.putTagId(tagId, intent);
        fragment.startActivityForResult(intent, RequestCodeHelper.PLAY);
    }


    @Override
    protected PlayFragment newFragmentInstance() {
        long tagId = ExtrasHelper.getTagId(getIntent());
        playFragment = PlayFragment.newInstance(tagId);
        return playFragment;
    }

    @Override
    public void onBackPressed() {
        playFragment.onBackPressed();
    }
}
