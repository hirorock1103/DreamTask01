package com.example.hirorock1103.template_01.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.Dream.Dream;
import com.example.hirorock1103.template_01.Member.Member;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DreamManager extends MyDbHelper {
    public DreamManager(Context context) {
        super(context);
    }

    public long addDream(Dream dream){

        ContentValues values = new ContentValues();

        values.put(DREAM_COLUMN_TITLE, dream.getTitle());
        values.put(DREAM_COLUMN_DETAIL, dream.getDetail());
        values.put(DREAM_COLUMN_DEADLINE, dream.getDeadline());
        values.put(DREAM_COLUMN_IMAGE, dream.getImage());
        values.put(DREAM_COLUMN_CREATEDATE, Common.formatDate(new Date(), Common.DB_DATE_FORMAT));

        SQLiteDatabase db = getWritableDatabase();
        long inertId = db.insert(TABLE_DREAM, null, values);

        return inertId;

    }

    public List<Dream> getList(){

        List<Dream> list = new ArrayList<>();

        String query = " SELECT * FROM " + TABLE_DREAM + " ORDER BY " + DREAM_COLUMN_ID + " ASC ";
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while(!c.isAfterLast()){
            Dream dream = new Dream();

            dream.setId(c.getInt(c.getColumnIndex(DREAM_COLUMN_ID)));
            dream.setTitle(c.getString(c.getColumnIndex(DREAM_COLUMN_TITLE)));
            dream.setDetail(c.getString(c.getColumnIndex(DREAM_COLUMN_DETAIL)));
            dream.setDeadline(c.getString(c.getColumnIndex(DREAM_COLUMN_DEADLINE)));
            dream.setImage(c.getBlob(c.getColumnIndex(DREAM_COLUMN_IMAGE)));
            dream.setCreatedate(c.getString(c.getColumnIndex(DREAM_COLUMN_CREATEDATE)));

            list.add(dream);

            c.moveToNext();
        }

        return list;
    }

    public Dream getListById(int dreamId){

        Dream dream = new Dream();

        String query = " SELECT * FROM " + TABLE_DREAM + " WHERE " + DREAM_COLUMN_ID + " = " + dreamId;
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while(!c.isAfterLast()){

            dream.setId(c.getInt(c.getColumnIndex(DREAM_COLUMN_ID)));
            dream.setTitle(c.getString(c.getColumnIndex(DREAM_COLUMN_TITLE)));
            dream.setDetail(c.getString(c.getColumnIndex(DREAM_COLUMN_DETAIL)));
            dream.setDeadline(c.getString(c.getColumnIndex(DREAM_COLUMN_DEADLINE)));
            dream.setImage(c.getBlob(c.getColumnIndex(DREAM_COLUMN_IMAGE)));
            dream.setCreatedate(c.getString(c.getColumnIndex(DREAM_COLUMN_CREATEDATE)));

            c.moveToNext();
        }

        return dream;
    }

    public long update(Dream dream){

        ContentValues values = new ContentValues();
        values.put(DREAM_COLUMN_TITLE, dream.getTitle());
        values.put(DREAM_COLUMN_DETAIL, dream.getDetail());
        values.put(DREAM_COLUMN_DEADLINE, dream.getDeadline());
        values.put(DREAM_COLUMN_IMAGE, dream.getImage());

        SQLiteDatabase db = getWritableDatabase();

        String[] args = new String[]{String.valueOf(dream.getId())};

        long insertId = db.update(TABLE_DREAM, values, DREAM_COLUMN_ID + " = ?", args);

        return insertId;

    }

    public void delete(int dreamId){

        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + TABLE_DREAM + " WHERE " + DREAM_COLUMN_ID + " = " + dreamId;
        db.execSQL(query);


    }

}
