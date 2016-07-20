package com.example.phonesafe1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import com.example.phonesafe1.business.MessageEngine;
import com.example.phonesafe1.entity.SmsBody;
import com.example.phonesafe1.toast.ToastUtils;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class ShowSmsActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_sms);
		TextView smsText = (TextView)findViewById(R.id.sms_tv);
		FileInputStream in=null;
		try {
			in = openFileInput("smss.xml");
			String fullMessage = "";
			List<SmsBody> list = MessageEngine.getBackupSmss(in);
			for (SmsBody smsBody : list) {
				String address = smsBody.getAddress();
				String body = smsBody.getBody();
				String date = smsBody.getDate();
				String type = smsBody.getType();
				fullMessage = fullMessage +"来电号码： " + address + "消息： " + body +"日期： " + date + "类型: " + type+"                ";
			}
			smsText.setText(fullMessage);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}

}
