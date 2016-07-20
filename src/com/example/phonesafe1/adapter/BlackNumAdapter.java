package com.example.phonesafe1.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.phonesafe1.R;
import com.example.phonesafe1.dao.BlackNumDao;
import com.example.phonesafe1.entity.BlackNumInfo;

/**
 * 黑名单适配器
 * 
 * @author Administrator
 *
 */
public class BlackNumAdapter extends BaseAdapter implements ListAdapter {
	Context context;
	private List<BlackNumInfo> mData;
	private BlackNumDao mBlackNumDao;

	public BlackNumAdapter(Context context, List<BlackNumInfo> mData) {
		this.context = context;
		this.mData = mData;
		mBlackNumDao = new BlackNumDao(context);
	}
	public void setData( List<BlackNumInfo> mData){
		this.mData = mData;
	}

	@Override
	public int getCount() {

		return mData == null ? 0 : mData.size();
	}

	@Override
	public Object getItem(int position) {

		return mData == null ? null : mData.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final BlackNumInfo blackNumInfo = mData.get(position);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.black_num_item, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.numTv = (TextView)convertView.findViewById(R.id.num_tv);
			viewHolder.modeTv = (TextView)convertView.findViewById(R.id.mode_tv);
			viewHolder.deleteImageView = (ImageView)convertView.findViewById(R.id.delete_iv);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		viewHolder.numTv.setText(blackNumInfo.getNum());
		int mode = blackNumInfo.getMode();
		String modeInfo=null;
		switch (mode) {
		case BlackNumDao.CALL:
			modeInfo = "电话拦截";
			break;
		case BlackNumDao.SMS:
			modeInfo = "短信拦截";
			break;
		case BlackNumDao.ALL:
			modeInfo = "拦截全部";
			break;

		default:
			break;
		}
		viewHolder.modeTv.setText(modeInfo);
		viewHolder.deleteImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mBlackNumDao.delete(blackNumInfo.getNum());
				mData.remove(blackNumInfo);
				notifyDataSetChanged();
			}
		});
		return convertView;
	}
	
	private static class ViewHolder{
		TextView numTv;
		TextView modeTv;
		ImageView deleteImageView;
		
	}

}
