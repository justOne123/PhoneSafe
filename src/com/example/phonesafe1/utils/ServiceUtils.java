package com.example.phonesafe1.utils;

import java.util.List;
/**
 * ȡ���������еķ����ҵ����
 * 
 */

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

public class ServiceUtils{
	public static boolean isServiceRunning(Context context,String className) {
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> runningServices = activityManager.getRunningServices(100);
			for (RunningServiceInfo runningServiceInfo : runningServices) {
				if (className.equals(runningServiceInfo.service.getClassName())) {
					return true;
				}
			}
		return false;
	}
}
