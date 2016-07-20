package com.example.phonesafe1.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AddressDao {
	/**
	 * 查询电话号码归属地的工具类
	 * 
	 * @param context
	 * @param num
	 * @return
	 */
	
	public static String qureyAddress(Context context,String num) {
		SQLiteDatabase database = SQLiteDatabase.openDatabase(context.getFilesDir().getAbsolutePath() + "/address.db", 
				null, SQLiteDatabase.OPEN_READONLY);
		int numLength = num.length();
		String regex = "1[345678]\\d{9}";
		String address = null;
		switch (numLength) {
		case 11:
			if (num.matches(regex)) {
				num = num.substring(0,7);
			}
			break;
		case 4:
			address = "这是虚拟号码";
			return address;
		case 5:
			String[] nums = {"中国移动客服","中国联通客服","中国电信客服","..."};
			if ("10086".equals(num)) {
				address = nums[0];
			}else if ("10010".equals(num)) {
				address = nums[1];
			}else if ("10000".equals(num)) {
				address = nums[2];
			}else {
				address = nums[3];
			}
			return address;
		case 7:
		case 8:
			address = "这是本地号码";
			return address;
		default:
			String area = null;
			if (num.startsWith("0") && numLength >= 10) {
				area = num.substring(1,3);
				Cursor cursor = database.rawQuery("select location from data2 where arer=?", new String[]{area});
				if (cursor.moveToNext()) {
					return cursor.getString(0);
				}else {
					area = num.substring(1,4);
					Cursor cursor1 = database.rawQuery("select location from data2 where arer=?", new String[]{area});
					return cursor.getString(0);
				}
			}
		}
		Cursor cursor = database.rawQuery("select location from data2 where id in(select outkey from data1 where id=?)", 
				new String[]{num});
		
		if(cursor.moveToNext()) {
			address = cursor.getString(0);
		}
		return address;
	}

}
