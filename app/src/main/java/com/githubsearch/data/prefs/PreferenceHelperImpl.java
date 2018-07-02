package com.githubsearch.data.prefs;

import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Den on 01.06.2017.
 */

public class PreferenceHelperImpl implements PreferenceHelper {

    private static final String USER_CREDENTIAL = "user_credential";
    private static final String USER_VIEWED_REPO = "user_repositories";


    SharedPreferences prefs;

    @Inject
    public PreferenceHelperImpl(@Named("user_prefs") SharedPreferences prefs) {
        this.prefs = prefs;
    }

    @Override
    public void saveUserCredential(String credential) {
        prefs.edit().putString(USER_CREDENTIAL, credential).apply();
    }

    @Override
    public String loadUserCredential() {
        return prefs.getString(USER_CREDENTIAL, null);
    }

    @Override
    public void clearUserCredential() {
        prefs.edit().remove(USER_CREDENTIAL).apply();
    }

    @Override
    public void saveViewedRepositories(JSONArray jsonArray) {
        if (loadViewedRepositories().length() != 0) {
            clearViewedRepositories();
        }
        prefs.edit().putString(USER_VIEWED_REPO, jsonArray.toString()).apply();
    }

    @Override
    public JSONArray loadViewedRepositories() {
        try {
            return new JSONArray(prefs.getString(USER_VIEWED_REPO, "[]")) ;
        } catch (JSONException e) {
            Log.v("Json :" , String.valueOf(e));
        }
        return new JSONArray();
    }

    @Override
    public void clearViewedRepositories() {
        prefs.edit().remove(USER_VIEWED_REPO).apply();
    }
}


