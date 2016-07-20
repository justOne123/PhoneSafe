package com.example.phonesafe1.page;

import com.example.phonesafe1.R;
import com.example.phonesafe1.SettingView;
import com.example.phonesafe1.utils.CacheUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;

public class Setup2Page extends BasePage {
	
	@ViewInject(R.id.bind_sim_sv)
	private SettingView bindSimView;

	public Setup2Page(Context context) {
		super(context);
	}

	@Override
	public View initView() {
		View rooView = View.inflate(context, R.layout.layout_setup2, null);
		ViewUtils.inject(this,rooView);
		//���ֻ�����ʵ�м���
				bindSimView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						//��ȡ�绰�������к�
						TelephonyManager manager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
						String simSerialNumber = manager.getSimSerialNumber();
						
						if (bindSimView.getChecked()) {
							bindSimView.setChecked(false);
							CacheUtils.putString(CacheUtils.BIND_SIM, null);
						}else {
							bindSimView.setChecked(true);
							CacheUtils.putString(CacheUtils.BIND_SIM, simSerialNumber);
						}
					}
				});
				
				//���û����ó�ʼ��
				bindSimView.getSIMFromUser(CacheUtils.BIND_SIM);
		
		
		return rooView;
	}

	@Override
	public void initData() {
	}

}
