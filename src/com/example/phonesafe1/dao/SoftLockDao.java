package com.example.phonesafe1.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.phonesafe1.db.SoftLockOpenHelper;

/**
 * 软件锁数据库的增删改查
 * 
 * @author Administrator
 *
 */

public class SoftLockDao {
	Context context;
	public static final String TABLE = "t_info";
	private SoftLockOpenHelper helper;
	
	
	public SoftLockDao(Context context) {
		this.context = context;
		helper = new SoftLockOpenHelper(context);
	}
	//添加软件包
	public void addLockedSoft(String packageName) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("package_name", packageName);
		db.insert(TABLE, null, values);
		db.close();
	}
	
	//删除软件包
	public void deleteLockedSoft(String packageName) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.delete(TABLE, "package_name=?", new String[]{packageName});
		db.close();
	}
	
	//查询软件是否加锁
	public boolean queryLockedSoft(String packageName) {
		boolean isLocked =false;
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.query(TABLE, null, "package_name=?", new String[]{packageName}, null, null, null);
		if(cursor.moveToNext()) {
			isLocked = true;
		}
		cursor.close();
		db.close();
		return isLocked;
	}
	
	//查询所有的加锁的应用  ，返回的是包的列表
	
		public List<String> getAllLockedApp(){
			List<String> data=null;
			SQLiteDatabase db=helper.getReadableDatabase();
			Cursor c = db.query(TABLE, new String[]{"package_name"}, null, null, null, null,null);
			if(c.getCount()>0){
//				c.moveToPosition(position); //把游标指定到目标位置
				data=new ArrayList<String>();
			}
			while(c.moveToNext()){
				String packagename=c.getString(0);
				data.add(packagename);
			}
			c.close();
			db.close();
			return data;
		}

}
