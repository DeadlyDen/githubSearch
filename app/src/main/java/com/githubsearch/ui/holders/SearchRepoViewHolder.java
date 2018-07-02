package com.githubsearch.ui.holders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.githubsearch.R;
import com.githubsearch.data.network.model.RepositoryItem;
import com.githubsearch.data.network.model.SearchRepoResponse;

import butterknife.BindView;

/**
 * Created by den on 30.06.18.
 */

public class SearchRepoViewHolder extends AbstractContentHolder<RepositoryItem> {

    @BindView(R.id.repo_name)
    TextView repoName;
    @BindView(R.id.viewed)
    TextView viewedStatus;

    public SearchRepoViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void initData(Context context) {

        RepositoryItem item = getItem();
        int maxSymbols = 30;
        if (item.getName().length() > maxSymbols) {
            repoName.setText(item.getName().substring(0, maxSymbols).concat(context.getString(R.string.three_dots)));
        } else {
            repoName.setText(item.getName());
        }



    }
}
