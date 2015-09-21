package com.example.business.mybusiness.communication.interfaces;

import com.example.business.mybusiness.communication.thread.ThreadTask;

/**
 * 服务层回调
 * Created by zhang.la on 2015/9/15.
 */
public interface SenderCallBack {
    boolean senderSuccess(ThreadTask task, int index);
    boolean senderFail(ThreadTask task, int index);
}
