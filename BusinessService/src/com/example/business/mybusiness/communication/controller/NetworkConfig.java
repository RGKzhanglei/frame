package com.example.business.mybusiness.communication.controller;

/**
 * Created by zhang.la on 2015/9/14.
 */
public class NetworkConfig {
    public static final String IP = "10.32.184.14";
    public static final int PORT = 5389;

    public static int getReadTimeOut() {
        return 300 * 1000;
    }

}
