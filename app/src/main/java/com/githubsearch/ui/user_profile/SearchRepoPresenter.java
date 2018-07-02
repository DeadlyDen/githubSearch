package com.githubsearch.ui.user_profile;


import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.githubsearch.App;
import com.githubsearch.base.BasePresenter;
import com.githubsearch.data.DataManager;
import com.githubsearch.data.network.model.RepositoryItem;
import com.githubsearch.data.network.model.SearchRepoResponse;
import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by den on 10.05.18.
 */
@InjectViewState
public class SearchRepoPresenter extends BasePresenter<SearchRepoView> {

    @Inject
    DataManager dataManager;

    public final static String TAG = "UserPresenter";

    private final String SORT_TYPE = "stars";
    private final String ORDER = "desc";
    private final int PER_PAGE = 15;

    public final static int INIT_STATE = 0;
    public final static int UPLOAD_STATE = 1;
    public final static int UPDATE_STATE = 2;

    private boolean initStatus = true;
    private int lastPage = 0;

    private Subscription subscription;

    @Override
    public void attachView(SearchRepoView view) {
        super.attachView(view);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        App.getAppComponent().inject(this);
        getViewState().prepareViewedRepositories(dataManager.loadViewedRepositories());
    }

    void subscribeSearchView(SearchView searchView) {
        RxSearchView.queryTextChanges(searchView)
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter(charSequence -> {
                    final boolean empty = TextUtils.isEmpty(charSequence) || charSequence.toString().length() == 0;
                    return !empty;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .distinctUntilChanged()
                .subscribe(charSequence -> {
                    getViewState().clearDataInRecyclerAdapter();
                    getViewState().showProgressBar();
                    lastPage = 0;
                    loadRepositories(charSequence.toString(), 1, initStatus ? INIT_STATE : UPDATE_STATE);
                }, throwable -> Log.v("Error search : ", String.valueOf(throwable)));
    }

    void loadRepositories(String q, int page, int state) {
        Observable<SearchRepoResponse> searchRepoFirst = dataManager.searchRepo(dataManager.loadUserCredential(),
                q, SORT_TYPE, ORDER, PER_PAGE, page + lastPage).subscribeOn(Schedulers.computation());
        Observable<SearchRepoResponse> searchRepoSecond = dataManager.searchRepo(dataManager.loadUserCredential(),
                q, SORT_TYPE, ORDER, PER_PAGE, page + page).subscribeOn(Schedulers.computation());
        lastPage = page;

        subscription = Observable.zip(searchRepoFirst, searchRepoSecond, (searchRepoResponse, searchRepoResponse2) -> {
            Log.v("Thread : ", Thread.currentThread().getName());
            searchRepoResponse.getItems().addAll(searchRepoResponse2.getItems());
            for (RepositoryItem item : searchRepoResponse.getItems()) {
                setViewedItem(item);
            }
            return searchRepoResponse;
        }).observeOn(AndroidSchedulers.mainThread())
                .doOnCompleted(() -> getViewState().hideProgressBar())
                .subscribe(searchRepoResponse -> {
                    switch (state) {
                        case INIT_STATE:
                            Log.v("State : ", "init");
                            Log.v("Thread : ", Thread.currentThread().getName());
                            getViewState().initRecycleView(searchRepoResponse.getItems());
                            getViewState().initRecycleViewMoreListener();
                            initStatus = false;
                            break;
                        case UPLOAD_STATE:
                            Log.v("State : ", "upload");
                            getViewState().uploadDataInRecyclerAdapter(searchRepoResponse.getItems());
                            break;
                        case UPDATE_STATE:
                            Log.v("State : ", "update");
                            getViewState().updateDataInRecyclerAdapter(searchRepoResponse.getItems());
                            break;
                        default:
                            break;
                    }

                }, throwable -> {
                    Log.v("Error search : ", String.valueOf(throwable));
                    getViewState().hideProgressBar();
                });
    }


    void saveViewedRepositories(JSONArray jsonArray) {
        dataManager.saveViewedRepositories(jsonArray);
    }

    private void setViewedItem(RepositoryItem viewedItem) {
        for (int i = 0; i < dataManager.loadViewedRepositories().length(); i++) {
            try {
                int idAvRepo = Integer.valueOf(dataManager.loadViewedRepositories().getJSONObject(i).getString("id"));
                if (idAvRepo == viewedItem.getId()) {
                    viewedItem.setViewed(true);
                }
            } catch (JSONException e) {
                Log.v("Json : ", String.valueOf(e));
            }
        }
    }

    void clearViewedRepositories() {
        dataManager.clearViewedRepositories();
    }

    void stop() {

        // one problem. When i unsubscribe search observer, i cant load more items, because observable is unsubscribe;

        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

}
