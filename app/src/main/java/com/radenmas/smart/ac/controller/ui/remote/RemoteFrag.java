package com.radenmas.smart.ac.controller.ui.remote;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.radenmas.smart.ac.controller.R;
import com.radenmas.smart.ac.controller.base.BaseFragment;
import com.radenmas.smart.ac.controller.ui.main.MainFrag;

public class RemoteFrag extends BaseFragment {
    private TextView tvTemp, tvSwing;
    private ImageView imgBack, imgPower, imgSpeed, imgSwing, minTemp, addTemp;
    private TextView btnSwing, btnSpeed;
    private DatabaseReference dbReff;
    private RelativeLayout rlTemp, rlSwing, rlSpeed;
    int swing = 0;
    int speed = 0;
    int power, temp;
    private int position;

    @Override
    protected int getLayoutResource() {
        return R.layout.frag_remote;
    }

    @Override
    protected void myCodeHere(View view) {
        dbReff = FirebaseDatabase.getInstance().getReference();
        initView(view);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            TextView tvTitleAC = view.findViewById(R.id.tvTitleAC);
            String acName = bundle.getString(getResources().getString(R.string.ac_name));
            String lastTemp = bundle.getString(getResources().getString(R.string.last_temp));
            temp = Integer.parseInt(lastTemp);

            position = bundle.getInt(getResources().getString(R.string.ac_position));

            int fromPower = bundle.getInt(getResources().getString(R.string.ac_power));
            if (fromPower == 0) {
                power = 1;
                Off();
            } else {
                On();
            }
            tvTitleAC.setText(acName);
        }

        DatabaseReference dbLastTemp = FirebaseDatabase.getInstance().getReference("lastTemp");
        dbLastTemp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String valTemp = snapshot.getValue().toString();
                tvTemp.setText("" + valTemp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        switch (position) {
            case 1:
                onClick(getResources().getString(R.string.prefPowerAC11));
                break;
            case 2:
                onClick(getResources().getString(R.string.prefPowerAC12));
                break;
            case 3:
                onClick(getResources().getString(R.string.prefPowerAC21));
                break;
            case 4:
                onClick(getResources().getString(R.string.prefPowerAC22));
                break;
        }

        imgBack.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentMain, new MainFrag()).commit();
        });
    }

    private void onClick(String prefPower) {
        imgPower.setOnClickListener(v -> {
            power = power + 1;
            switch (power) {
                case 1:
                    Off();
                    dbReff.child("Power").setValue(1);
                    setZero("Power");
                    editor.putInt(prefPower, 0);
                    editor.apply();
                    break;
                case 2:
                    On();
                    dbReff.child("Power").setValue(2);
                    setZero("Power");
                    editor.putInt(prefPower, 1);
                    editor.apply();
                    break;
            }
        });
    }

    private void On() {
        power = 0;
        tvTemp.setText("" + temp);
        rlTemp.setVisibility(View.VISIBLE);
        rlSwing.setVisibility(View.VISIBLE);
        rlSpeed.setVisibility(View.VISIBLE);
        imgPower.setColorFilter(Color.argb(255, 0, 200, 83));
        btnSwing.setClickable(true);
        btnSpeed.setClickable(true);
        addTemp.setClickable(true);
        minTemp.setClickable(true);

        btnSpeed.setOnClickListener(view -> {
            speed = speed + 1;
            switch (speed) {
                case 1:
                    dbReff.child("Speed").setValue(1);
                    imgSpeed.setImageResource(R.drawable.ic_speed_1);
                    break;

                case 2:
                    dbReff.child("Speed").setValue(2);
                    imgSpeed.setImageResource(R.drawable.ic_speed_2);
                    break;

                case 3:
                    dbReff.child("Speed").setValue(3);
                    imgSpeed.setImageResource(R.drawable.ic_speed_3);
                    speed = 0;
                    break;
            }
            setZero("Speed");
        });

        btnSwing.setOnClickListener(view -> {
            swing = swing + 1;
            switch (swing) {
                case 1:
                    imgSwing.setImageResource(R.drawable.ic_swing_down);
                    tvSwing.setText(getResources().getString(R.string.arrow_bottom));
                    break;

                case 2:
                    imgSwing.setImageResource(R.drawable.ic_swing_up);
                    tvSwing.setText(getResources().getString(R.string.arrow_top));
                    break;

                case 3:
                    imgSwing.setImageResource(R.drawable.ic_swing_center);
                    tvSwing.setText(getResources().getString(R.string.arrow_center));
                    swing = 0;
                    break;
            }
            dbReff.child("Swing").setValue(1);
            setZero("Swing");
        });

        minTemp.setOnClickListener(view -> {
            if (temp <= 17) {
                minTemp.setClickable(false);
            } else {
                temp = temp - 1;
                minTemp.setClickable(true);
            }
            addTemp.setClickable(true);
            dbReff.child("Suhu").setValue(1);
            setZero("Suhu");
        });

        addTemp.setOnClickListener(view -> {
            if (temp >= 30) {
                addTemp.setClickable(false);
            } else {
                temp = temp + 1;
                addTemp.setClickable(true);
            }
            minTemp.setClickable(true);
            dbReff.child("Suhu").setValue(1);
            setZero("Suhu");
        });
    }

    private void setZero(String path) {
        new Handler().postDelayed(() -> dbReff.child(path).setValue(0), 500);

    }

    private void Off() {
        rlTemp.setVisibility(View.INVISIBLE);
        rlSwing.setVisibility(View.INVISIBLE);
        rlSpeed.setVisibility(View.INVISIBLE);
        imgPower.setColorFilter(Color.argb(255, 222, 222, 222));
        btnSwing.setClickable(false);
        btnSpeed.setClickable(false);
        addTemp.setClickable(false);
        minTemp.setClickable(false);
    }

    private void initView(View view) {
        imgBack = view.findViewById(R.id.imgBack);
        rlTemp = view.findViewById(R.id.rlTemp);
        rlSwing = view.findViewById(R.id.rlSwing);
        rlSpeed = view.findViewById(R.id.rlSpeed);
        tvTemp = view.findViewById(R.id.tvTemp);
        tvSwing = view.findViewById(R.id.tvSwing);
        imgPower = view.findViewById(R.id.imgPower);
        imgSpeed = view.findViewById(R.id.imgSpeed);
        imgSwing = view.findViewById(R.id.imgSwing);
        minTemp = view.findViewById(R.id.minTemp);
        addTemp = view.findViewById(R.id.addTemp);
        btnSwing = view.findViewById(R.id.btnSwing);
        btnSpeed = view.findViewById(R.id.btnSpeed);
    }
}
