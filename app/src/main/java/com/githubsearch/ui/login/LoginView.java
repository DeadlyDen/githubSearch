package com.githubsearch.ui.login;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.githubsearch.base.view.HideShowContentView;
import com.githubsearch.data.network.model.UserResponse;

/**
 * Created by den on 08.05.18.
 */
@StateStrategyType(AddToEndSingleStrategy.class)
public interface LoginView extends MvpView, HideShowContentView {

    void starSearchRepoActivity(UserResponse userResponse);

}
