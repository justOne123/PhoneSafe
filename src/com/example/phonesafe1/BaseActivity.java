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
	//按返回键回到主界面
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		ActivityCollector.finishAll();
	}
	
	//上一步
		public void pre(View v) {
				onPreActivity();
		}
		
		//下一步
		public void next(View v) {
			onNextActivity();
		}
		
		//设置标题
		public void setTitle(String title) {
			TextView titleTv = (TextView)findViewById(R.id.title_bar);
			titleTv.setText(title);
		}
		//跳转到下一个活动页面
		public void skipPager(Class<?> cls) {
			Intent intent = new Intent();
			intent.setClass(this, cls);
			startActivity(intent);
		}
	
	public abstract void onPreActivity();
	public abstract void onNextActivity();
	
}
