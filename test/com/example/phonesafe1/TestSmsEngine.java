package com.example.phonesafe1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.example.phonesafe1.business.MessageEngine;
import com.example.phonesafe1.entity.SmsBody;
import com.example.phonesafe1.toast.ToastUtils;

import android.content.Context;
import android.test.AndroidTestCase;

public class TestSmsEngine extends AndroidTestCase {
	

	public void testBackupSms() {
		/*FileOutputStream fos =null;
		try {
			fos = getContext().openFileOutput("smss.xml",Context.MODE_PRIVATE);
			MessageEngine.backupSms(getContext(),fos);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		*/
		FileInputStream in=null;
		try {
			in = getContext().openFileInput("smss.xml");
			String fullMessage = "";
			List<SmsBody> list = MessageEngine.getBackupSmss(in);
			for (SmsBody smsBody : list) {
				String address = smsBody.getAddress();
				String body = smsBody.getBody();
				String date = smsBody.getDate();
				String type = smsBody.getType();
				fullMessage = fullMessage +"来电号码： " + address + "消息： " + body +"日期： " + date + "类型: " + type+"/n";
				ToastUtils.show("显示成功显示成功", 1);
			}
			System.out.println(fullMessage);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
}
