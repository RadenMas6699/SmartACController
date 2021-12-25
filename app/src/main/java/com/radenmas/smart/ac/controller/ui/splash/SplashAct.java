package com.radenmas.smart.ac.controller.ui.splash;

import android.content.Intent;
import android.os.Handler;

import com.radenmas.smart.ac.controller.R;
import com.radenmas.smart.ac.controller.base.BaseActivity;
import com.radenmas.smart.ac.controller.ui.main.MainAct;

public class SplashAct extends BaseActivity {
    @Override
    protected int getLayoutResource() {
        return R.layout.act_splash;
    }

    @Override
    protected void myCodeHere() {

        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashAct.this, MainAct.class));
            finish();
        }, 1500);
    }
}
