package com.radenmas.smart.ac.controller.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.radenmas.smart.ac.controller.R;

public abstract class BaseActivity extends AppCompatActivity {
    protected abstract int getLayoutResource();

    protected abstract void myCodeHere();

    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(getLayoutResource());

        sharedPreferences = getSharedPreferences(getResources().getString(R.string.prefAccount), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        myCodeHere();
    }

    protected void toastS(String message) {
        Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_SHORT).show();
    }

    protected void toastL(String message) {
        Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_LONG).show();
    }
}
