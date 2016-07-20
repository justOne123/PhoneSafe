package com.example.phonesafe1;

import com.example.phonesafe1.dao.AddressDao;
import com.example.phonesafe1.toast.ToastUtils;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddressActivity extends Activity {
	Context context;
	private String mLocation;
	private EditText numEt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address);
		context = this;
		Button queryButton = (Button)findViewById(R.id.query_btn);
		numEt = (EditText)findViewById(R.id.num_et);
		final TextView locationTv = (TextView)findViewById(R.id.location_tv);
		
		queryButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String num = numEt.getText().toString().trim();
				if (TextUtils.isEmpty(num)) {
					Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake);
					numEt.startAnimation(shake);
					ToastUtils.show("号码为空，请重新输入");
				}else {
					mLocation = AddressDao.qureyAddress(context, num);
					locationTv.setText(mLocation);
				}
				
				//设置震动
				Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
				vibrator.vibrate(new long[]{500,300,200,100}, -1);
			}
		});
		
		numEt.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String num = s.toString();
				mLocation = AddressDao.qureyAddress(context, num);
				locationTv.setText(mLocation);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		
	}

}
