package com.githubsearch.ui.user_profile;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.githubsearch.base.view.HideShowContentView;
import com.githubsearch.data.network.model.RepositoryItem;
import com.githubsearch.data.network.model.SearchRepoResponse;
import com.githubsearch.ui.content_adapters.ContentRecyclerView;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by den on 10.05.18.
 */
@StateStrategyType(AddToEndSingleStrategy.class)
public interface SearchRepoView extends MvpView, ContentRecyclerView<RepositoryItem>, HideShowContentView {

    void showProgressBar();
    void hideProgressBar();
    void prepareViewedRepositories(JSONArray jsonArray);

}
