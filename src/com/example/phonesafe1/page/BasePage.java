package com.example.phonesafe1.page;


import android.content.Context;
import android.view.View;

public abstract class BasePage {
	public Context context;
	public View rootView;
	public BasePage(Context context) {
		this.context = context;
		//��ʼ����ͼ
		rootView = initView();
		//��ʼ������
		initData();
	}
	
	public abstract View initView();
	public abstract void initData();
	
	public View getRootView() {
		return rootView;
	}
	
	


}
