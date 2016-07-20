package com.example.phonesafe1;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;

import com.example.phonesafe1.adapter.BlackNumAdapter;
import com.example.phonesafe1.dao.BlackNumDao;
import com.example.phonesafe1.entity.BlackNumInfo;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class CallAndSmsActivity extends Activity {
	@ViewInject(R.id.blacknum_lv)
	private ListView mBlackNumLstView;
	@ViewInject(R.id.progress_pb)
	private ProgressBar mProgressBar;
	
	private BlackNumDao mBlackNumDao;
	private List<BlackNumInfo> mData;
	private static final int LIMIT = 20;
	private static int startLine = 0;
	Context context;
	private BlackNumAdapter mAdapter;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call_sms);
		ViewUtils.inject(this);
		mBlackNumDao = new BlackNumDao(this);
		context = this;
		//初始化数据
		initData();
		
		mBlackNumLstView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_IDLE:
					int size = mData.size() - 1;
					if (mBlackNumLstView.getLastVisiblePosition() == size) {
						startLine = startLine + LIMIT;
						initData();
					}
					break;

				default:
					break;
				}
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
			}
		});
	}


	//初始化数据
	private void initData() {
		new AsyncTask<String, Integer, String>() {
			@Override
			protected void onPreExecute() {
				mProgressBar.setVisibility(View.VISIBLE);
			}

			@Override
			protected String doInBackground(String... params) {
				if (mData == null) {
					mData = mBlackNumDao.queryPartBlackNum(LIMIT, startLine);
				}else {
					if (mBlackNumDao.queryPartBlackNum(LIMIT, startLine) != null) {
						mData.addAll(mBlackNumDao.queryPartBlackNum(LIMIT, startLine));
					}
				}
				
				return null;
			}
			@Override
			protected void onPostExecute(String result) {
				mProgressBar.setVisibility(View.INVISIBLE);
				if (mAdapter == null) {
					mAdapter = new BlackNumAdapter(context,mData);
					mBlackNumLstView.setAdapter(mAdapter);
				}else {
					mAdapter.setData(mData);
					mAdapter.notifyDataSetChanged();
				}
			}
			
		}.execute();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.setting, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_add:
			showAddDialog();
			
			break;

		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}

	//显示添加黑名单对话框
	private void showAddDialog() {
		View dialogView = View.inflate(context, R.layout.dialog_add_blacknum, null);
		final AlertDialog alertDialog = new AlertDialog.Builder(context).setView(dialogView).create();
		final EditText blackNumEt = (EditText)dialogView.findViewById(R.id.num_et);
		final RadioGroup radioGroup = (RadioGroup)dialogView.findViewById(R.id.mode_rg);
		Button okButton = (Button)dialogView.findViewById(R.id.add_btn);
		Button cancelButton = (Button)dialogView.findViewById(R.id.cancel_btn);
		//获取黑名单号码
		
		okButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String num = blackNumEt.getText().toString().trim();
				int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
				int mode = -1;
				switch (checkedRadioButtonId) {
				case R.id.call_rb:
					mode = BlackNumDao.CALL;
					break;
				case R.id.sms_rb:
					mode = BlackNumDao.SMS;
					break;
				case R.id.all_rb:
					mode = BlackNumDao.ALL;
					break;

				default:
					break;
				}
				mBlackNumDao.add(num, mode);
				mData = mBlackNumDao.queryAll();
				mAdapter.setData(mData);
				mAdapter.notifyDataSetChanged();
				alertDialog.dismiss();
				
			}
		});
		
		cancelButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				alertDialog.dismiss();
			}
		});
		
		alertDialog.show();
	}

}
