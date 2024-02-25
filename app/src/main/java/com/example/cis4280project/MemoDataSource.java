package com.example.cis4280project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class MemoDataSource {

    private SQLiteDatabase database;
    private MemoDBHelper dbHelper;
    public MemoDataSource(Context context){
        dbHelper = new MemoDBHelper(context);
    }
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    public void close() {
        database.close();
    }

    public boolean insertMemo(Memo m){
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();

            initialValues.put("subject", m.getSubject());
            initialValues.put("memoText", m.getMemoText());
            initialValues.put("level", m.getLevel());
            initialValues.put("date", String.valueOf(m.getDate().getTimeInMillis()));

            didSucceed = database.insert("memo", null, initialValues) > 0;
        }catch (Exception e) {

        }
        return didSucceed;
    }
    public boolean updateMemo(Memo m) {
        boolean didSucceed = false;
        try {
            Long rowID = (long) m.getMemoID();
            ContentValues updateValues = new ContentValues();

            updateValues.put("subject", m.getSubject());
            updateValues.put("memoText", m.getMemoText());
            updateValues.put("level", m.getLevel());
            updateValues.put("date",
                    String.valueOf(m.getDate().getTimeInMillis()));

            didSucceed = database.update("memo", updateValues, "_id" + rowID,
                    null) > 0;
        }catch (Exception e){

        }
        return didSucceed;
    }

    public int getLastMemoID(){
        int lastID;
        try {
            String query = "Select MAX(_id) from memo";
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();
            lastID = cursor.getInt(0);
            cursor.close();

        }catch (Exception e) {
            lastID = -1;
        }
        return lastID;
    }

    //grabbing the database to display in a Array list
    public ArrayList<Memo> getMemos(){
        ArrayList<Memo> memos = new ArrayList<Memo>();
        try {
            String query = "Select * From memo";
            Cursor cursor = database.rawQuery(query, null);

            Memo newMemo;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                newMemo = new Memo();
                newMemo.setMemoID(cursor.getInt(0));
                newMemo.setSubject(cursor.getString(1));
                newMemo.setMemoText(cursor.getString(2));
                newMemo.setLevel(cursor.getString(3));

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(Long.valueOf(cursor.getString(4)));
                newMemo.setDate(calendar);

                memos.add(newMemo);
                cursor.moveToNext();
            }
            cursor.close();
        }catch (Exception e) {
            memos = new ArrayList<Memo>();
        }
        return memos;
    }
    public Memo getSpecificMemo(int memoID){
        Memo memo = new Memo();
        String query = "Select * From memo Where _id =" + memoID;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            memo.setMemoID(cursor.getInt(0));
            memo.setSubject(cursor.getString(1));
            memo.setMemoText(cursor.getString(2));
            memo.setLevel(cursor.getString(3));

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Long.valueOf(cursor.getString(4)));
            memo.setDate(calendar);

            cursor.close();
        }
        return memo;
    }
    //gets called to the memoAdaptor
    public boolean deleteMemo(int memoID){
        boolean didDel = false;
        try {
            didDel = database.delete("memo", "_id=" + memoID, null) > 0;
        }catch (Exception e) {

        }
        return didDel;
    }

}


