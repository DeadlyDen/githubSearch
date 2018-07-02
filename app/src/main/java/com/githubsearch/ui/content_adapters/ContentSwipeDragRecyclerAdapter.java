package com.githubsearch.ui.content_adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.githubsearch.App;
import com.githubsearch.R;
import com.githubsearch.data.DataManager;
import com.githubsearch.data.network.model.RepositoryItem;
import com.githubsearch.data.network.model.SearchRepoResponse;
import com.githubsearch.ui.holders.SearchRepoViewHolder;
import com.githubsearch.ui.holders.ViewHolderManager;
import com.githubsearch.ui.holders.ViewedRepoViewHolder;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import github.nisrulz.recyclerviewhelper.RVHAdapter;

/**
 * Created by den on 12.12.17.
 */

public class ContentSwipeDragRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RVHAdapter {

    @Inject
    DataManager dataManager;

    private List<RepositoryItem> items = new ArrayList<>();
    private Context context;
    private JSONArray viewedRepositories = new JSONArray();
    private Gson gson = new Gson();

    public ContentSwipeDragRecyclerAdapter(List<RepositoryItem> items, Context context) {
        App.getAppComponent().inject(this);
        this.items = items;
        this.context = context;

        prepareJsonArray();
    }

    private void prepareJsonArray() {
            for (RepositoryItem repositoryItem : items) {
                try {
                    viewedRepositories.put(new JSONObject(gson.toJson(repositoryItem)));
                } catch (JSONException e) {
                    Log.v("Json : ", String .valueOf(e));
                }
            }
    }

    public void updateItems(List<RepositoryItem> updatedItems) {
        items.clear();
        items.addAll(updatedItems);
        notifyDataSetChanged();
    }

    public void uploadItems(List<RepositoryItem> updatedItems) {
        items.addAll(updatedItems);
        notifyDataSetChanged();
    }

    public void updateItem(RepositoryItem item, int position) {
        items.set(position, item);
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, items.size());
    }

    public void clearData() {
        if (items != null && items.size() != 0) {
            items.clear();
            notifyDataSetChanged();
        }
    }

    public RepositoryItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
            return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewedRepoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_search_repo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewedRepoViewHolder viewedRepoViewHolder = (ViewedRepoViewHolder) holder;
        RepositoryItem itemRepo = (RepositoryItem) items.get(position);
        viewedRepoViewHolder.setItem(itemRepo);
        viewedRepoViewHolder.initData(context);
    }

    @Override
    public int getItemViewType(int position) {
            return position;
    }

    public List<RepositoryItem> getItems() {
        return items;
    }

    @Override
    public void onItemDismiss(int position, int direction) {
        remove(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        swap(fromPosition, toPosition);
        return false;
    }

    private void remove(int position) {
        items.remove(position);
        viewedRepositories.remove(position);
        notifyItemRemoved(position);
        dataManager.saveViewedRepositories(viewedRepositories);
    }

    private void swap(int firstPosition, int secondPosition) {
        Collections.swap(items, firstPosition, secondPosition);
        notifyItemMoved(firstPosition, secondPosition);
    }
}
