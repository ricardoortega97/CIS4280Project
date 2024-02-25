package com.example.cis4280project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MemoDBHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "memo.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_MEMO =
            "create table memo (_id integer primary key autoincrement,"
            + "subject text, memoText text, "
            + "level text, date text); ";

    public MemoDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MEMO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MemoDBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
                 db.execSQL("DROP TABLE IF EXISTS memo");
                 onCreate(db);
    }
}
