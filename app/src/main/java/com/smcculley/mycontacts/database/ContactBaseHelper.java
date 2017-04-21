package com.smcculley.mycontacts.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.smcculley.mycontacts.database.ContactDbSchema.*;

/**
 * Created by smcculley on 4/20/2017.
 */

public class ContactBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "contactBase.db";

    public ContactBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table " + ContactTable.NAME + "( " +
                "_id integer primary key autoincrement, " +
                ContactTable.Cols.UUID + ", " +
                ContactTable.Cols.NAME + ", " +
                ContactTable.Cols.EMAIL + ", " +
                ContactTable.Cols.FAVORITE + ", " +
                ContactTable.Cols.ADDRESS + ", " +
                ContactTable.Cols.IMAGE +
                ")";
                sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
