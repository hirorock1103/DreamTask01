package com.example.hirorock1103.template_01.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.DB.DreamManager;
import com.example.hirorock1103.template_01.Dream.Dream;
import com.example.hirorock1103.template_01.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

public class DialogEditDream extends AppCompatDialogFragment {

    private final static int PICWIDTH = 200;

    private String mode = "new";//when dreamId is not empty ,change edit mode

    //view for edit
    private ConstraintLayout layout;
    private EditText title;
    private TextView guide_date;
    private Button pickImage;
    private byte[] byteImage;
    private ImageView image_area;

    //
    Dialog dialog;
    AlertDialog.Builder builder;

    //manager
    DreamManager dreamManager;

    //dream id
    private int dreamId;

    //Listener
    public EditResultListner listner;


    public interface EditResultListner{
        public void editResultNotice();
    }

    public void setText(String date){
        guide_date.setText(date);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);


        View view = LayoutInflater.from(getContext()).inflate(R.layout.f_edit_dream, null);

            //when update, get dream id
            layout = view.findViewById(R.id.layout);
            title = view.findViewById(R.id.edit_title);
            guide_date = view.findViewById(R.id.guide_date);
            pickImage = view.findViewById(R.id.pick_image);
            image_area = view.findViewById(R.id.image_area);
            setListener();

            dreamManager = new DreamManager(getActivity());


        try{
            //judge Edit mode
            Bundle bundle = getArguments();
            dreamId = bundle.getInt("dreamId", 0);
            mode = "edit";

            dreamManager = new DreamManager(getContext());

            //■■■■■■■■■■■■■　get data by dreamId　■■■■■■■■■■■
            Dream dream = dreamManager.getListById(dreamId);
            //set data to view
            title.setText(dream.getTitle());
            guide_date.setText(dream.getDeadline());
            if(dream.getDeadline() == null){
                guide_date.setText("日程未選択です");
            }

            if(dream.getImage() != null){
                byteImage = dream.getImage();
                image_area.setImageBitmap(BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length));
            }

            //■■■■■■■■■■■■■　get data by dreamId　■■■■■■■■■■■


        }catch(Exception e){
            //add mode
            dreamId = 0;
            Common.log(e.getMessage());
        }


        builder = new AlertDialog.Builder(getContext());

        if(mode == "new"){

            guide_date.setText("日程未選択です");

            builder.setView(view)
                    .setTitle("add Dream")
                    .setPositiveButton("登録", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Dream dream = new Dream();
                            dream.setTitle(title.getText().toString());
                            dream.setImage(byteImage);
                            dreamManager.addDream(dream);
                            listner.editResultNotice();

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
                            Dream dream = new Dream();
                            dream.setId(dreamId);
                            dream.setTitle(title.getText().toString());
                            dream.setImage(byteImage);
                            long insertId = dreamManager.update(dream);
                            Common.log("insertId:" + insertId);
                            listner.editResultNotice();
                        }
                    })
                    .setNegativeButton("cancel", null);
            dialog = builder.create();


        }


        return dialog;

    }

















    /**************************************
     * pick image / pick date
     **************************************/
    //set listener
    private void setListener(){


        pickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //image picker
                //set intent
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

                //image file directory
                File picDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String path = picDirectory.getPath();

                //uri
                Uri data = Uri.parse(path);
                photoPickerIntent.setDataAndType(data, "image/*");
                startActivityForResult(photoPickerIntent, 10);

            }
        });

        guide_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //date pickerを呼ぶ
                DialogFragment dialogFragment = new DialogDatepick();
                Bundle bundle = new Bundle();
                dialogFragment.show(getFragmentManager(),null);
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            listner = (EditResultListner)context;
        }catch(Exception e){
            Common.log(e.getMessage());
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){

            if(requestCode == 10){

                Uri imageUri = data.getData();
                InputStream inputStream;
                BitmapFactory.Options imageOptions;

                try {

                    inputStream = getActivity().getContentResolver().openInputStream(imageUri);

                    //画像サイズ情報
                    imageOptions = new BitmapFactory.Options();
                    imageOptions.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(inputStream,null,imageOptions);
                    inputStream.close();

                    //再度読み込み
                    inputStream = getActivity().getContentResolver().openInputStream(imageUri);
                    int width = imageOptions.outWidth;

                    int p = 1;
                    while(width > PICWIDTH){
                        //縮小率を決める
                        p *= 2;
                        width /= p;
                    }

                    Bitmap imageBitmap;
                    if(p > 1){
                        //縮小
                        imageOptions = new BitmapFactory.Options();
                        imageOptions.inSampleSize = p;
                        imageBitmap = BitmapFactory.decodeStream(inputStream, null,imageOptions);

                    }else{
                        //等倍
                        imageBitmap = BitmapFactory.decodeStream(inputStream, null,null);
                    }

                    inputStream.close();


                    //bitmapをblob保存用に変換
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byteImage = stream.toByteArray();
                    stream.close();

                    //確認用の画像
                    Bitmap img = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
                    image_area.setImageBitmap(img);
                    //cardview.setVisibility(View.VISIBLE);

                }catch(FileNotFoundException e){
                    e.printStackTrace();
                    Common.log(e.getMessage());
                }catch(IOException e){
                    e.printStackTrace();
                    Common.log(e.getMessage());
                }

            }else if(requestCode == 20){

                Common.log("get data from datepick fragment by code 20");

            }

        }else{

        }
    }

}
