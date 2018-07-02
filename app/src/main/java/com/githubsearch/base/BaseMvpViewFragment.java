package com.githubsearch.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.githubsearch.App;
import com.githubsearch.R;
import com.githubsearch.base.view.HideShowContentInteractionView;
import com.githubsearch.base.view.HideShowContentView;
import com.githubsearch.utils.RxBus;
import com.githubsearch.utils.ToastUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import butterknife.Unbinder;
import icepick.Icepick;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by den on 04.07.17.
 */

public abstract class BaseMvpViewFragment extends MvpAppCompatFragment
        implements HideShowContentView, HideShowContentInteractionView, RxSupport, IcepickSupport {

    @Nullable
    @BindView(R.id.loadingView)
    View loadingView;
    @Nullable
    @BindView(R.id.errorView)
    View errorView;
    @Nullable
    @BindView(R.id.emptyView)
    View emptyView;
    @Nullable
    @BindView(R.id.contentView)
    View contentView;

    @Inject
    protected ToastUtils toastUtils;

    private CompositeSubscription compositeSubscription;
    private Unbinder unbinder;

    protected View getLoadingView() {
        return loadingView;
    }

    protected View getContentView() {
        return contentView;
    }

    protected View getErrorView() {
        return errorView;
    }

    protected View getEmptyView() {
        return emptyView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppComponent().inject(this);
        restoreInstanceState(this, savedInstanceState);
        setRetainInstance(isRetainInstance());
        compositeSubscription = new CompositeSubscription();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.unregister(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        initViews();
        initRxSubscriptions();
    }

    @Override
    public void rxUnsubscribe() {
        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
        }
    }

    @Override
    public void rxAddSubscription(Subscription sub) {
        if (compositeSubscription != null) {
            compositeSubscription.add(sub);
        }
    }

    @Override
    public void initRxSubscriptions() {
    }

    @Override
    public void showContent() {
        if (getContentView() != null) {
            setVisibility(getContentView(), View.VISIBLE);
            setVisibility(getLoadingView(), View.GONE);
            setVisibility(getErrorView(), View.GONE);
            setVisibility(getEmptyView(), View.GONE);
        }
    }

    @Override
    public void showLoading() {
        if (getLoadingView() != null) {
            setVisibility(getContentView(), View.GONE);
            setVisibility(getLoadingView(), View.VISIBLE);
            setVisibility(getErrorView(), View.GONE);
            setVisibility(getEmptyView(), View.GONE);
        }
    }

    @Override
    public void hideLoading() {
        if (getLoadingView() != null) {
            setVisibility(getLoadingView(), View.GONE);
        }
    }

    @Override
    public void showError(String msg) {
        if (getErrorView() != null) {
            setVisibility(getContentView(), View.GONE);
            setVisibility(getLoadingView(), View.GONE);
            setVisibility(getErrorView(), View.VISIBLE);
            setVisibility(getEmptyView(), View.GONE);

            ((TextView) getErrorView()).setText(msg);
        }
    }

    @Override
    public void showError(@StringRes int msg) {
        showError(getString(msg));
    }

    @Override
    public void showEmpty(String msg) {
        if (getEmptyView() != null) {
            setVisibility(getContentView(), View.GONE);
            setVisibility(getLoadingView(), View.GONE);
            setVisibility(getErrorView(), View.GONE);
            setVisibility(getEmptyView(), View.VISIBLE);

            ((TextView) getEmptyView()).setText(msg);
        }
    }

    @Override
    public void showEmpty(@StringRes int msg) {
        showEmpty(getString(msg));
    }

    @Override
    public void showMessage(String msg) {
        toastUtils.show(msg);
    }

    @Override
    public void showMessage(int msg) {
        toastUtils.show(msg);
    }

    @Override
    public void showMessageSnack(String msg) {
        if (getActivity() != null) {
            Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), msg, 1500);
            View view = snackbar.getView();
            view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            TextView text = view.findViewById(android.support.design.R.id.snackbar_text);
            text.setTextColor(getResources().getColor(R.color.colorWhite));
            text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            snackbar.show();
        }
    }

    @Optional
    @OnClick(R.id.errorView)
    public void onErrorViewClick() {
        //do nothing
    }

    @Optional
    @OnClick(R.id.emptyView)
    public void onEmptyViewClick() {
        //do nothing
    }

    @Override
    public <T> void restoreInstanceState(T t, Bundle savedInstanceState) {
        Icepick.restoreInstanceState(t, savedInstanceState);
    }

    @Override
    public <T> void saveInstanceState(T t, Bundle savedInstanceState) {
        Icepick.saveInstanceState(t, savedInstanceState);
    }

    public void setVisibility(View view, int visibility) {
        if (view != null) {
            view.setVisibility(visibility);
        }
    }

    public abstract int getLayoutId();

    public boolean isRetainInstance() {
        return false;
    }



}
