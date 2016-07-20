package com.example.phonesafe1.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.example.phonesafe1.toast.ToastUtils;
import com.example.phonesafe1.utils.CacheUtils;

public class BootCompletedReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		
		//获取用户存储的安全码
		String storageSafeNum = CacheUtils.getString(CacheUtils.SAFE_NUM);
		//获取用户存储的SIM卡序列号
		String storageSimSerialNumber = CacheUtils.getString(CacheUtils.BIND_SIM);
		TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String simSerialNumber = manager.getSimSerialNumber();
		if (!TextUtils.isEmpty(storageSafeNum) && !TextUtils.isEmpty(storageSafeNum)) {
			if (!simSerialNumber.equals(storageSimSerialNumber) && CacheUtils.getBoolean(CacheUtils.PROTECTED_SETTING) ) {
				SmsManager smsManager = SmsManager.getDefault();
				smsManager.sendTextMessage(storageSafeNum, null, "I'm lost ,help me!", null, null);
				ToastUtils.show("SIM卡序列号: " + simSerialNumber);
			}
		}
	}

}
