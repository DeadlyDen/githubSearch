package com.githubsearch.ui.content_adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.githubsearch.ui.holders.ViewHolderManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by den on 12.12.17.
 */

public class ContentRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<T> items = new ArrayList<>();
    public ContentRecycleOnClick contentRecycleOnClick;
    public ContentRecycleOnLongClick contentRecycleOnLongClick;

    private ViewHolderManager viewHolderManager;

    public ContentRecyclerAdapter(Context context, List<T> items, int holderType) {
        this.items = items;
        viewHolderManager = new ViewHolderManager<T>(holderType, context, this);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return viewHolderManager.createHolder(parent);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        viewHolderManager.initHolder(holder, items.get(position), position);
    }

    public void updateItems(List<T> updatedItems) {
        items.clear();
        items.addAll(updatedItems);
        notifyDataSetChanged();
    }

    public void uploadItems(List<T> updatedItems) {
        items.addAll(updatedItems);
        notifyDataSetChanged();
    }

    public void updateItem(T item, int position) {
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

    public T getItem(int position) {
        return items.get(position);
    }

    public void setContentRecycleOnClick(ContentRecycleOnClick contentRecycleOnClick) {
        this.contentRecycleOnClick = contentRecycleOnClick;
    }

    public void setContentRecycleOnLongClick(ContentRecycleOnLongClick contentRecycleOnLongClick) {
        this.contentRecycleOnLongClick = contentRecycleOnLongClick;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
            return position;
    }

    @Override
    public int getItemViewType(int position) {
            return position;
    }

    public List<T> getItems() {
        return items;
    }

    public ViewHolderManager getViewHolderManager() {
        return viewHolderManager;
    }

}
