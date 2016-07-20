package com.example.phonesafe1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.phonesafe1.business.MessageEngine;
import com.example.phonesafe1.toast.ToastUtils;
import com.example.phonesafe1.utils.ActivityUtils;

public class AdvanceToolsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_advance_tools);
	}
	
	//对归属地查询按钮设置监听
	public void query(View v) {
		ActivityUtils.skipPager(this, AddressActivity.class);
	}
	//对小火箭动画进行监听
	public void rocket(View v) {
		ActivityUtils.skipPager(this, RocaketActivity.class);
	}
	
	//对短信进行备份
	public void backupMessage(View v) {
		FileOutputStream fos =null;
		try {
			fos = openFileOutput("smss.xml",Context.MODE_PRIVATE);
			MessageEngine.backupSms(this,fos);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	//对短信还原
	public void lookBackMessage(View v) {
		File file = new File(getFilesDir(), "smss.xml");
		if (file.exists() && file.length()>0) {
			Intent intent = new Intent(this, ShowSmsActivity.class);
			startActivity(intent);
		}else {
			ToastUtils.show("请先备份短信", 1);
		}
		
	}
}
