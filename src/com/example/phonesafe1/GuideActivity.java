package com.example.phonesafe1;

import java.util.ArrayList;
import java.util.List;

import com.example.phonesafe1.utils.CacheUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

public class GuideActivity extends Activity {
	Context context;
	private List<ImageView> mPagerList;
	private ViewPager mViewPager;
	private Button mPagerButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_guide);
		context = this;
		mPagerButton = (Button) findViewById(R.id.guide_btn);
		mViewPager = (ViewPager) findViewById(R.id.guide_vp);
		//初始化数据
		initViewPager();
		//装配数据
		mViewPager.setAdapter(new PagerAdapter() {
			
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				
				return arg0 == arg1;
			}
			
			@Override
			public int getCount() {
				
				return mPagerList == null ? 0 : mPagerList.size();
			}

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView(mPagerList.get(position));
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				container.addView(mPagerList.get(position));
				
				return mPagerList.get(position);
			}
		});
		
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			//在最后一页显示按钮
			@Override
			public void onPageSelected(int position) {
				if(position == mPagerList.size() - 1) {
					mPagerButton.setVisibility(View.VISIBLE);
				}else {
					mPagerButton.setVisibility(View.GONE);
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}
	private void initViewPager() {
		mPagerList = new ArrayList<ImageView>();
		
		ImageView imageView1 = new ImageView(context);
		imageView1.setBackgroundResource(R.drawable.guide_1);
		mPagerList.add(imageView1);
		
		ImageView imageView2= new ImageView(context);
		imageView2.setBackgroundResource(R.drawable.guide_2);
		mPagerList.add(imageView2);
		
		ImageView imageView3 = new ImageView(context);
		imageView3.setBackgroundResource(R.drawable.guide_3);
		mPagerList.add(imageView3);
	}
	
	public void enterSystem(View v) {
		CacheUtils.putBoolean("is_first_enter", false);
		Intent intent = new Intent(context, SplashActivity.class);
		startActivity(intent);
		finish();
	}

}
