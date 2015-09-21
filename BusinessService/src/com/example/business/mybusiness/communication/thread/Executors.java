package com.example.business.mybusiness.communication.thread;

import com.example.business.mybusiness.communication.connection.ConnectionImp;
import com.example.business.mybusiness.communication.model.BeanModel;
import com.example.business.mybusiness.communication.model.RequestEntity;
import com.example.business.mybusiness.communication.model.ResponseEntity;

/**
 * Created by zhang.la on 2015/9/14.
 */
public class Executors {

    public static ResponseEntity sendService(String token, RequestEntity requestEntity, Class<?> clz) {
        ResponseEntity responseEntity = ResponseEntity.getInstance();

        ExecutorTask task = new ExecutorTask(requestEntity, token);
        byte[] requestBytes = task.serializeRequest();

        // 拼装报文头
        task.buildRequest(requestBytes);

        // 检查是否取消
        if (task.isCanceled()) {
            responseEntity.setResponseState(ResponseEntity.RESPONSE_CANCELED);
            return responseEntity;
        }

        // 建立连接
        if (task.isSuccess()) {
            ConnectionImp connectionImp = new ConnectionImp(task);
            connectionImp.initSocket();
            connectionImp.sendTask();
            connectionImp.readLenght();
            connectionImp.readBody();
            connectionImp.closeConnection();
        }

        if (task.isSuccess()) {
            // 反序列化
            BeanModel responseModel = task.deserializeResponse(clz);
            responseEntity.setReponseBean(responseModel);
        }

        if (!task.isCanceled() && task.isSuccess()) {
            responseEntity.setResponseState(ResponseEntity.RESPONSE_SUCCESSED);
        } else if (task.isCanceled()){
            responseEntity.setResponseState(ResponseEntity.RESPONSE_CANCELED);
        } else {
            responseEntity.setResponseState(ResponseEntity.RESPONSE_FAILED);
        }

        return responseEntity;
    }

}
