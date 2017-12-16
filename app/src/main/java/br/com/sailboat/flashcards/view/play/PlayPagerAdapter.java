package br.com.sailboat.flashcards.view.play;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.List;

import br.com.sailboat.flashcards.model.view.CardPlay;
import br.com.sailboat.flashcards.view.play.card_play.CardPlayFragment;

public class PlayPagerAdapter extends FragmentStatePagerAdapter {

    public static final int POSICAO_FRAGMENT_INFORMACOES = 0;

    private PlayPagerAdapter.Callback callback;
    private SparseArray<Fragment> fragments;

    public PlayPagerAdapter(FragmentManager fm, PlayPagerAdapter.Callback callback) {
        super(fm);
        this.callback = callback;
        this.fragments = new SparseArray<>();
    }

    @Override
    public Fragment getItem(int position) {
        CardPlay cardPlay = callback.getCardPlayList().get(position);
        return CardPlayFragment.newInstance(cardPlay, callback);
    }

    @Override
    public int getCount() {
        return callback.getCardPlayList().size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        fragments.put(position, fragment);

        ((CardPlayFragment) fragment).setCallback(callback);

        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        fragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return fragments.get(position);
    }


    public interface Callback extends CardPlayFragment.Callback {
        List<CardPlay> getCardPlayList();
    }


}
