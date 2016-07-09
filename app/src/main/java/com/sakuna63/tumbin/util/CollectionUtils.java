package com.sakuna63.tumbin.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collection;
import java.util.List;

public class CollectionUtils {
    public static <E> boolean equals(@Nullable Collection<E> collection1,
                                     @Nullable Collection<E> collection2) {
        return collection1 == null && collection2 == null
                || collection1 != null && collection2 != null
                && collection1.containsAll(collection2)
                && collection1.size() == collection2.size();

    }

    public static boolean isEmpty(@Nullable Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static void fillUntil(@NonNull List<?> list, int position) {
        while (list.size() <= position) {
            list.add(null);
        }
    }
}
