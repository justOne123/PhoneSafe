package com.example.phonesafe1.page;

import com.example.phonesafe1.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class Setup1Page extends BasePage {

	public Setup1Page(Context context) {
		super(context);
	}

	@Override
	public View initView() {
		View rooView = View.inflate(context, R.layout.layout_setup1, null);

		return rooView;
	}

	@Override
	public void initData() {
	}

}
