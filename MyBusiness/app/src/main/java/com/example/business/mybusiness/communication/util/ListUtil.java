package com.example.business.mybusiness.communication.util;

/**
 * Created by zhang.la on 2015/9/11.
 */
public class ListUtil {

    public static byte[] subByteArr(byte[] inputeByte, int startIndex) {
        if (inputeByte == null || inputeByte.length < startIndex) {
            return new byte[0];
        }
        byte[] responseByte = new byte[inputeByte.length - startIndex];
        System.arraycopy(inputeByte, startIndex, responseByte, 0, responseByte.length);
        return responseByte;
    }

    public static byte[] subByteArr(byte[] inputeByte, int startIndex, int length) {
        if (inputeByte == null || inputeByte.length < startIndex) {
            return new byte[0];
        }
        byte[] responseByte = new byte[length];
        System.arraycopy(inputeByte, startIndex, responseByte, 0, length);
        return responseByte;
    }
}
