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
		//��ʼ�����õ�����
		initData();
		//�����Զ�����
		initUpdate();
		//�������������
		initAddress();
		//����������
		BlackNum();
		//������ʾ����
		initDialog();
		//���������
		initSoftLock();
	}
	
	//��ʼ�����õ�����
	private void initData() {
				
	}
	//����������
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
		//�����Զ�����
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
		//�������������
		private void initAddress() {
			addressView = (SettingView)findViewById(R.id.address_sv);
			addressView.setTitleText(addressView.settingTitle);
			addressView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent serviceIntent = new Intent(context, AddressService.class);
					if (addressView.getChecked()) {//ֹͣ����
						addressView.setChecked(false);
						stopService(serviceIntent);
					}else {//��������
						addressView.setChecked(true);
						startService(serviceIntent);
					}
				}
			});
		}
		//������ʾ����
		private void initDialog() {
			final String[] items = {"��ʿ��", "������", "ƻ����", "������", "��͸��"};
			mStytleView = (SettingStytleView)findViewById(R.id.dialog_ssv);
			mStytleView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					new AlertDialog.Builder(context)
					.setTitle("��������ط��")
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
					.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					})
					.show();
				}
			});
		}
		//���������
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
						//��ʾ��������Ի���
						initDialog(R.layout.safe_setup_dialog,R.id.setup_btn);
//						mConfirmEdit = (EditText)setView.findViewById(R.id.confirm_pwd_et);
					}
				}
			});
		}
	//��������ʾ��绰����ͬ��	
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
	
	//��ʼ���Ի�����ͼ
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
			case R.id.setup_btn://������������ȷ����ť������
				String pwd = mAuthText.getText().toString().trim();
				String repeatPwd = mConfirmEdit.getText().toString().trim();
				
				if (!pwd.equals(repeatPwd)) {
					ToastUtils.show("������������벻һ�£�����������");
				}else if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(repeatPwd)) {
					ToastUtils.show("���벻��Ϊ��,����������");
				}else {
					CacheUtils.putString(CacheUtils.SOFTLOCK_PWD, MD5.getMD5(pwd));
					softView.setChecked(true);
					Intent intent = new Intent(this, SoftLockService.class);
					startService(intent);
					ToastUtils.show("��������óɹ�",1);
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
		
		//��ȡ�û������������Ϣ
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
