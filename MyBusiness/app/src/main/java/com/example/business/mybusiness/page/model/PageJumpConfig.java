package com.example.business.mybusiness.page.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.business.mybusiness.page.controller.CacheBeanController;

/**
 * 页面跳转控制器
 * Created by zhang.la on 2015/9/16.
 */
public class PageJumpConfig {
    /**activity code*/
    public static final String JUMP_CODE_ACTIVITY = "ACTIVITY";
    /**fragment code*/
    public static final String JUMP_CODE_FRAGMENT = "FRAGMENT";
    /**默认跳转到activity，JUMP_CODE_FRAGMENT时跳转到fragment*/
    private String jumpCode = JUMP_CODE_ACTIVITY;

    /**跳转fragment class*/
    private Class<?> fragmentClass;
    /**跳转fragment 承载id*/
    private int fragmentContentID = -1;

    /**跳转activity class*/
    private Class<?> activityClass;
    /**跳转activity flag*/
    private int intentFlags = -1;
    /**跳转activity resultCode*/
    private int intentResultCode = 0;

    /**下个页面的cacheBean*/
    private CacheBean cacheBean;

    private ExchangePageModel exchangePageModel;

    public PageJumpConfig(CacheBean cacheBean) {
        this.cacheBean = cacheBean;
        exchangePageModel = new ExchangePageModel(cacheBean);
    }

    public CacheBean getCacheBean() {
        return cacheBean;
    }

    public String getJumpCode() {
        return jumpCode;
    }

    public PageJumpConfig setJumpCode(String jumpCode) {
        this.jumpCode = jumpCode;
        return this;
    }

    public Class<?> getFragmentClass() {
        return fragmentClass;
    }

    public PageJumpConfig setFragmentClass(Class<?> fragmentClass) {
        this.fragmentClass = fragmentClass;
        return this;
    }

    public Class<?> getActivityClass() {
        return activityClass;
    }

    public PageJumpConfig setActivityClass(Class<?> activityClass) {
        this.activityClass = activityClass;
        return this;
    }

    public int getIntentFlags() {
        return intentFlags;
    }

    public PageJumpConfig setIntentFlags(int intentFlags) {
        this.intentFlags = intentFlags;
        return this;
    }

    public int getIntentResultCode() {
        return intentResultCode;
    }

    public PageJumpConfig setIntentResultCode(int intentResultCode) {
        this.intentResultCode = intentResultCode;
        return this;
    }

    public int getFragmentContentID() {
        return fragmentContentID;
    }

    public PageJumpConfig setFragmentContentID(int fragmentContentID) {
        this.fragmentContentID = fragmentContentID;
        return this;
    }

    public ExchangePageModel getExchangePageModel() {
        return exchangePageModel;
    }

    /**
     * 切换页面时传值
     */
    public static class ExchangePageModel implements Parcelable {
        protected String cacheBeanKey = "";

        protected ExchangePageModel(Parcel in) {
            cacheBeanKey = in.readString();
        }

        public static final Creator<ExchangePageModel> CREATOR = new Creator<ExchangePageModel>() {
            @Override
            public ExchangePageModel createFromParcel(Parcel in) {
                return new ExchangePageModel(in);
            }

            @Override
            public ExchangePageModel[] newArray(int size) {
                return new ExchangePageModel[size];
            }
        };

        public ExchangePageModel(CacheBean cacheBean) {
            cacheBeanKey = cacheBean.hashCode() + "+" + cacheBean.getClass().getName();
            CacheBeanController.saveCacheBean(cacheBean);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(cacheBeanKey);
        }

        public String getCacheBeanKey() {
            return cacheBeanKey;
        }

        public void setCacheBeanKey(String cacheBeanKey) {
            this.cacheBeanKey = cacheBeanKey;
        }

        public CacheBean getCacheBean() {
            return CacheBeanController.getCacheBean(cacheBeanKey);
        }
    }
}
