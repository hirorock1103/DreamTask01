package com.example.hirorock1103.template_01.Fragments;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.DB.DreamManager;
import com.example.hirorock1103.template_01.Dialog.DialogEditDream;
import com.example.hirorock1103.template_01.Dream.Dream;
import com.example.hirorock1103.template_01.MainDreamDetailActivity;
import com.example.hirorock1103.template_01.MainDreamListActivity;
import com.example.hirorock1103.template_01.R;

import java.util.List;
import java.util.Random;

public class FragTop extends Fragment {

    private ImageButton bt1;
    private ImageButton bt2;

    private RecyclerView recyclerView;
    private DreamAdapter adapter;
    private List<Dream> list;
    private DreamManager dreamManager;

    FloatingActionButton fab;

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

        //Dream manager
        dreamManager = new DreamManager(getContext());
        list = dreamManager.getList();

        //recycelr view
        recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new DreamAdapter(list);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(),4);
        linearLayoutManager.canScrollVertically();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);


        return view;

    }

    /********************************************
     * recycler view
     ********************************************/
    public class DreamIconViewHolder extends RecyclerView.ViewHolder{

        private ImageView image;
        private ConstraintLayout layout;


        public DreamIconViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            layout = itemView.findViewById(R.id.layout);

        }
    }

    public class DreamAdapter extends RecyclerView.Adapter<DreamIconViewHolder>{

        private List<Dream> list;

        public DreamAdapter(List<Dream> list){
            this.list = list;
        }

        public void setList(List<Dream> list){
            this.list = list;
        }

        @NonNull
        @Override
        public DreamIconViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_icon, viewGroup, false);
            DreamIconViewHolder holder = new DreamIconViewHolder(view);

            return holder;

        }

        @Override
        public void onBindViewHolder(@NonNull DreamIconViewHolder dreamIconViewHolder, int i) {

            final Dream dream = list.get(i);

            //main image
            if(dream.getImage() != null){
                dreamIconViewHolder.image.setImageBitmap(BitmapFactory.decodeByteArray(dream.getImage(), 0 , dream.getImage().length));
            }

            dreamIconViewHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), MainDreamDetailActivity.class);
                    intent.putExtra("dreamId", dream.getId());
                    startActivity(intent);
                }
            });
        }


        @Override
        public int getItemCount() {
            return list.size();
        }
    }


}
