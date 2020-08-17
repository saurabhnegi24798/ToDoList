package com.example.to_do_list;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class TaskDbHelper extends SQLiteOpenHelper {

    public TaskDbHelper(@Nullable Context context) {
        super(context, TaskContract.DATABASE_NAME, null, TaskContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TASK_TABLE = "CREATE TABLE " +
                TaskContract.TABLE_NAME + " (" +
                TaskContract.id + "  INTEGER PRIMARY KEY , " +
                TaskContract.COLUMN_TASK + "  TASK NOT NULL );";

        db.execSQL(SQL_CREATE_TASK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
