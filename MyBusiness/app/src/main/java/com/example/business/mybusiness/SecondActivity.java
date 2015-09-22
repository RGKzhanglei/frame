package com.example.business.mybusiness;

import android.os.Bundle;
import android.widget.Toast;

import com.example.business.mybusiness.communication.interfaces.BusinessCallBack;
import com.example.business.mybusiness.communication.model.ResponseModel;
import com.example.business.mybusiness.page.cachebean.SecondCacheBean;
import com.example.business.mybusiness.page.BaseActivity;
import com.example.business.mybusiness.utils.LogUtil;
import com.example.business.mybusiness.view.LoadingLayout;

/**
 * Created by zhang.la on 2015/9/22.
 */
public class SecondActivity extends BaseActivity {
    private SecondCacheBean cacheBean;

    private LoadingLayout loadingLayout;

    private BusinessCallBack mCallBack = new BusinessCallBack() {
        @Override
        public void onBusinessSuccess(String token, ResponseModel responseModel) {
            Toast.makeText(SecondActivity.this, "SUCESS ====== SecondActivity", Toast.LENGTH_SHORT).show();
            LogUtil.e(TAG, "SUCESS ====== SecondActivity");
        }

        @Override
        public void onBusinessFail(String token, ResponseModel responseModel) {
            Toast.makeText(SecondActivity.this, "Fail ====== SecondActivity", Toast.LENGTH_SHORT).show();
            LogUtil.e(TAG, "Fail ====== SecondActivity");
        }

        @Override
        public void onBusinessCancel(String token, ResponseModel responseModel) {
            Toast.makeText(SecondActivity.this, "Cancel ====== SecondActivity", Toast.LENGTH_SHORT).show();
            LogUtil.e(TAG, "Cancel ====== SecondActivity");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.second_activity_layout);
        loadingLayout = (LoadingLayout) findViewById(R.id.loading_layout);
        loadingLayout.setBusinessCallBack(mCallBack);

        super.onCreate(savedInstanceState);

        if (mViewBean != null) {
            cacheBean = (SecondCacheBean) mViewBean;
            LogUtil.e(TAG, "cacheBean is not null cacheBean.token=" + cacheBean.token);
        } else {
            cacheBean = new SecondCacheBean();
            LogUtil.e(TAG, "cacheBean is null");
        }

    }

    @Override
    protected BusinessCallBack getBusinessCallBack() {
        return loadingLayout;
    }
}
