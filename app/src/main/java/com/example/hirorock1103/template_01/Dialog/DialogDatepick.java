package com.example.hirorock1103.template_01.Dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.widget.DatePicker;

import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.MainActivity;

import java.util.Calendar;

public class DialogDatepick extends AppCompatDialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        try{

            Bundle bundle = getArguments();

        }catch(Exception e){
            Common.log(e.getMessage());
        }

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
        Common.log("ondataSet " + year +"/"+ (month + 1) +"/"+ dayOfMonth);

    }
}
