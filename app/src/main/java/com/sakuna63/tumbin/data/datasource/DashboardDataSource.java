package com.sakuna63.tumbin.data.datasource;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import rx.Completable;
import rx.Single;

public interface DashboardDataSource {
    @NonNull
    Single<Integer> fetchDashboard(@Nullable @IntRange(from = 1, to = 20) Integer limit,
                                   @Nullable @IntRange(from = 0) Integer offset,
                                   @Nullable Long sinceId,
                                   @Nullable Long beforeId);

    Completable deleteAllCache();
}
