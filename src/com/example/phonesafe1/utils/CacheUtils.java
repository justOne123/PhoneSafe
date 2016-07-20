package com.example.phonesafe1.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class CacheUtils {
	/**��sharedpreference���г־û��洢�Ĺ�����
	 * 
	 * 
	 */
	private static SharedPreferences mSp;
	public static final String CONFIG_PREFS = "config_prefs";//�洢�ļ���
	public static final String IS_FIRST_ENTER = "is_first_enter";//�Ƿ��һ�ν���
	public static final String UPDATE_APK = "update_apk";//�Ƿ�Ҫ���°汾
	public static final String SOFT_APK = "soft_apk";//
	public static final String SETUP_PWD = "setup_pwd";//�����ֻ������������key
	public static final String PROTECTED_SETTING = "protected_setting";//
	public static final String BIND_SIM = "bind_sim";//��SIM��
	public static final String SAFE_NUM = "safe_num";
	public static final String COMPLETED_SETTING = "completed_setting";
	public static final String LATITUDE = "latitude";
	public static final String LONGITUTDE = "longitutde";
	public static final String ADDRESS_STYTLE = "address_stytle";
	public static final String SOFTLOCK_PWD = "softlock_pwd";//�����
	private static Context context = MyApp.getContext();
	
	public static SharedPreferences getSharedPreference(){
		if(mSp == null){
			mSp = context.getSharedPreferences(CacheUtils.CONFIG_PREFS, Context.MODE_PRIVATE);
		}
		return mSp;
	}
	
	public static void putBoolean(String key,boolean b) {
		mSp = getSharedPreference();
		mSp.edit().putBoolean(key, b).commit();
	}
	
	public static Boolean getBoolean(String key,Boolean defValue) {
		return getSharedPreference().getBoolean(key, defValue);
	}
	public static Boolean getBoolean(String key) {
		return getSharedPreference().getBoolean(key, false);
	}
	
	
	public static void putString(String key,String value) {
		mSp = getSharedPreference();
		mSp.edit().putString(key, value).commit();
	}
	
	public static String getString(String key) {
		return getSharedPreference().getString(key, null);
	}
	public static void putInt(String key,int value) {
		mSp = getSharedPreference();
		mSp.edit().putInt(key, value).commit();
	}
	
	public static int getInt(String key) {
		return getSharedPreference().getInt(key, 0);
	}

}
