package com.example.business.mybusiness.communication;

import com.example.business.mybusiness.model.FirstResponseModel;
import com.example.business.mybusiness.utils.StringUtil;

/**
 * 获取response Class<?>总线
 * Created by zhang.la on 2015/9/22.
 */
public final class BusinessBus {
    private BusinessBus() {
    }

    public static Class<?> getResponseClass(String serviceCode) {
        if (StringUtil.isEmpty(serviceCode)) return null;
        int code = Integer.parseInt(serviceCode.substring(0, 2));
        Class<?> responseClass = null;
        switch (code) {
            case 11:
                responseClass = FirstResponseModel.class;
                break;
            default:
                break;
        }
        return responseClass;
    }
}
