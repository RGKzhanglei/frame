package com.example.business.mybusiness;

import com.example.business.mybusiness.database.DBHelper;
import com.example.business.mybusiness.service.ServiceThread;

public class MainService {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		ServiceThread serviceThread = new ServiceThread();
//		serviceThread.start();
		DBHelper dbHelper = DBHelper.getInstance();
	}

}
