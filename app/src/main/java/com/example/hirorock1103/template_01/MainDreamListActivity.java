package com.example.hirorock1103.template_01;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.DB.DreamManager;
import com.example.hirorock1103.template_01.Dream.Dream;
import com.example.hirorock1103.template_01.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainDreamListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DreamAdapter adapter;
    private List<Dream> list;
    private DreamManager dreamManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dream_list);

        //action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Dream manager
        dreamManager = new DreamManager(this);
        dreamManager.addDream(new Dream("New Yorkiに行きたい", "2019/09/11"));
        list = dreamManager.getList();

        //recycelr view
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new DreamAdapter(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.canScrollVertically();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);


    }

    /********************************************
     * recycler view
     ********************************************/
    public class DreamViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView detail;
        private TextView no;
        private TextView diff;
        private TextView createdate;
        private ConstraintLayout layout;

        public DreamViewHolder(@NonNull View itemView) {
            super(itemView);

            no = itemView.findViewById(R.id.no);
            title = itemView.findViewById(R.id.title);
            diff = itemView.findViewById(R.id.diff);
            detail = itemView.findViewById(R.id.detail);
            createdate = itemView.findViewById(R.id.createdate);
            layout = itemView.findViewById(R.id.layout);

        }
    }

    public class DreamAdapter extends RecyclerView.Adapter<DreamViewHolder>{

        private List<Dream> list;

        public DreamAdapter(List<Dream> list){
            this.list = list;
        }

        public void setList(List<Dream> list){
            this.list = list;
        }

        @NonNull
        @Override
        public DreamViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(MainDreamListActivity.this).inflate(R.layout.list_item, viewGroup, false);
            DreamViewHolder holder = new DreamViewHolder(view);

            return holder;

        }

        @Override
        public void onBindViewHolder(@NonNull DreamViewHolder dreamViewHolder, int i) {
            final Dream dream = list.get(i);
            dreamViewHolder.no.setText("No." + dream.getId());
            dreamViewHolder.title.setText(dream.getTitle());
            try{
                String from = Common.formatStrToDate(dream.getCreatedate(), Common.DB_DATE_FORMAT, Common.DATE_FORMAT_SAMPLE_1);
                String to = dream.getDeadline();
                dreamViewHolder.diff.setText("残日数 " + Common.getDateDiff(from, to, Common.DATE_FORMAT_SAMPLE_1) + "日");
            }catch(Exception e){
                dreamViewHolder.diff.setText("no deadline setted");
            }

            dreamViewHolder.detail.setText(dream.getDetail());
            dreamViewHolder.createdate.setText(Common.formatStrToDate(dream.getCreatedate(), Common.DB_DATE_FORMAT, Common.DATE_FORMAT_SAMPLE_2));
            dreamViewHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainDreamListActivity.this, MainDreamDetailActivity.class);
                    intent.putExtra("dreamId", dream.getId());
                    startActivity(intent);
                }
            });

            registerForContextMenu(dreamViewHolder.layout);//onContextItemSelectedにて挙動設定
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    //Context menu click
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.option1:
                break;

            case R.id.option2:
                //delete
                
                break;

        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.option_menu_1, menu);

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
