package com.example.hirorock1103.template_01;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.DB.DreamManager;
import com.example.hirorock1103.template_01.Dialog.DialogDatepick;
import com.example.hirorock1103.template_01.Dialog.DialogEditDream;
import com.example.hirorock1103.template_01.Dream.Dream;
import com.example.hirorock1103.template_01.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainDreamListActivity extends AppCompatActivity implements DialogEditDream.EditResultListner, DialogDatepick.DateListener {

    private RecyclerView recyclerView;
    private DreamAdapter adapter;
    private List<Dream> list;
    private DreamManager dreamManager;

    private int dreamId = 0;

    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dream_list);

        //action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Dream manager
        dreamManager = new DreamManager(this);
        list = dreamManager.getList();

        //recycelr view
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new DreamAdapter(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.canScrollVertically();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open dialog
                DialogFragment dialogFragment = new DialogEditDream();
                dialogFragment.show(getSupportFragmentManager(), "mydialog");
            }
        });

    }

    @Override
    public void editResultNotice() {
        //result
        updateRecyclerView("更新");
    }

    private void updateRecyclerView(String mode){
        list = dreamManager.getList();
        adapter.setList(list);
        adapter.notifyDataSetChanged();

        View view = findViewById(android.R.id.content);
        Snackbar.make(view,"Dreamが"+mode+"されました。",Snackbar.LENGTH_SHORT).show();
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
        private ImageView image;
        private ImageView achievement;
        private ConstraintLayout layout;

        public DreamViewHolder(@NonNull View itemView) {
            super(itemView);

            no = itemView.findViewById(R.id.no);
            title = itemView.findViewById(R.id.title);
            diff = itemView.findViewById(R.id.diff);
            detail = itemView.findViewById(R.id.detail);
            createdate = itemView.findViewById(R.id.createdate);
            image = itemView.findViewById(R.id.image);
            achievement = itemView.findViewById(R.id.achievement);
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

            //Dream
            final Dream dream = list.get(i);

            //No.
            dreamViewHolder.no.setText("No." + dream.getId());

            //title
            dreamViewHolder.title.setText(dream.getTitle());

            //day count if deadline is setted
            try{
                String from = Common.formatStrToDate(dream.getCreatedate(), Common.DB_DATE_FORMAT, Common.DATE_FORMAT_SAMPLE_1);
                String to = dream.getDeadline();
                dreamViewHolder.diff.setText("残日数 " + Common.getDateDiff(from, to, Common.DATE_FORMAT_SAMPLE_1) + "日");
            }catch(Exception e){
                dreamViewHolder.diff.setText("no deadline setted");
            }

            //achieve image if dream has been achieved
            Random rand = new Random();
            int num = rand.nextInt(10);
            if(num % 2 == 0){
                dreamViewHolder.achievement.setImageDrawable(getDrawable(R.drawable.achieve));
            }

            //main image
            if(dream.getImage() != null){
                dreamViewHolder.image.setImageBitmap(BitmapFactory.decodeByteArray(dream.getImage(), 0 , dream.getImage().length));
            }

            //detail
            dreamViewHolder.detail.setText(dream.getDetail());

            //dream createdate
            dreamViewHolder.createdate.setText(Common.formatStrToDate(dream.getCreatedate(), Common.DB_DATE_FORMAT, Common.DATE_FORMAT_SAMPLE_2));

            //move to detail
            dreamViewHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainDreamListActivity.this, MainDreamDetailActivity.class);
                    intent.putExtra("dreamId", dream.getId());
                    startActivity(intent);
                }
            });

            //set context menu
            dreamViewHolder.layout.setTag(String.valueOf(dream.getId()));
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
                //edit
                DialogFragment dialogFragment = new DialogEditDream();
                Bundle bundle = new Bundle();
                bundle.putInt("dreamId", dreamId);
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getSupportFragmentManager(), "mydialog");


                break;

            case R.id.option2:
                //delete
                dreamManager.delete(dreamId);
                updateRecyclerView("削除");
                break;

        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.option_menu_1, menu);

        String tag = v.getTag().toString();
        dreamId = Integer.parseInt(tag);

        Common.log("tag:" + dreamId);

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

    /**
     * DialogEditDreamのメソッドを呼ぶ　※dialogの内容は、DialogEditDreamのメソッドでハンドリングする
     * @param date
     */
    @Override
    public void getDate(String date) {

        //！！！！！！！！！！！！Activity上に表示しているdialogを取得する！！！！！！！！！！！！！！

        Fragment fragment = getSupportFragmentManager().findFragmentByTag("mydialog");
        if(fragment!=null){
            DialogEditDream dialogEditDream = (DialogEditDream)fragment;
            dialogEditDream.setText(date);
            View view = findViewById(android.R.id.content);
            Snackbar.make(view,"日付がセットされました。",Snackbar.LENGTH_SHORT).show();
        }


    }

}
