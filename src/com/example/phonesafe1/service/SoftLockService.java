package com.example.phonesafe1.service;

import java.util.List;

import com.example.phonesafe1.SoftActivity;
import com.example.phonesafe1.dao.SoftLockDao;

import android.app.ActivityManager;
import android.app.ActivityManager.RecentTaskInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;

public class SoftLockService extends Service {
	Context context;
	private boolean flag = true;
	private ActivityManager activityManager;
	private SoftLockDao softLockDao;
	private int taskId = -5;
	private String packageName;
	private PackageManager packageManager;
	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			taskId = intent.getIntExtra("id", 0);
			packageName = intent.getStringExtra("packagename");
		}
	};

	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}
	
	@Override
	public void onCreate() {
		activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
		context = this;
		softLockDao = new SoftLockDao(this);
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.example.phonesafe1.myReceiver");
		registerReceiver(receiver, intentFilter);
		
		packageManager = getPackageManager();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (flag) {
					List<RunningTaskInfo> runningTasks = activityManager.getRunningTasks(1);
					String taskPackageName = null;
					for (RunningTaskInfo runningTaskInfo : runningTasks) {
						taskPackageName = runningTaskInfo.baseActivity.getPackageName();
						int pid = runningTaskInfo.id;
//						int numActivities = runningTaskInfo.numActivities;
//						System.out.println(numActivities);
//						System.out.println("当前的包名"+taskPackageName);
						try {
							ApplicationInfo applicationInfo = packageManager.getApplicationInfo(taskPackageName, 0);
							String label = applicationInfo.loadLabel(packageManager).toString();
							boolean isLocked = softLockDao.queryLockedSoft(taskPackageName);
							if (isLocked) {
								if (!(taskPackageName.equals(packageName)) && pid != taskId) {
									Intent intent = new Intent();
									intent.setClass(context, SoftActivity.class);
									intent.putExtra("ID", taskId);
									intent.putExtra("label", label);
									intent.putExtra("packagename", taskPackageName);
									intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									startActivity(intent);
//								System.out.println("密码页面打不开");
								}
							}
							
							
						} catch (NameNotFoundException e) {
							
							e.printStackTrace();
						}
//					System.out.println("子线程正在运行...");
//					SystemClock.sleep(1000);
					}
				}
				
			}
		}).start();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		flag = false;
		unregisterReceiver(receiver);
		
	}

}
