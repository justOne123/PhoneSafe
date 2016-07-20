package com.example.phonesafe1;

import java.util.List;

import com.example.phonesafe1.business.TaskManager;
import com.example.phonesafe1.entity.TaskInfo;

import android.test.AndroidTestCase;

public class TestTaskManager extends AndroidTestCase {
	
	public void testGetAllProcessInfos() {
		TaskManager taskManager = new TaskManager();
		List<TaskInfo> allProcessInfos = taskManager.getAllProcessInfos();
		for (TaskInfo taskInfo : allProcessInfos) {
			System.out.println(taskInfo.getLabel());
		}
	}

}
