package com.example.phonesafe1.entity;

import android.graphics.drawable.Drawable;

/**
 * 封装包信息的实体类
 * 
 * @author Administrator
 *
 */

public class SoftInfo {
	private String name;
	private String versionName;
	private String packageName;
	private Drawable icon;
	private boolean isUser;
	private boolean isSdCard;
	public SoftInfo(String name, String versionName, String packageName,
			Drawable icon, boolean isUser, boolean isSdCard) {
		super();
		this.name = name;
		this.versionName = versionName;
		this.packageName = packageName;
		this.icon = icon;
		this.isUser = isUser;
		this.isSdCard = isSdCard;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public Drawable getIcon() {
		return icon;
	}
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}
	public boolean isUser() {
		return isUser;
	}
	public void setUser(boolean isUser) {
		this.isUser = isUser;
	}
	public boolean isSdCard() {
		return isSdCard;
	}
	public void setSdCard(boolean isSdCard) {
		this.isSdCard = isSdCard;
	}
	@Override
	public String toString() {
		return "SoftInfo [name=" + name + ", versionName=" + versionName
				+ ", packageName=" + packageName + ", icon=" + icon + "]";
	}
	
	
}
