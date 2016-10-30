package com.sakuna63.tumbin.application.contract

interface BaseView<in T> {
    fun setPresenter(presenter: T)
}
