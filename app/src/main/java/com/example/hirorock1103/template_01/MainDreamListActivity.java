package com.example.hirorock1103.template_01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.hirorock1103.template_01.R;

public class MainDreamListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dream_list);

        recyclerView = findViewById(R.id.recycler_view);


    }
}
