package com.example.phonesafe1;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import com.example.phonesafe1.dao.BlackNumDao;
import com.example.phonesafe1.dao.SoftLockDao;
import com.example.phonesafe1.service.AddressService;
import com.example.phonesafe1.service.BlackNumService;
import com.example.phonesafe1.service.SoftLockService;
import com.example.phonesafe1.toast.MD5;
import com.example.phonesafe1.toast.ToastUtils;
import com.example.phonesafe1.utils.ActivityUtils;
import com.example.phonesafe1.utils.CacheUtils;
import com.example.phonesafe1.utils.ServiceUtils;
import com.example.phonesafe1.view.SettingStytleView;

public class SettingActivity extends Activity implements OnClickListener{
	Context context;
	private SettingView setView;
	private SettingView softView;
	private SettingView blackNumView;
	private SettingView addressView;
	private SettingStytleView mStytleView;
	private EditText mPwdEdit;
	private EditText mConfirmEdit;
	private Button mSetupButton;
	private Button mCancelButton;
	private AlertDialog.Builder mBuilder;
	private AlertDialog mAlertDialog;
	private EditText mAuthText;
	private SoftLockDao mSoftLockDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		context = this;
		//初始化设置的数据
		initData();
		//监听自动更新
		initUpdate();
		//监听号码归属地
		initAddress();
		//黑名单拦截
		BlackNum();
		//监听提示框风格
		initDialog();
		//监听软件锁
		initSoftLock();
	}
	
	//初始化设置的数据
	private void initData() {
				
	}
	//黑名单拦截
		private void BlackNum() {
			blackNumView = (SettingView)findViewById(R.id.blacknum_sv);
			if (ServiceUtils.isServiceRunning(context, "com.example.phonesafe1.service.BlackNumService")) {
				blackNumView.setChecked(true);
			}else {
				blackNumView.setChecked(false);
			}
			blackNumView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(context, BlackNumService.class);
					if (blackNumView.getChecked()) {
						blackNumView.setChecked(false);
						stopService(intent);
					}else {
						blackNumView.setChecked(true);
						startService(intent);
					}
					
					
					
				}
			});
		}
		//监听自动更新
		private void initUpdate() {
			setView = (SettingView)findViewById(R.id.update_sv);
			setView.getSettingFromUser(CacheUtils.UPDATE_APK);
			setView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (setView.getChecked()) {
						setView.setChecked(false);
						CacheUtils.putBoolean(CacheUtils.UPDATE_APK, false);
						
					}else {
						setView.setChecked(true);
						CacheUtils.putBoolean(CacheUtils.UPDATE_APK, true);
					}
				}
			});
		}
		//监听号码归属地
		private void initAddress() {
			addressView = (SettingView)findViewById(R.id.address_sv);
			addressView.setTitleText(addressView.settingTitle);
			addressView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent serviceIntent = new Intent(context, AddressService.class);
					if (addressView.getChecked()) {//停止服务
						addressView.setChecked(false);
						stopService(serviceIntent);
					}else {//开启服务
						addressView.setChecked(true);
						startService(serviceIntent);
					}
				}
			});
		}
		//监听提示框风格
		private void initDialog() {
			final String[] items = {"卫士蓝", "金属灰", "苹果绿", "活力橙", "半透明"};
			mStytleView = (SettingStytleView)findViewById(R.id.dialog_ssv);
			mStytleView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					new AlertDialog.Builder(context)
					.setTitle("号码归属地风格")
					.setIcon(R.drawable.main_icon)
					.setSingleChoiceItems(items, CacheUtils.getInt(CacheUtils.ADDRESS_STYTLE), new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							mStytleView.setDes(items[which]);
							CacheUtils.putInt(CacheUtils.ADDRESS_STYTLE, which);
						}
					}
					)
					.setNegativeButton("取消", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					})
					.show();
				}
			});
		}
		//监听软件锁
		private void initSoftLock() {
			mSoftLockDao = new SoftLockDao(context);
			softView = (SettingView)findViewById(R.id.softlock_sv);
			getSoftLockFromUser(CacheUtils.SOFTLOCK_PWD);
			
			softView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (softView.getChecked()) {
						softView.setChecked(false);
						CacheUtils.putString(CacheUtils.SOFTLOCK_PWD, "");
						List<String> allLockedApp = mSoftLockDao.getAllLockedApp();
						for (String packageName : allLockedApp) {
							mSoftLockDao.deleteLockedSoft(packageName);
						}
						Intent intent = new Intent(SettingActivity.this, SoftLockService.class);
						stopService(intent);
						
					}else {
						//显示设置密码对话框
						initDialog(R.layout.safe_setup_dialog,R.id.setup_btn);
//						mConfirmEdit = (EditText)setView.findViewById(R.id.confirm_pwd_et);
					}
				}
			});
		}
	//归属地显示与电话更新同步	
	@Override
	protected void onStart() {
		super.onStart();
		boolean isRunning = ServiceUtils.isServiceRunning(context,"com.example.phonesafe1.service.AddressService");
		if (isRunning) {
			addressView.setChecked(true);
		}else {
			addressView.setChecked(false);
		}
	}
	
	//初始化对话框视图
		private View initDialog(int layoutId,int buttonId) {
			mBuilder = new AlertDialog.Builder(context);
			View setupView = View.inflate(context, layoutId, null);
			mAuthText = (EditText)setupView.findViewById(R.id.pwd_et);
			mBuilder.setView(setupView);
			mBuilder.setCancelable(false);
			mSetupButton = (Button)setupView.findViewById(buttonId);
			mCancelButton = (Button)setupView.findViewById(R.id.cancel_btn);
			mConfirmEdit = (EditText)setupView.findViewById(R.id.confirm_pwd_et);
			mSetupButton.setOnClickListener(this);
			mCancelButton.setOnClickListener(this);
			mAlertDialog = mBuilder.create();
			mAlertDialog.show();
			return setupView;
		}
		
		@Override
		public void onClick(View v) {
			
			switch (v.getId()) {
			case R.id.setup_btn://对设置密码框的确定按钮做监听
				String pwd = mAuthText.getText().toString().trim();
				String repeatPwd = mConfirmEdit.getText().toString().trim();
				
				if (!pwd.equals(repeatPwd)) {
					ToastUtils.show("两次输入的密码不一致，请重新输入");
				}else if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(repeatPwd)) {
					ToastUtils.show("密码不能为空,请重新输入");
				}else {
					CacheUtils.putString(CacheUtils.SOFTLOCK_PWD, MD5.getMD5(pwd));
					softView.setChecked(true);
					Intent intent = new Intent(this, SoftLockService.class);
					startService(intent);
					ToastUtils.show("软件锁设置成功",1);
					mAlertDialog.dismiss();
					
				}
				
				break;
				
			case R.id.cancel_btn:
				mAlertDialog.dismiss();
				break;
				
			default:
				break;
			}
		}
		
		//获取用户软件锁设置信息
		public void getSoftLockFromUser(String key) {
			if (!TextUtils.isEmpty(CacheUtils.getString(key))){
				softView.getCheckBox().setChecked(true);
				softView.setDes(softView.desOn);
			}else {
				softView.getCheckBox().setChecked(false);
				softView.setDes(softView.desOff);
			}	
		}
}
