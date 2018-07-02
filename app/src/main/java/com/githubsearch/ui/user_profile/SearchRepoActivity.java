package com.githubsearch.ui.user_profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.github.ybq.android.spinkit.SpinKitView;
import com.githubsearch.R;
import com.githubsearch.base.BaseMvpViewActivity;
import com.githubsearch.data.network.model.RepositoryItem;
import com.githubsearch.data.network.model.SearchRepoResponse;
import com.githubsearch.data.network.model.UserResponse;
import com.githubsearch.ui.content_adapters.ContentRecycleOnClick;
import com.githubsearch.ui.content_adapters.ContentRecyclerAdapter;
import com.githubsearch.ui.holders.ViewHolderManager;
import com.githubsearch.ui.recent_search.RecentSearchActivity;
import com.githubsearch.utils.EndlessRecyclerViewScrollListener;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchRepoActivity extends BaseMvpViewActivity implements SearchRepoView, ContentRecycleOnClick, NavigationView.OnNavigationItemSelectedListener {

    @InjectPresenter
    SearchRepoPresenter searchRepoPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.search_view)
    SearchView searchView;
    @BindView(android.support.v7.appcompat.R.id.search_src_text)
    EditText txtSearch;
    @BindView(R.id.spin_kit_more)
    SpinKitView progressBar;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private JSONArray viewedRepositories;
    private Gson gson = new Gson();

    private UserResponse userResponse;

    private ContentRecyclerAdapter contentRecyclerAdapter;

    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_repo);
        setSupportActionBar(toolbar);
        initToolBar();

        if (getIntent().getExtras() != null) {
            userResponse = (UserResponse) getIntent().getExtras().getSerializable("user");
        }
        loadUserInfo();
    }

    private void initToolBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        searchView.setIconifiedByDefault(false);
        txtSearch.setHint(getResources().getString(R.string.search_hint));
        txtSearch.setHintTextColor(getColor(R.color.colorAccent));
        txtSearch.setTextColor(getColor(R.color.colorAccent));
    }

    private void loadUserInfo() {
        try {

            View v = navigationView.inflateHeaderView(R.layout.nav_header);
            ImageView avatar = v.findViewById(R.id.avatar);
            TextView userName = v.findViewById(R.id.user_name);

            Picasso.with(this).load(userResponse.getAvatar()).into(avatar);
            userName.setText(userResponse.getLogin());

        } catch (NullPointerException ex) {
            showMessageSnack(getString(R.string.oops_something_went_wrong));
        }
    }

    @Override
    public void prepareViewedRepositories(JSONArray jsonArray) {
        if (jsonArray.length() != 0) {
            viewedRepositories = jsonArray;
        } else {
            viewedRepositories = new JSONArray();
        }
    }

    @Override
    public void initRxSubscriptions() {
        super.initRxSubscriptions();
        searchRepoPresenter.subscribeSearchView(searchView);
    }


    @Override
    public void initRecycleView(List<RepositoryItem> items) {
        recyclerView.setLayoutManager(linearLayoutManager);
        contentRecyclerAdapter = new ContentRecyclerAdapter<>(this, items, ViewHolderManager.SEARCH_REPO);
        recyclerView.setAdapter(contentRecyclerAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        contentRecyclerAdapter.setContentRecycleOnClick(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

    }

    @SuppressWarnings("unchecked")
    @Override
    public void updateDataInRecyclerAdapter(List<RepositoryItem> items) {
        contentRecyclerAdapter.updateItems(items);

    }

    @SuppressWarnings("unchecked")
    @Override
    public void uploadDataInRecyclerAdapter(List<RepositoryItem> items) {
        contentRecyclerAdapter.uploadItems(items);
    }

    @Override
    public void clearDataInRecyclerAdapter() {
        if (contentRecyclerAdapter != null) {
            hideProgressBar();
            contentRecyclerAdapter.clearData();
        }
    }

    @Override
    public void initSwipeRefreshLayout() {

    }

    @Override
    public void initRecycleViewMoreListener() {
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                showProgressBar();
                searchRepoPresenter.loadRepositories(searchView.getQuery().toString(), page, SearchRepoPresenter.UPLOAD_STATE);
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onClickItem(int position, int id, View v) {
        RepositoryItem item = (RepositoryItem) contentRecyclerAdapter.getItem(position);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getUrl()));
        startActivity(browserIntent);
        addViewedRepositories(item);
        item.setViewed(true);
        contentRecyclerAdapter.updateItem(item, position);
    }

    private void addViewedRepositories(RepositoryItem item) {
            if (checkViewedRepositories(item.getId())) {
                try {
                    viewedRepositories.put(new JSONObject(gson.toJson(item)));
                    searchRepoPresenter.saveViewedRepositories(viewedRepositories);
                } catch (JSONException e) {
                    Log.v("Json : ", String .valueOf(e));
                }
                Log.v("Json : ", viewedRepositories.toString());
            }
    }

    private boolean checkViewedRepositories(int id) {
        if (viewedRepositories.length() == 0) {
            return true;
        } else {
            for (int i = 0; i < viewedRepositories.length(); i++) {
                try {
                    int idAvRepo = Integer.valueOf(viewedRepositories.getJSONObject(i).getString("id"));
                    return idAvRepo != id;
                } catch (JSONException e) {
                    Log.v("Json : ", String.valueOf(e));
                }
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            showMessageSnack("Click again to close!");
        }
        mBackPressed = System.currentTimeMillis();
    }

    @OnClick(R.id.stop_search)
    public void stopSearch() {
        showMessageSnack("Stop search");
        hideProgressBar();
        searchRepoPresenter.stop();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_recent:
                startActivity(new Intent(this, RecentSearchActivity.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_clear:
                clearDataInRecyclerAdapter();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_clear_viewed:
                searchRepoPresenter.clearViewedRepositories();
                drawerLayout.closeDrawer(GravityCompat.START);
                viewedRepositories = new JSONArray();
                return true;
            default:
                return false;
        }
    }
}
