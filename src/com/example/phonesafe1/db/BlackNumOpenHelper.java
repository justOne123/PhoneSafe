package com.example.phonesafe1.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BlackNumOpenHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "black_num.db";
	public static final int VERSION = 4;

	public BlackNumOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table if not exists t_info(id integer primary key autoincrement,num text,mode integer)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("create table if not exists t_info(id integer primary key autoincrement,num text,mode integer)");
	}

}
