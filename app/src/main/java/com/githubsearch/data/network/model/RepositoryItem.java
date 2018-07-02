package com.githubsearch.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by den on 02.07.18.
 */

public class RepositoryItem {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("full_name")
    @Expose
    private String name;

    @SerializedName("html_url")
    @Expose
    private String url;

    private boolean viewed = false;

    public RepositoryItem(int id, String name, String url, boolean viewed) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.viewed = viewed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }
}
