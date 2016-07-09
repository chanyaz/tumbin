package com.sakuna63.tumbin.data.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sakuna63.tumbin.application.di.module.ApiModule;
import com.sakuna63.tumbin.data.api.TumblrService;
import com.sakuna63.tumbin.data.dao.DashboardRealmDao;
import com.sakuna63.tumbin.data.model.response.PostsResponse;
import com.sakuna63.tumbin.data.model.response.TumblrResponse;
import com.sakuna63.tumbin.data.util.TestUtils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.Callable;

import rx.Single;
import rx.observers.TestSubscriber;
import rx.schedulers.TestScheduler;
import rx.subjects.TestSubject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DashboardRepositoryTest {
    private static final TumblrResponse<PostsResponse> RESPONSE = TestUtils.readJsonAssets(
            ApiModule.objectMapper(), new TypeReference<TumblrResponse<PostsResponse>>() {
            },
            "get_user_dashboard.json");

    DashboardRepository repository;

    @Mock
    TumblrService mockService;

    @Mock
    DashboardRealmDao mockDashboardRealmDao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        repository = new DashboardRepository(mockService, mockDashboardRealmDao);

        when(mockService.getDashboard(anyInt(), anyInt(), anyLong(), anyLong(), anyBoolean(), anyBoolean()))
                .thenReturn(Single.fromCallable(new Callable<TumblrResponse<PostsResponse>>() {
                    @Override
                    public TumblrResponse<PostsResponse> call() throws Exception {
                        return RESPONSE;
                    }
                }));
    }

    @Test
    public void fetchDashboard() throws Throwable {
        int expectedResponseSize = RESPONSE.getResponse().getPosts().size();

        TestScheduler testScheduler = new TestScheduler();
        TestSubject<TumblrResponse<PostsResponse>> testSubject = TestSubject.create(testScheduler);

        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();
        {
            Single<Integer> single = repository.fetchDashboard(null, null, null, null);
            single.subscribe(testSubscriber);

            testSubject.onNext(null);
            testSubject.onCompleted();
            testScheduler.triggerActions();
        }

        verify(mockDashboardRealmDao).insert(RESPONSE.getResponse().getPosts());

        Integer actualResponseSize = testSubscriber.getOnNextEvents().get(0);
        assertThat(actualResponseSize, is(expectedResponseSize));
    }
}
