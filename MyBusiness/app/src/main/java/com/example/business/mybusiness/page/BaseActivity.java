package com.example.business.mybusiness.page;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.View;

import com.example.business.mybusiness.communication.interfaces.BusinessCallBack;
import com.example.business.mybusiness.communication.model.ResponseModel;
import com.example.business.mybusiness.page.controller.CacheBeanController;
import com.example.business.mybusiness.page.controller.PageJumpController;
import com.example.business.mybusiness.page.controller.ServiceController;
import com.example.business.mybusiness.page.model.CacheBean;
import com.example.business.mybusiness.page.model.PageJumpConfig;
import com.example.business.mybusiness.page.model.ServiceConfig;

/**
 * Created by zhang.la on 2015/9/15.
 */
public class BaseActivity extends FragmentActivity {
    protected CacheBean mViewBean;
    protected String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getIntent() != null && getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            PageJumpConfig.ExchangePageModel exchangePageModel = bundle.getParcelable(PageJumpController.JUMP_PAGE_CACHEBEAN);
            ServiceConfig.ExchangeServiceModel exchangeServiceModel = bundle.getParcelable(PageJumpController.JUMP_PAGE_SERVICE_MODEL);
            if (null != exchangePageModel) {
                mViewBean = exchangePageModel.getCacheBean();
            }
            if (null != exchangeServiceModel) {
                ServiceConfig serviceConfig = new ServiceConfig(exchangeServiceModel);
                serviceConfig.setBusinessCallBack(getBusinessCallBack());
                ServiceController.getServiceResult(serviceConfig);
            }
        }
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onDestroy() {
        CacheBeanController.removeCacheBean(mViewBean);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected BusinessCallBack getBusinessCallBack() {
        return new BusinessCallBack() {
            @Override
            public void onBusinessSuccess(String token, ResponseModel responseModel) {

            }

            @Override
            public void onBusinessFail(String token, ResponseModel responseModel) {

            }

            @Override
            public void onBusinessCancel(String token, ResponseModel responseModel) {

            }
        };
    }
}
