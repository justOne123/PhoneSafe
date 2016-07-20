package com.example.phonesafe1.toast;

import com.example.phonesafe1.utils.MyApp;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
	private static Context context = MyApp.getContext();

	public static void show(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
	public static void show(String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
	public static void show(String text,int duringTime) {
		Toast.makeText(context, text, duringTime).show();
	}
	

}
