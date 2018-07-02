package com.githubsearch.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import javax.inject.Inject;

/**
 * Created by Den on 12.06.2017.
 */

public class KeyboardUtils {

    private Context context;

    @Inject
    public KeyboardUtils(Context context) {
        this.context = context;
    }

    public void showKeyboard(View target) {
        if (target != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(target, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public void hideKeyboard(View target) {
        if (target != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(target.getWindowToken(), 0);
        }
    }

}
