package com.githubsearch.di.component;

import com.githubsearch.base.BaseMvpViewActivity;
import com.githubsearch.base.BaseMvpViewFragment;
import com.githubsearch.di.module.AppModule;
import com.githubsearch.di.module.UtilsModule;

import com.githubsearch.ui.content_adapters.ContentSwipeDragRecyclerAdapter;
import com.githubsearch.ui.login.LoginPresenter;
import com.githubsearch.ui.recent_search.RecentSearchPresenter;
import com.githubsearch.ui.user_profile.SearchRepoPresenter;


import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Den on 31.05.2017.
 */

@Component(modules = {AppModule.class, UtilsModule.class})
@Singleton
public interface AppComponent {

    void inject(BaseMvpViewActivity activity);
    void inject(BaseMvpViewFragment fragment);

    void inject(LoginPresenter loginPresenter);
    void inject(SearchRepoPresenter searchRepoPresenter);
    void inject(RecentSearchPresenter recentSearchPresenter);
    void inject(ContentSwipeDragRecyclerAdapter contentSwipeDragRecyclerAdapter);
}
