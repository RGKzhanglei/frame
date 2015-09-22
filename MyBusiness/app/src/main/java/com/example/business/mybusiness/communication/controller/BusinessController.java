package com.example.business.mybusiness.communication.controller;

import com.example.business.mybusiness.communication.BusinessBus;
import com.example.business.mybusiness.communication.enums.ThreadStateEnum;
import com.example.business.mybusiness.communication.model.RequestEntity;
import com.example.business.mybusiness.communication.model.ResponseEntity;
import com.example.business.mybusiness.communication.thread.Executors;
import com.example.business.mybusiness.communication.thread.ThreadStateManager;
import com.example.business.mybusiness.utils.StringUtil;

/**
 * Created by zhang.la on 2015/9/14.
 */
public class BusinessController {
    private static BusinessController ourInstance = new BusinessController();

    public static BusinessController getInstance() {
        return ourInstance;
    }

    private BusinessController() {
    }

    public ResponseEntity executeData(String token, RequestEntity requestEntity) {
        ResponseEntity responseEntity = ResponseEntity.getInstance();
        try {
            responseEntity = executeCommand(token,requestEntity);
        } catch (Exception e) {
            responseEntity.setResponseState(ResponseEntity.RESPONSE_FAILED);
        }
        return responseEntity;
    }

    private ResponseEntity executeCommand(String token, RequestEntity requestEntity) throws Exception{
        ResponseEntity responseEntity = ResponseEntity.getInstance();
        if (ThreadStateManager.isThreadCanceled(token)) {
            responseEntity.setResponseState(ResponseEntity.RESPONSE_CANCELED);
        } else {
            responseEntity = Executors.sendService(token, requestEntity, getResponseClass(requestEntity.getServiceCode()));
            ThreadStateManager.setThreadState(token, ThreadStateEnum.finish);
        }
        return responseEntity;
    }

    private Class<?> getResponseClass(String serviceCode) {
        if (StringUtil.isEmpty(serviceCode)) return null;
        return BusinessBus.getResponseClass(serviceCode);
    }
}
