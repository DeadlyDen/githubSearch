package com.githubsearch.data;

import com.githubsearch.data.network.ApiHelper;

import com.githubsearch.data.network.model.SearchRepoResponse;
import com.githubsearch.data.network.model.UserResponse;
import com.githubsearch.data.prefs.PreferenceHelper;

import org.json.JSONArray;

import java.util.Map;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Den on 31.05.2017.
 */

public class DataManagerImpl implements DataManager {

    private ApiHelper apiHelper;
    private PreferenceHelper preferenceHelper;


    @Inject
    public DataManagerImpl(ApiHelper apiHelper, PreferenceHelper preferenceHelper) {
        this.apiHelper = apiHelper;
        this.preferenceHelper = preferenceHelper;
    }


    @Override
    public Observable<UserResponse> doLogin(String authorization) {
        return apiHelper.doLogin(authorization);
    }

    @Override
    public Observable<SearchRepoResponse> searchRepo(String authorization, String q, String sort, String order, int per, int page) {
        return apiHelper.searchRepo(authorization, q, sort, order, per, page);
    }

    @Override
    public void saveUserCredential(String credential) {
        preferenceHelper.saveUserCredential(credential);
    }

    @Override
    public String loadUserCredential() {
        return preferenceHelper.loadUserCredential();
    }

    @Override
    public void clearUserCredential() {
        preferenceHelper.clearUserCredential();
    }

    @Override
    public void saveViewedRepositories(JSONArray jsonArray) {
        preferenceHelper.saveViewedRepositories(jsonArray);
    }

    @Override
    public JSONArray loadViewedRepositories() {
        return preferenceHelper.loadViewedRepositories();
    }

    @Override
    public void clearViewedRepositories() {
        preferenceHelper.clearViewedRepositories();
    }
}
