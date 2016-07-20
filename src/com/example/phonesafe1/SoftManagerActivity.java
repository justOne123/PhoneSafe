package com.example.phonesafe1;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.phonesafe1.adapter.SoftManagerAdapter;
import com.example.phonesafe1.business.SoftManager;
import com.example.phonesafe1.dao.SoftLockDao;
import com.example.phonesafe1.entity.SoftInfo;
import com.example.phonesafe1.toast.ToastUtils;
import com.example.phonesafe1.utils.CacheUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * ���̹������
 * @author Administrator
 *
 */
public class SoftManagerActivity extends Activity implements View.OnClickListener{
	Context context;
	
	@ViewInject(R.id.soft_lv)
	private ListView softLstView;
	@ViewInject(R.id.count_tv1)
	private TextView mcountTv;
	private SoftManager mSoftManager;
	private List<SoftInfo> mData;
	private SoftManagerAdapter adapter;
	private List<SoftInfo> userSoftInfos;
	private List<SoftInfo> systemSoftInfos;//
	private LinearLayout mLayoutView;//������ͼ
	private SoftInfo softInfo;

	private SoftLockDao mSoftLockDao;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_soft_manager);
		ViewUtils.inject(this);
		context = this;
		mSoftLockDao = new SoftLockDao(context);
		//��ʼ������
		fillData();
		
		initData();//��ʼ����������
	}
	//��ʼ������
	private void fillData() {
		userSoftInfos = new ArrayList<SoftInfo>();
		systemSoftInfos = new ArrayList<SoftInfo>();
		mLayoutView = (LinearLayout) View.inflate(context, R.layout.popup_window, null);
		//�Ե������б���������
		int childCount = mLayoutView.getChildCount();
		for (int i = 0; i < childCount; i++) {
			mLayoutView.getChildAt(i).setOnClickListener(SoftManagerActivity.this);
		}
		mSoftManager = new SoftManager();
	}
	
	//��ListView���м���
	private void listenLstView() {
		//��ListView���й�������
		softLstView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem <= userSoftInfos.size()) {
					mcountTv.setText("�û�����(" + userSoftInfos.size() + ")��");
				}else {
					mcountTv.setText("ϵͳ����(" + systemSoftInfos.size() + ")��");
				}
			}
		});
		
		//��ListView�����б������
		softLstView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//���ݲ�ͬ��λ�û�ȡsoftInfo��ʵ��
				if (position == 0 || position == userSoftInfos.size() + 1) {
					return;
				}else if (position <= userSoftInfos.size()) {
					softInfo = userSoftInfos.get(position - 1);
				}else {
					softInfo = systemSoftInfos.get(position - (userSoftInfos.size() + 2));
				}
				//���õ���
				PopupWindow popupWindow = new PopupWindow(mLayoutView, ViewGroup.LayoutParams.WRAP_CONTENT, 
						ViewGroup.LayoutParams.WRAP_CONTENT);
				popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
				popupWindow.setFocusable(true);
				//���ö���
				AnimationSet animationSet = animationSet();
				mLayoutView.startAnimation(animationSet);
				popupWindow.showAsDropDown(view, 150, -view.getHeight());
			}
		});
		
		//��ListView�����б��������
		softLstView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				//���ݲ�ͬ��λ�û�ȡsoftInfo��ʵ��
				if (position == 0 || position == userSoftInfos.size() + 1) {
					return true;
				}else if (position <= userSoftInfos.size()) {
					softInfo = userSoftInfos.get(position - 1);
				}else {
					softInfo = systemSoftInfos.get(position - (userSoftInfos.size() + 2));
				}
				ImageView lockIv=(ImageView)view.findViewById(R.id.lock_iv);
				
				if (!TextUtils.isEmpty(CacheUtils.getString(CacheUtils.SOFTLOCK_PWD))) {
					String packageName = softInfo.getPackageName();
					if (mSoftLockDao.queryLockedSoft(packageName)) {
						lockIv.setImageResource(R.drawable.unlock);
						mSoftLockDao.deleteLockedSoft(packageName);
					}else {
						if (packageName.equals(getPackageName())) {
							ToastUtils.show("��ǰ�����ܼ���");
						}else {
							lockIv.setImageResource(R.drawable.lock);
							mSoftLockDao.addLockedSoft(packageName);
						}
					}
					
				}else {
					ToastUtils.show("���ȿ��������", 1);
				}
				
				return true;
			}
		});
		
		
		
	}
	
	//���ö���
	private AnimationSet animationSet() {
		//͸���ȶ���
		AnimationSet animationSet = new AnimationSet(true);
		AlphaAnimation alphaAnimation = new AlphaAnimation(0.4f, 1);
		alphaAnimation.setDuration(2000);
		alphaAnimation.setFillAfter(true);
		alphaAnimation.setInterpolator(new BounceInterpolator());
		animationSet.addAnimation(alphaAnimation);
		//���Ŷ���
		ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimation.setDuration(2000);
		scaleAnimation.setFillAfter(true);
		scaleAnimation.setInterpolator(new BounceInterpolator());
		animationSet.addAnimation(scaleAnimation);
		//��ת����
		RotateAnimation rotateAnimation = new RotateAnimation(0, 360, 
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rotateAnimation.setDuration(2000);
		rotateAnimation.setFillAfter(true);
		rotateAnimation.setInterpolator(new BounceInterpolator());
		animationSet.addAnimation(rotateAnimation);
		return animationSet;
	}
	//�Ե������б���������
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.uninstall_tv://ж��
			/* 
             <action android:name="android.intent.action.UNINSTALL_PACKAGE"/>
             <category android:name="android.intent.category.DEFAULT"/>
             <data android:scheme="package"/>*/
			
			
			if (getPackageName().equals(softInfo.getPackageName())) {
				ToastUtils.show("�����������У�����ж��");
			}else if (!softInfo.isUser()) {
				ToastUtils.show("ϵͳ���򣬲���ж��");
			}else {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_DELETE);
//				intent.addCategory("android.intent.category.DEFAULT");
				intent.setData(Uri.parse("package:" + softInfo.getPackageName()));
				intent.putExtra("package", softInfo.getPackageName());
				startActivityForResult(intent,6);
			}
			break;
		case R.id.run_tv://����
			Intent launchIntent = getPackageManager().getLaunchIntentForPackage(softInfo.getPackageName());
			if (launchIntent == null) {
				ToastUtils.show("ϵͳ������ֱ������");
			}else if (getPackageName().equals(softInfo.getPackageName())) {
				ToastUtils.show("������������");
			}else {
				startActivity(launchIntent);
			}
			
			break;
		case R.id.share_tv://����
			 /*<action android:name="android.intent.action.SEND" />
             <category android:name="android.intent.category.DEFAULT" />
             <data android:mimeType="text/plain" />*/
			Intent intent1= new Intent();
			intent1.setAction("android.intent.action.SEND");
			intent1.setType("text/plain");
			intent1.putExtra(Intent.EXTRA_TEXT, "android�����������");//��������ı�����Ӧ�ø��Ӹ�key
			startActivity(intent1);
			break;
		case R.id.detail_tv://��ϸ
			Intent intent2 = new Intent();
			intent2.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
			intent2.setData(Uri.parse("package:"+softInfo.getPackageName()));
			startActivity(intent2);
			break;

		default:
			break;
		}
	}
	
	//ж��Ӧ�ú�ͬ�������б�
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 6) {
			/*String packageName = data.getData().getHost();
			for (SoftInfo info : mData) {
				if (packageName.equals(info.getPackageName())) {
					mData.remove(info);
				}
			}*/
			initData();
		}
	}
	
	//��ʼ����������
	private void initData() {
		new AsyncTask<String, Integer, String>() {
			@Override
			protected void onPreExecute() {
			}

			@Override
			protected String doInBackground(String... params) {
				mData = mSoftManager.getAllSoftInfos();
				userSoftInfos.clear();
				systemSoftInfos.clear();
				for (SoftInfo softInfo : mData) {
					if (softInfo.isUser()) {
						userSoftInfos.add(softInfo);
					}else {
						systemSoftInfos.add(softInfo);
					}
				}
				return null;
			}
			@Override
			protected void onPostExecute(String result) {
				if (adapter == null) {
					adapter = new SoftManagerAdapter(context,userSoftInfos,systemSoftInfos);
					softLstView.setAdapter(adapter);
					//��ListView���м���
					listenLstView();
				}else {
					adapter.setData(userSoftInfos,systemSoftInfos);
					adapter.notifyDataSetChanged();
				}
				
			}
			
		}.execute();
	}
}
