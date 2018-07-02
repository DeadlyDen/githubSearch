package com.githubsearch.ui.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import butterknife.ButterKnife;

/**
 * Created by den on 12.12.17.
 */

public abstract class AbstractContentHolder<T> extends RecyclerView.ViewHolder {

    private T item;

    public AbstractContentHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public abstract void initData(Context context);

}

