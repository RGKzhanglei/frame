package com.example.business.mybusiness.communication.controller;

import com.example.business.mybusiness.communication.model.ResponseModel;
import com.example.business.mybusiness.utils.StringUtil;

import java.util.HashMap;

/**
 * Created by zhang.la on 2015/9/15.
 */
public class ResponseCache {
    /**response 结果*/
    private static HashMap<String, ResponseModel> responseMap = new HashMap<>();
    private static Object lock = new Object();

    private static ResponseCache mInstance = new ResponseCache();

    private ResponseCache(){}

    public static ResponseCache getInstance() {
        return mInstance;
    }

    public static void clearToken(String token) {
        if (!StringUtil.isEmpty(token) && responseMap.containsKey(token)) {
            synchronized (lock) {
                responseMap.remove(token);
            }
        }
    }

    public void putResponse(String token, ResponseModel responseModel) {
        synchronized (lock) {
            responseMap.put(token, responseModel);
        }
    }

    public ResponseModel getResponse(String token) {
        synchronized (lock) {
            if (responseMap.containsKey(token)) {
                return responseMap.get(token);
            }
            return null;
        }
    }

}
