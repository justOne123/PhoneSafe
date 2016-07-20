package com.example.phonesafe1;

import com.example.phonesafe1.utils.CacheUtils;
import com.example.phonesafe1.utils.ServiceUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class SettingView extends RelativeLayout {
	private  TextView titleView;
	private TextView desView;
	private  CheckBox checkBox;
	public  String settingTitle;
	public  String desOn;
	public String desOff;

	public SettingView(Context context) {
		super(context);
		init();
	}

	public SettingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		//获取属性值
		String itcast="http://schemas.android.com/apk/res/com.example.phonesafe1";
		settingTitle = attrs.getAttributeValue(itcast, "settingTitle");
		desOn = attrs.getAttributeValue(itcast, "des_on");
		desOff = attrs.getAttributeValue(itcast, "des_off");
		titleView.setText(settingTitle);
	}

	public SettingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
//初始化视图，挂载到自定义空间上
	private void init() {
		View.inflate(getContext(), R.layout.setting_view, this);
		titleView = (TextView)findViewById(R.id.title_tv);
		desView = (TextView)findViewById(R.id.des_tv);
		checkBox = (CheckBox)findViewById(R.id.checkbox);
		setChecked(false);
	}
	
	//获取用户用SharedPreference存储的数据
	public void getSettingFromUser(String pref) {
		if (CacheUtils.getBoolean(pref)) {
			getCheckBox().setChecked(true);
			setDes(desOn);
		}else {
			getCheckBox().setChecked(false);
			setDes(desOff);
		}	
	}
		
	//获取用户SIM卡绑定信息
	public void getSIMFromUser(String key) {
			if (CacheUtils.getString(key) != null){
				getCheckBox().setChecked(true);
				setDes(desOn);
			}else {
				getCheckBox().setChecked(false);
				setDes(desOff);
			}	
		}
	public void setChecked(boolean isChecked) {
		checkBox.setChecked(isChecked);
		if (isChecked) {
			desView.setText(desOn);
		}else {
			desView.setText(desOff);
		}
	}
	
	public boolean getChecked() {
		return checkBox.isChecked();
	}
	//设置标题
	
	public void setTitleText(String msg1) {
		titleView.setText(msg1);
	}
	
	//设置描述信息
	public void setDes(String msg2) {
		desView.setText(msg2);
	}
	
	
	//获取复选按钮控件
	public CheckBox getCheckBox() {
		return checkBox;
	}
	

}
