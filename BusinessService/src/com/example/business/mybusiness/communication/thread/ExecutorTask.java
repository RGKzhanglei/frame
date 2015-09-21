package com.example.business.mybusiness.communication.thread;

import com.example.business.mybusiness.communication.enums.TaskFailEnum;
import com.example.business.mybusiness.communication.model.BeanModel;
import com.example.business.mybusiness.communication.model.RequestDataBean;
import com.example.business.mybusiness.communication.model.RequestEntity;
import com.example.business.mybusiness.communication.serializer.Serialize;
import com.example.business.mybusiness.communication.serializer.SerializeWriter;
import com.example.business.mybusiness.utils.LogUtil;

/**
 * Created by zhang.la on 2015/9/14.
 */
public class ExecutorTask {
    /**request data model*/
    private RequestDataBean requestDataBean;

    private TaskFailEnum taskState = TaskFailEnum.NO_FAIL;

    //请求Entity
    private RequestEntity requestEntity;

    private String token = "";

    private byte[] requestByte = null;
    private byte[] responseByte = null;

    private int responseLenght;

    public ExecutorTask(RequestEntity requestEntity, String token) {
        this.requestEntity = requestEntity;
        this.token = token;
    }

    public RequestDataBean getRequestDataBean() {
        return requestDataBean;
    }

    public void setRequestDataBean(RequestDataBean requestDataBean) {
        this.requestDataBean = requestDataBean;
    }

    public byte[] getResponseByte() {
        return responseByte;
    }

    public void setResponseByte(byte[] responseByte) {
        this.responseByte = responseByte;
    }

    public boolean isSuccess() {
        return taskState == TaskFailEnum.NO_FAIL;
    }

    public void setTaskState(TaskFailEnum taskState) {
        this.taskState = taskState;
    }

    public byte[] getRequestByte() {
        return requestByte;
    }

    /**
     * 序列化request
     * @return
     */
    public byte[] serializeRequest() {
        byte[] requestByte = null;
        if (null != requestEntity) {
            try {
                requestByte = Serialize.serialize(requestEntity.getRequestBean(), Serialize.charsetName_UTF8);
            } catch (Exception e) {
                setTaskState(TaskFailEnum.SERIALIZE_REQUEST_FAIL);
                LogUtil.e("ExecutorTask", "序列化出错 e=" + e.toString());
            }
        }
        this.requestByte = requestByte;
        return requestByte;
    }

    /**
     * 反序列化 response
     * @param clz
     * @return
     */
    public BeanModel deserializeResponse(Class<?> clz) {
        BeanModel beanModel = null;
        try {
            beanModel = (BeanModel) Serialize.deserialize(responseByte, clz, Serialize.charsetName_UTF8);
        } catch (Exception e) {
            LogUtil.e("ExecutorTask", "反序列化出错 e=" + e.toString());
            setTaskState(TaskFailEnum.DESERIALIZE_RESPONSE_FAIL);
        }
        return beanModel;
    }

    /**
     * 拼装报文头
     * @param bodyBytes
     */
    public void buildRequest(byte[] bodyBytes) {
        // TODO
        byte[] headerBytes = buildRequestHeader();
        this.requestByte =  combineRequest(headerBytes, bodyBytes);
    }

    private byte[] buildRequestHeader() {
        SerializeWriter writer = new SerializeWriter(12, Serialize.charsetName_UTF8);
        writer.writeInt(8, 4);
        writer.writeString(requestEntity.getServiceCode(), 8);
        return writer.toByteArr();
    }

    private byte[] combineRequest(byte[] header, byte[] body) {
        int headerLen = header.length;
        int bodyLen = body.length;
        SerializeWriter writer = new SerializeWriter(headerLen + bodyLen + 8, Serialize.charsetName_UTF8);
        writer.writeInt(bodyLen, 8);//前八位byte[]长度
        writer.write(header);
        writer.write(body);
        return writer.toByteArr();
    }

    public boolean isCanceled() {
        return ThreadStateManager.isThreadCanceled(token);
    }

    public void setResponseLenght(int maxLenght) {
        this.responseLenght = maxLenght;
    }

    public int getResponseLenght() {
        return responseLenght;
    }

}
