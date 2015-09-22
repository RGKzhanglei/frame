package com.example.business.mybusiness.communication.thread;

import android.os.Handler;
import android.os.Message;

import com.example.business.mybusiness.communication.controller.GlobalConfig;
import com.example.business.mybusiness.communication.controller.ResponseCache;
import com.example.business.mybusiness.communication.model.ResponseModel;
import com.example.business.mybusiness.utils.LogUtil;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhang.la on 2015/9/15.
 */
public class ThreadReadData extends Thread{

    private String token = "";
    private Handler handler;
    private Message message;

    public ThreadReadData(String token, Handler handler, Message message) {
        this.token = token;
        this.handler = handler;
        this.message = message;
    }

    @Override
    public void run() {
        int timeOut;
        if (GlobalConfig.DEBUG) {
            timeOut = 1 * 60;
        } else {
            timeOut = 3 * 60;
        }
        HashMap<String, ArrayBlockingQueue<ResponseModel>> responseMap = ThreadPool.getInstance().getNowExeTask();
        if (responseMap.containsKey(token)) {
            ArrayBlockingQueue<ResponseModel> queue = responseMap.get(token);
            try {
                // 从队列中获取数据
                ResponseModel responseModel = queue.poll(timeOut, TimeUnit.SECONDS);
                ResponseCache.getInstance().putResponse(token, responseModel);
                message.obj = token;
                if (handler != null) {
                    handler.sendMessage(message);
                }
            } catch (Exception e) {
                LogUtil.e("ThreadData", e.toString());
            }
        }

        ThreadPool.getInstance().getNowExeTask().remove(token);
    }

}
