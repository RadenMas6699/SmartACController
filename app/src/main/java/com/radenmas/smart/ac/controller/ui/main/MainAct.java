package com.radenmas.smart.ac.controller.ui.main;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
        DatabaseReference dbLastTemp = FirebaseDatabase.getInstance().getReference("lastTemp");
        dbLastTemp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String valTemp = snapshot.getValue().toString();
                editor.putString(getResources().getString(R.string.last_temp), valTemp);
                editor.apply();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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