package com.example.phonesafe1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phonesafe1.R;
import com.example.phonesafe1.utils.MyApp;

public class HomeAdapter extends BaseAdapter {
	private int[] iconsId={R.drawable.safe,R.drawable.callmsgsafe,R.drawable.app,
			R.drawable.taskmanager,R.drawable.netmanager,R.drawable.trojan,R.drawable.sysoptimize
			,R.drawable.atools,R.drawable.settings};
	private String[] names={"手机防盗","通信卫士","软件管理","进程管理","流量统计","手机杀毒" 
			,"缓存清理","高级工具","设置中心"};

	@Override
	public int getCount() {

		return names.length;
	}

	@Override
	public Object getItem(int position) {

		return names[position];
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(MyApp.getContext()).inflate(R.layout.home_grid_adapter, parent, false);
		ImageView imageView = (ImageView)convertView.findViewById(R.id.icon_iv);
		TextView textView = (TextView)convertView.findViewById(R.id.name_tv);
		imageView.setImageResource(iconsId[position]);
		textView.setText(names[position]);
		return convertView;
	}

}
