package com.githubsearch.base.view;

import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by Den on 12.09.2016.
 */
public interface HideShowContentView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showContent();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showLoading();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void hideLoading();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showError(String msg);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showError(@StringRes int msg);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showEmpty(String msg);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showEmpty(@StringRes int msg);

    @StateStrategyType(SkipStrategy.class)
    void showMessage(String msg);

    @StateStrategyType(SkipStrategy.class)
    void showMessage(int msg);

    @StateStrategyType(SkipStrategy.class)
    void showMessageSnack(String msg);
}
