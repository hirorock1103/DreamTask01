package com.example.hirorock1103.template_01.Dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.widget.DatePicker;

import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.MainActivity;

import java.util.Calendar;

public class DialogDatepick extends AppCompatDialogFragment implements DatePickerDialog.OnDateSetListener {

    public DateListener listener;
    public interface DateListener{
        public void getDate(String date);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            listener = (DateListener)context;
        }catch(Exception e){
            Common.log(e.getMessage());
        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //default 1年後
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR) + 1;
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(),this, year, month, day);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //取得結果
        Common.log("ondataSet " + year +"/"+ String.format("%02d",(month + 1)) +"/"+ String.format("%02d",dayOfMonth));

        //取得した値を呼び出し元に通知する
        try{
            listener.getDate(year +"/"+ String.format("%02d",(month + 1)) +"/"+ String.format("%02d",dayOfMonth));
        }catch (Exception e){
            Common.log(e.getMessage());
        }


    }


}
