package com.chen.jokesreader.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ChenSir on 2015/6/3 0003.
 */
public class DBHelper extends SQLiteOpenHelper {
    // Database name
    private static final String DB_NAME = "9gag.db";

    // Database version
    private static final int VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        FeedsDataHelper.FeedsDBInfo.TABLE.create(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
