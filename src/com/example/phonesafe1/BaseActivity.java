package com.example.phonesafe1;

import com.example.phonesafe1.toast.ActivityCollector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public abstract class BaseActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityCollector.addActivity(this);
	}
	
	@Override
	protected void onDestroy() {
		ActivityCollector.removeActivity(this);
		super.onDestroy();
	}
	//�����ؼ��ص�������
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		ActivityCollector.finishAll();
	}
	
	//��һ��
		public void pre(View v) {
				onPreActivity();
		}
		
		//��һ��
		public void next(View v) {
			onNextActivity();
		}
		
		//���ñ���
		public void setTitle(String title) {
			TextView titleTv = (TextView)findViewById(R.id.title_bar);
			titleTv.setText(title);
		}
		//��ת����һ���ҳ��
		public void skipPager(Class<?> cls) {
			Intent intent = new Intent();
			intent.setClass(this, cls);
			startActivity(intent);
		}
	
	public abstract void onPreActivity();
	public abstract void onNextActivity();
	
}
