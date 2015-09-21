package com.example.business.mybusiness.communication.thread;


import com.example.business.mybusiness.communication.controller.BusinessController;
import com.example.business.mybusiness.communication.enums.ThreadStateEnum;
import com.example.business.mybusiness.communication.interfaces.SenderCallBack;
import com.example.business.mybusiness.communication.model.RequestEntity;
import com.example.business.mybusiness.communication.model.ResponseEntity;
import com.example.business.mybusiness.communication.model.ResponseModel;
import com.example.business.mybusiness.utils.LogUtil;
import com.example.business.mybusiness.utils.StringUtil;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by zhang.la on 2015/9/8.
 */
public class ThreadTask implements Runnable {
    private String token = "";
    private RequestEntity[] requestEntities = null;
    private ResponseEntity[] responseEntities = null;

    private SenderCallBack senderCallBack;

    public ThreadTask(String token, SenderCallBack senderCallBack, RequestEntity... requestEntities) {
        this.token = token;
        this.requestEntities = requestEntities;
        this.senderCallBack = senderCallBack;
        if (null == this.senderCallBack) {
            this.senderCallBack = new SenderCallBack() {
                @Override
                public boolean senderSuccess(ThreadTask task, int index) {
                    return true;
                }

                @Override
                public boolean senderFail(ThreadTask task, int index) {
                    return true;
                }
            };
        }
    }

    @Override
    public void run() {
        try {
//            Looper looper = Looper.myLooper();
//            if (looper == null) {
//                Looper.prepare();
//            }

            if (!StringUtil.isEmpty(token)) {
                //设置线程状态为开始
                ThreadStateManager.setThreadState(token, ThreadStateEnum.activite);
            }

            //发送request
            sendRequest();

            if (!StringUtil.isEmpty(token)) {
                //清除状态
                ThreadStateManager.removeThreadState(token);
            }

        } catch (Exception e) {
            LogUtil.e("ThreadTask", e.toString());
        }
    }

    /**
     * 发送request
     */
    private void sendRequest() {
        responseEntities = new ResponseEntity[requestEntities.length];
        // sender call back
        for (int index = 0; index < requestEntities.length; index++) {
            // 发送服务
            ResponseEntity responseEntity = BusinessController.getInstance().executeData(token, requestEntities[index]);
            responseEntities[index] = responseEntity;
            if (responseEntity.getResponseState() == ResponseEntity.RESPONSE_SUCCESSED) {
                if (null != senderCallBack) {
                    senderCallBack.senderSuccess(this, index);
                }
            } else if (responseEntity.getResponseState() == ResponseEntity.RESPONSE_CANCELED) {
                if (null != senderCallBack) {
                    senderCallBack.senderFail(this, index);
                }
            } else {
                if (null != senderCallBack) {
                    senderCallBack.senderFail(this, index);
                }
            }
        }
        // business call back
        if (responseEntities != null && responseEntities.length > 0) {
            ArrayBlockingQueue<ResponseModel> queue = ThreadPool.getInstance().getNowExeTask().get(token);
            try {
                ResponseModel responseModel = new ResponseModel();
                responseModel.setToken(token);
                responseModel.setResponseState(responseEntities[responseEntities.length-1].getResponseState());
                queue.put(responseModel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void receiveResponseFail() {

    }

    private void receiveResponseSuccess() {

    }

    public String getToken() {
        return token;
    }

    public RequestEntity[] getRequestEntities() {
        return requestEntities;
    }

    public ResponseEntity[] getResponseEntities() {
        return responseEntities;
    }
}
