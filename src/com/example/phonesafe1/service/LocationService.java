package com.example.phonesafe1.service;

import java.util.ArrayList;
import java.util.List;

import com.example.phonesafe1.business.ContactsManager;
import com.example.phonesafe1.entity.ContractsInfo;
import com.example.phonesafe1.utils.CacheUtils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

public class LocationService extends Service {
	private LocationListener listener = new LocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
		
		@Override
		public void onProviderEnabled(String provider) {
		}
		
		@Override
		public void onProviderDisabled(String provider) {
		}
		
		@Override
		public void onLocationChanged(Location location) {
			double latitude = location.getLatitude();
			double longitude = location.getLongitude();
			System.out.println("纬度" +latitude + "经度" + longitude);
			
			CacheUtils.putString(CacheUtils.LATITUDE, latitude + "");
			CacheUtils.putString(CacheUtils.LONGITUTDE, longitude + "");
		}
	};

	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		//获取所有的定位方式
		List<String> allProviders = locationManager.getAllProviders();
		for (String provider : allProviders) {
			System.out.println(provider);
		}
		
		Criteria criteria = new Criteria();
		criteria.setAltitudeRequired(true);
		
		String bestProvider = locationManager.getBestProvider(criteria, true);
		locationManager.requestLocationUpdates(bestProvider, 5000, 2, listener);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		
		super.onDestroy();
	}

}
