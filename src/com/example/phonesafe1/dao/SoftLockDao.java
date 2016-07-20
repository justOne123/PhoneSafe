package com.example.phonesafe1.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.phonesafe1.db.SoftLockOpenHelper;

/**
 * ��������ݿ����ɾ�Ĳ�
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
	//��������
	public void addLockedSoft(String packageName) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("package_name", packageName);
		db.insert(TABLE, null, values);
		db.close();
	}
	
	//ɾ�������
	public void deleteLockedSoft(String packageName) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.delete(TABLE, "package_name=?", new String[]{packageName});
		db.close();
	}
	
	//��ѯ����Ƿ����
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
	
	//��ѯ���еļ�����Ӧ��  �����ص��ǰ����б�
	
		public List<String> getAllLockedApp(){
			List<String> data=null;
			SQLiteDatabase db=helper.getReadableDatabase();
			Cursor c = db.query(TABLE, new String[]{"package_name"}, null, null, null, null,null);
			if(c.getCount()>0){
//				c.moveToPosition(position); //���α�ָ����Ŀ��λ��
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
