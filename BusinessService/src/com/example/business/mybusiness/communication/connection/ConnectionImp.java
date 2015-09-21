package com.example.business.mybusiness.communication.connection;

import com.example.business.mybusiness.communication.controller.NetworkConfig;
import com.example.business.mybusiness.communication.enums.TaskFailEnum;
import com.example.business.mybusiness.communication.serializer.SerializeReader;
import com.example.business.mybusiness.communication.thread.ExecutorTask;
import com.example.business.mybusiness.utils.LogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by zhang.la on 2015/9/14.
 */
public class ConnectionImp {
    protected Socket socket = null;
    protected String ip = "";
    protected int port = 0;

    private ExecutorTask task = null;

    public ConnectionImp(ExecutorTask task) {
        this.task = task;
    }

    /**
     * 初始化socket
     */
    public void initSocket() {
        if (task.isCanceled() || !task.isSuccess()) return;
        if (null == socket || !socket.isClosed() || !socket.isConnected()) {
            this.ip = NetworkConfig.IP;
            this.port = NetworkConfig.PORT;
            try {
                this.socket = new Socket(ip, port);
            } catch (IOException e) {
                task.setTaskState(TaskFailEnum.CONNECTION_FAIL);
                e.printStackTrace();
            }
        }

    }

    /**
     * 发送数据
     */
    public void sendTask() {
        if (task.isCanceled() || !task.isSuccess()) return;
        OutputStream outputStream = null;
        byte[] requestByte = task.getRequestByte();
        try {
            outputStream = socket.getOutputStream();
            outputStream.write(requestByte);
        } catch (IOException e) {
            task.setTaskState(TaskFailEnum.SEND_DATA_FAIL);
            e.printStackTrace();
        }
    }

    /**
     * 读取response长度
     */
    public void readLenght() {
        if (task.isCanceled() || !task.isSuccess()) return;
        InputStream inputStream = null;
        int timeOut = NetworkConfig.getReadTimeOut();
        try {
            socket.setSoTimeout(timeOut);
            inputStream = socket.getInputStream();
            int maxLenght = -1;
            byte[] maxLenByte = new byte[8];
            int byteSize = inputStream.read(maxLenByte);
            if (byteSize == 8) {
                SerializeReader serializeReader = new SerializeReader(maxLenByte);
                maxLenght = serializeReader.readInt(8);
                task.setResponseLenght(maxLenght);
            } else {
                task.setTaskState(TaskFailEnum.RECEIVE_LENGTH_FAIL);
            }
        } catch (IOException e) {
            task.setTaskState(TaskFailEnum.RECEIVE_LENGTH_FAIL);
            e.printStackTrace();
        }
    }

    /**
     * 读取内容
     */
    public void readBody() {
        if (task.isCanceled() || !task.isSuccess()) return;
        InputStream inputStream = null;
        byte[] responseByte = null;
        try {
            inputStream = socket.getInputStream();
            responseByte = readData(inputStream, task.getResponseLenght(), 1024);
        } catch (IOException e) {
            task.setTaskState(TaskFailEnum.RECEIVE_BODY_FAIL);
            e.printStackTrace();
        } catch (Exception e) {
            task.setTaskState(TaskFailEnum.RECEIVE_BODY_FAIL);
            e.printStackTrace();
        }
        task.setResponseByte(responseByte);
    }

    public synchronized void closeConnection() {
        if (socket != null) {
            try {
                socket.close();
                socket = null;
                this.ip = "";
                this.port = 0;
            } catch (IOException e) {
                LogUtil.e("closeConnection", "Exception: " + e);
            }
        }
    }

    /**
     * 从输入流，读取指定长度的字节数，如果读到的长度不等于希望的长度，抛异常
     *
     * @param inputStream      输入流
     * @param needLength       需要读取长度
     * @param readBufferLength 每次读取的缓存大小
     * @return
     * @throws Exception
     */
    public static byte[] readData(InputStream inputStream, int needLength,
                                  int readBufferLength) throws Exception {
        byte[] inputeByte = new byte[needLength];
        int totalLength = 0;// 实际读到的总长度
        if (needLength > readBufferLength) {
            int readIndex = 0;
            int readLength = 0;// 每次读到的长度
            while (readIndex < needLength) {
                if (needLength - readIndex > readBufferLength) {
                    readLength = inputStream.read(inputeByte, readIndex,
                            readBufferLength);
                } else {
                    readLength = inputStream.read(inputeByte, readIndex,
                            needLength - readIndex);
                }
                if (readLength == -1) {
                    break;
                } else {
                    readIndex += readLength;
                    totalLength += readLength;
                }
            }
        } else {
            totalLength = inputStream.read(inputeByte);
        }
        if (totalLength != needLength) {
            new RuntimeException("totalLength!=needLength : needLength="
                    + needLength + ",totalLength=" + totalLength);
        }
        return inputeByte;
    }
}
