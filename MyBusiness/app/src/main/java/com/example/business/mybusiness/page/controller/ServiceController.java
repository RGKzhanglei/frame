package com.example.business.mybusiness.page.controller;

import android.os.Message;
import android.support.annotation.NonNull;

import com.example.business.mybusiness.communication.handle.DataReadHandler;
import com.example.business.mybusiness.communication.interfaces.BusinessCallBack;
import com.example.business.mybusiness.communication.model.SendResultModel;
import com.example.business.mybusiness.communication.thread.ThreadPool;
import com.example.business.mybusiness.page.model.ServiceConfig;
import com.example.business.mybusiness.view.LoadingLayout;

/**
 * Created by zhang.la on 2015/9/16.
 */
public final class ServiceController {
    private ServiceController() {
    }

    public static void getServiceResult(@NonNull ServiceConfig serviceConfig) {
        if (serviceConfig != null) {
            SendResultModel resultModel = serviceConfig.getResultModel();
            BusinessCallBack businessCallBack = serviceConfig.getBusinessCallBack();
            if (businessCallBack != null && businessCallBack instanceof LoadingLayout) {
                LoadingLayout loadingLayout = (LoadingLayout) businessCallBack;
                loadingLayout.showProgress();
            }
            DataReadHandler readHandler = new DataReadHandler(businessCallBack);
            ThreadPool.getInstance().getResponseModel(resultModel.getToken(), readHandler, Message.obtain());
        }
    }
}
