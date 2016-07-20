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

/**�������������ݿ�Ĺ�����
 * 
 * @author Administrator
 *
 */

public class BlackNumDao {
	private BlackNumOpenHelper helper;
	public static final String NUM = "num";
	public static final String MODE = "mode";
	public static final String TABLE = "t_info";
	
	public static final int CALL = 1;//�绰����
	public static final int SMS = 2;//��������
	public static final int ALL = 3;//����ȫ��
	/**
	 * ���
	 * @param num :�绰����
	 * @param mode �� ���صķ�ʽ   1 .�绰����   2. ��������  3. all 
	 */
	
	public BlackNumDao(Context context) {
		helper = new BlackNumOpenHelper(context);
	}
	//ɾ�������������ݵ��ǵ绰����
	public int delete(String num) {
		SQLiteDatabase db = helper.getWritableDatabase();
		int deleteId = db.delete(TABLE, NUM+"=?", new String[]{num});
		db.close();
		return deleteId;
	}
	
	//��ѯĳ���������绰��������ط�ʽ
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
	
	//��Ӻ�����
	public long add(String num,int mode) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(NUM, num);
		values.put(MODE, mode);
		long id = db.insert(TABLE, null, values);
		db.close();
		return id;
	}
	
	//���º����������ط�ʽ
	public int updateMode(String num,int mode) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(MODE, mode);
		int updateId = db.update(TABLE, values, NUM+"=?", new String[]{num});
		db.close();
		return updateId;
	}
	
	//��ѯ���еĺ�����
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
	//�ּ���ѯ������
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
