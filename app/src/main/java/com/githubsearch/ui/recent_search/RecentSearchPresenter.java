package com.githubsearch.ui.recent_search;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.githubsearch.App;
import com.githubsearch.base.BasePresenter;
import com.githubsearch.data.DataManager;
import com.githubsearch.data.network.model.RepositoryItem;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by den on 02.07.18.
 */
@InjectViewState
public class RecentSearchPresenter extends BasePresenter<RecentSearchView> {

    @Inject
    DataManager dataManager;

    @Override
    public void attachView(RecentSearchView view) {
        super.attachView(view);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        App.getAppComponent().inject(this);
        loadViewedRepositories();
    }

    private void loadViewedRepositories() {
        if (dataManager.loadViewedRepositories().length() != 0) {
            List<RepositoryItem> repositoryItems = new ArrayList<>();
            JSONArray jsonArray = dataManager.loadViewedRepositories();
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    repositoryItems.add(new RepositoryItem(Integer.valueOf(jsonArray.getJSONObject(i).getString("id"))
                            , jsonArray.getJSONObject(i).getString("full_name"), jsonArray.getJSONObject(i).getString("html_url"),
                            jsonArray.getJSONObject(i).getBoolean("viewed")));
                } catch (JSONException e) {
                    Log.v("Json :", String.valueOf(e));
                }
            }
            getViewState().initRecycleView(repositoryItems);
        }
    }
}
