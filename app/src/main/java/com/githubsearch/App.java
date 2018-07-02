package com.githubsearch;

import android.app.Application;

import com.githubsearch.di.component.AppComponent;
import com.githubsearch.di.component.DaggerAppComponent;
import com.githubsearch.di.module.AppModule;
import com.githubsearch.di.module.UtilsModule;

import timber.log.Timber;

/**
 * Created by Den on 22.05.2017.
 */

public class App extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = buildAppComponent();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

    }

    private AppComponent buildAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .utilsModule(new UtilsModule())
                .build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
