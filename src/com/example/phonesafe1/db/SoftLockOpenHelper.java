package com.example.phonesafe1.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SoftLockOpenHelper extends SQLiteOpenHelper {
	public static final String SOFT_LOCK_DB = "softlock.db";
	
	public static final int VERSION = 1;

	public SoftLockOpenHelper(Context context) {
		super(context, SOFT_LOCK_DB, null, VERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table if not exists t_info(id integer primary key autoincrement,package_name text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
