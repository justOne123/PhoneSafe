package com.example.phonesafe1;

import java.util.List;

import com.example.phonesafe1.business.ContactsManager;
import com.example.phonesafe1.entity.ContractsInfo;

import android.test.AndroidTestCase;

public class TestContactsManager extends AndroidTestCase{
	
	public void testGetAllContractsInfo() {
		List<ContractsInfo> list = ContactsManager.getAllContractsInfo(getContext());
		for (ContractsInfo contractsInfo : list) {
			System.out.println(contractsInfo.toString());
		}
	}
}
