package com.example.phonesafe1.entity;

import android.graphics.drawable.Drawable;

/**
 * 封装进程信息的实体类
 * 
 * @author Administrator
 *
 */
public class TaskInfo {
	private Drawable icon;
	private String label;
	private String memory;
	private boolean isUser = true;
	private String packageName;
	private boolean isChecked;
	
	public TaskInfo() {
		super();
		
	}

	public TaskInfo(Drawable icon, String label, String memory, boolean isUser,
			String packageName, boolean isChecked) {
		super();
		this.icon = icon;
		this.label = label;
		this.memory = memory;
		this.isUser = isUser;
		this.packageName = packageName;
		this.isChecked = isChecked;
	}

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public boolean isUser() {
		return isUser;
	}

	public void setUser(boolean isUser) {
		this.isUser = isUser;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	@Override
	public String toString() {
		return "TaskInfo [icon=" + icon + ", label=" + label + ", memory="
				+ memory + ", isUser=" + isUser + ", packageName="
				+ packageName + "]";
	}
	
	
	
}
