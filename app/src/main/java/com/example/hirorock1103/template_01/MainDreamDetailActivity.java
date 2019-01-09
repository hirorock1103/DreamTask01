package com.example.hirorock1103.template_01;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.DB.DreamManager;
import com.example.hirorock1103.template_01.Dream.Dream;

public class MainDreamDetailActivity extends AppCompatActivity {

    private DreamManager dreamManager;
    private Dream dream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dream_detail);

        //action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //遷移元からdreamIdを取得する
        Intent intent = getIntent();
        int dreamId = intent.getIntExtra("dreamId", 0);

        if(dreamId > 0){

            dreamManager = new DreamManager(this);
            dream = dreamManager.getListById(dreamId);

        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                finish();
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
