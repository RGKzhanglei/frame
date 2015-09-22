package com.example.business.mybusiness.handle;

import com.example.business.mybusiness.model.FirstRequstModel;
import com.example.business.mybusiness.model.FirstResponseModel;
import com.example.business.mybusiness.utils.StringUtil;

public final class BusinessBus {

	private BusinessBus() {
	}
	
	/**
	 * 根据serviceCode 获取response内容
	 * @return
	 */
	public static Object getResponseByServiceCode(String serviceCode, Object request) {
		if (StringUtil.isEmpty(serviceCode) || serviceCode.length() < 2) return null;
		int code = Integer.parseInt(serviceCode.substring(0, 2));
		switch (code) {
		case 11:
			return new FirstResponseModel();
		default:
			return null;
		}
	}
	
	/**
	 * 通过客户端传的serviceCode，得到service Class
	 * @return
	 */
	public static Class<?> getRequestClassByServiceCode(String serviceCode) {
		if (StringUtil.isEmpty(serviceCode) || serviceCode.length() < 2) return null;
		int code = Integer.parseInt(serviceCode.substring(0, 2));
		switch (code) {
		case 11:
			return FirstRequstModel.class;
		default:
			return null;
		}
	}

}
