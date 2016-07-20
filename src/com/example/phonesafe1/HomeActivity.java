package com.example.phonesafe1;

import net.youmi.android.AdManager;
import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import com.example.phonesafe1.adapter.HomeAdapter;
import com.example.phonesafe1.toast.MD5;
import com.example.phonesafe1.toast.ToastUtils;
import com.example.phonesafe1.utils.ActivityUtils;
import com.example.phonesafe1.utils.CacheUtils;

public class HomeActivity extends Activity implements OnClickListener{
	Context context;
	private EditText mPwdEdit;
	private EditText mConfirmEdit;
	private Button mSetupButton;
	private Button mCancelButton;
	private AlertDialog.Builder mBuilder;
	private AlertDialog mAlertDialog;
	private View mAuthView;
	private EditText mAuthText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		context = this;
		
		//���׹��sdk�ĳ�ʼ��
		AdManager.getInstance(this).init("a1a5f9d248ce4bca","bcfc1d6bccca2b86", true);
		
		
		
		// ʵ���������
		AdView adView = new AdView(this, AdSize.FIT_SCREEN);

		// ��ȡҪǶ�������Ĳ���
		LinearLayout adLayout=(LinearLayout)findViewById(R.id.adLayout);

		// ����������뵽������
		adLayout.addView(adView);
		
		
		
		//����������
		GridView gridView = (GridView)findViewById(R.id.home_gv);
		gridView.setAdapter(new HomeAdapter());
		
		//��gridView���ü���
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				switch (position) {
				case 0://�ֻ�����
					if (CacheUtils.getString(CacheUtils.SETUP_PWD) != null) {
						initDialog(R.layout.safe_auth_dialog,R.id.ok_btn);
					}else {
						//��ʾ��������Ի���
						View setView = initDialog(R.layout.safe_setup_dialog,R.id.setup_btn);
						mConfirmEdit = (EditText)setView.findViewById(R.id.confirm_pwd_et);
					}
					
					break;
				case 1://ͨ����ʿ
					intent.setClass(context, CallAndSmsActivity.class);
					startActivity(intent);
					break;
				case 2://�������
					
					intent.setClass(context, SoftManagerActivity.class);
					startActivity(intent);
					break;
				case 3://���̹���
					intent.setClass(context, TaskActivity.class);
					startActivity(intent);
					break;
				case 4://����ͳ��
					
					break;
				case 5://�ֻ�ɱ��
					
					break;
				case 6://��������
					
					break;
				case 7://�߼�����
					intent.setClass(context, AdvanceToolsActivity.class);
					startActivity(intent);
					break;
				case 8://��������
					intent.setClass(context,SettingActivity.class);
					startActivity(intent);
					break;

				default:
					break;
				}
			}
			//��֤����Ի���
		});
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
				CacheUtils.putString(CacheUtils.SETUP_PWD, MD5.getMD5(pwd));
				ToastUtils.show("���������ѷ��",1);
				mAlertDialog.dismiss();
				ActivityUtils.skipPager(context, LostFindActivity.class);
			}
			
			break;
		case R.id.ok_btn://����֤������ȷ����ť������
			String password = mAuthText.getText().toString().trim();
			if ((MD5.getMD5(password)).equals(CacheUtils.getString(CacheUtils.SETUP_PWD))) {
				ToastUtils.show("���������ѷ��",1);
				mAlertDialog.dismiss();
				if (CacheUtils.getBoolean(CacheUtils.COMPLETED_SETTING)) {
					ActivityUtils.skipPager(context, LostFindActivity.class);
				}else {
					ActivityUtils.skipPager(this,LostFindActivity.class);
				}

			}else {
				ToastUtils.show("�������,����������");
			}
			
			break;
			
		case R.id.cancel_btn:
			mAlertDialog.dismiss();
			break;
			
		default:
			break;
		}
	}
	
	//���������л�����
	public void changePwd(View v) {
		if (mAuthText.getInputType() == 129) {
			mAuthText.setInputType(EditorInfo.TYPE_CLASS_TEXT);
		}else {
			mAuthText.setInputType(129);
		}
	}
	
	private void enterHome() {
		Intent intent = new Intent();
		intent.setClass(this, HomeActivity.class);
		startActivity(intent);
	}
	
	/*//�����ؼ��˳�����
	long startTime = 0;
	@Override
	public void onBackPressed() {
		if (System.currentTimeMillis() - startTime > 2000) {
			ToastUtils.show("�ٰ�һ���˳�ѷ�練������");
			startTime = System.currentTimeMillis();
		}else {
			super.onBackPressed();
		}
	}*/

}
