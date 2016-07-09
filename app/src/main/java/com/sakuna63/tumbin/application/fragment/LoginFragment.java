package com.sakuna63.tumbin.application.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.sakuna63.tumbin.R;
import com.sakuna63.tumbin.application.activity.Henson;
import com.sakuna63.tumbin.application.contract.LoginContract;
import com.sakuna63.tumbin.databinding.FragmentLoginBinding;

@FragmentWithArgs
public class LoginFragment extends BaseFragment implements LoginContract.View {
    public static final String TAG = LoginFragment.class.getSimpleName();

    private LoginContract.Presenter presenter;
    private FragmentLoginBinding binding;

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
        binding.buttonLogin.setEnabled(active);
    }

    @Override
    public void setLoginProgress(boolean visible, @NonNull String message) {
        binding.containerProgress.setVisibility(visible ? View.VISIBLE : View.GONE);
        binding.progressbar.setVisibility(View.VISIBLE);
        binding.textLoginProgress.setText(message);
    }

    @Override
    public void showErrorMessage(@NonNull String message) {
        binding.containerProgress.setVisibility(View.VISIBLE);
        binding.progressbar.setVisibility(View.GONE);
        binding.textLoginProgress.setText(message);
    }

    @Override
    public void navigateToHome() {
        getActivity().finish();
        Intent intent = Henson.with(getContext()).gotoHomeActivity().build();
        startActivity(intent);
    }

    @Override
    public void navigateToLoginPage(@NonNull String url) {
        CustomTabsIntent tabsIntent = new CustomTabsIntent.Builder().build();
        tabsIntent.launchUrl(getActivity(), Uri.parse(url));
    }

    public void onNewIntent(Intent intent) {
        presenter.onLoginCallback(intent.getDataString());
    }
}
