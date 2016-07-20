package com.example.phonesafe1.adapter;

import java.util.List;

import com.example.phonesafe1.R;
import com.example.phonesafe1.entity.ContractsInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ContactsAdapter extends BaseAdapter{
	Context context;
	private List<ContractsInfo> contractsInfos;

	public ContactsAdapter(Context context,List<ContractsInfo> contractsInfos) {
		super();
		this.context= context;
		this.contractsInfos = contractsInfos;
	}

	@Override
	public int getCount() {

		return contractsInfos == null ? 0 : contractsInfos.size();
	}

	@Override
	public Object getItem(int position) {

		return contractsInfos == null ? null : contractsInfos.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.contact_item, parent, false);
		}
			ContractsInfo info = contractsInfos.get(position);
			String name = info.getName();
			String num = info.getNum();
			TextView nameTv = (TextView)convertView.findViewById(R.id.name_tv);
			TextView numTv = (TextView)convertView.findViewById(R.id.num_tv);
			nameTv.setText(name);
			numTv.setText(num);

		return convertView;
	}

}
