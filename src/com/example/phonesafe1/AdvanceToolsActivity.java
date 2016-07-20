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
	
	//�Թ����ز�ѯ��ť���ü���
	public void query(View v) {
		ActivityUtils.skipPager(this, AddressActivity.class);
	}
	//��С����������м���
	public void rocket(View v) {
		ActivityUtils.skipPager(this, RocaketActivity.class);
	}
	
	//�Զ��Ž��б���
	public void backupMessage(View v) {
		FileOutputStream fos =null;
		try {
			fos = openFileOutput("smss.xml",Context.MODE_PRIVATE);
			MessageEngine.backupSms(this,fos);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	//�Զ��Ż�ԭ
	public void lookBackMessage(View v) {
		File file = new File(getFilesDir(), "smss.xml");
		if (file.exists() && file.length()>0) {
			Intent intent = new Intent(this, ShowSmsActivity.class);
			startActivity(intent);
		}else {
			ToastUtils.show("���ȱ��ݶ���", 1);
		}
		
	}
}
