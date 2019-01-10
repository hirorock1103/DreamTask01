package com.example.hirorock1103.template_01.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.Dialog.DialogEditDream;
import com.example.hirorock1103.template_01.MainDreamListActivity;
import com.example.hirorock1103.template_01.R;

public class FragTop extends Fragment {

    private ImageButton bt1;
    private ImageButton bt2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.f_top,container,false);

        bt1 = view.findViewById(R.id.bt_1);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainDreamListActivity.class);
                startActivity(intent);
            }
        });

        bt2 = view.findViewById(R.id.bt_2);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open dialog
                DialogFragment dialogFragment = new DialogEditDream();
                dialogFragment.show(getFragmentManager(), null);
            }
        });


        return view;

    }
}
