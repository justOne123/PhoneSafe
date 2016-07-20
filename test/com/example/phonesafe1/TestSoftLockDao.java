package com.example.phonesafe1;

import com.example.phonesafe1.dao.SoftLockDao;
import com.example.phonesafe1.db.SoftLockOpenHelper;

import android.content.Context;
import android.test.AndroidTestCase;

public class TestSoftLockDao extends AndroidTestCase {
	Context context;
	private SoftLockOpenHelper helper;
	private SoftLockDao softLockDao;
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		context = getContext();
		helper = new SoftLockOpenHelper(context);
		softLockDao = new SoftLockDao(context);
	}
	
	public void testAddLockedSoft() {
		softLockDao.addLockedSoft("hello");
	}
	
	public void delete() {
		softLockDao.deleteLockedSoft("hello");
	}
	
	public void query() {
		softLockDao.queryLockedSoft("hello");
	}

}
