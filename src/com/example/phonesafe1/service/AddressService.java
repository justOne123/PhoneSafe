package com.example.phonesafe1.service;

import com.example.phonesafe1.R;
import com.example.phonesafe1.dao.AddressDao;
import com.example.phonesafe1.toast.ToastUtils;
import com.example.phonesafe1.utils.CacheUtils;

import android.R.integer;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AddressService extends Service{
	Context context;
	private MyPhoneStateListener myPhoneStateListener;
	private TelephonyManager manager;

	@Override
	public IBinder onBind(Intent intent) {
		
		return null;
	}
	
	@Override
	public void onCreate() {
		manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		context = getApplicationContext();
		myPhoneStateListener = new MyPhoneStateListener();
		manager.listen(myPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		manager.listen(myPhoneStateListener, PhoneStateListener.LISTEN_NONE);
	}
	
	private class MyPhoneStateListener extends PhoneStateListener{
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {
			case TelephonyManager.CALL_STATE_IDLE://ø’œ–◊¥Ã¨
				
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK://Ω”Ã˝◊¥Ã¨
				
				break;
			case TelephonyManager.CALL_STATE_RINGING://œÏ¡Â◊¥Ã¨
				String location = AddressDao.qureyAddress(getApplicationContext(), incomingNumber);
				showLocation(location);
				break;

			default:
				break;
			}
		}
		//œ‘ æπÈ ÙµÿŒª÷√
		private void showLocation(String location) {
			int[] styles = {R.drawable.call_locate_blue,R.drawable.call_locate_gray,R.drawable.call_locate_green,
					R.drawable.call_locate_orange,R.drawable.call_locate_white};
			Toast toast = new Toast(context);
			View view = View.inflate(context, R.layout.location_toast, null);
			view.setBackgroundResource(styles[CacheUtils.getInt(CacheUtils.ADDRESS_STYTLE)]);
			TextView address = (TextView)view.findViewById(R.id.location_tv);
			address.setText(location);
			toast.setView(view);
			toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 50);
			toast.setDuration(Toast.LENGTH_LONG);
			toast.show();
		}
		
	}

}
