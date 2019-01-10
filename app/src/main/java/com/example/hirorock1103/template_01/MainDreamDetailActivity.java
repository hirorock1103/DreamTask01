package com.example.hirorock1103.template_01;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.DB.DreamManager;
import com.example.hirorock1103.template_01.Dream.Dream;

public class MainDreamDetailActivity extends AppCompatActivity {

    private DreamManager dreamManager;
    private Dream dream;
    private ImageView mainImage;
    private android.support.v7.widget.Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dream_detail);

        //action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //views
        mainImage = findViewById(R.id.mainImage);
        toolbar = findViewById(R.id.toolbar);


        //遷移元からdreamIdを取得する
        Intent intent = getIntent();
        int dreamId = intent.getIntExtra("dreamId", 0);

        if(dreamId > 0){

            dreamManager = new DreamManager(this);
            dream = dreamManager.getListById(dreamId);
            toolbar.setTitle(dream.getTitle());
            if(dream.getImage() != null){
                mainImage.setImageBitmap(BitmapFactory.decodeByteArray(dream.getImage(),0,dream.getImage().length));
            }

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
