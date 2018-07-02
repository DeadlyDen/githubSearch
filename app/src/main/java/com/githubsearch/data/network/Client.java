package com.githubsearch.data.network;


import com.githubsearch.data.network.model.SearchRepoResponse;
import com.githubsearch.data.network.model.UserResponse;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Den on 31.05.2017.
 */

public interface Client {


    @GET("user")
    Observable<UserResponse> doLogin(@Header("Authorization") String authorization);
    @GET("search/repositories")
    Observable<SearchRepoResponse> searchRepo(@Header("Authorization") String authorization,
                                              @Query("q") String q, @Query("sort") String sort,
                                              @Query("order") String order, @Query("per_page") int per,
                                              @Query("page") int page);

}
