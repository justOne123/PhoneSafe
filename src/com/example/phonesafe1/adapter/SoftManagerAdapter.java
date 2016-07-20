package com.example.phonesafe1.adapter;

import java.util.List;

import com.example.phonesafe1.R;
import com.example.phonesafe1.dao.SoftLockDao;
import com.example.phonesafe1.entity.SoftInfo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SoftManagerAdapter extends BaseAdapter{
	private Context context;
	private List<SoftInfo> userSoftInfos;
	private List<SoftInfo> systemSoftInfos;
	private SoftLockDao mSoftLockDao;

	
	public SoftManagerAdapter(Context context, List<SoftInfo> userSoftInfos,
			List<SoftInfo> systemSoftInfos) {
		this.context = context;
		this.userSoftInfos = userSoftInfos;
		this.systemSoftInfos = systemSoftInfos;
		mSoftLockDao = new SoftLockDao(context);
	}
	
	public void setData(List<SoftInfo> userSoftInfos,
			List<SoftInfo> systemSoftInfos) {
		this.userSoftInfos = userSoftInfos;
		this.systemSoftInfos = systemSoftInfos;
	}

	@Override
	public int getCount() {
		
		return userSoftInfos.size() + systemSoftInfos.size() + 2;
	}

	@Override
	public Object getItem(int position) {
		
		return userSoftInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if (position == 0 || position == userSoftInfos.size() + 1) {
			TextView textView = new TextView(context);
			textView.setText(position == 0 ? ("用户程序("+userSoftInfos.size() +")个"): ("系统程序(" +systemSoftInfos.size() + ")个"));
			textView.setTextColor(Color.GRAY);
			textView.setBackgroundColor(Color.BLUE);
			return textView;
		}
			View view = null;
			if (convertView != null && convertView instanceof RelativeLayout) {
				view = convertView;
			}else {
				view = LayoutInflater.from(context).inflate(R.layout.soft_manager_item, parent, false);
			}
		
			ImageView iconIv = (ImageView)view.findViewById(R.id.icon_iv);
			TextView nameTv = (TextView)view.findViewById(R.id.name_tv);
			TextView sdTv = (TextView)view.findViewById(R.id.is_sdcard_tv);
			TextView versionTv = (TextView)view.findViewById(R.id.version_tv);
			ImageView lockIv = (ImageView)view.findViewById(R.id.lock_iv);
			SoftInfo softInfo = null;
			if (position <= userSoftInfos.size()) {
				softInfo = userSoftInfos.get(position - 1);
			}else {
				softInfo = systemSoftInfos.get(position - (userSoftInfos.size() + 2));
			}
			if (mSoftLockDao.queryLockedSoft(softInfo.getPackageName())) {
				lockIv.setImageResource(R.drawable.lock);
			}else {
				lockIv.setImageResource(R.drawable.unlock);
			}
			iconIv.setImageDrawable(softInfo.getIcon());
			nameTv.setText(softInfo.getName());
			sdTv.setText(softInfo.isSdCard() ? "SD卡" : "手机内存");
			versionTv.setText(softInfo.getVersionName());
		return view;
	}

	

}
