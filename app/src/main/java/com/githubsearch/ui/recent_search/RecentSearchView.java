package com.githubsearch.ui.recent_search;

import com.arellomobile.mvp.MvpView;
import com.githubsearch.base.view.HideShowContentView;
import com.githubsearch.data.network.model.RepositoryItem;
import com.githubsearch.ui.content_adapters.ContentRecyclerView;

/**
 * Created by den on 02.07.18.
 */

public interface RecentSearchView extends MvpView, ContentRecyclerView<RepositoryItem>, HideShowContentView {
}
