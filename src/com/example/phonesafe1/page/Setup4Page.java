package com.example.phonesafe1.page;

import com.example.phonesafe1.R;
import com.example.phonesafe1.toast.ToastUtils;
import com.example.phonesafe1.utils.CacheUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnCompoundButtonCheckedChange;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class Setup4Page extends BasePage {
	@ViewInject(R.id.protect_cb)
	private CheckBox checkBox;
	@OnCompoundButtonCheckedChange(R.id.protect_cb)
	public void onCheckedBoxChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			ToastUtils.show(context,"Ñ·¸ç·ÀµÁÒÑ¿ªÆô");
			CacheUtils.putBoolean(CacheUtils.PROTECTED_SETTING, true);
		}else {
			ToastUtils.show(context,"Ñ·¸ç·ÀµÁÒÑ¹Ø±Õ");
			CacheUtils.putBoolean(CacheUtils.PROTECTED_SETTING, false);
		}
	}
	
	public Setup4Page(Context context) {
		super(context);
	}

	@Override
	public View initView() {
		View rooView = View.inflate(context, R.layout.layout_setup4, null);
		ViewUtils.inject(this,rooView);
		return rooView;
	}

	@Override
	public void initData() {
		if (CacheUtils.getBoolean(CacheUtils.PROTECTED_SETTING)) {
			checkBox.setChecked(true);
		}else {
			checkBox.setChecked(false);
		}
	}

}
