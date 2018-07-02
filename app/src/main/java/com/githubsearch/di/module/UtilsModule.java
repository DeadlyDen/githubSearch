package com.githubsearch.di.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.githubsearch.data.DataManager;
import com.githubsearch.data.DataManagerImpl;
import com.githubsearch.data.network.ApiHelper;
import com.githubsearch.data.network.ApiHelperImpl;
import com.githubsearch.data.network.Client;
import com.githubsearch.data.network.ServiceGenerator;
import com.githubsearch.data.prefs.PreferenceHelper;
import com.githubsearch.data.prefs.PreferenceHelperImpl;
import com.githubsearch.utils.CollectionUtils;
import com.githubsearch.utils.KeyboardUtils;
import com.githubsearch.utils.ToastUtils;
import com.githubsearch.utils.impl.ToastUtilsImpl;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Den on 31.05.2017.
 */
@Module
public class UtilsModule {

    @Singleton
    @Provides
    public ToastUtils provideToastUtils(ToastUtilsImpl impl) {
        return impl;
    }

    @Singleton
    @Provides
    public ServiceGenerator serviceGenerator() {
        return new ServiceGenerator();
    }

    @Singleton
    @Provides
    public Client provideClient(ServiceGenerator serviceGenerator) {
        return serviceGenerator.createService(Client.class);
    }

    @Singleton
    @Provides
    public ApiHelper provideApiHelper(ApiHelperImpl impl) {
        return impl;
    }

    @Singleton
    @Provides
    public DataManager provideDataManager(DataManagerImpl impl) {
        return impl;
    }

    @Singleton
    @Provides
    @Named("user_prefs")
    SharedPreferences getUserPreferences(Context context) {
        String USER_PREFS = "user_prefs";
        return context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    public SharedPreferences provideSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Singleton
    @Provides
    CollectionUtils getCollectionsUtils() {
        return new CollectionUtils();
    }

    @Singleton
    @Provides
    public PreferenceHelper providePreferenceHelper(PreferenceHelperImpl impl) {
        return impl;
    }


    @Singleton
    @Provides
    public KeyboardUtils provideKeyBoardUtils(Context context) {
        return new KeyboardUtils(context);
    }
}
