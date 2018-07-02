package com.githubsearch.utils.impl;

import android.content.Context;
import android.widget.Toast;

import com.githubsearch.utils.ToastUtils;

import javax.inject.Inject;

/**
 * Created by Den on 03.06.2016.
 */
public class ToastUtilsImpl implements ToastUtils {

    private static Toast instance;

    private final int duration;

    Context context;

    @Inject
    public ToastUtilsImpl(Context context) {
        this.context = context;
        this.duration = Toast.LENGTH_SHORT;
    }

    private Toast makeText(Context context, String text) {
        cancel();
        instance = Toast.makeText(context, text, duration);
        return instance;
    }

    private Toast makeText(Context context, int resId) {
        cancel();
        instance = Toast.makeText(context, resId, duration);
        return instance;
    }

    public void show(String text) {
        makeText(context, text).show();
    }

    public void show(int resId) {
        makeText(context, resId).show();
    }



    private void cancel() {
        if (instance != null) {
            instance.cancel();
        }
    }
}
