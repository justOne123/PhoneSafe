package com.example.phonesafe1.business;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import com.example.phonesafe1.entity.SmsBody;
import com.example.phonesafe1.toast.ToastUtils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Xml;
/**
 * 备份短信的业务类
 * 
 * @author Administrator
 *
 */
public class MessageEngine {
	

	//备份短信
	public static void backupSms(Context context,FileOutputStream fos) throws Exception{
		String uri = "content://sms";
		Cursor cursor = context.getContentResolver().query(Uri.parse(uri), new String[]{"address","date","type","body"},
				null, null, null);
		XmlSerializer serializer = Xml.newSerializer();
		serializer.setOutput(fos, "UTF-8");
		serializer.startDocument("UTF-8", true);
		serializer.startTag(null, "smss");
		while (cursor.moveToNext()) {
			String address = cursor.getString(cursor.getColumnIndex("address"))+"";
			String date = cursor.getString(cursor.getColumnIndex("date"))+"";
			String type = cursor.getString(cursor.getColumnIndex("type"))+"";
			String body = cursor.getString(cursor.getColumnIndex("body"))+"";
			serializer.startTag(null, "message");
			serializer.startTag(null, "address");
			serializer.text(address);
			serializer.endTag(null, "address");
			serializer.startTag(null, "date");
			serializer.text(date);
			serializer.endTag(null, "date");
			serializer.startTag(null, "type");
			serializer.text(type);
			serializer.endTag(null, "type");
			serializer.startTag(null, "body");
			serializer.text(body);
			serializer.endTag(null, "body");
			serializer.endTag(null, "message");
		}
		serializer.endTag(null, "smss");
		serializer.endDocument();
		cursor.close();
		fos.close();
	}
	
	//备份还原
	public static List<SmsBody> getBackupSmss(FileInputStream in) throws Exception{
		List<SmsBody> list = null;
		SmsBody smsBody = null;
		XmlPullParser pullParser = Xml.newPullParser();
		pullParser.setInput(in, "UTF-8");
		int eventType = pullParser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				list = new ArrayList<SmsBody>();
				break;
			case XmlPullParser.START_TAG:
				String tagName = pullParser.getName();
				if ("message".equals(tagName)) {
					smsBody = new SmsBody();
				}else if ("address".equals(tagName)) {
					smsBody.setAddress(pullParser.nextText());
				}else if ("date".equals(tagName)) {
					smsBody.setDate(pullParser.nextText());
				}else if ("type".equals(tagName)) {
					smsBody.setType(pullParser.nextText());
				}else if ("body".equals(tagName)){
					smsBody.setBody(pullParser.nextText());
				}
				
				break;
			case XmlPullParser.END_TAG:
				String name = pullParser.getName();
				if ("message".equals(name)) {
					list.add(smsBody);
				}
				break;
			default:
				break;
			}
			eventType = pullParser.next();
		}
		in.close();
		return list;
	}
	

}
