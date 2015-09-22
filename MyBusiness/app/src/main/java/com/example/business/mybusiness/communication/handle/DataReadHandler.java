package com.example.business.mybusiness.communication.handle;

import android.os.Handler;
import android.os.Message;

import com.example.business.mybusiness.communication.controller.ResponseCache;
import com.example.business.mybusiness.communication.interfaces.BusinessCallBack;
import com.example.business.mybusiness.communication.model.ResponseEntity;
import com.example.business.mybusiness.communication.model.ResponseModel;

/**
 * Created by zhang.la on 2015/9/15.
 */
public class DataReadHandler extends Handler {
    private BusinessCallBack callBack;

    public DataReadHandler(BusinessCallBack callBack) {
        super();
        this.callBack = callBack;
    }

    @Override
    public void handleMessage(Message msg) {
        String token = (String) msg.obj;
        ResponseModel responseModel = ResponseCache.getInstance().getResponse(token);
        processResult(token, responseModel);
        ResponseCache.getInstance().clearToken(token);
    }

    private void processResult(String token, ResponseModel responseModel) {
        if (callBack != null) {
            if (null == responseModel) {
                ResponseModel responseFail = new ResponseModel();
                responseFail.setToken(token);
                responseFail.setResponseState(ResponseEntity.RESPONSE_FAILED);
                callBack.onBusinessFail(token, responseFail);
            } else {
                if (responseModel.getResponseState() == ResponseEntity.RESPONSE_SUCCESSED) {
                    callBack.onBusinessSuccess(token, responseModel);
                } else if (responseModel.getResponseState() == ResponseEntity.RESPONSE_FAILED) {
                    callBack.onBusinessFail(token, responseModel);
                } else {
                    callBack.onBusinessCancel(token, responseModel);
                }
            }
        }
    }
}
