package com.githubsearch.data.network;

import com.githubsearch.data.network.model.SearchRepoResponse;
import com.githubsearch.data.network.model.UserResponse;

import java.util.Map;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Den on 31.05.2017.
 */

public class ApiHelperImpl implements ApiHelper {

    Client client;

    @Inject
    public ApiHelperImpl(Client client) {
        this.client = client;
    }


    @Override
    public Observable<UserResponse> doLogin(String authorization) {
        return client.doLogin(authorization);
    }

    @Override
    public Observable<SearchRepoResponse> searchRepo(String authorization, String q, String sort, String order, int per, int page) {
        return client.searchRepo(authorization, q, sort, order, per, page);
    }
}
