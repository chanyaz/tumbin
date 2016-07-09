package com.sakuna63.tumblrin;

import com.f2prateek.dart.HensonNavigable;
import com.sakuna63.tumblrin.application.activity.BaseActivity;
import com.sakuna63.tumblrin.application.activity.LoginActivity;
import com.sakuna63.tumblrin.application.di.component.ActivityComponent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

@HensonNavigable
public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = Henson.with(this).gotoLoginActivity().build();
        startActivity(intent);
    }

    @Override
    public ActivityComponent getActivityComponent() {
        return null;
    }
}
