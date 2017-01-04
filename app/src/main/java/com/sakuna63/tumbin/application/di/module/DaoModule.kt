package com.sakuna63.tumbin.application.di.module

import com.sakuna63.tumbin.data.dao.DashboardRealmDao
import com.sakuna63.tumbin.data.dao.DashboardRealmDaoImpl
import dagger.Binds
import dagger.Module

@Suppress("unused")
@Module
abstract class DaoModule {
    @Binds
    abstract fun dashboardRealmDao(dao: DashboardRealmDaoImpl): DashboardRealmDao
}
