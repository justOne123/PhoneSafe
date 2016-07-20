package com.example.phonesafe1.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;

import com.example.phonesafe1.db.BlackNumOpenHelper;
import com.example.phonesafe1.entity.BlackNumInfo;

/**黑名单访问数据库的工具类
 * 
 * @author Administrator
 *
 */

public class BlackNumDao {
	private BlackNumOpenHelper helper;
	public static final String NUM = "num";
	public static final String MODE = "mode";
	public static final String TABLE = "t_info";
	
	public static final int CALL = 1;//电话拦截
	public static final int SMS = 2;//短信拦截
	public static final int ALL = 3;//拦截全部
	/**
	 * 添加
	 * @param num :电话号码
	 * @param mode ： 拦截的方式   1 .电话拦截   2. 短信拦截  3. all 
	 */
	
	public BlackNumDao(Context context) {
		helper = new BlackNumOpenHelper(context);
	}
	//删除黑名单，传递的是电话号码
	public int delete(String num) {
		SQLiteDatabase db = helper.getWritableDatabase();
		int deleteId = db.delete(TABLE, NUM+"=?", new String[]{num});
		db.close();
		return deleteId;
	}
	
	//查询某个黑名单电话号码的拦截方式
	public int queryMode(String num) {
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.query(TABLE, new String[]{MODE}, NUM+"=?", new String[]{num},
				null, null, "num desc");
		int mode = -1;
		while(cursor.moveToNext()) {
			mode = cursor.getInt(0);
		}
		cursor.close();
		db.close();
		return mode;
	}
	
	//添加黑名单
	public long add(String num,int mode) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(NUM, num);
		values.put(MODE, mode);
		long id = db.insert(TABLE, null, values);
		db.close();
		return id;
	}
	
	//更新黑名单的拦截方式
	public int updateMode(String num,int mode) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(MODE, mode);
		int updateId = db.update(TABLE, values, NUM+"=?", new String[]{num});
		db.close();
		return updateId;
	}
	
	//查询所有的黑名单
	public List<BlackNumInfo> queryAll() {
		SystemClock.sleep(2000);
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.query(TABLE, new String[]{NUM,MODE}, null, null, null, null, null);
		List<BlackNumInfo> data = null;
		if (cursor.getCount() > 0) {
			data = new ArrayList<BlackNumInfo>();
		}
		while(cursor.moveToNext()) {
			String num = cursor.getString(0);
			int mode = cursor.getInt(1);
			BlackNumInfo info = new BlackNumInfo(num, mode);
			data.add(info);
		}
		cursor.close();
		db.close();
		return data;
	}
	//分级查询黑名单
	public List<BlackNumInfo> queryPartBlackNum(int limit,int offset) {
		SystemClock.sleep(2000);
		SQLiteDatabase db = helper.getWritableDatabase();
//		Cursor cursor = db.query(TABLE, new String[]{NUM,MODE}, null, null, null, null, null);
		Cursor cursor = db.query(TABLE, new String[]{NUM,MODE}, null, null, null, null, null, offset +"," + limit);
		List<BlackNumInfo> data = null;
		if (cursor.getCount() > 0) {
			data = new ArrayList<BlackNumInfo>();
		}
		while(cursor.moveToNext()) {
			String num = cursor.getString(0);
			int mode = cursor.getInt(1);
			BlackNumInfo info = new BlackNumInfo(num, mode);
			data.add(info);
		}
		cursor.close();
		db.close();
		return data;
	}
}
