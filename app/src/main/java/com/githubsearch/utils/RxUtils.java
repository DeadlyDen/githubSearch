package com.githubsearch.utils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Den on 26.12.2016.
 */

public class RxUtils {

    private static final Observable.Transformer schedulersTransformer =
            obj -> ((Observable) obj).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());


    @SuppressWarnings("unchecked")
    public static <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) schedulersTransformer;
    }

}
