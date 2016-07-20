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
			if (mode == Constant.BLACK_NUM_ALL || mode == Constant.BLACK_NUM_SMS || fullMessage.contains("�ش�ϲѶ")) {
				ToastUtils.show("�绰���룺"+newNum + "����ģʽ" + mode);
				abortBroadcast();
			}
			
			
		}
	};
	//�����绰״̬�仯
	private PhoneStateListener listener = new PhoneStateListener(){

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING://����״̬����
				int mode = mBlackNumDao.queryMode(incomingNumber);
				if (mode == Constant.BLACK_NUM_ALL || mode == Constant.BLACK_NUM_CALL) {
//					ToastUtils.show("�绰���룺"+incomingNumber + "����ģʽ�绰����" + mode);
					endCalls();//�Ҷϵ绰
					deleteCallLogs(incomingNumber);//���ͨ����¼
				}
				break;

			default:
				break;
			}
		}

	};

	
	//���ͨ����¼
	private void deleteCallLogs(final String incomingNumber) {
		//ͨ�����ݹ۲���ɾ��ͨ����¼
		final ContentResolver contentResolver = getContentResolver();
		final String uri="content://call_log/calls";
		contentResolver.registerContentObserver(Uri.parse(uri), true, new ContentObserver(new Handler()){
			@Override
			public void onChange(boolean selfChange) {
				contentResolver.delete(Uri.parse(uri), "number=?", new String[]{incomingNumber});
			}
			
		});
	}
	//�Ҷϵ绰
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
		//��̬ע��㲥���غ���������
		IntentFilter filter = new IntentFilter();
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		filter.addAction("android.provider.Telephony.SMS_RECEIVED");
		registerReceiver(receiver, filter);
		//���ص绰
		abortCall();
		
	}
	//���ص绰
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
