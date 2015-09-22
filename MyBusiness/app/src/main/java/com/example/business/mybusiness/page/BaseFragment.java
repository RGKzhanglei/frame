package com.example.business.mybusiness.page;

import android.os.Bundle;
import android.support.v4.app.Fragment;

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
public class BaseFragment extends Fragment {
    protected CacheBean mViewBean;
    protected String TAG = this.getClass().getSimpleName();

    public BaseFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getArguments() != null) {
            Bundle bundle = getArguments();
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

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        CacheBeanController.removeCacheBean(mViewBean);
        super.onDestroy();
    }

    /**
     * 获取页面className
     * @return
     */
    public String getTagName() {
        return BaseFragment.this.getClass().getName();
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
