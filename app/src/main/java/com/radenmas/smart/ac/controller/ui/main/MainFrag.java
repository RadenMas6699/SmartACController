package com.radenmas.smart.ac.controller.ui.main;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.radenmas.smart.ac.controller.R;
import com.radenmas.smart.ac.controller.base.BaseFragment;
import com.radenmas.smart.ac.controller.ui.info.InfoAppFrag;
import com.radenmas.smart.ac.controller.ui.remote.RemoteFrag;
import com.radenmas.smart.ac.controller.ui.settings.SettingsFrag;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainFrag extends BaseFragment {
    private TextView tvCalender, tvTitleRoomOne, tvTitleRoomTwo,
            tvTitleACOneOne, tvTitleACOneTwo, tvTitleACTwoOne, tvTitleACTwoTwo,
            tvStatePowerAC11, tvStatePowerAC12, tvStatePowerAC21, tvStatePowerAC22,
            tvTempAC11, tvTempAC12, tvTempAC21, tvTempAC22;
    private CardView cardACOneOne, cardACOneTwo, cardACTwoOne, cardACTwoTwo;
    private ImageView imgProfile, imgSettings,
            imgPowerAC11, imgPowerAC12, imgPowerAC21, imgPowerAC22;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date, myRoomOne, myRoomTwo, myACName11, myACName12, myACName21, myACName22,  lastTemp;
    private int myACPower11, myACPower12, myACPower21, myACPower22;

    private Bundle bundle = new Bundle();

    @Override
    protected int getLayoutResource() {
        return R.layout.frag_main;
    }

    @Override
    protected void myCodeHere(View view) {
        initView(view);
        getDataPref();

        showDataPref();
        onClick();

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy");
        date = dateFormat.format(calendar.getTime());
        tvCalender.setText(date);

    }

    private void showDataPref() {
        tvTitleRoomOne.setText(myRoomOne);
        tvTitleRoomTwo.setText(myRoomTwo);

        tvTitleACOneOne.setText(myACName11);
        tvTitleACOneTwo.setText(myACName12);
        tvTitleACTwoOne.setText(myACName21);
        tvTitleACTwoTwo.setText(myACName22);

        setOnOffAC(imgPowerAC11, myACPower11, tvStatePowerAC11, tvTempAC11);
        setOnOffAC(imgPowerAC12, myACPower12, tvStatePowerAC12, tvTempAC12);
        setOnOffAC(imgPowerAC21, myACPower21, tvStatePowerAC21, tvTempAC21);
        setOnOffAC(imgPowerAC22, myACPower22, tvStatePowerAC22, tvTempAC22);
    }

    private void setOnOffAC(ImageView imgPower, int myACPower, TextView tvStatePowerAC, TextView tvTempAC) {
        if (myACPower == 0) {
            tvStatePowerAC.setText(getResources().getString(R.string.ac_off));
            tvTempAC.setText(getResources().getString(R.string.dash));
            imgPower.setColorFilter(Color.argb(255, 222, 222, 222));
        } else {
            tvStatePowerAC.setText(getResources().getString(R.string.ac_on));
            imgPower.setColorFilter(Color.argb(255, 0, 200, 83));
            tvTempAC.setText(lastTemp + " \u2103");
        }
    }

    private void getDataPref() {
        myRoomOne = sharedPreferences.getString(getResources().getString(R.string.prefRoomOne), getResources().getString(R.string.room_one));
        myRoomTwo = sharedPreferences.getString(getResources().getString(R.string.prefRoomTwo), getResources().getString(R.string.room_two));

        myACName11 = sharedPreferences.getString(getResources().getString(R.string.prefNameAC11), getResources().getString(R.string.ac_room_one_one));
        myACName12 = sharedPreferences.getString(getResources().getString(R.string.prefNameAC12), getResources().getString(R.string.ac_room_one_two));
        myACName21 = sharedPreferences.getString(getResources().getString(R.string.prefNameAC21), getResources().getString(R.string.ac_room_two_one));
        myACName22 = sharedPreferences.getString(getResources().getString(R.string.prefNameAC22), getResources().getString(R.string.ac_room_two_two));

        myACPower11 = sharedPreferences.getInt(getResources().getString(R.string.prefPowerAC11), 0);
        myACPower12 = sharedPreferences.getInt(getResources().getString(R.string.prefPowerAC12), 0);
        myACPower21 = sharedPreferences.getInt(getResources().getString(R.string.prefPowerAC21), 0);
        myACPower22 = sharedPreferences.getInt(getResources().getString(R.string.prefPowerAC22), 0);


        lastTemp = sharedPreferences.getString(getResources().getString(R.string.last_temp), "24");
    }

    private void onClick() {
        imgProfile.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentMain, new InfoAppFrag()).commit();
        });

        imgSettings.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentMain, new SettingsFrag()).commit();
        });

        cardACOnClick(cardACOneOne, myACName11, 1, myACPower11);
        cardACOnClick(cardACOneTwo, myACName12, 2, myACPower12);
        cardACOnClick(cardACTwoOne, myACName21, 3, myACPower21);
        cardACOnClick(cardACTwoTwo, myACName22, 4, myACPower22);
    }

    private void cardACOnClick(CardView cardAC, String nameAC, int position, int power) {
        cardAC.setOnClickListener(v -> {
            bundle.putString(getResources().getString(R.string.ac_name), nameAC);
            bundle.putString(getResources().getString(R.string.last_temp), lastTemp);
            bundle.putInt(getResources().getString(R.string.ac_position), position);
            bundle.putInt(getResources().getString(R.string.ac_power), power);
            RemoteFrag fragment = new RemoteFrag();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            fragment.setArguments(bundle);
            ft.replace(R.id.contentMain, fragment);
            ft.commit();
        });
    }

    private void initView(View view) {
        tvCalender = view.findViewById(R.id.tvCalender);

        tvTitleRoomOne = view.findViewById(R.id.tvTitleRoomOne);
        tvTitleRoomTwo = view.findViewById(R.id.tvTitleRoomTwo);

        cardACOneOne = view.findViewById(R.id.cardACOneOne);
        cardACOneTwo = view.findViewById(R.id.cardACOneTwo);
        cardACTwoOne = view.findViewById(R.id.cardACTwoOne);
        cardACTwoTwo = view.findViewById(R.id.cardACTwoTwo);

        tvTitleACOneOne = view.findViewById(R.id.tvTitleACOneOne);
        tvTitleACOneTwo = view.findViewById(R.id.tvTitleACOneTwo);
        tvTitleACTwoOne = view.findViewById(R.id.tvTitleACTwoOne);
        tvTitleACTwoTwo = view.findViewById(R.id.tvTitleACTwoTwo);

        imgPowerAC11 = view.findViewById(R.id.imgPowerAC11);
        imgPowerAC12 = view.findViewById(R.id.imgPowerAC12);
        imgPowerAC21 = view.findViewById(R.id.imgPowerAC21);
        imgPowerAC22 = view.findViewById(R.id.imgPowerAC22);

        tvStatePowerAC11 = view.findViewById(R.id.tvStatePowerAC11);
        tvStatePowerAC12 = view.findViewById(R.id.tvStatePowerAC12);
        tvStatePowerAC21 = view.findViewById(R.id.tvStatePowerAC21);
        tvStatePowerAC22 = view.findViewById(R.id.tvStatePowerAC22);

        tvTempAC11 = view.findViewById(R.id.tvTempAC11);
        tvTempAC12 = view.findViewById(R.id.tvTempAC12);
        tvTempAC21 = view.findViewById(R.id.tvTempAC21);
        tvTempAC22 = view.findViewById(R.id.tvTempAC22);

        imgProfile = view.findViewById(R.id.imgProfile);
        imgSettings = view.findViewById(R.id.imgSettings);
    }
}
