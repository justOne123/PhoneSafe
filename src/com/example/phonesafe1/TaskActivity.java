package com.example.phonesafe1;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ListView;

import com.example.phonesafe1.adapter.TaskAdapter;
import com.example.phonesafe1.business.TaskManager;
import com.example.phonesafe1.entity.TaskInfo;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
/**
 * 进程管理界面
 * 
 * @author Administrator
 *
 */
public class TaskActivity extends Activity {
	Context context;
	@ViewInject(R.id.task_lv)
	private ListView taskLstView;
	private TaskManager taskManager;//获取进程的业务类
	private List<TaskInfo> mData;
	private List<TaskInfo> userProcessInfos;
	private List<TaskInfo> systemProcessInfos;
	private TaskAdapter adapter;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task);
		ViewUtils.inject(this);
		context = this;
		taskManager = new TaskManager();
		userProcessInfos = new ArrayList<TaskInfo>();
		systemProcessInfos = new ArrayList<TaskInfo>();
		//初始化数据
		initData();
		
	}
	//初始化数据
	private void initData() {
		new AsyncTask<String, Integer, String>() {
			
			@Override
			protected void onPreExecute() {
				
			}

			@Override
			protected String doInBackground(String... params) {
				mData = taskManager.getAllProcessInfos();
				userProcessInfos.clear();
				systemProcessInfos.clear();
				for (TaskInfo taskInfo : mData) {
					if (taskInfo.isUser()) {
						userProcessInfos.add(taskInfo);
					}else {
						systemProcessInfos.add(taskInfo);
					}
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(String result) {
				if (adapter == null) {
					adapter = new TaskAdapter(context,userProcessInfos,systemProcessInfos);
					taskLstView.setAdapter(adapter);
					//对listView的列表项做监听
					listenLstView();
				}else {
					adapter.setData(userProcessInfos, systemProcessInfos);
					adapter.notifyDataSetChanged();
				}
			}
			
		}.execute();
	}
	
	//对listView的列表项做监听
	private void listenLstView() {
		taskLstView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 0 || position == userProcessInfos.size() + 1) {
					return;
				}
				CheckBox checkBox = (CheckBox)view.findViewById(R.id.task_chb);
				TaskInfo taskInfo = null;
				if (position <= userProcessInfos.size()) {
					taskInfo = userProcessInfos.get(position-1);
				}else {
					taskInfo = systemProcessInfos.get(position - (userProcessInfos.size()+2));
				}
				
				if (checkBox.isChecked()) {
					checkBox.setChecked(false);
					taskInfo.setChecked(false);
				}else {
					checkBox.setChecked(true);
					taskInfo.setChecked(true);
				}
			}
		});
	}
	
	//全选
	public void selectAll(View v) {
		for (TaskInfo taskInfo : userProcessInfos) {
			taskInfo.setChecked(true);
		}
		
		for (TaskInfo taskInfo : systemProcessInfos) {
			taskInfo.setChecked(true);
		}
		adapter.setData(userProcessInfos, systemProcessInfos);
		adapter.notifyDataSetChanged();
	}
	//取消全选
	public void cancelSelect(View v) {
		for (TaskInfo taskInfo : userProcessInfos) {
			taskInfo.setChecked(false);
		}
		
		for (TaskInfo taskInfo : systemProcessInfos) {
			taskInfo.setChecked(false);
		}
		adapter.setData(userProcessInfos, systemProcessInfos);
		adapter.notifyDataSetChanged();
	}
	//清除选中的进程
	public void clearTask(View v) {
		ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
		ListIterator<TaskInfo> userIterator = userProcessInfos.listIterator();
		while (userIterator.hasNext()) {
			TaskInfo taskInfo = userIterator.next();
			if (taskInfo.isChecked()) {
				activityManager.killBackgroundProcesses(taskInfo.getPackageName());
				userIterator.remove();
			}
		}
		ListIterator<TaskInfo> systemIterator = systemProcessInfos.listIterator();
		while (systemIterator.hasNext()) {
			TaskInfo taskInfo = systemIterator.next();
			if (taskInfo.isChecked()) {
				activityManager.killBackgroundProcesses(taskInfo.getPackageName());
				systemIterator.remove();
			}
		}
		adapter.setData(userProcessInfos, systemProcessInfos);
		adapter.notifyDataSetChanged();
	}
	//设置进程
	public void setTask(View v) {
		
	}
	
	
}
