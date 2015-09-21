package com.example.business.mybusiness.communication.serializer;

import com.example.business.mybusiness.communication.util.SerializeUtil;

import java.io.UnsupportedEncodingException;

/**
 * byte[] 生成器
 * Created by zhang.la on 2015/9/9.
 */
public class SerializeWriter {

    private byte[] data = null;
    private int count;
    private String charsetName = "";

    public SerializeWriter(int initSize) {
        if (initSize <= 0) {
            throw new IllegalArgumentException("Negative initial size: " + initSize);
        }
        data = new byte[initSize];
        this.charsetName = Serialize.charsetName;
    }

    public SerializeWriter(int initSize, String charsetName) {
        if (initSize <= 0) {
            throw new IllegalArgumentException("Negative initial size: " + initSize);
        }
        data = new byte[initSize];
        this.charsetName = charsetName;
    }

    /**
     * string变为byte后保存到data中
     * @param value
     * @param length
     */
    public void writeString(String value, int length) {
        int newLength = count + length;
        if (newLength > data.length) {
            expandCapacity(newLength);
        }

        byte[] strByte = null;
        try {
            strByte = SerializeUtil.getByteArrByStr(value, charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (strByte.length < length) {
            System.arraycopy(strByte, 0, data, count, strByte.length);
            count = count + strByte.length;
            privateWriteBlankSpace(length - strByte.length);
            count = count + (length - strByte.length);
        } else if (strByte.length >= length) {
            System.arraycopy(strByte, 0, data, count, length);
            count = newLength;
        }
    }

    /**
     * int变为byte后保存到data中
     * @param value
     * @param length
     */
    public void writeInt(int value, int length) {
        writeString(Integer.toString(value), length);
    }

    /**
     * 保存数组到data中
     * @param b
     */
    public void write(byte b[]) {
        write(b, 0, b.length);
    }

    /**
     * 将数组保存到data数组中
     * @param b
     * @param off
     * @param len
     */
    public void write(byte b[], int off, int len) {
        if (off < 0 || off > b.length || len < 0 || off + len > b.length || off + len < 0) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return;
        }

        int newcount = count + len;
        if (newcount > data.length) {
            expandCapacity(newcount);
        }
        System.arraycopy(b, off, data, count, len);
        count = newcount;
    }

    /**
     * 内部调用，count不会变，也不会扩充数组
     * 插入length个空格
     * @param length
     */
    private void privateWriteBlankSpace(int length) {
        int newcount = count + length;
        if (newcount > data.length) {
            throw new IllegalArgumentException("Out Of Buffer Size");
        }
        for (int i = 0; i < length; i++) {
            data[count + i] = (byte) 32;
        }
    }

    /**
     * 扩展数组
     * @param miniSize
     */
    private void expandCapacity(int miniSize) {
        int newSize = (data.length * 3) / 2 + 1;
        if (newSize < miniSize) {
            newSize = miniSize;
        }
        byte[] tempData = new byte[newSize];
        System.arraycopy(data, 0, tempData, 0, count);
        data = tempData;
    }

    public String getCharsetName() {
        return charsetName;
    }

    public void setCharsetName(String charsetName) {
        this.charsetName = charsetName;
    }

    public byte[] toByteArr() {
        byte[] newValue = new byte[count];
        System.arraycopy(data, 0, newValue, 0, count);
        return newValue;
    }
}
