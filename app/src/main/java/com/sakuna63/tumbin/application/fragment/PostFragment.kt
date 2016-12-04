package com.sakuna63.tumbin.application.fragment

import com.sakuna63.tumbin.application.contract.PostContract

abstract class PostFragment : BaseFragment(), PostContract.View {
    companion object {
        val TAG = PostFragment::class.java.simpleName
    }
}
