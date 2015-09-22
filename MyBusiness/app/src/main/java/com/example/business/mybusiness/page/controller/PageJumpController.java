package com.example.business.mybusiness.page.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.business.mybusiness.page.BaseActivity;
import com.example.business.mybusiness.page.BaseFragment;
import com.example.business.mybusiness.page.model.PageJumpConfig;
import com.example.business.mybusiness.page.model.ServiceConfig;
import com.example.business.mybusiness.utils.LogUtil;
import com.example.business.mybusiness.utils.StringUtil;

/**
 * 页面跳转，发送服务控制器
 * Created by zhang.la on 2015/9/16.
 */
public final class PageJumpController {
    /**存储cacheBean Key*/
    public static final String JUMP_PAGE_CACHEBEAN = "JUMP_PAGE_CACHEBEAN";
    /**发送服务，跳转下个下面，传递的model*/
    public static final String JUMP_PAGE_SERVICE_MODEL = "JUMP_PAGE_SERVICE_MODEL";

    private PageJumpController() {
    }

    /**
     * {@link PageJumpConfig}
     * <P>go Activity: {
     * <ul>
     *     <li>jumpConfig.JumpCode();// {@link PageJumpConfig.JUMP_CODE_ACTIVITY } || {@link PageJumpConfig.JUMP_CODE_FRAGMENT}
     *     <li>jumpConfig.setActivityClass(Class activityClass);// 跳转Activity.class
     *     <li>jumpConfig.setIntentFlags();// intent的加载方式
     *     <li>jumpConfig.setIntentResultCode();// startActivityForResult();
     * <P>}
     * </ul>
     * <P>go Fragment: {
     * <ul>
     *     <li>jumpConfig.JumpCode();// {@link PageJumpConfig.JUMP_CODE_ACTIVITY } || {@link PageJumpConfig.JUMP_CODE_FRAGMENT}
     *     <li>jumpConfig.setFragmentClass(Class fragmentClass);// 跳转Fragment.class
     *     <li>jumpConfig.setFragmentContentID();// 承载fragment的contentID
     * <P>}
     * </ul>
     * @param jumpConfig
     * @param currentFragment
     * @param currentActivity
     */
    public static void goNextPage(@NonNull PageJumpConfig jumpConfig, BaseFragment currentFragment, @NonNull BaseActivity currentActivity) {
        PageJumpController.goNextPage(jumpConfig, currentFragment, currentActivity, null);
    }

    public static void goNextPage(@NonNull PageJumpConfig jumpConfig, BaseFragment currentFragment, @NonNull BaseActivity currentActivity, ServiceConfig.ExchangeServiceModel exchangeServiceModel) {
        Bundle bundle = new Bundle();
        if (null != jumpConfig.getCacheBean()) {
            bundle.putParcelable(JUMP_PAGE_CACHEBEAN, jumpConfig.getExchangePageModel());
        }
        if (null != exchangeServiceModel) {
            bundle.putParcelable(JUMP_PAGE_SERVICE_MODEL, exchangeServiceModel);
        }

        if (null == currentActivity) {
            LogUtil.e("PageJumpController", "goNextPage()  param (currentActivity == null)");
            return;
        }

        if (jumpConfig.getJumpCode() == PageJumpConfig.JUMP_CODE_ACTIVITY) {
            // activity
            if (jumpConfig.getActivityClass() == null) {
                return;
            }
            Intent intent = new Intent(currentActivity, jumpConfig.getActivityClass());
            intent.putExtras(bundle);
            if (jumpConfig.getIntentFlags() != -1) {
                intent.setFlags(jumpConfig.getIntentFlags());
            }

            if (jumpConfig.getIntentResultCode() == 0) {
                currentActivity.startActivity(intent);
            } else {
                currentActivity.startActivityForResult(intent, jumpConfig.getIntentResultCode());
            }

        } else {
            // fragment
            if (null != jumpConfig.getFragmentClass()) {
                BaseFragment baseFragment = (BaseFragment) Fragment.instantiate(currentActivity, jumpConfig.getFragmentClass().getName());
                baseFragment.setArguments(bundle);
                if (null != currentFragment) {
                    baseFragment.setTargetFragment(currentFragment, -1);
                }
                String tag = baseFragment.getTagName();
                if (StringUtil.isEmpty(tag)) {
                    LogUtil.e("PageJumpController", "jump fragment tag is empty");
                } else {
                    if (jumpConfig.getFragmentContentID() > 0) {
                        FragmentController.addFragment(currentActivity.getSupportFragmentManager(), baseFragment, jumpConfig.getFragmentContentID(), tag);
                    } else {
                        FragmentController.addFragment(currentActivity.getSupportFragmentManager(), baseFragment, tag);
                    }
                }
            }
        }
    }
}
