package com.radenmas.smart.ac.controller.ui.main;

import com.radenmas.smart.ac.controller.R;
import com.radenmas.smart.ac.controller.base.BaseActivity;

public class MainAct extends BaseActivity {
    int state = 0;

    @Override
    protected int getLayoutResource() {
        return R.layout.act_main;
    }

    @Override
    protected void myCodeHere() {
        getSupportFragmentManager().beginTransaction().replace(R.id.contentMain, new MainFrag()).commit();
    }

    @Override
    public void onBackPressed() {
        state = state + 1;
        if (state == 1) {
            getSupportFragmentManager().beginTransaction().replace(R.id.contentMain, new MainFrag()).commit();
        } else {
            super.onBackPressed();
        }
    }
}