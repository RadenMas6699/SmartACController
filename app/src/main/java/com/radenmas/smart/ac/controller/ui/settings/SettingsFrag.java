package com.radenmas.smart.ac.controller.ui.settings;

import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.radenmas.smart.ac.controller.R;
import com.radenmas.smart.ac.controller.base.BaseFragment;
import com.radenmas.smart.ac.controller.ui.main.MainFrag;

public class SettingsFrag extends BaseFragment {
    private RelativeLayout rlRoomOne, rlRoomTwo, rlNameAC11, rlNameAC12, rlNameAC21, rlNameAC22;
    private TextView tvNameRoom1, tvNameRoom2, tvTitleRoom1, tvTitleRoom2,
            tvNameAC11, tvNameAC12, tvNameAC21, tvNameAC22;
    private String myRoomOne, myRoomTwo, myACName11, myACName12, myACName21, myACName22;

    @Override
    protected int getLayoutResource() {
        return R.layout.frag_settings;
    }

    @Override
    protected void myCodeHere(View view) {
        ImageView imgBack = view.findViewById(R.id.imgBack);
        imgBack.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentMain, new MainFrag()).commit();
        });

        initView(view);
        getDataPref();
        onClick();

    }

    private void getDataPref() {
        myRoomOne = sharedPreferences.getString(getResources().getString(R.string.prefRoomOne), getResources().getString(R.string.room_one));
        myRoomTwo = sharedPreferences.getString(getResources().getString(R.string.prefRoomTwo), getResources().getString(R.string.room_two));

        myACName11 = sharedPreferences.getString(getResources().getString(R.string.prefNameAC11), getResources().getString(R.string.ac_room_one_one));
        myACName12 = sharedPreferences.getString(getResources().getString(R.string.prefNameAC12), getResources().getString(R.string.ac_room_one_two));
        myACName21 = sharedPreferences.getString(getResources().getString(R.string.prefNameAC21), getResources().getString(R.string.ac_room_two_one));
        myACName22 = sharedPreferences.getString(getResources().getString(R.string.prefNameAC22), getResources().getString(R.string.ac_room_two_two));

        tvNameRoom1.setText(myRoomOne);
        tvNameRoom2.setText(myRoomTwo);
        tvTitleRoom1.setText(myRoomOne);
        tvTitleRoom2.setText(myRoomTwo);

        tvNameAC11.setText(myACName11);
        tvNameAC12.setText(myACName12);
        tvNameAC21.setText(myACName21);
        tvNameAC22.setText(myACName22);
    }

    private void onClick() {
        rlRoomOne.setOnClickListener(v -> {
            showBottomDialogRoom(getResources().getString(R.string.ruangan_one),
                    getResources().getString(R.string.desc_ruangan_one),
                    getResources().getString(R.string.prefRoomOne),
                    tvTitleRoom1, tvNameRoom1);
        });

        rlRoomTwo.setOnClickListener(v -> {
            showBottomDialogRoom(getResources().getString(R.string.ruangan_two),
                    getResources().getString(R.string.desc_ruangan_two),
                    getResources().getString(R.string.prefRoomTwo),
                    tvTitleRoom2, tvNameRoom2);
        });

        rlNameAC11.setOnClickListener(v -> {
            showBottomDialogAC(getResources().getString(R.string.air_conditioner_one),
                    getResources().getString(R.string.desc_air_conditioner_one),
                    getResources().getString(R.string.prefNameAC11),
                    tvNameAC11);
        });

        rlNameAC12.setOnClickListener(v -> {
            showBottomDialogAC(getResources().getString(R.string.air_conditioner_two),
                    getResources().getString(R.string.desc_air_conditioner_two),
                    getResources().getString(R.string.prefNameAC12),
                    tvNameAC12);
        });

        rlNameAC21.setOnClickListener(v -> {
            showBottomDialogAC(getResources().getString(R.string.air_conditioner_one),
                    getResources().getString(R.string.desc_air_conditioner_one),
                    getResources().getString(R.string.prefNameAC21),
                    tvNameAC21);
        });

        rlNameAC22.setOnClickListener(v -> {
            showBottomDialogAC(getResources().getString(R.string.air_conditioner_two),
                    getResources().getString(R.string.desc_air_conditioner_two),
                    getResources().getString(R.string.prefNameAC22),
                    tvNameAC22);
        });

    }

    private void initView(View view) {
        rlRoomOne = view.findViewById(R.id.rlRoomOne);
        rlRoomTwo = view.findViewById(R.id.rlRoomTwo);
        rlNameAC11 = view.findViewById(R.id.rlNameAC11);
        rlNameAC12 = view.findViewById(R.id.rlNameAC12);
        rlNameAC21 = view.findViewById(R.id.rlNameAC21);
        rlNameAC22 = view.findViewById(R.id.rlNameAC22);

        tvNameRoom1 = view.findViewById(R.id.tvNameRoom1);
        tvNameRoom2 = view.findViewById(R.id.tvNameRoom2);
        tvTitleRoom1 = view.findViewById(R.id.tvTitleRoom1);
        tvTitleRoom2 = view.findViewById(R.id.tvTitleRoom2);

        tvNameAC11 = view.findViewById(R.id.tvNameAC11);
        tvNameAC12 = view.findViewById(R.id.tvNameAC12);
        tvNameAC21 = view.findViewById(R.id.tvNameAC21);
        tvNameAC22 = view.findViewById(R.id.tvNameAC22);
    }

    private void showBottomDialogRoom(String title, String desc, String preff, TextView tvTitle, TextView tvName) {
        final BottomSheetDialog bottomDialog = new BottomSheetDialog(getContext());
        bottomDialog.setContentView(R.layout.bottom_dialog);

        final TextView tvTitleSettings = bottomDialog.findViewById(R.id.tvTitleSettings);
        final TextView tvDescSettings = bottomDialog.findViewById(R.id.tvDescSettings);
        final EditText etValueSettings = bottomDialog.findViewById(R.id.etValueSettings);
        MaterialButton btnSaveSettings = bottomDialog.findViewById(R.id.btnSaveSettings);

        tvTitleSettings.setText(title);
        tvDescSettings.setText(desc);

        bottomDialog.show();

        btnSaveSettings.setOnClickListener(view -> {
            String strValPress = etValueSettings.getText().toString();
            if (strValPress.isEmpty()) {
                toastS("All empty");
            } else {
                editor.putString(preff, strValPress);
                editor.apply();
                toastS("Berhasil diubah");
                etValueSettings.setText("");
                tvTitle.setText(strValPress);
                tvName.setText(strValPress);
                bottomDialog.dismiss();
            }

        });
    }

    private void showBottomDialogAC(String title, String desc, String preff, TextView tvName) {
        final BottomSheetDialog bottomDialog = new BottomSheetDialog(getContext());
        bottomDialog.setContentView(R.layout.bottom_dialog);

        final TextView tvTitleSettings = bottomDialog.findViewById(R.id.tvTitleSettings);
        final TextView tvDescSettings = bottomDialog.findViewById(R.id.tvDescSettings);
        final EditText etValueSettings = bottomDialog.findViewById(R.id.etValueSettings);
        MaterialButton btnSaveSettings = bottomDialog.findViewById(R.id.btnSaveSettings);

        tvTitleSettings.setText(title);
        tvDescSettings.setText(desc);

        bottomDialog.show();

        btnSaveSettings.setOnClickListener(view -> {
            String strValPress = etValueSettings.getText().toString();
            if (strValPress.isEmpty()) {
                toastS("All empty");
            } else {
                editor.putString(preff, strValPress);
                editor.apply();
                toastS("Berhasil diubah");
                etValueSettings.setText("");
                tvName.setText(strValPress);
                bottomDialog.dismiss();
            }

        });
    }
}
