package com.example.phonesafe1.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.phonesafe1.R;
import com.example.phonesafe1.utils.CacheUtils;

public class ProtectedFragment extends Fragment {
	private View rooView;
	private ImageView protectedIv;
	private TextView protectTv;
	private TextView safeNum;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rooView = inflater.inflate(R.layout.fragment_protected, container, false);
		initView();
		initData();
		return rooView;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			
	}
	private void initView() {
		safeNum = (TextView)rooView.findViewById(R.id.safe_num_tv);
		RelativeLayout layout = (RelativeLayout)rooView.findViewById(R.id.lock_rly);
		protectedIv = (ImageView)rooView.findViewById(R.id.protected_iv);
		protectTv = (TextView)rooView.findViewById(R.id.protect_tv);
		layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (CacheUtils.getBoolean(CacheUtils.PROTECTED_SETTING)) {
					protectTv.setText("安全防盗关闭");
					protectedIv.setImageResource(R.drawable.unlock);
					CacheUtils.putBoolean(CacheUtils.PROTECTED_SETTING, false);
				}else {
					protectTv.setText("安全防盗开启");
					protectedIv.setImageResource(R.drawable.lock);
					CacheUtils.putBoolean(CacheUtils.PROTECTED_SETTING, true);
				}
			}
		});
		TextView resetView = (TextView)rooView.findViewById(R.id.reset_tv);
		resetView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ProtectedSetupFragment protectedSetupFragment = new ProtectedSetupFragment();
//				Fragment setFragment = getFragmentManager().findFragmentByTag("protectedSetupFragment");
				getFragmentManager().beginTransaction().replace(R.id.safe_fly, protectedSetupFragment, "protectedSetupFragment").commit();
			}
		});
	}
	
	private void initData() {
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
				}
				
	}

	public View getRootView() {
		return rooView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
	}
}
