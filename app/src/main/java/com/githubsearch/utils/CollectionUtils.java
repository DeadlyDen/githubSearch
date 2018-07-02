package com.githubsearch.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Den on 24.05.2016.
 */
public class CollectionUtils {

    public static boolean isNullOrEmpty(@NonNull final Collection<?> c) {
        return c == null || c.isEmpty();
    }

    public static boolean isNullOrEmpty(@NonNull final Object[] c) {
        return c == null || c.length == 0;
    }

    public static boolean isNullOrEmpty(@NonNull final Map<?, ?> m) {
        return m == null || m.isEmpty();
    }

    public static <T, E> T getKeyByValue(@NonNull Map<T, E> map, @Nullable E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

}
