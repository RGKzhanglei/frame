package com.example.business.mybusiness;

import java.io.IOException;

import javax.activation.CommandObject;
import javax.activation.DataHandler;

import com.example.business.mybusiness.service.ServiceThread;

public class MainService {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServiceThread serviceThread = new ServiceThread();
		serviceThread.start();
		
		CommandObject object = new CommandObject() {
			
			@Override
			public void setCommandContext(String arg0, DataHandler arg1)
					throws IOException {
				// TODO Auto-generated method stub
				
			}
		};
	}

}
