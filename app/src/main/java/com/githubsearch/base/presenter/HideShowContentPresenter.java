package com.githubsearch.base.presenter;

import android.support.annotation.StringRes;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.githubsearch.base.view.HideShowContentView;

/**
 * Created by Den on 22.05.2017.
 */

@InjectViewState
public class HideShowContentPresenter extends MvpPresenter<HideShowContentView> {

    public void showContent() {
        getViewState().showContent();
    }

    public void showLoading() {
        getViewState().showLoading();
    }

    public void showError(String msg) {
        getViewState().showError(msg);
    }

    public void showError(@StringRes int msg) {
        getViewState().showError(msg);
    }

    public void showEmpty(String msg) {
        getViewState().showError(msg);
    }

    public void showEmpty(@StringRes int msg) {
        getViewState().showEmpty(msg);
    }

    public void showMessage(String msg) {
        getViewState().showMessage(msg);
    }

    public void showMessage(int msg) {
        getViewState().showMessage(msg);
    }
}
