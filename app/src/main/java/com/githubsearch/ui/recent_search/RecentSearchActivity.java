package com.githubsearch.ui.recent_search;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.githubsearch.R;
import com.githubsearch.base.BaseMvpViewActivity;
import com.githubsearch.data.network.model.RepositoryItem;
import com.githubsearch.ui.content_adapters.ContentSwipeDragRecyclerAdapter;

import java.util.List;

import butterknife.BindView;
import github.nisrulz.recyclerviewhelper.RVHItemClickListener;
import github.nisrulz.recyclerviewhelper.RVHItemDividerDecoration;
import github.nisrulz.recyclerviewhelper.RVHItemTouchHelperCallback;

public class RecentSearchActivity extends BaseMvpViewActivity implements RecentSearchView {

    @InjectPresenter
    RecentSearchPresenter recentSearchPresenter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    ContentSwipeDragRecyclerAdapter contentSwipeDragRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_search);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void initRecycleView(List<RepositoryItem> items) {
        recyclerView.setLayoutManager(linearLayoutManager);
        contentSwipeDragRecyclerAdapter = new ContentSwipeDragRecyclerAdapter(items, this);
        recyclerView.setAdapter(contentSwipeDragRecyclerAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        ItemTouchHelper.Callback callback = new RVHItemTouchHelperCallback(contentSwipeDragRecyclerAdapter, true, true,
                true);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);

//        recyclerView.addOnItemTouchListener(new RVHItemClickListener(this, (view, position) -> {
//
//        }));
    }

    @Override
    public void updateDataInRecyclerAdapter(List<RepositoryItem> items) {

    }

    @Override
    public void uploadDataInRecyclerAdapter(List<RepositoryItem> items) {

    }

    @Override
    public void clearDataInRecyclerAdapter() {

    }

    @Override
    public void initSwipeRefreshLayout() {

    }

    @Override
    public void initRecycleViewMoreListener() {

    }
}
