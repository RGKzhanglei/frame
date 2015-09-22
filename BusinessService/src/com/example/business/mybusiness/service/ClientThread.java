package com.example.business.mybusiness.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.example.business.mybusiness.communication.serializer.Serialize;
import com.example.business.mybusiness.communication.serializer.SerializeReader;
import com.example.business.mybusiness.communication.serializer.SerializeWriter;
import com.example.business.mybusiness.handle.BusinessBus;
import com.example.business.mybusiness.model.FirstRequstModel;
import com.example.business.mybusiness.model.FirstResponseModel;
import com.example.business.mybusiness.utils.LogUtil;
import com.example.business.mybusiness.utils.StringUtil;

public class ClientThread extends Thread {

	private Socket socket;
	
	private String serviceCode = "";
	
	private Object request;
	
	public ClientThread(Socket socket) {
		super();
		this.socket = socket;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		LogUtil.log("server get socket");
		
		try {
			readInputStream(socket.getInputStream());
			
			LogUtil.log("server read over, start write");
			
			writeOutputStream(socket);
			
			LogUtil.log("server write over");
			
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 返回结果给客户端
	 * @param socket
	 */
	private void writeOutputStream(Socket socket) {
		// TODO Auto-generated method stub
		byte[] body = Serialize.serialize(getResponseByServiceCode(), Serialize.charsetName_UTF8);
		byte[] responseByte = getResponseByteWithHeader(body);
		try {
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(responseByte);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取response byte
	 * @param body
	 * @return
	 */
	private byte[] getResponseByteWithHeader(byte[] body) {
		SerializeWriter writer = new SerializeWriter(body.length, Serialize.charsetName_UTF8);
		writer.writeInt(body.length + 8, 8);
		writer.write(body);
		return writer.toByteArr();
	}
	
	/**
	 * 根据serviceCode 获取response内容
	 * @return
	 */
	private Object getResponseByServiceCode() {
		if (StringUtil.isEmpty(serviceCode) || serviceCode.length() < 2) return null;
		return BusinessBus.getResponseByServiceCode(serviceCode, request);
	}
	
	/**
	 * 通过客户端传的serviceCode，得到service Class
	 * @return
	 */
	private Class<?> getRequestClassByServiceCode() {
		if (StringUtil.isEmpty(serviceCode) || serviceCode.length() < 2) return null;
		return BusinessBus.getRequestClassByServiceCode(serviceCode);
	}

	/**
	 * 读取request 内容
	 * @param inputStream
	 */
	private void readInputStream(InputStream inputStream) {
		int maxLenght = -1;
		byte[] maxLenByte = new byte[8];
		int byteSize;
		try {
			byteSize = inputStream.read(maxLenByte);
			// 读取header
			readRequestHeader(inputStream);
			
			byte[] requestBodyByte = null;
			if (byteSize == 8) {
				SerializeReader serializeReader = new SerializeReader(maxLenByte);
				maxLenght = serializeReader.readInt(8);
				requestBodyByte = readData(inputStream, maxLenght, 1024);
				request = Serialize.deserialize(requestBodyByte, getRequestClassByServiceCode(), Serialize.charsetName_UTF8);
				LogUtil.log("read request success object = " +  request.toString() );
			} else {
				LogUtil.log("Serialize wrong lenght");
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private int readRequestHeader(InputStream inputStream) {
		int readLength = 0;
		byte[] headerLenByte = new byte[4];
		try {
			readLength += inputStream.read(headerLenByte);
			SerializeReader serializeReader = new SerializeReader(headerLenByte);
			int headerLen = serializeReader.readInt(4);
			byte [] header = new byte[headerLen];
			readLength += inputStream.read(header);
			SerializeReader headerReader = new SerializeReader(header);
			this.serviceCode = headerReader.readString(headerLen);
 		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return readLength;
		
	}
	
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
