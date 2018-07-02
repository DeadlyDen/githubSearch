package com.githubsearch.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by den on 10.08.17.
 */

public abstract class ListFragmentAdapter<T extends Fragment> extends FragmentStatePagerAdapter {

    private FragmentManager fm;
    private final List<T> mFragments = new ArrayList<>();

    public ListFragmentAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
    }

    public void addFragment(T fragment) {
        mFragments.add(fragment);
        notifyDataSetChanged();
    }

    public void removeFragment(int position) {
        mFragments.remove(position);
        notifyDataSetChanged();
    }

    public void removeAll() {
        mFragments.clear();
        notifyDataSetChanged();
    }


    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
