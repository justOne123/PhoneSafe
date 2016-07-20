package com.example.phonesafe1.adapter;

import java.util.List;
import com.example.phonesafe1.R;
import com.example.phonesafe1.entity.SoftInfo;
import com.example.phonesafe1.entity.TaskInfo;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 显示进程管理适配器
 * 
 * @author Administrator
 *
 */
public class TaskAdapter extends BaseAdapter{
	Context context;
	private List<TaskInfo> userProcessInfos;
	private List<TaskInfo> systemProcessInfos;

	public TaskAdapter(Context context, List<TaskInfo> userProcessInfos,
			List<TaskInfo> systemProcessInfos) {
		this.context = context;
		this.userProcessInfos =userProcessInfos;
		this.systemProcessInfos = systemProcessInfos;
	}
	
	public void setData(List<TaskInfo> userProcessInfos,List<TaskInfo> systemProcessInfos){
		this.userProcessInfos =userProcessInfos;
		this.systemProcessInfos = systemProcessInfos;
	}

	@Override
	public int getCount() {

		return userProcessInfos.size() + systemProcessInfos.size() + 2;
	}

	@Override
	public Object getItem(int position) {

		return userProcessInfos.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (position == 0 || position == userProcessInfos.size() + 1) {
			TextView textView = new TextView(context);
			textView.setText(position == 0 ? ("用户程序("+userProcessInfos.size() +")个"): ("系统程序(" +systemProcessInfos.size() + ")个"));
			textView.setTextColor(Color.GRAY);
			textView.setBackgroundColor(Color.BLUE);
			return textView;
		}
		
		View view = null;
		if (convertView != null && convertView instanceof RelativeLayout) {
			view = convertView;
		}else {
			view = LayoutInflater.from(context).inflate(R.layout.task_manager_item, parent, false);
		}
	
		ImageView iconIv = (ImageView)view.findViewById(R.id.icon_iv);
		TextView nameTv = (TextView)view.findViewById(R.id.name_tv);
		TextView memoryTv = (TextView)view.findViewById(R.id.memory_tv);
		CheckBox checkBox = (CheckBox)view.findViewById(R.id.task_chb);
		TaskInfo taskInfo = null;
		if (position <= userProcessInfos.size()) {
			taskInfo = userProcessInfos.get(position - 1);
		}else {
			taskInfo = systemProcessInfos.get(position - (userProcessInfos.size() + 2));
		}
		if (taskInfo.getIcon() != null) {
			iconIv.setImageDrawable(taskInfo.getIcon());
		}
		nameTv.setText(TextUtils.isEmpty(taskInfo.getLabel()) ? (taskInfo.getPackageName()+"") : (taskInfo.getLabel()));
		memoryTv.setText(taskInfo.getMemory());
		checkBox.setChecked(taskInfo.isChecked());
		return view;
	}

}
