package com.githubsearch.base;

import android.support.v7.widget.RecyclerView;

import com.arellomobile.mvp.MvpDelegate;
import com.arellomobile.mvp.MvpView;

/**
 * Created by Den on 06.06.2017.
 */

public abstract class BaseMvpAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements MvpView {

    private MvpDelegate<? extends BaseMvpAdapter> mvpDelegate;
    private MvpDelegate<?> parentDelegate;
    private String childId;

    public BaseMvpAdapter(MvpDelegate<?> parentDelegate, String childId) {
        this.parentDelegate = parentDelegate;
        this.childId = childId;

        getMvpDelegate().onCreate();
    }

    protected MvpDelegate getMvpDelegate() {
        if (mvpDelegate == null) {
            mvpDelegate = new MvpDelegate<>(this);
            mvpDelegate.setParentDelegate(parentDelegate, childId);
        }
        return mvpDelegate;
    }
}
