package com.example.business.mybusiness.utils;

import android.util.Log;

import com.example.business.mybusiness.communication.controller.GlobalConfig;

/**
 * Created by zhang.la on 2015/9/14.
 */
public class LogUtil{
    private static boolean DEBUG = GlobalConfig.DEBUG;

    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (DEBUG) {
            Log.v(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            Log.e(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (DEBUG) {
            Log.w(tag, msg);
        }
    }
}
