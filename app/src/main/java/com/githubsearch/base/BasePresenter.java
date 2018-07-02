package com.githubsearch.base;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by Den on 22.05.2017.
 */

public class BasePresenter<View extends MvpView> extends MvpPresenter<View> {

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    public void rxAddSubscription(Subscription sub) {
        compositeSubscription.add(sub);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeSubscription.clear();
    }

    public void logException(Throwable throwable) {
        Timber.e(throwable);
    }
}
