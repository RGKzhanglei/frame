package com.example.business.mybusiness.communication.util;

import java.io.UnsupportedEncodingException;

/**
 * Created by zhang.la on 2015/9/11.
 */
public class SerializeUtil {

    /**
     * 字符串变转为byte[]
     * @param value
     * @param charsetName
     * @return
     * @throws UnsupportedEncodingException
     */
    public static byte[] getByteArrByStr(String value, String charsetName) throws UnsupportedEncodingException {
        if (value == null) {
            value = "";
        }
        byte[] temp = null;
        temp = value.getBytes(charsetName);
        return temp;
    }
}
