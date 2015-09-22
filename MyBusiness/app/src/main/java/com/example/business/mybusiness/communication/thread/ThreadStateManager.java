package com.example.business.mybusiness.communication.thread;

import com.example.business.mybusiness.communication.enums.ThreadStateEnum;
import com.example.business.mybusiness.utils.StringUtil;

import java.util.HashMap;

/**
 * Created by zhang.la on 2015/9/14.
 */
public class ThreadStateManager {
    private static HashMap<String, ThreadStateEnum> threadStateMap = new HashMap<>();

    public static void setThreadState(String token, ThreadStateEnum stateEnum) {
        if (StringUtil.isEmpty(token) || null == stateEnum) return;
        if (stateEnum == ThreadStateEnum.cancel) {

            removeThreadState(token);
        } else {
            synchronized (threadStateMap) {
                threadStateMap.put(token, stateEnum);
            }
        }
    }

    public static void removeThreadState(String token) {
        if (StringUtil.isEmpty(token)) return;
        synchronized (threadStateMap) {
            threadStateMap.remove(token);
        }
    }

    public static boolean isThreadCanceled(String token) {
        if (StringUtil.isEmpty(token)) return true;
        if (!threadStateMap.containsKey(token)) {
            return true;
        } else {
            synchronized (threadStateMap) {
                return (threadStateMap.get(token) == ThreadStateEnum.cancel);
            }
        }
    }

}
