package com.example.phonesafe1.business;

import java.util.ArrayList;
import java.util.List;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.example.phonesafe1.entity.SoftInfo;
import com.example.phonesafe1.utils.MyApp;


/**
 * 获取软件包信息的业务类
 * 
 * @author Administrator
 *
 */

public class SoftManager {
	
	public List<SoftInfo> getAllSoftInfos() {
		List<SoftInfo> softInfos = new ArrayList<SoftInfo>();
		PackageManager packageManager = MyApp.getContext().getPackageManager();
		List<PackageInfo> packages = packageManager.getInstalledPackages(0);
		for (PackageInfo packageInfo : packages) {
			String packageName = packageInfo.packageName;
			String versionName = packageInfo.versionName;
			String name = packageInfo.applicationInfo.loadLabel(packageManager).toString();
			Drawable icon = packageInfo.applicationInfo.loadIcon(packageManager);
			int flags = packageInfo.applicationInfo.flags;
			boolean isUser = true;
			boolean isSdCard = false;
			if ((flags & ApplicationInfo.FLAG_SYSTEM) == ApplicationInfo.FLAG_SYSTEM) {
				isUser = false;
			}
			if ((flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) == ApplicationInfo.FLAG_EXTERNAL_STORAGE) {
				isSdCard = true;
			}
			SoftInfo softInfo = new SoftInfo(name, versionName, packageName, icon, isUser, isSdCard);
			softInfos.add(softInfo);
		}
		return softInfos;
	}

}
