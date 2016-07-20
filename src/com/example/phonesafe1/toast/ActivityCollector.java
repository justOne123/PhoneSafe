package com.example.phonesafe1.toast;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

public class ActivityCollector {
	public static List<Activity> list = new ArrayList<Activity>();
	
	public static void addActivity(Activity activity) {
		list.add(activity);
	}
	
	public static void removeActivity(Activity activity) {
		list.remove(activity);
	}
	
	public static int getCount() {
		return list.size();
	}
	
	public static void finishAll() {
		int size = list.size();
		for (int i = size-1; i >=0; i--) {
			if (!list.get(i).isFinishing()) {
				list.get(i).finish();
			}
		}
	}
	

}
