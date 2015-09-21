package com.example.business.mybusiness.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.example.business.mybusiness.utils.LogUtil;

public class ServiceThread extends Thread {

	public ServiceThread() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		ServerSocket serverSocket = null;
		Socket socket;
		try {
			serverSocket = new ServerSocket(5389);
			LogUtil.log("start server");
			while (true) {
				socket = serverSocket.accept();
				new ClientThread(socket).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

}
