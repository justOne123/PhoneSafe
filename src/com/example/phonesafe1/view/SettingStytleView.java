package com.example.phonesafe1.view;

import com.example.phonesafe1.R;
import com.example.phonesafe1.utils.CacheUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class SettingStytleView extends RelativeLayout {
	private  TextView titleView;
	private TextView desView;
	public  String settingTitle;
	public  String desOn;

	public SettingStytleView(Context context) {
		super(context);
		init();
	}

	public SettingStytleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		//��ȡ����ֵ
		String itcast="http://schemas.android.com/apk/res/com.example.phonesafe1";
		settingTitle = attrs.getAttributeValue(itcast, "settingTitle");
		desOn = attrs.getAttributeValue(itcast, "des_on");
		initData();
		
	}

//��ʼ����ͼ�����ص��Զ���ռ���
	private void init() {
		View.inflate(getContext(), R.layout.setting_stytle_view, this);
		
	}
	
	private void initData() {
		titleView = (TextView)findViewById(R.id.title_tv);
		desView = (TextView)findViewById(R.id.des_tv);
		titleView.setText(settingTitle);
		desView.setText(desOn);
	}

	public SettingStytleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	//��ȡ�û���SharedPreference�洢������
	public void getSettingFromUser(String pref) {
		setTitleText(settingTitle);
	}
		
	//���ñ���
	
	public void setTitleText(String msg1) {
		titleView.setText(msg1);
	}
	
	//����������Ϣ
	public void setDes(String msg2) {
		desView.setText(msg2);
	}
	
	

}
