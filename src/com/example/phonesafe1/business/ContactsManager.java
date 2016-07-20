package com.example.phonesafe1.business;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.phonesafe1.entity.ContractsInfo;

/**
 * 
 * @author Administrator
 *
 */
public class ContactsManager {
	//获取所有手机联系人信息
	public static List<ContractsInfo> getAllContractsInfo(Context context) {
		/**
		 * 访问raw_contacts表的uri： content://com.android.contacts/raw_contacts
		 * 访问view_data表的uri：content://com.android.contacts/data
		 */
		List<ContractsInfo> list = new ArrayList<ContractsInfo>();
		String uri = "content://com.android.contacts/raw_contacts";
		Cursor cursorId = context.getContentResolver().query(Uri.parse(uri), new String[]{"contact_id"}, null, null, null);
		while (cursorId.moveToNext()) {
			String contactId = cursorId.getString(0);
			if (contactId != null) {
				String dataUri = "content://com.android.contacts/data";
				Cursor dataQuery = context.getContentResolver().query(Uri.parse(dataUri), 
						new String[]{"mimetype","data1"}, "contact_id=?", new String[]{contactId}, null);
				ContractsInfo contractsInfo = new ContractsInfo();
				while(dataQuery.moveToNext()) {
					String mimeType = dataQuery.getString(dataQuery.getColumnIndex("mimetype"));
					String data1 = dataQuery.getString(dataQuery.getColumnIndex("data1"));
					if ("vnd.android.cursor.item/name".equals(mimeType)) {
						contractsInfo.setName(data1);
					}else if("vnd.android.cursor.item/phone_v2".equals(mimeType)) {
						contractsInfo.setNum(data1);
					}
				}
				dataQuery.close();
				list.add(contractsInfo);
			}
		}
		cursorId.close();
		return list;
	}

}
