package com.example.phonesafe1;

import com.example.phonesafe1.toast.ToastUtils;
import com.example.phonesafe1.utils.CacheUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class WelcomesActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcomes);
		ImageView imageView = (ImageView) findViewById(R.id.welcome_iv);
		
		AnimationSet set = new AnimationSet(false);
		//设置旋转效果
		RotateAnimation rotateAnimation = new RotateAnimation(0, 360, 
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rotateAnimation.setFillAfter(true);
		rotateAnimation.setDuration(3000);
		set.addAnimation(rotateAnimation);
		
		//设置缩放效果
		ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 
				0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimation.setFillAfter(true);
		scaleAnimation.setDuration(3000);
		set.addAnimation(scaleAnimation);
		
		
		
				
		//设置透明度
		AlphaAnimation alphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(this, R.anim.alpha);
		set.addAnimation(alphaAnimation);
		
		//设置动画监听
		
		set.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				ToastUtils.show(getApplicationContext(),"欢迎进入登入界面");
				
				if(CacheUtils.getBoolean(CacheUtils.IS_FIRST_ENTER, true)) {
					Intent intent = new Intent(WelcomesActivity.this, GuideActivity.class);
					startActivity(intent);
					finish();
				}else {
					Intent intent = new Intent(WelcomesActivity.this, SplashActivity.class);
					startActivity(intent);
					finish();
				}
			}
		});
		//开启动画
		imageView.startAnimation(set);
		
	}

	

	
}
