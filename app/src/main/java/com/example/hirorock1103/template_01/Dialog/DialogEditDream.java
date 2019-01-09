package com.example.hirorock1103.template_01.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hirorock1103.template_01.DB.DreamManager;
import com.example.hirorock1103.template_01.Dream.Dream;
import com.example.hirorock1103.template_01.R;

public class DialogEditDream extends AppCompatDialogFragment {


    private String mode = "new";//when dreamId is not empty ,change edit mode

    //view for edit
    private EditText title;
    private EditText deadline;
    private Button pickDate;

    //manager
    DreamManager dreamManager;

    //dream id
    private int dreamId;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.f_edit_dream, null);

            //when update, get dream id

        title = view.findViewById(R.id.edit_title);
        pickDate = view.findViewById(R.id.pick_date);
        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //date pickerを呼ぶ
                DialogFragment dialogFragment = new DialogDatepick();
                Bundle bundle = new Bundle();

                dialogFragment.show(getFragmentManager(),null);
            }
        });

        try{
            Bundle bundle = getArguments();
            dreamId = bundle.getInt("dreamId", 0);
            mode = "edit";

            dreamManager = new DreamManager(getContext());

            //get data by dreamId
            Dream dream = dreamManager.getListById(dreamId);

            //set data to view
            title.setText(dream.getTitle());
            deadline.setText(dream.getDeadline());


        }catch(Exception e){
            dreamId = 0;
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        Dialog dialog;
        if(mode == "new"){

            builder.setView(view)
                    .setTitle("add Dream")
                    .setPositiveButton("登録", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //ok button is clicked
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            dialog = builder.create();


        }else{

            builder.setView(view)
                    .setTitle("edit Dream")
                    .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //ok button is clicked

                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            dialog = builder.create();


        }

        return dialog;


    }
}
