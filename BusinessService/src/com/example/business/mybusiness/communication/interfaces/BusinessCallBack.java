package com.example.business.mybusiness.communication.interfaces;

import com.example.business.mybusiness.communication.model.ResponseModel;

/**
 * 页面回调
 * Created by zhang.la on 2015/9/15.
 */
public interface BusinessCallBack {
    void onBusinessSuccess(String token, ResponseModel responseModel);
    void onBusinessFail(String token, ResponseModel responseModel);
    void onBusinessCancel(String token, ResponseModel responseModel);
}
