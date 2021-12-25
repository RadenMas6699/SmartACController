package com.radenmas.smart.ac.controller.ui.info;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.radenmas.smart.ac.controller.R;
import com.radenmas.smart.ac.controller.base.BaseFragment;
import com.radenmas.smart.ac.controller.ui.main.MainFrag;

public class InfoAppFrag extends BaseFragment {


    @Override
    protected int getLayoutResource() {
        return R.layout.frag_info_app;
    }

    @Override
    protected void myCodeHere(View view) {
        ImageView imgBack = view.findViewById(R.id.imgBack);
        imgBack.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentMain, new MainFrag()).commit();
        });

        TextView tvVersionApp = view.findViewById(R.id.app_version);
        PackageManager manager = getContext().getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(getContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        tvVersionApp.setText("Versi " + info.versionName);
    }
}