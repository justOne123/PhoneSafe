package com.example.phonesafe1;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.phonesafe1.adapter.ContactsAdapter;
import com.example.phonesafe1.business.ContactsManager;
import com.example.phonesafe1.entity.ContractsInfo;
import com.example.phonesafe1.utils.CacheUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class ContactsActivity extends Activity{
	@ViewInject(R.id.contact_lv)
	private ListView contactLv;
	private List<ContractsInfo> allContractsInfo;
	Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);
		ViewUtils.inject(this);
		context = this;
		allContractsInfo = ContactsManager.getAllContractsInfo(this);
		//设置适配器
		contactLv.setAdapter(new ContactsAdapter(this,allContractsInfo));
		//对列表项进行监听
		contactLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ContractsInfo contractsInfo = allContractsInfo.get(position);
				String num = contractsInfo.getNum();
				Intent intent = getIntent();
				intent.putExtra("num", num);
				setResult(RESULT_OK,intent);
				CacheUtils.putString(CacheUtils.SAFE_NUM, num);
				finish();
			}
		});
	}

}
