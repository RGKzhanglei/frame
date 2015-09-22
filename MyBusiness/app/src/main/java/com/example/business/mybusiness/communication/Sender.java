package com.example.business.mybusiness.communication;

import com.example.business.mybusiness.communication.interfaces.SenderCallBack;
import com.example.business.mybusiness.communication.model.RequestEntity;
import com.example.business.mybusiness.communication.model.SendResultModel;
import com.example.business.mybusiness.communication.thread.ThreadPool;
import com.example.business.mybusiness.communication.thread.ThreadTask;

/**
 * sender service base class
 * Created by zhang.la on 2015/9/15.
 */
public class Sender {

    protected void sender(String token, SenderCallBack senderCallBack, RequestEntity... requestEntitys) {
        ThreadTask threadTask = new ThreadTask(token, senderCallBack, requestEntitys);
        ThreadPool.getInstance().executeTask(threadTask);
    }

    protected SendResultModel createResultModel(String methodName) {
        SendResultModel resultModel = new SendResultModel();
        String token = this.getClass().getName();
        token += "+" + methodName + "+" + System.currentTimeMillis();
        resultModel.setToken(token);
        return resultModel;
    }
}
