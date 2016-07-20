package com.example.phonesafe1;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.example.phonesafe1.fragment.ProtectedFragment;
import com.example.phonesafe1.fragment.ProtectedSetupFragment;
import com.example.phonesafe1.toast.ToastUtils;
import com.example.phonesafe1.utils.CacheUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class LostFindActivity extends Activity {
	Context context;
	private EditText safeNumEt;
	private ProtectedSetupFragment protectedSetupFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lost_find);
		context = this;
		//加载碎片是否是第一次设置
		if (CacheUtils.getBoolean(CacheUtils.COMPLETED_SETTING)) {
			ProtectedFragment protectedFragment = new ProtectedFragment();
			getFragmentManager()
			.beginTransaction()
			.replace(R.id.safe_fly, protectedFragment, "protectedFragment")
			.commit();
		}else {
			protectedSetupFragment = new ProtectedSetupFragment();
			getFragmentManager()
			.beginTransaction()
			.replace(R.id.safe_fly, protectedSetupFragment, "protectedSetupFragment")
			.commit();
			
//			Fragment setFragment = getFragmentManager().findFragmentByTag("protectedSetupFragment");
			
		}
	}
	public void next(View v) {
		if (CacheUtils.getString(CacheUtils.BIND_SIM) != null) {
			CacheUtils.putBoolean(CacheUtils.COMPLETED_SETTING, true);
			ToastUtils.show("设置已完成");
			finish();
		}else {
			ToastUtils.show("请绑定SIM卡");
		}
		
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 2:
			if (resultCode == RESULT_OK && data != null) {
				ToastUtils.show("安全号码已设置");
				safeNumEt = (EditText)findViewById(R.id.safe_num_et);
				safeNumEt.setText(data.getStringExtra("num"));
				String safeNum = safeNumEt.getText().toString().trim();
				CacheUtils.putString(CacheUtils.SAFE_NUM, safeNum);
			}
			
			break;

		default:
			break;
		}
		
	}
}		
		
		
		
		/*setContentView(R.layout.activity_lost_find);
		ViewUtils.inject(this);
		//显示用户设置的安全码
		if (CacheUtils.getString(CacheUtils.SAFE_NUM) != null) {
			safeNum.setText(CacheUtils.getString(CacheUtils.SAFE_NUM));
			safeNum.setEnabled(false);
		}
		
		if (CacheUtils.getBoolean(CacheUtils.PROTECTED_SETTING)) {
			protectTv.setText("安全防盗开启");
			protectedIv.setImageResource(R.drawable.lock);
		}else {
			protectTv.setText("安全防盗关闭");
			protectedIv.setImageResource(R.drawable.unlock);
		}*/
		
//	}

	
	

