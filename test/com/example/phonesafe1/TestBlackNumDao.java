package com.example.phonesafe1;

import java.util.Random;

import com.example.phonesafe1.dao.BlackNumDao;

import android.content.Context;
import android.test.AndroidTestCase;

public class TestBlackNumDao extends AndroidTestCase {
	Context context;
	private BlackNumDao blackNumDao;
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		context = getContext();
		blackNumDao = new BlackNumDao(context);
	}
	
	public void testAdd(){
		Random r = new Random();
		for (int i = 1; i < 40; i++) {
			blackNumDao.add("15298572036" + i*2, r.nextInt(3)+1);
		}
	}
}
