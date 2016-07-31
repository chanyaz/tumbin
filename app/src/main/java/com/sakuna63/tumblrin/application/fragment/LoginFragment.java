package com.sakuna63.tumblrin.application.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sakuna63.tumblrin.R;
import com.sakuna63.tumblrin.application.activity.Henson;
import com.sakuna63.tumblrin.application.contract.LoginContract;
import com.sakuna63.tumblrin.databinding.FragmentLoginBinding;

public class LoginFragment extends BaseFragment implements LoginContract.View {
    public static final String TAG = LoginFragment.class.getSimpleName();

    private LoginContract.Presenter presenter;
    private FragmentLoginBinding binding;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.setPresenter(presenter);
        presenter.init();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setLoginButtonActive(boolean active) {
        binding.buttonLogin.setActivated(active);
    }

    @Override
    public void setLoginProgress(boolean visible) {
        binding.progressbar.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showErrorMessage(String message) {
        Log.d("LoginFragment", message);
    }

    @Override
    public void navigateToHome() {
        getActivity().finish();
        Intent intent = Henson.with(getContext()).gotoHomeActivity().build();
        startActivity(intent);
    }

    @Override
    public void navigateToLoginPage(String url) {
        CustomTabsIntent tabsIntent = new CustomTabsIntent.Builder().build();
        tabsIntent.launchUrl(getActivity(), Uri.parse(url));
    }

    public void onNewIntent(Intent intent) {
        presenter.onLoginCallback(intent.getDataString());
    }
}
