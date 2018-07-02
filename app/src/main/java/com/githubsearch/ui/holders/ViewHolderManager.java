package com.githubsearch.ui.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.githubsearch.R;
import com.githubsearch.data.network.model.RepositoryItem;
import com.githubsearch.data.network.model.SearchRepoResponse;
import com.githubsearch.ui.content_adapters.ContentRecyclerAdapter;

/**
 * Created by den on 12.02.18.
 */

public class ViewHolderManager<T> {

    private int selectedPos = 0;

    public final static int SEARCH_REPO = 0;


    private ContentRecyclerAdapter tAbs;
    private Context context;
    private int holderType;

    public ViewHolderManager(int holderType, Context context, ContentRecyclerAdapter tAbs) {
        this.holderType = holderType;
        this.context = context;
        this.tAbs = tAbs;
    }

    public RecyclerView.ViewHolder createHolder(ViewGroup parent) {
        switch (holderType) {
            case SEARCH_REPO:
                return new SearchRepoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_search_repo, parent, false));
            default:
                return null;
        }

    }

    public void initHolder(RecyclerView.ViewHolder holder, T item, int position) {
        switch (holderType) {
            case SEARCH_REPO:
                SearchRepoViewHolder searchRepoViewHolder = (SearchRepoViewHolder) holder;
                RepositoryItem itemRepo = (RepositoryItem) item;
                searchRepoViewHolder.setItem(itemRepo);
                searchRepoViewHolder.initData(context);
                searchRepoViewHolder.itemView.setOnClickListener(view -> tAbs.contentRecycleOnClick.onClickItem(position, view.getId(), view));
                if (itemRepo.isViewed()) {
                    searchRepoViewHolder.viewedStatus.setVisibility(View.VISIBLE);
                    searchRepoViewHolder.viewedStatus.setText(context.getString(R.string.viewed));
                } else {
                    searchRepoViewHolder.viewedStatus.setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }
    }

    public int getSelectedPos() {
        return selectedPos;
    }

    public void setSelectedPos(int selectedPos) {
        this.selectedPos = selectedPos;
    }

}
