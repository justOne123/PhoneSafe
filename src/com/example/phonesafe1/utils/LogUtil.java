package com.example.phonesafe1.utils;

import android.util.Log;

public class LogUtil {
	public static boolean IS_DEBUG = true;
	
	public static void i(String tag,String msg) {
		if (IS_DEBUG) {
			Log.i(tag, msg);
		}
	}
	public static void e(String tag,String msg) {
		if (IS_DEBUG) {
			Log.e(tag, msg);
		}
	}
	public static void d(String tag,String msg) {
		if (IS_DEBUG) {
			Log.e(tag, msg);
		}
	}

}
