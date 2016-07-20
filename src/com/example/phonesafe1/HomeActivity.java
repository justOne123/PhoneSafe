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
		
		//有米广告sdk的初始化
		AdManager.getInstance(this).init("a1a5f9d248ce4bca","bcfc1d6bccca2b86", true);
		
		
		
		// 实例化广告条
		AdView adView = new AdView(this, AdSize.FIT_SCREEN);

		// 获取要嵌入广告条的布局
		LinearLayout adLayout=(LinearLayout)findViewById(R.id.adLayout);

		// 将广告条加入到布局中
		adLayout.addView(adView);
		
		
		
		//设置适配器
		GridView gridView = (GridView)findViewById(R.id.home_gv);
		gridView.setAdapter(new HomeAdapter());
		
		//对gridView设置监听
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				switch (position) {
				case 0://手机防盗
					if (CacheUtils.getString(CacheUtils.SETUP_PWD) != null) {
						initDialog(R.layout.safe_auth_dialog,R.id.ok_btn);
					}else {
						//显示设置密码对话框
						View setView = initDialog(R.layout.safe_setup_dialog,R.id.setup_btn);
						mConfirmEdit = (EditText)setView.findViewById(R.id.confirm_pwd_et);
					}
					
					break;
				case 1://通信卫士
					intent.setClass(context, CallAndSmsActivity.class);
					startActivity(intent);
					break;
				case 2://软件管理
					
					intent.setClass(context, SoftManagerActivity.class);
					startActivity(intent);
					break;
				case 3://进程管理
					intent.setClass(context, TaskActivity.class);
					startActivity(intent);
					break;
				case 4://流量统计
					
					break;
				case 5://手机杀毒
					
					break;
				case 6://缓存清理
					
					break;
				case 7://高级工具
					intent.setClass(context, AdvanceToolsActivity.class);
					startActivity(intent);
					break;
				case 8://设置中心
					intent.setClass(context,SettingActivity.class);
					startActivity(intent);
					break;

				default:
					break;
				}
			}
			//验证密码对话框
		});
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
				CacheUtils.putString(CacheUtils.SETUP_PWD, MD5.getMD5(pwd));
				ToastUtils.show("防火防盗防逊哥",1);
				mAlertDialog.dismiss();
				ActivityUtils.skipPager(context, LostFindActivity.class);
			}
			
			break;
		case R.id.ok_btn://对验证密码框的确定按钮做监听
			String password = mAuthText.getText().toString().trim();
			if ((MD5.getMD5(password)).equals(CacheUtils.getString(CacheUtils.SETUP_PWD))) {
				ToastUtils.show("防火防盗防逊哥",1);
				mAlertDialog.dismiss();
				if (CacheUtils.getBoolean(CacheUtils.COMPLETED_SETTING)) {
					ActivityUtils.skipPager(context, LostFindActivity.class);
				}else {
					ActivityUtils.skipPager(this,LostFindActivity.class);
				}

			}else {
				ToastUtils.show("密码错误,请重新输入");
			}
			
			break;
			
		case R.id.cancel_btn:
			mAlertDialog.dismiss();
			break;
			
		default:
			break;
		}
	}
	
	//对明密文切换监听
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
	
	/*//按返回键退出程序
	long startTime = 0;
	@Override
	public void onBackPressed() {
		if (System.currentTimeMillis() - startTime > 2000) {
			ToastUtils.show("再按一次退出逊哥反恐中心");
			startTime = System.currentTimeMillis();
		}else {
			super.onBackPressed();
		}
	}*/

}
