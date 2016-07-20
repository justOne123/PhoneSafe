package com.example.phonesafe1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.phonesafe1.service.SoftLockService;
import com.example.phonesafe1.toast.ToastUtils;
import com.example.phonesafe1.utils.CacheUtils;
import com.example.phonesafe1.utils.Constant;
import com.example.phonesafe1.utils.ServiceUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class SplashActivity extends Activity {
	Context context;
	private TextView mVersionText;
	private ProgressBar mProgressBar;
	private TextView mProgressView;
	private int newVersion;
	private String des;
	private String apkUrl;
	
	private long time ;
	private static final int UPDATE_NORMAL = 1;
	private static final int UPDATE_EXCEPTION = -2;
	private static final int UPDATE_ERROR = -1;
	
	private  Handler handler = new Handler(){
		public void handleMessage(Message msg) {
				switch (msg.what) {
				case UPDATE_NORMAL:
					if (newVersion > getVersionCode()) {
						showUpdateDialog();
					}else {
						enterHome();
					}
					
					break;
				case UPDATE_EXCEPTION:
					ToastUtils.show("网络异常");
					enterHome();
					break;
				case UPDATE_ERROR:
					ToastUtils.show("从服务端获取数据失败...");
					enterHome();
					break;

				default:
					break;
				
			}
		}

	};
	
	private void showUpdateDialog() {
		new AlertDialog.Builder(context)
		.setTitle("有新版本要更新: " + newVersion)
		.setMessage(des)
		.setIcon(R.drawable.ic_launcher)
		.setPositiveButton("更新", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ToastUtils.show("下载新版本...");
				loadApk();
			}
		})
		.setNegativeButton("取消", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				enterHome();
			}
		})
		.create()
		.show();
	}
	
	
	protected void loadApk() {
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.download(apkUrl, getExternalCacheDir().getAbsolutePath() + "/safe1.apk", new RequestCallBack<File>() {
			//访问成功
			@Override
			public void onSuccess(ResponseInfo<File> responseInfo) {
				installApk();
			}

			//访问失败
			@Override
			public void onFailure(HttpException error, String msg) {
				ToastUtils.show("有异常，进入主界面");
				enterHome();
			}

			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				mProgressBar.setVisibility(View.VISIBLE);
				mProgressBar.setMax((int)total);
				mProgressBar.setProgress((int)current);
				float currentF = (float)current;
				float progress = (float)((currentF/total)*100);
				mProgressView.setText(progress + "%");
			}
		});
	};
	
	//安装下载后的APK
	private void installApk() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.setDataAndType(Uri.fromFile(new File(getExternalCacheDir(), "safe1.apk")),
				"application/vnd.android.package-archive");
		
		startActivityForResult(intent, 1);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 1:
			if (resultCode == RESULT_OK) {
				enterHome();
			}
			break;

		default:
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		//初始化视图
		initView();
		//检查版本更新
		if(CacheUtils.getBoolean(CacheUtils.UPDATE_APK, true)) {
			checkUpdate();
		}else {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					SystemClock.sleep(2000);
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							long duringTime = System.currentTimeMillis() - time;
							if(duringTime < 2500) {
								SystemClock.sleep(2500 - duringTime);
							}	
							enterHome();
						}
					});
					
				}
			}).start();
		}
		copyDB();
	}
	//从资产目录中拷贝数据库到手机内存
	private void copyDB() {
		AssetManager assetManager = getAssets();
		try {
			File file = new File(getFilesDir(), "address.db");
			if (!file.exists()) {
				InputStream in = assetManager.open("address.db");
				FileOutputStream fos = new FileOutputStream(file);
				byte[] bytes = new byte[8096];
				int len = 0;
				while((len = in.read(bytes)) != -1) {
					fos.write(bytes, 0, len);
				}
				
				in.close();
				fos.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void initView() {
		context = this;
		mVersionText = (TextView) findViewById(R.id.version_tv);
		mProgressBar = (ProgressBar) findViewById(R.id.download_apk_pb);
		mProgressView = (TextView)findViewById(R.id.progress_tv);
		mVersionText.setText("version:" + getVersionCode());
		
		}
	
	private int getVersionCode() {
		try {
			PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
			return info.versionCode;
		} catch (NameNotFoundException e) {
			
			e.printStackTrace();
		}
			
			return 0;
		}

//检查版本更新
	private void checkUpdate() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				Message message = new Message();
				time =System.currentTimeMillis();
				try {
					URL newUrl = new URL(Constant.VERSION_UPDATE_URL);
					HttpURLConnection conn = (HttpURLConnection)newUrl.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(5000);
					int resultCode = conn.getResponseCode();
					if (resultCode == HttpURLConnection.HTTP_OK) { 
						InputStream in = conn.getInputStream();
						BufferedReader bReader = new BufferedReader(new InputStreamReader(in));
						String urlJson = bReader.readLine();
						JSONObject jsonObject = new JSONObject(urlJson);
						newVersion = jsonObject.getInt("code");
						apkUrl = jsonObject.getString("apkurl");
						des = jsonObject.getString("des");
						message.what = UPDATE_NORMAL;
						conn.disconnect();
					}else {
						message.what = UPDATE_EXCEPTION;
					}
				}catch (Exception e) {
					message.what = UPDATE_ERROR;
					e.printStackTrace();
				}finally{
					handler.sendMessage(message);
					
					long duringTime = System.currentTimeMillis() - time;
					if(duringTime < 2000) {
						SystemClock.sleep(2000 - duringTime);
					}	
				}
			}

			}).start();
		
	}
	
	
	private void enterHome() {
		if (!TextUtils.isEmpty(CacheUtils.getString(CacheUtils.SOFTLOCK_PWD))) {
			if (!ServiceUtils.isServiceRunning(this, "com.example.phonesafe1.service.SoftLockService")) {
				Intent intent = new Intent(this, SoftLockService.class);
				startService(intent);
			}
		}
		Intent intent = new Intent(context, HomeActivity.class);
		startActivity(intent);
		finish();
	}

	
}
