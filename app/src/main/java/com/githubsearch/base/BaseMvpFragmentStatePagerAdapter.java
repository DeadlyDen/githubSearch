package com.githubsearch.base;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.arellomobile.mvp.MvpView;

/**
 * Created by den on 04.07.17.
 */

public abstract class BaseMvpFragmentStatePagerAdapter extends FragmentStatePagerAdapter implements MvpView {

    public BaseMvpFragmentStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }


}
