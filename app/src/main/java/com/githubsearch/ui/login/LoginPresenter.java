package com.githubsearch.ui.login;

import com.arellomobile.mvp.InjectViewState;
import com.githubsearch.App;
import com.githubsearch.base.BasePresenter;
import com.githubsearch.data.DataManager;

import javax.inject.Inject;

import okhttp3.Credentials;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by den on 08.05.18.
 */
@InjectViewState
public class LoginPresenter extends BasePresenter<LoginView> {

    public final String TAG = "LoginPresenter";

    private final String ERROR_LOGIN = "Invalid username or password";


    @Inject
    DataManager dataManager;

    @Override
    public void attachView(LoginView view) {
        super.attachView(view);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        App.getAppComponent().inject(this);
    }

    protected void doLoginCheck(String email, String password) {
        String credential = Credentials.basic(email, password);
        dataManager.saveUserCredential(credential);
        dataManager.doLogin(credential)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnCompleted(() -> getViewState().hideLoading())
                .subscribe(userResponse -> {
                   getViewState().starSearchRepoActivity(userResponse);
                } ,throwable -> {
                    getViewState().showMessageSnack(ERROR_LOGIN);
                    getViewState().hideLoading();
                });

    }
}
