package com.githubsearch.data.prefs;

import com.githubsearch.data.network.model.SearchRepoResponse;

import org.json.JSONArray;

import java.util.HashMap;

/**
 * Created by den on 15.08.17.
 */

public interface PreferenceHelper {


    void saveUserCredential(String credential);
    String loadUserCredential();
    void clearUserCredential();
    void saveViewedRepositories(JSONArray jsonArray);
    JSONArray loadViewedRepositories();
    void clearViewedRepositories();

}
