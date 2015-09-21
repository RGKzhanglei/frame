package com.example.business.mybusiness.communication.serializer;

import java.io.UnsupportedEncodingException;

/**
 *
 * Created by zhang.la on 2015/9/8.
 */
public class SerializeReader {
    private static final String TAG = "SerializeReader";
    private byte[] data = null;
    private int currentIndex = 0;
    private String charsetName = "";

    public SerializeReader(byte[] data) {
        this.data = data;
        this.charsetName = Serialize.charsetName;
    }

    public SerializeReader(String charsetName, byte[] data) {
        this.charsetName = charsetName;
        this.data = data;
    }

    public int readInt(int i) {
        int retInt = 0;
        String strInt = readString(i);
        if (strInt != null && !"".equalsIgnoreCase(strInt)) {
            retInt = Integer.parseInt(strInt);
        }
        return retInt;
    }

    public String readString(int readLength) {
        String string = "";
        try {
            string = new String(data, currentIndex, readLength, this.charsetName);
            string = string.trim();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        currentIndex += readLength;
        return string;
    }

    public byte[] readByteArr(int size) {
        byte[] readByte = new byte[size];
        System.arraycopy(data, currentIndex, readByte, 0, size);
        currentIndex += size;
        return readByte;

    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public String getCharsetName() {
        return charsetName;
    }

    public void setCharsetName(String charsetName) {
        this.charsetName = charsetName;
    }
}
