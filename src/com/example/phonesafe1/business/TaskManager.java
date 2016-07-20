package com.example.phonesafe1.business;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Debug.MemoryInfo;
import android.text.format.Formatter;

import com.example.phonesafe1.entity.TaskInfo;
import com.example.phonesafe1.utils.MyApp;

/**
 * 获取进程信息的业务类
 * 
 * @author Administrator
 *
 */
public class TaskManager {
	public List<TaskInfo> getAllProcessInfos() {
		List<TaskInfo> processInfos	= new ArrayList<TaskInfo>();
		ActivityManager manager = (ActivityManager) MyApp.getContext().getSystemService(Context.ACTIVITY_SERVICE);
		PackageManager packageManager = MyApp.getContext().getPackageManager();
		List<RunningAppProcessInfo> runningAppProcesses = manager.getRunningAppProcesses();
		for (RunningAppProcessInfo processInfo : runningAppProcesses) {
			String packageName = processInfo.processName;
			
			try {
				ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
				Drawable icon = applicationInfo.loadIcon(packageManager);
				String label = applicationInfo.loadLabel(packageManager).toString();
				int flags = applicationInfo.flags;
				boolean isUser = true;
				if ((flags & ApplicationInfo.FLAG_SYSTEM) == ApplicationInfo.FLAG_SYSTEM) {
					isUser = false;
				}
				
				MemoryInfo[] memoryInfos = manager.getProcessMemoryInfo(new int[]{processInfo.pid});
				long processMemory = memoryInfos[0].getTotalPss()*1024;
				String memory = Formatter.formatFileSize(MyApp.getContext(), processMemory);
				TaskInfo taskInfo = new TaskInfo(icon, label, memory, isUser, packageName, false);
				processInfos.add(taskInfo);
			} catch (NameNotFoundException e) {
				
				e.printStackTrace();
			}
		}
		return processInfos;
	}
}
