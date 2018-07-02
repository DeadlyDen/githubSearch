package com.githubsearch.ui.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.githubsearch.R;
import com.githubsearch.data.network.model.RepositoryItem;
import com.githubsearch.data.network.model.SearchRepoResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import github.nisrulz.recyclerviewhelper.RVHViewHolder;

/**
 * Created by den on 30.06.18.
 */

public class ViewedRepoViewHolder extends RecyclerView.ViewHolder implements RVHViewHolder {

    private RepositoryItem item;

    @BindView(R.id.repo_name)
    TextView repoName;
    @BindView(R.id.viewed)
    TextView viewedStatus;

    public ViewedRepoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void initData(Context context) {

        RepositoryItem item = this.item;
        int maxSymbols = 30;
        if (item.getName().length() > maxSymbols) {
            repoName.setText(item.getName().substring(0, maxSymbols).concat(context.getString(R.string.three_dots)));
        } else {
            repoName.setText(item.getName());
        }

    }

    @Override
    public void onItemClear() {

    }

    @Override
    public void onItemSelected(int actionstate) {

    }

    public RepositoryItem getItem() {
        return item;
    }

    public void setItem(RepositoryItem item) {
        this.item = item;
    }
}
