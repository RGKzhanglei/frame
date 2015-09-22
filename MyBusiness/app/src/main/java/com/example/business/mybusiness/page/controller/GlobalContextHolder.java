package com.example.business.mybusiness.page.controller;

import android.content.Context;

/**
 * Created by zhang.la on 2015/9/17.
 */
public class GlobalContextHolder {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        GlobalContextHolder.context = context;
    }
}
