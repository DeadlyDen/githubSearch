package com.githubsearch.ui.content_adapters;

import com.arellomobile.mvp.MvpView;
import com.githubsearch.base.view.HideShowContentView;

import java.util.List;

/**
 * Created by den on 12.12.17.
 */
public interface ContentRecyclerView<T> extends MvpView, HideShowContentView {

    void initRecycleView(List<T> items);
    void updateDataInRecyclerAdapter(List<T> items);
    void uploadDataInRecyclerAdapter(List<T> items);
    void clearDataInRecyclerAdapter();
    void initSwipeRefreshLayout();
    void initRecycleViewMoreListener();

}
