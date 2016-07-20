package com.example.phonesafe1;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phonesafe1.toast.MD5;
import com.example.phonesafe1.toast.ToastUtils;
import com.example.phonesafe1.utils.CacheUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class SoftActivity extends Activity {
	@ViewInject(R.id.icon_iv)
	private ImageView iconIv;
	@ViewInject(R.id.appName_tv)
	private TextView appNameTv;
	@ViewInject(R.id.pwd_et)
	private EditText passwordEt;
	@ViewInject(R.id.button1)
	private Button loginButton;
	private Intent mIntent;
	private String packageName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_watch_dog);
		ViewUtils.inject(this);
		mIntent = getIntent();
		appNameTv.setText(mIntent.getStringExtra("label"));
		packageName = mIntent.getStringExtra("packagename");
		PackageManager packageManager = getPackageManager();
		try {
			ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
			Drawable loadIcon = applicationInfo.loadIcon(packageManager);
			iconIv.setImageDrawable(loadIcon);
		} catch (NameNotFoundException e) {
			
			e.printStackTrace();
		}
		
		loginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String password = passwordEt.getText().toString().trim();
				if (TextUtils.isEmpty(password)) {
					ToastUtils.show("密码为空，请重新输入");
				}else if(!(MD5.getMD5(password)).equals(CacheUtils.getString(CacheUtils.SOFTLOCK_PWD))){
					ToastUtils.show("密码错误，请重新输入");
				}else {
					Intent intent = new Intent();
					intent.putExtra("id", mIntent.getIntExtra("ID", 0));
					intent.putExtra("packagename", packageName);
					intent.setAction("com.example.phonesafe1.myReceiver");
					sendBroadcast(intent);
					finish();
				}
			}
		});
	}
	
	/*@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			startActivity(intent);
			finish();
		}
		
		return true;
	}*/
	
	@Override
	public void onBackPressed() {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		startActivity(intent);
		super.onBackPressed();
	}

}
