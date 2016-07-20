package com.example.phonesafe1.utils;

import android.content.Context;
import android.content.Intent;

public class ActivityUtils {
	public static void skipPager(Context context,Class<?> cls) {
		Intent intent = new Intent();
		intent.setClass(context, cls);
		context.startActivity(intent);
	}

}
