package com.example.phonesafe1.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.phonesafe1.R;
import com.example.phonesafe1.page.BasePage;
import com.example.phonesafe1.page.Setup1Page;
import com.example.phonesafe1.page.Setup2Page;
import com.example.phonesafe1.page.Setup3Page;
import com.example.phonesafe1.page.Setup4Page;
import com.example.phonesafe1.toast.ToastUtils;
import com.example.phonesafe1.utils.CacheUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class ProtectedSetupFragment extends Fragment{
	Context context;
	@ViewInject(R.id.safe_vp)
	private ViewPager mViewPager;
	@ViewInject(R.id.dot_lly)
	private LinearLayout layout;
	private List<BasePage> pageList;
	private int prePosition;
	private String[] titles = {"1 欢迎使用手机防盗","2 手机卡绑定","3 设置安全号码","4 设置完成"};
	private View rootView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_protected_setup, container, false);
		ViewUtils.inject(this,rootView);
		context = getActivity();
		setTitle(titles[0]);
		
		
		
		//初始化ViewPager
		init();
		mViewPager.setAdapter(new PagerAdapter() {
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				
				return arg0 == arg1;
			}
			
			@Override
			public int getCount() {
				
				return pageList.size();
			}
			
			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				
				container.removeView((View)object);
				
			}
			
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				container.addView(pageList.get(position).getRootView());
				
				
				return pageList.get(position).getRootView();
			}
		});
		
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				setTitle(titles[position]);
//				 LinearLayout layout = (LinearLayout)rootView.findViewById(R.id.dot_lly);
				 TextView dotView = (TextView)layout.getChildAt(position);
				 dotView.setEnabled(false);
				 TextView preDotView = (TextView)layout.getChildAt(prePosition);
				 preDotView.setEnabled(true);
				 prePosition = position;
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		return rootView;
	}
	
	//设置标题
		public void setTitle(String title) {
			TextView titleTv = (TextView)rootView.findViewById(R.id.title_bar);
			titleTv.setText(title);
		}
	
	private void init() {
		pageList = new ArrayList<BasePage>();
		BasePage setup1Page = new Setup1Page(context);
		pageList.add(setup1Page);
		
		BasePage setup2Page = new Setup2Page(context);
		pageList.add(setup2Page);
		
		BasePage setup3Page = new Setup3Page(context);
		pageList.add(setup3Page);
		
		BasePage setup4Page = new Setup4Page(context);
		pageList.add(setup4Page);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onActivityCreated(savedInstanceState);
	}
}
