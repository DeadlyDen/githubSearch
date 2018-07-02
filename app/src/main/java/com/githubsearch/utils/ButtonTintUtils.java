package com.githubsearch.utils;

import android.content.res.ColorStateList;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.view.View;

/**
 * Created by Den on 26.12.2016.
 */

public class ButtonTintUtils {

    private ButtonTintUtils() {
    }

    public static void setButtonEnabled(@NonNull View view, boolean enabled, @ColorRes int colorEnabled, @ColorRes int colorDisabled) {
        if (enabled) {
            ViewCompat.setBackgroundTintList(view, ColorStateList.valueOf(view.getContext().getResources().getColor(colorEnabled)));
            view.setEnabled(true);
        } else {
            ViewCompat.setBackgroundTintList(view, ColorStateList.valueOf(view.getContext().getResources().getColor(colorDisabled)));
            view.setEnabled(false);
        }
    }

}
