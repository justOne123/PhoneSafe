package com.example.phonesafe1.page;

import com.example.phonesafe1.ContactsActivity;
import com.example.phonesafe1.LostFindActivity;
import com.example.phonesafe1.R;
import com.example.phonesafe1.utils.CacheUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

public class Setup3Page extends BasePage {
	@ViewInject(R.id.safe_num_et)
	private EditText safeNumEt;
	private String safeNum;
	
	@OnClick(R.id.select_contact_tv)
	public void selectContacts(View v) {
		Intent intent = new Intent();
		intent.setClass(context, ContactsActivity.class);
		((LostFindActivity)context).startActivityForResult(intent, 2);
	}

	public Setup3Page(Context context) {
		super(context);
	}

	@Override
	public View initView() {
		View rooView = View.inflate(context, R.layout.layout_setup3, null);
		ViewUtils.inject(this, rooView);
		if (CacheUtils.getString(CacheUtils.SAFE_NUM) != null) {
			safeNumEt.setText(CacheUtils.getString(CacheUtils.SAFE_NUM));
		}
		
		return rooView;
	}

	@Override
	public void initData() {
		if (CacheUtils.getString(CacheUtils.SAFE_NUM) != null) {
			safeNumEt.setText(CacheUtils.getString(CacheUtils.SAFE_NUM));
		}
		
	}

}
