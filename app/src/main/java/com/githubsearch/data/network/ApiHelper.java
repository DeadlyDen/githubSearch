package com.githubsearch.data.network;


import com.githubsearch.data.network.model.SearchRepoResponse;
import com.githubsearch.data.network.model.UserResponse;

import java.util.Map;

import retrofit2.http.Header;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Den on 31.05.2017.
 */

public interface ApiHelper {

    Observable<UserResponse> doLogin( String authorization);

    Observable<SearchRepoResponse> searchRepo(String authorization,
                                              String q, String sort,
                                              String order,  int per, int page);


}
