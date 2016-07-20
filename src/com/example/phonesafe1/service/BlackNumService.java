package com.example.phonesafe1.service;

import java.lang.reflect.Method;

import com.android.internal.telephony.ITelephony;
import com.example.phonesafe1.dao.BlackNumDao;
import com.example.phonesafe1.toast.ToastUtils;
import com.example.phonesafe1.utils.Constant;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;

public class BlackNumService extends Service {
	private BlackNumDao mBlackNumDao;
	private TelephonyManager manager;
	
	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Object[] objs = (Object[])intent.getExtras().get("pdus");
			String fullMessage = "";
			String num = null;
			for (Object object : objs) {
				byte[] pdu = (byte[])object;
				SmsMessage message = SmsMessage.createFromPdu(pdu);
				if (num == null) {
					num = message.getOriginatingAddress();
				}
				String content = message.getMessageBody();
				fullMessage = fullMessage + content;
			}
			String newNum = num;
			if (num.startsWith("+86")) {
				newNum = num.substring(3);
			}
			int mode = mBlackNumDao.queryMode(newNum);
			if (mode == Constant.BLACK_NUM_ALL || mode == Constant.BLACK_NUM_SMS || fullMessage.contains("特大喜讯")) {
				ToastUtils.show("电话号码："+newNum + "拦截模式" + mode);
				abortBroadcast();
			}
			
			
		}
	};
	//监听电话状态变化
	private PhoneStateListener listener = new PhoneStateListener(){

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING://响铃状态监听
				int mode = mBlackNumDao.queryMode(incomingNumber);
				if (mode == Constant.BLACK_NUM_ALL || mode == Constant.BLACK_NUM_CALL) {
//					ToastUtils.show("电话号码："+incomingNumber + "拦截模式电话拦截" + mode);
					endCalls();//挂断电话
					deleteCallLogs(incomingNumber);//清除通话记录
				}
				break;

			default:
				break;
			}
		}

	};

	
	//清除通话记录
	private void deleteCallLogs(final String incomingNumber) {
		//通过内容观察者删除通话记录
		final ContentResolver contentResolver = getContentResolver();
		final String uri="content://call_log/calls";
		contentResolver.registerContentObserver(Uri.parse(uri), true, new ContentObserver(new Handler()){
			@Override
			public void onChange(boolean selfChange) {
				contentResolver.delete(Uri.parse(uri), "number=?", new String[]{incomingNumber});
			}
			
		});
	}
	//挂断电话
	private void endCalls() {
		try {
			Class<?> clazz = getClassLoader().loadClass("android.os.ServiceManager");
			Method method = clazz.getDeclaredMethod("getService", String.class);
			IBinder iBinder = (IBinder)method.invoke(null, Context.TELEPHONY_SERVICE);
			ITelephony.Stub.asInterface(iBinder).endCall();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}
	
	@Override
	public void onCreate() {
		mBlackNumDao = new BlackNumDao(this);
		//动态注册广播拦截黑名单短信
		IntentFilter filter = new IntentFilter();
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		filter.addAction("android.provider.Telephony.SMS_RECEIVED");
		registerReceiver(receiver, filter);
		//拦截电话
		abortCall();
		
	}
	//拦截电话
	private void abortCall() {
		manager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		manager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
	}

	@Override
	public void onDestroy() {
		unregisterReceiver(receiver);
		manager.listen(listener, PhoneStateListener.LISTEN_NONE);
	}

}
