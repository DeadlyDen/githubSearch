package com.githubsearch.base;

import android.os.Bundle;

/**
 * Created by Den on 12.09.2016.
 */
public interface IcepickSupport {

    <T> void restoreInstanceState(T t, Bundle savedInstanceState);

    <T> void saveInstanceState(T t, Bundle savedInstanceState);
}
