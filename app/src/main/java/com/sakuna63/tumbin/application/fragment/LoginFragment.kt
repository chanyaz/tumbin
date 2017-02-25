package com.sakuna63.tumbin.application.fragment

import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sakuna63.tumbin.R
import com.sakuna63.tumbin.application.activity.HomeActivity
import com.sakuna63.tumbin.application.contract.LoginContract
import com.sakuna63.tumbin.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment(), LoginContract.View {

    lateinit private var presenter: LoginContract.Presenter
    lateinit private var binding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentLoginBinding>(inflater!!,
                R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.presenter = presenter
        presenter.init()
    }

    override fun setPresenter(presenter: LoginContract.Presenter) {
        this.presenter = presenter
    }

    override fun setLoginButtonActive(active: Boolean) {
        binding.buttonLogin.isEnabled = active
    }

    override fun setLoginProgress(visible: Boolean, message: String) {
        binding.containerProgress.visibility = if (visible) View.VISIBLE else View.GONE
        binding.progressbar.visibility = View.VISIBLE
        binding.textLoginProgress.text = message
    }

    override fun showErrorMessage(message: String) {
        binding.containerProgress.visibility = View.VISIBLE
        binding.progressbar.visibility = View.GONE
        binding.textLoginProgress.text = message
    }

    override fun navigateToHome() {
        activity.finish()
        startActivity(Intent(context, HomeActivity::class.java))
    }

    override fun navigateToLoginPage(url: String) {
        val tabsIntent = CustomTabsIntent.Builder().build()
        tabsIntent.launchUrl(activity, Uri.parse(url))
    }

    fun onNewIntent(intent: Intent) {
        presenter.onLoginCallback(intent.dataString)
    }

    companion object {
        val TAG = LoginFragment::class.java.simpleName!!

        fun newInstance() = LoginFragment()
    }
}
