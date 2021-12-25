package com.radenmas.smart.ac.controller.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.radenmas.smart.ac.controller.R;

public abstract class BaseFragment extends Fragment {

    protected abstract int getLayoutResource();

    protected abstract void myCodeHere(View view);

    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResource(), container, false);
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        sharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.prefAccount), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        myCodeHere(view);

        return view;
    }

    protected void toastS(String message) {
        Toast.makeText(getContext(), "" + message, Toast.LENGTH_SHORT).show();
    }

    protected void toastL(String message) {
        Toast.makeText(getContext(), "" + message, Toast.LENGTH_LONG).show();
    }
}