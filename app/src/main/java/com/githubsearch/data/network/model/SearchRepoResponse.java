package com.githubsearch.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by den on 30.06.18.
 */

public class SearchRepoResponse {

    @SerializedName("items")
    @Expose
    private List<RepositoryItem> items;


    public List<RepositoryItem> getItems() {
        return items;
    }

    public void setItems(List<RepositoryItem> items) {
        this.items = items;
    }

}
